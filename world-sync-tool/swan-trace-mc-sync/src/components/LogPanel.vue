<script setup>
import { computed } from 'vue'
import { Terminal, Trash2, Copy } from 'lucide-vue-next'
import { useSyncTask } from '../composables/useSyncTask'
import { useSyncLogs } from '../composables/useSyncLogs'
import LogEntry from './LogEntry.vue'

const { running, elapsed } = useSyncTask()
const { logs, logContainer, addLog, clearLogs, copyLogs } = useSyncLogs()

const logCount = computed(() => logs.value.length)

async function handleCopy() {
  if (!logs.value.length) return
  try {
    await copyLogs()
    addLog('日志已复制到剪贴板', 'success')
  } catch (error) {
    addLog(`复制日志失败：${String(error)}`, 'error')
  }
}

function handleClear() {
  if (!logs.value.length) return
  clearLogs()
}
</script>

<template>
  <section class="log-panel">
    <header class="log-panel__head">
      <div class="log-panel__title">
        <Terminal :size="16" />
        <span>同步日志</span>
        <span class="log-panel__live" :class="{ 'is-live': running }">
          <i></i>
          {{ running ? 'LIVE' : 'READY' }}
        </span>
      </div>

      <div class="log-panel__actions">
        <button
          class="icon-btn"
          :disabled="!logs.length"
          title="复制全部日志"
          @click="handleCopy"
        >
          <Copy :size="15" />
        </button>
        <button
          class="icon-btn"
          :disabled="!logs.length"
          title="清空日志"
          @click="handleClear"
        >
          <Trash2 :size="15" />
        </button>
      </div>
    </header>

    <div class="log-panel__body" ref="logContainer">
      <div v-if="logs.length === 0" class="log-panel__empty">
        <Terminal :size="36" />
        <p>等待同步任务</p>
        <small>点击左侧「开始同步」查看实时日志</small>
      </div>

      <LogEntry v-for="log in logs" :key="log.id" :log="log" />
    </div>

    <footer class="log-panel__foot">
      <span>共 {{ logCount }} 条日志</span>
      <span class="log-panel__sep">·</span>
      <span>{{ running ? `用时 ${elapsed}` : 'UTF-8 · Rsync' }}</span>
    </footer>
  </section>
</template>

<style scoped>
.log-panel {
  display: flex;
  flex-direction: column;
  min-height: 0;
  border-radius: var(--radius-lg);
  background: var(--bg-panel);
  border: 1px solid var(--border);
  overflow: hidden;
}

/* 头部 */
.log-panel__head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 18px;
  border-bottom: 1px solid var(--border);
  flex-shrink: 0;
}

.log-panel__title {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
  color: var(--text-primary);
  font-weight: 500;
}

.log-panel__live {
  display: inline-flex;
  align-items: center;
  gap: 5px;
  padding: 3px 9px;
  border-radius: 999px;
  background: var(--bg-card);
  border: 1px solid var(--border);
  font-size: 10.5px;
  color: var(--text-muted);
  letter-spacing: 0.8px;
  font-weight: 600;
}

.log-panel__live i {
  display: inline-block;
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: var(--text-muted);
}

.log-panel__live.is-live {
  color: #86efac;
  border-color: rgba(34, 197, 94, 0.35);
}

.log-panel__live.is-live i {
  background: var(--success);
  box-shadow: 0 0 0 3px rgba(34, 197, 94, 0.15);
  animation: pulse 1.4s ease-in-out infinite;
}

.log-panel__actions {
  display: flex;
  gap: 6px;
}

.icon-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 30px;
  height: 30px;
  border-radius: var(--radius-sm);
  border: 1px solid var(--border);
  background: transparent;
  color: var(--text-secondary);
  transition: background 0.15s, color 0.15s, border-color 0.15s;
}

.icon-btn:hover:not(:disabled) {
  background: var(--bg-hover);
  color: var(--text-primary);
  border-color: var(--border-strong);
}

.icon-btn:disabled {
  opacity: 0.35;
  cursor: not-allowed;
}

/* 主体 */
.log-panel__body {
  flex: 1;
  min-height: 0;
  overflow-y: auto;
  padding: 12px 14px;
  display: flex;
  flex-direction: column;
  gap: 1px;
}

.log-panel__empty {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 8px;
  color: var(--text-muted);
  padding: 40px 0;
}

.log-panel__empty p {
  margin: 6px 0 0;
  font-size: 13px;
  color: var(--text-secondary);
}

.log-panel__empty small {
  font-size: 11.5px;
}

/* 底部 */
.log-panel__foot {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 18px;
  border-top: 1px solid var(--border);
  color: var(--text-muted);
  font-size: 11.5px;
  letter-spacing: 0.3px;
  flex-shrink: 0;
}

.log-panel__sep {
  opacity: 0.5;
}

@keyframes pulse {
  0%, 100% { opacity: 1; transform: scale(1); }
  50% { opacity: 0.55; transform: scale(0.85); }
}
</style>