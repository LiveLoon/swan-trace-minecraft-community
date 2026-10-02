mod commands;

use commands::sync::{cancel_sync, start_sync, SyncState};

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .manage(SyncState::default())
        .plugin(tauri_plugin_shell::init())
        .plugin(tauri_plugin_dialog::init())
        .plugin(tauri_plugin_store::Builder::default().build())
        .invoke_handler(tauri::generate_handler![
            start_sync,
            cancel_sync
        ])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}