import { reactive, ref, watch } from 'vue'
import { load } from '@tauri-apps/plugin-store'

const STORE_PATH = 'sync-config.json'

const config = reactive({
  host: '',
  port: 8873,
  module: 'world',
  remotePath: '',
  localPath: '',
  deleteExtra: false
})

const configLoaded = ref(false)
let store = null
let watcherInstalled = false

async function initConfigStore(onError) {
  try {
    store = await load(STORE_PATH, {
      defaults: { ...config },
      autoSave: true
    })
    const saved = await store.get('config')
    if (saved) Object.assign(config, saved)
  } catch (error) {
    onError?.(`读取配置失败：${String(error)}`)
  }

  // 加载完成后再挂 watcher，避免加载时立刻回写
  if (!watcherInstalled) {
    watcherInstalled = true
    watch(config, () => saveConfig(onError), { deep: true })
  }

  configLoaded.value = true
}

async function saveConfig(onError) {
  if (!store) return
  try {
    await store.set('config', {
      host: config.host,
      port: config.port,
      module: config.module,
      remotePath: config.remotePath,
      localPath: config.localPath,
      deleteExtra: config.deleteExtra
    })
    await store.save()
  } catch (error) {
    onError?.(`保存配置失败：${String(error)}`)
  }
}

export function useSyncConfig() {
  return { config, configLoaded, initConfigStore, saveConfig }
}