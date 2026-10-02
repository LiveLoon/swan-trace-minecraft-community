import { load } from "@tauri-apps/plugin-store";

export interface AppConfig {
  server: string;
  port: number;
  moduleName: string;
  username: string;
  password: string;
  worldPath: string;
}


const DEFAULT_CONFIG: AppConfig = {
  server: "8.155.146.189",
  port: 873,
  moduleName: "world",
  username: "swan_trace_mc",
  password: "",
  worldPath: "",
};

const STORE_FILE = "settings.json";

export async function loadConfig(): Promise<AppConfig> {
  const store = await load(STORE_FILE, {
    defaults: {},
    autoSave: true,
  });

  const saved = await store.get<Partial<AppConfig>>("syncConfig");

  return {
    ...DEFAULT_CONFIG,
    ...saved,
  };
}

export async function saveConfig(config: AppConfig): Promise<void> {
  const store = await load(STORE_FILE, {
    defaults: {},
    autoSave: true,
  });

  await store.set("syncConfig", config);
  await store.save();
}