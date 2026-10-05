use std::path::{Path, PathBuf};

use tauri::Manager;

/// 获取内置 Rsync 资源目录。
///
/// 开发环境和打包环境统一使用 Tauri resource_dir。
fn bundled_rsync_dir(app: &tauri::AppHandle) -> Result<PathBuf, String> {
    let resource_dir = app
        .path()
        .resource_dir()
        .map_err(|e| format!("无法获取应用资源目录：{e}"))?;

    // 兼容不同资源目录布局
    let candidates = [
        resource_dir.join("resources").join("rsync"),
        resource_dir.join("rsync"),
    ];

    for dir in candidates {
        if dir.is_dir() {
            return Ok(dir);
        }
    }

    Err(format!(
        "找不到内置 Rsync 资源目录。\n资源目录：{}",
        resource_dir.display()
    ))
}

/// 获取内置 bash.exe 路径。
///
/// 开发环境和运行环境统一使用内置资源。
pub fn bundled_bash_path(app: &tauri::AppHandle) -> Result<PathBuf, String> {
    let rsync_dir = bundled_rsync_dir(app)?;

    let path = rsync_dir.join("bash.exe");

    if path.is_file() {
        return Ok(path);
    }

    Err(format!(
        "找不到内置 bash.exe。\n查找位置：{}",
        path.display()
    ))
}

/// 获取内置 rsync.exe 路径。
///
/// 开发环境和运行环境统一使用内置资源。
pub fn bundled_rsync_path(app: &tauri::AppHandle) -> Result<PathBuf, String> {
    let rsync_dir = bundled_rsync_dir(app)?;

    let path = rsync_dir.join("rsync.exe");

    if path.is_file() {
        return Ok(path);
    }

    Err(format!(
        "找不到内置 rsync.exe。\n查找位置：{}",
        path.display()
    ))
}

/// 获取内置 cygpath.exe 路径。
pub fn bundled_cygpath_path(app: &tauri::AppHandle) -> Result<PathBuf, String> {
    let rsync_dir = bundled_rsync_dir(app)?;

    let path = rsync_dir.join("cygpath.exe");

    if path.is_file() {
        return Ok(path);
    }

    Err(format!(
        "找不到内置 cygpath.exe。\n查找位置：{}",
        path.display()
    ))
}

/// 获取内置 Rsync 目录。
pub fn bundled_rsync_resource_dir(
    app: &tauri::AppHandle,
) -> Result<PathBuf, String> {
    bundled_rsync_dir(app)
}

/// 去掉 Windows 扩展路径前缀 `\\?\`，便于 MSYS 处理。
pub fn normalize_windows_path(path: &Path) -> String {
    let value = path.to_string_lossy().to_string();

    if let Some(stripped) = value.strip_prefix(r"\\?\") {
        stripped.to_string()
    } else {
        value
    }
}