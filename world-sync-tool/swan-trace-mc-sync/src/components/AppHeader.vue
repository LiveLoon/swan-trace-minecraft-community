<script setup>
import { computed } from 'vue'
import { useSyncTask } from '../composables/useSyncTask'
import logoUrl from '../assets/logo.png'

const { running, status } = useSyncTask()

const statusText = computed(() => {
  if (running.value) return '正在同步'
  const map = {
    ready: '就绪',
    success: '同步完成',
    error: '同步失败',
    cancelled: '已取消',
    cancelling: '正在取消'
  }
  return map[status.value] || '就绪'
})

const statusClass = computed(() => {
  if (running.value) return 'is-running'
  if (status.value === 'success') return 'is-success'
  if (status.value === 'error') return 'is-error'
  if (status.value === 'cancelled') return 'is-warning'
  return 'is-idle'
})
</script>

<template>
  <header class="app-header">
    <div class="brand">
      <div class="brand__logo">
        <img :src="logoUrl" alt="Swan Trace" />
      </div>
      <div class="brand__text">
        <h1>鸿迹 · 存档同步工具</h1>
        <span>SWAN TRACE WORLD SYNC</span>
      </div>
    </div>

    <div class="status-pill" :class="statusClass">
      <span class="status-pill__dot"></span>
      <span>{{ statusText }}</span>
    </div>
  </header>
</template>

<style scoped>
/* ============ 顶栏 ============ */
.app-header {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 22px;
  background: var(--bg-panel);
  border-bottom: 1px solid var(--border);
  flex-shrink: 0;
}

/* 底部一条极淡的高光，代替厚重的分隔感 */
.app-header::after {
  content: "";
  position: absolute;
  left: 0;
  right: 0;
  bottom: -1px;
  height: 1px;
  background: linear-gradient(
    90deg,
    transparent,
    rgba(99, 102, 241, 0.25),
    transparent
  );
  pointer-events: none;
}

/* ============ 品牌区 ============ */
.brand {
  display: flex;
  align-items: center;
  gap: 12px;
}

.brand__logo {
  width: 40px;
  height: 40px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  flex-shrink: 0;

  /* 安静的玻璃感背景，而不是发光色块 */
  background:
    radial-gradient(
      120% 120% at 30% 20%,
      rgba(99, 102, 241, 0.18),
      rgba(99, 102, 241, 0.04) 60%
    ),
    rgba(255, 255, 255, 0.02);

  border: 1px solid rgba(148, 163, 184, 0.18);
  box-shadow:
    inset 0 1px 0 rgba(255, 255, 255, 0.04),
    0 1px 2px rgba(0, 0, 0, 0.25);

  transition: border-color 0.2s, background 0.2s;
}

.brand__logo img {
  width: 100%;
  height: 100%;
  object-fit: contain;
  display: block;
  /* logo 本身若为深色，可稍作提亮；若是彩色可去掉 */
  filter: drop-shadow(0 1px 1px rgba(0, 0, 0, 0.35));
}

.brand__text h1 {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 0.2px;
  color: var(--text-primary);
}

.brand__text span {
  display: block;
  margin-top: 2px;
  font-size: 10.5px;
  color: var(--text-muted);
  letter-spacing: 1.5px;
}

/* ============ 状态胶囊 ============ */
.status-pill {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 6px 14px;
  border-radius: 999px;
  background: var(--bg-card);
  border: 1px solid var(--border);
  font-size: 12.5px;
  color: var(--text-secondary);
  transition: color 0.2s, border-color 0.2s, background 0.2s;
}

.status-pill__dot {
  width: 7px;
  height: 7px;
  border-radius: 50%;
  background: var(--text-muted);
  box-shadow: 0 0 0 3px rgba(148, 163, 184, 0.1);
  transition: background 0.2s, box-shadow 0.2s;
}

.status-pill.is-running {
  color: #93c5fd;
  border-color: rgba(59, 130, 246, 0.4);
  background: var(--accent-soft);
}
.status-pill.is-running .status-pill__dot {
  background: var(--accent);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
  animation: pulse 1.4s ease-in-out infinite;
}

.status-pill.is-success {
  color: #86efac;
  border-color: rgba(34, 197, 94, 0.35);
}
.status-pill.is-success .status-pill__dot {
  background: var(--success);
  box-shadow: 0 0 0 3px rgba(34, 197, 94, 0.15);
}

.status-pill.is-error {
  color: #fca5a5;
  border-color: rgba(239, 68, 68, 0.4);
}
.status-pill.is-error .status-pill__dot {
  background: var(--danger);
  box-shadow: 0 0 0 3px rgba(239, 68, 68, 0.18);
}

.status-pill.is-warning {
  color: #fcd34d;
  border-color: rgba(245, 158, 11, 0.35);
}
.status-pill.is-warning .status-pill__dot {
  background: var(--warning);
  box-shadow: 0 0 0 3px rgba(245, 158, 11, 0.15);
}

@keyframes pulse {
  0%, 100% { opacity: 1; transform: scale(1); }
  50% { opacity: 0.55; transform: scale(0.85); }
}
</style>