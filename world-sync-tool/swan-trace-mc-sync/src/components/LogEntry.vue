<script setup>
defineProps({
  log: { type: Object, required: true }
})
</script>

<template>
  <div class="log-entry" :class="`is-${log.type}`">
    <span class="log-entry__time">{{ log.time }}</span>
    <span class="log-entry__msg">{{ log.message }}</span>
  </div>
</template>

<style scoped>
.log-entry {
  display: flex;
  gap: 10px;
  padding: 3px 6px;
  border-radius: 4px;
  font-family: "Cascadia Code", "Consolas", "JetBrains Mono", monospace;
  font-size: 12.5px;
  line-height: 1.55;
  word-break: break-all;
}

.log-entry__time {
  color: var(--text-muted);
  flex-shrink: 0;
  font-size: 11.5px;
  padding-top: 1px;
}

.log-entry__msg {
  color: var(--text-primary);
  white-space: pre-wrap;
}

/* 类型 */
.log-entry.is-file .log-entry__msg {
  color: #7dd3fc;
  font-weight: 500;
}

.log-entry.is-progress .log-entry__msg {
  color: var(--text-secondary);
}

.log-entry.is-success {
  background: rgba(34, 197, 94, 0.08);
}
.log-entry.is-success .log-entry__msg {
  color: #86efac;
}

.log-entry.is-warning {
  background: rgba(245, 158, 11, 0.08);
}
.log-entry.is-warning .log-entry__msg {
  color: #fcd34d;
}

.log-entry.is-error {
  background: rgba(239, 68, 68, 0.08);
}
.log-entry.is-error .log-entry__msg {
  color: #fca5a5;
}
</style>