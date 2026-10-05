use std::path::PathBuf;
use std::process::Stdio;
use std::sync::atomic::Ordering;
use std::sync::Arc;

use tauri::State;
use tokio::io::{AsyncReadExt, BufReader};
use tokio::process::Command;

use crate::events::{emit_file_log, emit_log};
use crate::log_parser::{
    extract_percent, is_ignored_rsync_line, is_itemize_line, is_progress_line,
};
use crate::models::{SyncConfig, SyncResult};
use crate::paths::{
    bundled_bash_path,
    bundled_rsync_path,
    bundled_rsync_resource_dir,
    normalize_windows_path,
};
use crate::state::SyncState;
#[cfg(windows)]
use std::os::windows::process::CommandExt;
// ============================================================
// 常量
// ============================================================

/// MSYS bash 启动脚本：
/// - $0 = swan-trace-sync
/// - $1 = Windows 格式的 rsync.exe 路径
/// - $2 = Windows 格式的本地目录
/// - $3.. = 传给 rsync 的参数
///
/// 用 `cygpath` 做路径转换；参数通过 `"$@"` 传递，不拼接动态命令字符串。
const BASH_SCRIPT: &str = r#"
set -e

rsync_exe="$(cygpath -u "$1")" || exit 90
local_path="$(cygpath -u "$2")" || exit 91

if [ ! -f "$rsync_exe" ]; then
    echo "找不到内置 Rsync：$rsync_exe"
    exit 92
fi

shift 2
exec "$rsync_exe" "$@" "${local_path%/}/"
"#;

const READ_BUFFER_SIZE: usize = 8192;
const MAX_LINE_LENGTH: usize = 65536;

// ============================================================
// 命令入口
// ============================================================

#[tauri::command]
pub async fn start_sync(
    app: tauri::AppHandle,
    state: State<'_, Arc<SyncState>>,
    config: SyncConfig,
) -> Result<SyncResult, String> {
    // 防重复启动
    if state.running.swap(1, Ordering::SeqCst) != 0 {
        return Err("已有同步任务正在运行".into());
    }

    // 参数校验（失败自动清 running 标记）
    if let Err(error) = validate_config(&config) {
        state.running.store(0, Ordering::SeqCst);
        return Err(error);
    }

    // 复位取消标记
    *state.cancelled.lock().await = false;

    // 准备本地目录
    let local_path = PathBuf::from(config.local_path.trim());
    if let Err(error) = std::fs::create_dir_all(&local_path) {
        state.running.store(0, Ordering::SeqCst);
        return Err(format!(
            "无法创建本地目录 {}：{}",
            local_path.display(),
            error
        ));
    }

    // 组装远程地址 + 参数
    let remote = build_remote(&config);
    let rsync_args = build_rsync_args(&config, &remote);

    emit_log(&app, format!("远程地址：{remote}"), "normal", 0);
    emit_log(
        &app,
        format!("本地目录：{}", local_path.display()),
        "normal",
        0,
    );

    // 获取统一的内置资源目录
let rsync_dir = match bundled_rsync_resource_dir(&app) {
    Ok(path) => path,
    Err(error) => {
        state.running.store(0, Ordering::SeqCst);
        return Err(error);
    }
};

// 创建 MSYS2 临时目录
let temp_dir = rsync_dir.join("tmp");

if let Err(error) = std::fs::create_dir_all(&temp_dir) {
    state.running.store(0, Ordering::SeqCst);
    return Err(format!(
        "无法创建 MSYS2 临时目录 {}：{}",
        temp_dir.display(),
        error
    ));
}

emit_log(
    &app,
    format!("内置 Rsync 目录：{}", rsync_dir.display()),
    "normal",
    0,
);

emit_log(
    &app,
    format!("MSYS2 临时目录：{}", temp_dir.display()),
    "normal",
    0,
);
    // 查找资源路径
    let rsync_path = match bundled_rsync_path(&app) {
        Ok(path) => path,
        Err(error) => {
            state.running.store(0, Ordering::SeqCst);
            return Err(error);
        }
    };

    emit_log(
        &app,
        format!("内置 Rsync：{}", rsync_path.display()),
        "normal",
        0,
    );
    emit_log(&app, "正在启动 Rsync...", "normal", 0);

    let bash_path = match bundled_bash_path(&app) {
        Ok(path) => path,
        Err(error) => {
            state.running.store(0, Ordering::SeqCst);
            return Err(error);
        }
    };

    // 启动进程
    let mut process = match spawn_rsync(
    &bash_path,
    &rsync_path,
    &rsync_dir,
    &temp_dir,
    &local_path,
    &rsync_args,
) {
        Ok(process) => process,
        Err(error) => {
            state.running.store(0, Ordering::SeqCst);
            return Err(error);
        }
    };

    let pid = match process.id() {
        Some(pid) => pid,
        None => {
            state.running.store(0, Ordering::SeqCst);
            return Err("无法获取 Rsync 进程 PID".into());
        }
    };

    state.pid.store(pid, Ordering::SeqCst);
    emit_log(
        &app,
        format!("Rsync 进程已启动，PID：{pid}"),
        "success",
        0,
    );

    // 取出 stdout / stderr
    let stdout = match process.stdout.take() {
        Some(s) => s,
        None => {
            state.pid.store(0, Ordering::SeqCst);
            state.running.store(0, Ordering::SeqCst);
            return Err("无法读取标准输出".into());
        }
    };

    let stderr = match process.stderr.take() {
        Some(s) => s,
        None => {
            state.pid.store(0, Ordering::SeqCst);
            state.running.store(0, Ordering::SeqCst);
            return Err("无法读取错误输出".into());
        }
    };

    // 并发解析
    let app_stdout = app.clone();
    let app_stderr = app.clone();

    let stdout_task = tokio::spawn(async move { consume_stdout(app_stdout, stdout).await });
    let stderr_task = tokio::spawn(async move { consume_stderr(app_stderr, stderr).await });

    // 等待进程退出
    let status = process
        .wait()
        .await
        .map_err(|e| format!("等待 Rsync 进程结束失败：{e}"))?;

    let file_count = stdout_task
        .await
        .map_err(|e| format!("读取 Rsync 标准输出失败：{e}"))?;

    stderr_task
        .await
        .map_err(|e| format!("读取 Rsync 错误输出失败：{e}"))?;

    // 清理状态
    state.pid.store(0, Ordering::SeqCst);
    state.running.store(0, Ordering::SeqCst);

    // 结果汇总
    let cancelled = *state.cancelled.lock().await;

    if cancelled {
        emit_log(&app, "同步已取消", "warning", file_count);
        return Ok(SyncResult {
            success: false,
            cancelled: true,
            message: "同步已取消".into(),
        });
    }

    if status.success() {
        emit_log(&app, "同步完成", "success", file_count);
        Ok(SyncResult {
            success: true,
            cancelled: false,
            message: "同步完成".into(),
        })
    } else {
        let message = format!("Rsync 执行失败，退出码：{:?}", status.code());
        emit_log(&app, &message, "error", file_count);
        Ok(SyncResult {
            success: false,
            cancelled: false,
            message,
        })
    }
}

// ============================================================
// 内部辅助函数
// ============================================================

fn validate_config(config: &SyncConfig) -> Result<(), String> {
    if config.host.trim().is_empty()
        || config.module.trim().is_empty()
        || config.local_path.trim().is_empty()
    {
        return Err("服务器地址、模块名和本地目录不能为空".into());
    }

    if config.host.contains('/')
        || config.host.contains('\\')
        || config.host.contains('@')
        || config.module.contains('/')
        || config.module.contains('\\')
        || config.module.contains('@')
    {
        return Err("服务器地址或模块名包含非法字符".into());
    }

    if config.port == 0 {
        return Err("服务器端口无效".into());
    }

    Ok(())
}

/// 组装 rsync 模块 URL：`host::module[/subpath]/`
fn build_remote(config: &SyncConfig) -> String {
    let host = config.host.trim();
    let module = config.module.trim();
    let sub = config.remote_path.trim().trim_matches('/');

    let remote = if sub.is_empty() {
        format!("{host}::{module}")
    } else {
        format!("{host}::{module}/{sub}")
    };

    format!("{}/", remote.trim_end_matches('/'))
}

fn build_rsync_args(config: &SyncConfig, remote: &str) -> Vec<String> {
    let mut args = vec![
        "-av".to_string(),
        "--port".to_string(),
        config.port.to_string(),
        "--partial".to_string(),
        "--no-perms".to_string(),
        "--itemize-changes".to_string(),
        "--progress".to_string(),
    ];

    if config.delete_extra {
        args.push("--delete".into());
    }

    // 远程源必须在本地目标之前
    args.push(remote.to_string());
    args
}



#[cfg(windows)]
const CREATE_NO_WINDOW: u32 = 0x08000000;

fn spawn_rsync(
    bash_path: &std::path::Path,
    rsync_path: &std::path::Path,
    rsync_dir: &std::path::Path,
    temp_dir: &std::path::Path,
    local_path: &std::path::Path,
    rsync_args: &[String],
) -> Result<tokio::process::Child, String> {
    let mut paths = vec![rsync_dir.to_path_buf()];

    if let Some(system_path) = std::env::var_os("PATH") {
        paths.extend(std::env::split_paths(&system_path));
    }

    let path_env = std::env::join_paths(paths)
        .map_err(|e| format!("构建 PATH 环境变量失败：{e}"))?;

    let temp_path = temp_dir.to_string_lossy().to_string();

    let mut command = Command::new(bash_path);

    #[cfg(windows)]
    command
        .as_std_mut()
        .creation_flags(CREATE_NO_WINDOW);

    command
        .arg("--noprofile")
        .arg("--norc")
        .arg("-c")
        .arg(BASH_SCRIPT)
        .arg("swan-trace-sync")
        .arg(normalize_windows_path(rsync_path))
        .arg(normalize_windows_path(local_path))
        .args(rsync_args)
        .env("PATH", path_env)
        .env("MSYSTEM", "MSYS")
        .env("MSYS2_PATH_TYPE", "inherit")
        .env("CHERE_INVOKING", "1")
        .env("TMP", &temp_path)
        .env("TEMP", &temp_path)
        .env("TMPDIR", &temp_path)
        .stdout(Stdio::piped())
        .stderr(Stdio::piped())
        .kill_on_drop(true);

    command
        .spawn()
        .map_err(|e| format!("无法启动 Bash/Rsync 进程：{e}"))
}

// ============================================================
// 输出流消费
// ============================================================

/// 解析 stdout：识别 itemize 行（文件）、进度行、其他日志。
///
/// 返回本次同步处理的文件数量。
async fn consume_stdout<R>(app: tauri::AppHandle, stream: R) -> usize
where
    R: tokio::io::AsyncRead + Unpin,
{
    let mut reader = BufReader::new(stream);
    let mut buffer = [0u8; READ_BUFFER_SIZE];
    let mut line_buffer: Vec<u8> = Vec::new();

    let mut file_count: usize = 0;
    let mut current_file: Option<String> = None;
    // 记录 (文件标识, 上次发出的百分比)，避免 IPC 洪水
    let mut last_progress: Option<(String, u32)> = None;

    loop {
        let n = match reader.read(&mut buffer).await {
            Ok(0) => break,
            Ok(n) => n,
            Err(error) => {
                emit_log(
                    &app,
                    format!("读取 Rsync 输出失败：{error}"),
                    "error",
                    file_count,
                );
                break;
            }
        };

        for &byte in &buffer[..n] {
            // rsync 进度用 \r 刷新，所以 \r 和 \n 都要断行
            if byte != b'\r' && byte != b'\n' {
                if line_buffer.len() < MAX_LINE_LENGTH {
                    line_buffer.push(byte);
                }
                continue;
            }

            if line_buffer.is_empty() {
                continue;
            }

            let line = String::from_utf8_lossy(&line_buffer).trim().to_string();
            line_buffer.clear();

            if line.is_empty() {
                continue;
            }

            handle_stdout_line(&app, &line, &mut file_count, &mut current_file, &mut last_progress);
        }
    }

    // 处理最后一段未以换行结束的内容
    if !line_buffer.is_empty() {
        let line = String::from_utf8_lossy(&line_buffer).trim().to_string();
        if !line.is_empty() {
            emit_log(&app, line, "normal", file_count);
        }
    }

    file_count
}

fn handle_stdout_line(
    app: &tauri::AppHandle,
    line: &str,
    file_count: &mut usize,
    current_file: &mut Option<String>,
    last_progress: &mut Option<(String, u32)>,
) {
    // ① 新的文件变更
    if is_itemize_line(line) {
        *file_count += 1;
        *current_file = Some(line.to_string());
        *last_progress = None;

        emit_file_log(
            app,
            line,
            "file",
            *file_count,
            current_file.clone(),
        );
        return;
    }

    // ② 传输进度行
    if is_progress_line(line) {
        let file_key = current_file.clone().unwrap_or_default();
        let percent = extract_percent(line);

        // 只在百分比变化时上报
        let should_emit = match (last_progress.as_ref(), percent) {
            (Some((f, p)), Some(np)) => f != &file_key || *p != np,
            _ => true,
        };

        if let Some(np) = percent {
            *last_progress = Some((file_key, np));
        }

        if should_emit {
            emit_file_log(
                app,
                format!("    {line}"),
                "progress",
                *file_count,
                current_file.clone(),
            );
        }
        return;
    }

    // ③ 忽略噪音行
    if is_ignored_rsync_line(line) {
        return;
    }

    // ④ 其它普通日志
    emit_log(app, line, "normal", *file_count);
}

/// 解析 stderr：仅作为错误/普通日志上报。
async fn consume_stderr<R>(app: tauri::AppHandle, stream: R)
where
    R: tokio::io::AsyncRead + Unpin,
{
    let mut reader = BufReader::new(stream);
    let mut buffer = [0u8; READ_BUFFER_SIZE];
    let mut line_buffer: Vec<u8> = Vec::new();

    loop {
        let n = match reader.read(&mut buffer).await {
            Ok(0) => break,
            Ok(n) => n,
            Err(error) => {
                emit_log(&app, format!("读取 Rsync 输出失败：{error}"), "error", 0);
                break;
            }
        };

        for &byte in &buffer[..n] {
            if byte != b'\r' && byte != b'\n' {
                if line_buffer.len() < MAX_LINE_LENGTH {
                    line_buffer.push(byte);
                }
                continue;
            }

            if line_buffer.is_empty() {
                continue;
            }

            let line = String::from_utf8_lossy(&line_buffer).trim().to_string();
            line_buffer.clear();

            if line.is_empty() {
                continue;
            }

            emit_stderr_line(&app, &line);
        }
    }

    if !line_buffer.is_empty() {
        let line = String::from_utf8_lossy(&line_buffer).trim().to_string();
        if !line.is_empty() {
            emit_stderr_line(&app, &line);
        }
    }
}

fn emit_stderr_line(app: &tauri::AppHandle, line: &str) {
    let lower = line.to_lowercase();
    let log_type = if lower.contains("error")
        || lower.contains("failed")
        || lower.contains("denied")
    {
        "error"
    } else {
        "normal"
    };

    emit_log(app, line, log_type, 0);
}