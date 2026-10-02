import { invoke } from "@tauri-apps/api/core";
import { listen } from "@tauri-apps/api/event";

export interface SyncConfig {
  server: string;
  port: number;
  moduleName: string;
  user: string;
  password: string;
  localPath: string;
}

export function startSync(config: SyncConfig): Promise<void> {
  return invoke<void>("start_sync", { ...config });
}

export function cancelSync(): Promise<void> {
  return invoke<void>("cancel_sync");
}

export function onSyncLog(
  callback: (message: string) => void,
) {
  return listen<string>("sync-log", (event) => {
    callback(event.payload);
  });
}

export function onSyncStatus(
  callback: (status: string) => void,
) {
  return listen<string>("sync-status", (event) => {
    callback(event.payload);
  });
}