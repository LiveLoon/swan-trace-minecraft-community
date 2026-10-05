import { ref, computed } from 'vue'
import { invoke } from '@tauri-apps/api/core'
import { ask } from '@tauri-apps/plugin-dialog'
import { listen } from '@tauri-apps/api/event'
import { useSyncConfig } from './useSyncConfig'
import { useSyncLogs } from './useSyncLogs'
import { parseLogPayload, getFriendlyRsyncMessage } from '../utils/rsyncMessages'

const running = ref(false)
const status = ref('ready')
const elapsed = ref('00:00')

let timer = null
let startedAt = 0
let unlistenLog = null
let initialized = false

export function useSyncTask() {
  const { config, initConfigStore } = useSyncConfig()
  const { logs, addLog, clearLogs } = useSyncLogs()

  const endpoint = computed(() => {
    const host = config.host.trim()
    const module = config.module.trim()
    if (!host || !module) return ''
    return `${host}:${config.port}/${module}`
  })

  function validate() {
    const host = config.host.trim()
    const module = config.module.trim()
    const port = Number(config.port)

    if (!host) return '请输入服务器地址'
    if (/[/\\@]/.test(host) || /\s/.test(host)) return '服务器地址格式不正确'
    if (!Number.isInteger(port) || port < 1 || port > 65535) {
      return '请输入 1 到 65535 之间的有效端口'
    }
    if (!module) return '请输入同步模块名'
    if (/[/\\@]/.test(module) || /\s/.test(module)) {
      return '模块名不能包含斜杠、@ 或空格'
    }
    if (!config.localPath.trim()) return '请选择本地保存位置'
    return ''
  }

  function startTimer() {
    stopTimer()
    startedAt = Date.now()
    elapsed.value = '00:00'
    timer = setInterval(() => {
      const s = Math.floor((Date.now() - startedAt) / 1000)
      elapsed.value =
        String(Math.floor(s / 60)).padStart(2, '0') +
        ':' +
        String(s % 60).padStart(2, '0')
    }, 1000)
  }

  function stopTimer() {
    if (timer) {
      clearInterval(timer)
      timer = null
    }
  }

  async function toggleDeleteExtra() {
    if (running.value) return

    // 关 → 开：需要二次确认
    if (!config.deleteExtra) {
      let confirmed = false
      try {
        confirmed = await ask(
          '启用「删除本地多余文件」后，同步时服务器上不存在的本地文件将被永久删除，且无法恢复。\n\n请确认你已备份重要数据，并确保同步模块选择正确。',
          {
            title: '危险操作确认',
            kind: 'warning',
            okLabel: '我已了解，启用',
            cancelLabel: '取消'
          }
        )
      } catch (error) {
        addLog(`弹窗调用失败：${String(error)}`, 'error')
        return
      }

      if (!confirmed) {
        addLog('已取消启用「删除本地多余文件」', 'warning')
        return
      }

      config.deleteExtra = true
      addLog('已启用「删除本地多余文件」，同步时本地多余文件将被删除', 'warning')
      return
    }

    // 开 → 关：直接关闭
    config.deleteExtra = false
    addLog('已关闭「删除本地多余文件」', 'normal')
  }

  async function startSync() {
    const error = validate()
    if (error) {
      addLog(error, 'error')
      status.value = 'error'
      return
    }

    if (running.value) return

    // 危险操作：启动前再做一次汇总确认
    if (config.deleteExtra) {
      let confirmed = false
      try {
        confirmed = await ask(
          `即将开始同步，并启用「删除本地多余文件」。\n\n` +
          `目标服务器：${config.host.trim()}::${config.module.trim()}\n` +
          `本地目录：${config.localPath.trim()}\n\n` +
          `本地存在但服务器没有的文件将被永久删除。是否继续？`,
          {
            title: '再次确认：即将删除本地多余文件',
            kind: 'warning',
            okLabel: '确认开始同步',
            cancelLabel: '取消'
          }
        )
      } catch (e) {
        addLog(`确认弹窗调用失败：${String(e)}`, 'error')
        return
      }

      if (!confirmed) {
        addLog('已取消本次同步', 'warning')
        return
      }
    }

    running.value = true
    status.value = 'connecting'
    clearLogs()
    startTimer()

    addLog(`连接目标：${endpoint.value}`)
    addLog(`本地目录：${config.localPath.trim()}`)

    try {
      status.value = 'running'

      const result = await invoke('start_sync', {
        config: {
          host: config.host.trim(),
          port: Number(config.port),
          module: config.module.trim(),
          remotePath: config.remotePath.trim(),
          localPath: config.localPath.trim(),
          deleteExtra: Boolean(config.deleteExtra)
        }
      })

      if (result.success) {
        status.value = 'success'
        addLog(result.message || '同步完成', 'success')
      } else if (result.cancelled) {
        status.value = 'cancelled'
        addLog(result.message || '同步已取消', 'warning')
      } else {
        status.value = 'error'
        addLog(result.message || '同步失败', 'error')
      }
    } catch (error) {
      status.value = 'error'
      addLog(`同步启动或执行失败：${String(error)}`, 'error')
    } finally {
      running.value = false
      stopTimer()
    }
  }

  async function cancelSync() {
    if (!running.value) return
    status.value = 'cancelling'
    addLog('正在请求取消同步...', 'warning')
    try {
      await invoke('cancel_sync')
    } catch (error) {
      addLog(`取消失败：${String(error)}`, 'error')
    }
  }

  async function initialize() {
    if (initialized) return
    initialized = true

    await initConfigStore((msg) => addLog(msg, 'error'))

    try {
      unlistenLog = await listen('sync-log', (event) => {
        const parsed = parseLogPayload(event.payload)

        addLog(parsed.message, parsed.type, { file: parsed.file })

        if (parsed.type === 'error' || parsed.type === 'normal') {
          const friendly = getFriendlyRsyncMessage(parsed.message)
          if (friendly) addLog(`提示：${friendly}`, 'warning')
        }
      })
    } catch (error) {
      addLog(`日志监听初始化失败：${String(error)}`, 'error')
    }
  }

  function dispose() {
    unlistenLog?.()
    stopTimer()
  }

  return {
    config,
    running,
    status,
    elapsed,
    endpoint,
    logs,
    startSync,
    cancelSync,
    toggleDeleteExtra,
    initialize,
    dispose
  }
}