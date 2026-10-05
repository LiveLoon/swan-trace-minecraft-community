use serde::{Deserialize, Serialize};

/// 前端传入的同步配置。
#[derive(Debug, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct SyncConfig {
    pub host: String,
    pub port: u16,
    pub module: String,
    pub remote_path: String,
    pub local_path: String,
    pub delete_extra: bool,
}

/// `start_sync` 的返回值。
#[derive(Debug, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct SyncResult {
    pub success: bool,
    pub cancelled: bool,
    pub message: String,
}

/// 通过 `sync-log` 事件推送给前端的日志。
#[derive(Debug, Serialize, Clone)]
#[serde(rename_all = "camelCase")]
pub struct SyncLog {
    pub message: String,
    pub log_type: String,
    pub file_count: usize,
    /// 当前正在处理/传输的文件标识（前端据此合并同一文件的进度）
    pub file: Option<String>,
}