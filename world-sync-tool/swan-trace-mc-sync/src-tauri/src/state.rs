use std::sync::atomic::AtomicU32;

use tokio::sync::Mutex;

/// 同步任务的全局运行状态。
///
/// - `pid`：当前 Rsync 进程 PID，0 表示无进程
/// - `cancelled`：是否已请求取消
/// - `running`：是否正在运行（0 空闲，1 运行）
#[derive(Default)]
pub struct SyncState {
    pub pid: AtomicU32,
    pub cancelled: Mutex<bool>,
    pub running: AtomicU32,
}