use std::sync::atomic::Ordering;
use std::sync::Arc;

use tauri::State;
use tokio::process::Command;

use crate::state::SyncState;

#[tauri::command]
pub async fn cancel_sync(state: State<'_, Arc<SyncState>>) -> Result<(), String> {
    *state.cancelled.lock().await = true;

    let pid = state.pid.load(Ordering::SeqCst);

    if pid == 0 {
        return Ok(());
    }

    let pid_string = pid.to_string();

    let output = Command::new("taskkill")
        .args(["/PID", pid_string.as_str(), "/T", "/F"])
        .output()
        .await
        .map_err(|e| format!("执行 taskkill 失败：{}", e))?;

    if !output.status.success() {
        let message = String::from_utf8_lossy(&output.stderr);
        return Err(format!("终止 Rsync 失败：{}", message));
    }

    Ok(())
}