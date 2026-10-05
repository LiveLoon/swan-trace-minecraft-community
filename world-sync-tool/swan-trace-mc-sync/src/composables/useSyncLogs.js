import { ref, nextTick } from 'vue'

const MAX_LOGS = 3000
const TRIM_TO = 1000

const logs = ref([])
const logContainer = ref(null)
let logId = 0

function scrollToBottom() {
  nextTick(() => {
    const el = logContainer.value
    if (el) el.scrollTop = el.scrollHeight
  })
}

/**
 * 添加日志。
 *
 * 当 type === 'progress' 且带 file 时，会尝试合并到最后一条相同文件的
 * 进度日志里（原地更新），实现"每个文件的实时进度"。
 */
function addLog(message, type = 'normal', options = {}) {
  const file = options.file || ''
  const time = new Date().toLocaleTimeString('zh-CN', { hour12: false })

  if (type === 'progress' && file) {
    const last = logs.value[logs.value.length - 1]
    if (last && last.type === 'progress' && last.file === file) {
      last.message = String(message)
      last.time = time
      scrollToBottom()
      return
    }
  }

  logs.value.push({
    id: ++logId,
    time,
    message: String(message),
    type,
    file
  })

  if (logs.value.length > MAX_LOGS) {
    logs.value.splice(0, logs.value.length - TRIM_TO)
  }

  scrollToBottom()
}

function clearLogs() {
  logs.value = []
}

async function copyLogs() {
  const text = logs.value
    .map(item => `[${item.time}] ${item.message}`)
    .join('\n')

  await navigator.clipboard.writeText(text)
}

export function useSyncLogs() {
  return { logs, logContainer, addLog, clearLogs, copyLogs, scrollToBottom }
}