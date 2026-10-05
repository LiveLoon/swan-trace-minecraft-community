<script setup>
import { CloudDownload } from 'lucide-vue-next'
</script>

<template>
  <div class="direction">
    <div class="direction__icon">
      <CloudDownload :size="20" />
    </div>
    <div class="direction__text">
      <strong>单向同步</strong>
      <p>远程服务器 → 本地电脑</p>
    </div>
    <span class="direction__tag">只读远程</span>
  </div>
</template>

<style scoped>
.direction {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 14px;
  border-radius: var(--radius-md);
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.08), rgba(99, 102, 241, 0.04));
  border: 1px solid rgba(59, 130, 246, 0.18);
}

.direction__icon {
  width: 36px;
  height: 36px;
  border-radius: var(--radius-sm);
  background: var(--accent-soft);
  color: #93c5fd;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.direction__text strong {
  display: block;
  font-size: 13.5px;
  color: var(--text-primary);
}

.direction__text p {
  margin: 2px 0 0;
  font-size: 11.5px;
  color: var(--text-secondary);
}

.direction__tag {
  margin-left: auto;
  padding: 3px 9px;
  border-radius: 999px;
  font-size: 10.5px;
  color: #93c5fd;
  background: rgba(59, 130, 246, 0.14);
  border: 1px solid rgba(59, 130, 246, 0.25);
  letter-spacing: 0.3px;
}
</style>