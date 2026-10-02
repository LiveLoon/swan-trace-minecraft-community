
use std::sync::{
    atomic::{AtomicBool, Ordering},
    Mutex,
};

use tauri::{AppHandle, Emitter, State};
use tauri_plugin_shell::{
    process::{CommandChild, CommandEvent},
    ShellExt,
};

#[derive(Default)]
pub struct SyncState {
    /// 当前 rsync 子进程
    pub child: Mutex<Option<CommandChild>>,

    /// 是否收到取消请求
    pub cancelled: AtomicBool,
}

fn emit_log(app: &AppHandle, message: impl Into<String>) {
    let _ = app.emit("sync-log", message.into());
}

fn emit_status(app: &AppHandle, status: &str) {
    let _ = app.emit("sync-status", status.to_string());
}

#[tauri::command]
pub async fn start_sync(
    app: AppHandle,
    state: State<'_, SyncState>,
    server: String,
    port: u16,
    module_name: String,
    user: String,
    password: String,
    local_path: String,
) -> Result<(), String> {
    // ==============================
    // 1. 基础参数检查
    // ==============================

    if server.trim().is_empty()
        || module_name.trim().is_empty()
        || user.trim().is_empty()
        || local_path.trim().is_empty()
    {
        return Err("服务器、模块、用户名和本地目录不能为空".into());
    }

    if port == 0 {
        return Err("端口无效".into());
    }

    // ==============================
    // 2. 检查是否已有同步任务
    // ==============================

    {
        let child = state.child.lock().map_err(|e| e.to_string())?;

        if child.is_some() {
            return Err("已有同步任务正在运行".into());
        }
    }

    // 重置取消标记
    state.cancelled.store(false, Ordering::SeqCst);

    emit_status(&app, "正在启动");
    emit_log(&app, "正在启动 rsync...");

    // ==============================
    // 3. 构建 rsync 参数
    // ==============================

    let source = format!(
        "{}@{}::{}{}",
        user,
        server,
        module_name,
        "/"
    );

    let local_path = std::path::Path::new(&local_path)
        .to_string_lossy()
        .replace('\\', "/");

    emit_log(&app, format!("连接目标：{}", source));
    emit_log(&app, format!("本地目录：{}", local_path));

    let command = app
        .shell()
        .sidecar("rsync")
        .map_err(|e| {
            let message = format!("找不到 rsync 程序：{e}");
            emit_status(&app, "启动失败");
            emit_log(&app, &message);
            message
        })?
        .args([
            "-avh",
            "--progress",
            "--delete",
            "--partial",
            &format!("--port={}", port),
            &source,
            &local_path,
        ])
        .env("RSYNC_PASSWORD", password);

    // ==============================
    // 4. 启动 rsync
    // ==============================

    let (mut receiver, child) = command
        .spawn()
        .map_err(|e| {
            let message = format!("启动 rsync 失败：{e}");
            emit_status(&app, "启动失败");
            emit_log(&app, &message);
            message
        })?;

    // 保存子进程句柄
    {
        let mut current = state.child.lock().map_err(|e| e.to_string())?;

        *current = Some(child);
    }

    emit_status(&app, "正在同步");
    emit_log(&app, "rsync 已启动，开始同步文件。");

    // ==============================
    // 5. 接收 rsync 输出
    // ==============================

    let mut exit_code: Option<i32> = None;

    while let Some(event) = receiver.recv().await {
        match event {
            CommandEvent::Stdout(bytes) => {
                let output = String::from_utf8_lossy(&bytes).to_string();

                if !output.trim().is_empty() {
                    emit_log(&app, output);
                }
            }

            CommandEvent::Stderr(bytes) => {
                let output = String::from_utf8_lossy(&bytes).to_string();

                if !output.trim().is_empty() {
                    emit_log(&app, format!("[错误输出] {}", output));
                }
            }

            CommandEvent::Terminated(payload) => {
                exit_code = payload.code;

                emit_log(
                    &app,
                    format!("rsync 进程已退出，退出代码：{:?}", exit_code),
                );

                break;
            }

            _ => {}
        }
    }

    // ==============================
    // 6. 统一清理进程句柄
    // ==============================

    {
        let mut current = state.child.lock().map_err(|e| e.to_string())?;

        *current = None;
    }

    // 检查是否主动取消
    let cancelled = state.cancelled.load(Ordering::SeqCst);

    // ==============================
    // 7. 处理同步结果
    // ==============================

    if cancelled {
        emit_status(&app, "已取消");
        emit_log(&app, "同步已取消。");

        return Ok(());
    }

    match exit_code {
        Some(0) => {
            emit_status(&app, "同步完成");
            emit_log(&app, "同步完成。");

            Ok(())
        }

        Some(code) => {
            emit_status(&app, "同步失败");

            let message = format!(
                "rsync 同步失败，退出代码：{}",
                code
            );

            emit_log(&app, &message);

            Err(message)
        }

        None => {
            emit_status(&app, "同步失败");

            let message =
                "rsync 进程已结束，但没有返回退出代码".to_string();

            emit_log(&app, &message);

            Err(message)
        }
    }
}

// ==================================
// 取消同步
// ==================================
#[tauri::command]
pub fn cancel_sync(
    state: State<'_, SyncState>,
    app: AppHandle,
) -> Result<(), String> {
    let child = {
        let mut current = state.child.lock().map_err(|e| e.to_string())?;

        current.take()
    };

    if let Some(child) = child {
        // 标记为主动取消
        state.cancelled.store(true, Ordering::SeqCst);

        // CommandChild::kill(self) 会消耗 child
        child
            .kill()
            .map_err(|e| {
                let message = format!("取消同步失败：{}", e);
                emit_log(&app, &message);
                message
            })?;

        emit_status(&app, "正在取消");

        emit_log(
            &app,
            "已发送取消请求，等待 rsync 进程退出。",
        );

        Ok(())
    } else {
        Err("当前没有正在运行的同步任务".into())
    }
}