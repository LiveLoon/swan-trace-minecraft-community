use tauri::Emitter;

use crate::models::SyncLog;

const EVENT_SYNC_LOG: &str = "sync-log";

/// 发送普通日志（无关联文件）。
pub fn emit_log(
    app: &tauri::AppHandle,
    message: impl Into<String>,
    log_type: &str,
    count: usize,
) {
    let _ = app.emit(
        EVENT_SYNC_LOG,
        SyncLog {
            message: message.into(),
            log_type: log_type.to_string(),
            file_count: count,
            file: None,
        },
    );
}

/// 发送带文件标识的日志（用于前端原地合并进度）。
pub fn emit_file_log(
    app: &tauri::AppHandle,
    message: impl Into<String>,
    log_type: &str,
    count: usize,
    file: Option<String>,
) {
    let _ = app.emit(
        EVENT_SYNC_LOG,
        SyncLog {
            message: message.into(),
            log_type: log_type.to_string(),
            file_count: count,
            file,
        },
    );
}