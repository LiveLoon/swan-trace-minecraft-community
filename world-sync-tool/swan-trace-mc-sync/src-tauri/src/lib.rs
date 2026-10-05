mod commands;
mod events;
mod log_parser;
mod models;
mod paths;
mod state;

use std::sync::Arc;

use state::SyncState;

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .plugin(tauri_plugin_dialog::init())
        .plugin(tauri_plugin_store::Builder::default().build())
        .manage(Arc::new(SyncState::default()))
        .invoke_handler(tauri::generate_handler![
            commands::sync::start_sync,
            commands::cancel::cancel_sync,
        ])
        .run(tauri::generate_context!())
        .expect("启动应用失败");
}