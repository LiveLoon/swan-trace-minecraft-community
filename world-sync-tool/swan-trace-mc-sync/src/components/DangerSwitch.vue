<script setup>
import { AlertTriangle } from 'lucide-vue-next'
import { useSyncTask } from '../composables/useSyncTask'

const { config, running, toggleDeleteExtra } = useSyncTask()
</script>

<template>
  <div class="danger">
    <div class="danger__option" :class="{ 'is-enabled': config.deleteExtra }">
      <div class="danger__info">
        <div class="danger__title">
          <AlertTriangle :size="15" />
          <strong>删除本地多余文件</strong>
          <span class="danger__badge">危险</span>
        </div>
        <p class="danger__desc">
          启用后，本地存在但服务器没有的文件将被删除，使本地目录与服务器完全一致。
          <b>此操作不可恢复，请谨慎启用。</b>
        </p>
      </div>

      <button
        type="button"
        class="danger__switch"
        :class="{ on: config.deleteExtra }"
        :disabled="running"
        :aria-pressed="config.deleteExtra"
        @click="toggleDeleteExtra"
      >
        <span class="danger__track">
          <span class="danger__thumb"></span>
        </span>
        <span class="danger__label">
          {{ config.deleteExtra ? '已启用' : '已关闭' }}
        </span>
      </button>
    </div>

    <Transition name="banner">
      <div v-if="config.deleteExtra" class="danger__banner">
        <AlertTriangle :size="14" />
        <span>删除模式已启用：同步时本地多出的文件将被永久删除。</span>
        <button
          class="danger__banner-btn"
          :disabled="running"
          @click="toggleDeleteExtra"
        >
          立即关闭
        </button>
      </div>
    </Transition>
  </div>
</template>

<style scoped>
.danger {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.danger__option {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  padding: 14px 16px;
  border-radius: var(--radius-md);
  border: 1px solid var(--border);
  background: rgba(255, 255, 255, 0.015);
  transition: border-color 0.2s, background 0.2s;
}

.danger__option.is-enabled {
  border-color: rgba(239, 68, 68, 0.5);
  background: var(--danger-soft);
}

.danger__info {
  flex: 1;
  min-width: 0;
}

.danger__title {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13.5px;
  color: var(--text-primary);
}

.danger__option.is-enabled .danger__title {
  color: #fca5a5;
}

.danger__badge {
  margin-left: 4px;
  padding: 1px 6px;
  border-radius: 4px;
  font-size: 10.5px;
  font-weight: 600;
  color: #fff;
  background: var(--danger);
  letter-spacing: 0.5px;
}

.danger__desc {
  margin: 6px 0 0;
  font-size: 12px;
  line-height: 1.55;
  color: var(--text-secondary);
}

.danger__desc b {
  color: #f87171;
  font-weight: 600;
}

/* 开关 */
.danger__switch {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
  padding: 0;
  border: none;
  background: transparent;
  outline: none;
  flex-shrink: 0;
}

.danger__switch:disabled {
  cursor: not-allowed;
  opacity: 0.55;
}

.danger__track {
  position: relative;
  display: inline-block;
  width: 46px;
  height: 26px;
  border-radius: 999px;
  background: #334155;
  transition: background 0.2s;
}

.danger__switch.on .danger__track {
  background: var(--danger);
}

.danger__thumb {
  position: absolute;
  top: 3px;
  left: 3px;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: #fff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.35);
  transition: transform 0.2s;
}

.danger__switch.on .danger__thumb {
  transform: translateX(20px);
}

.danger__label {
  font-size: 11px;
  color: var(--text-muted);
  user-select: none;
}

.danger__switch.on .danger__label {
  color: #fca5a5;
}

/* 警告条 */
.danger__banner {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 12px;
  border-radius: var(--radius-sm);
  border: 1px solid rgba(239, 68, 68, 0.45);
  background: rgba(239, 68, 68, 0.1);
  color: #fca5a5;
  font-size: 12.5px;
  line-height: 1.5;
}

.danger__banner-btn {
  margin-left: auto;
  flex-shrink: 0;
  padding: 4px 10px;
  border-radius: var(--radius-sm);
  border: 1px solid rgba(239, 68, 68, 0.6);
  background: transparent;
  color: #fca5a5;
  font-size: 12px;
  transition: background 0.15s;
}

.danger__banner-btn:hover:not(:disabled) {
  background: rgba(239, 68, 68, 0.18);
}

.danger__banner-btn:disabled {
  cursor: not-allowed;
  opacity: 0.55;
}

.banner-enter-active,
.banner-leave-active {
  transition: opacity 0.2s, transform 0.2s;
}
.banner-enter-from,
.banner-leave-to {
  opacity: 0;
  transform: translateY(-4px);
}
</style>