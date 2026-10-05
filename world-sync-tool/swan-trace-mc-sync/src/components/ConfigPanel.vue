<script setup>
import { Play, Square, FolderOpen } from 'lucide-vue-next'
import { open } from '@tauri-apps/plugin-dialog'
import { useSyncTask } from '../composables/useSyncTask'
import { useSyncLogs } from '../composables/useSyncLogs'
import DangerSwitch from './DangerSwitch.vue'
import DirectionCard from './DirectionCard.vue'

const { config, running, startSync, cancelSync } = useSyncTask()
const { addLog } = useSyncLogs()

async function selectDirectory() {
  try {
    const selected = await open({
      directory: true,
      multiple: false,
      title: '选择本地同步目录'
    })
    if (typeof selected === 'string' && selected) {
      config.localPath = selected
    }
  } catch (error) {
    addLog(`选择目录失败：${error}`, 'error')
  }
}
</script>

<template>
  <section class="config-panel">
    <header class="config-panel__head">
      <div>
        <h2>配置</h2>
        <p>配置远程 Rsync 服务器和本地目录</p>
      </div>
      <span class="config-panel__tag">Base On Rsync</span>
    </header>

    <div class="config-card">
      <div class="field">
        <label>服务器地址</label>
        <input
          v-model.trim="config.host"
          placeholder="例如：192.168.1.100"
          :disabled="running"
        />
      </div>

      <div class="field-row">
        <div class="field">
          <label>服务器端口</label>
          <input
            v-model.number="config.port"
            type="number"
            min="1"
            max="65535"
            :disabled="running"
          />
        </div>
        <div class="field">
          <label>同步模块名</label>
          <input
            v-model.trim="config.module"
            placeholder="world"
            :disabled="running"
          />
        </div>
      </div>

      <div class="field">
        <label>远程子目录<span class="opt">可选</span></label>
        <input
          v-model.trim="config.remotePath"
          placeholder="留空表示同步整个模块"
          :disabled="running"
        />
      </div>

      <div class="field">
        <label>本地保存位置</label>
        <div class="field-path">
          <input
            v-model="config.localPath"
            placeholder="选择本地世界目录"
            :disabled="running"
          />
          <button
            class="field-path__btn"
            :disabled="running"
            @click="selectDirectory"
          >
            <FolderOpen :size="16" />
            浏览
          </button>
        </div>
      </div>

      <DangerSwitch />

      <DirectionCard />

      <div class="config-actions">
        <button
          v-if="!running"
          class="btn btn--primary"
          @click="startSync"
        >
          <Play :size="17" />
          开始同步
        </button>
        <button
          v-else
          class="btn btn--danger"
          @click="cancelSync"
        >
          <Square :size="16" />
          取消同步
        </button>
      </div>
    </div>
  </section>
</template>

<style scoped>
.config-panel {
  display: flex;
  flex-direction: column;
  gap: 14px;
  min-height: 0;
  overflow-y: auto;
  padding-right: 4px;
}

.config-panel__head {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 12px;
}

.config-panel__head h2 {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
}

.config-panel__head p {
  margin: 4px 0 0;
  font-size: 12px;
  color: var(--text-muted);
}

.config-panel__tag {
  padding: 3px 10px;
  border-radius: 999px;
  font-size: 10.5px;
  color: #93c5fd;
  background: var(--accent-soft);
  border: 1px solid rgba(59, 130, 246, 0.25);
  letter-spacing: 0.4px;
  white-space: nowrap;
  margin-top: 2px;
}

.config-card {
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding: 18px;
  border-radius: var(--radius-lg);
  background: var(--bg-panel);
  border: 1px solid var(--border);
}

/* 字段 */
.field {
  display: flex;
  flex-direction: column;
  gap: 7px;
}

.field label {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 12.5px;
  color: var(--text-secondary);
  font-weight: 500;
}

.field label .opt {
  padding: 1px 5px;
  border-radius: 4px;
  font-size: 10px;
  color: var(--text-muted);
  background: rgba(148, 163, 184, 0.1);
  font-weight: 400;
}

.field input {
  height: 38px;
  padding: 0 12px;
  border-radius: var(--radius-sm);
  border: 1px solid var(--border);
  background: var(--bg-input);
  color: var(--text-primary);
  font-size: 13.5px;
  outline: none;
  transition: border-color 0.15s, box-shadow 0.15s;
}

.field input::placeholder {
  color: var(--text-muted);
}

.field input:focus {
  border-color: var(--accent);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
}

.field input:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.field-row {
  display: grid;
  grid-template-columns: 130px 1fr;
  gap: 12px;
}

.field-path {
  display: flex;
  gap: 8px;
}

.field-path input {
  flex: 1;
  min-width: 0;
}

.field-path__btn {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  height: 38px;
  padding: 0 14px;
  border-radius: var(--radius-sm);
  border: 1px solid var(--border-strong);
  background: var(--bg-card);
  color: var(--text-primary);
  font-size: 13px;
  transition: background 0.15s, border-color 0.15s;
  flex-shrink: 0;
}

.field-path__btn:hover:not(:disabled) {
  background: var(--bg-hover);
  border-color: var(--accent);
}

.field-path__btn:disabled {
  opacity: 0.55;
  cursor: not-allowed;
}

/* 操作按钮 */
.config-actions {
  margin-top: 4px;
}

.btn {
  width: 100%;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  height: 44px;
  border: none;
  border-radius: var(--radius-md);
  font-size: 14px;
  font-weight: 500;
  letter-spacing: 0.3px;
  transition: background 0.15s, transform 0.08s, box-shadow 0.15s;
}

.btn:active:not(:disabled) {
  transform: translateY(1px);
}

.btn--primary {
  background: linear-gradient(135deg, #3b82f6, #2563eb);
  color: #fff;
  box-shadow: 0 4px 14px rgba(59, 130, 246, 0.35);
}

.btn--primary:hover {
  background: linear-gradient(135deg, #4b8efb, #2e6ff0);
  box-shadow: 0 6px 18px rgba(59, 130, 246, 0.45);
}

.btn--danger {
  background: linear-gradient(135deg, #ef4444, #dc2626);
  color: #fff;
  box-shadow: 0 4px 14px rgba(239, 68, 68, 0.35);
}

.btn--danger:hover {
  background: linear-gradient(135deg, #f26060, #e63939);
  box-shadow: 0 6px 18px rgba(239, 68, 68, 0.45);
}
</style>