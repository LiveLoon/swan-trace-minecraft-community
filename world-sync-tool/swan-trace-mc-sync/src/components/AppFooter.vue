<template>
  <footer class="app-footer">
    <span>Swan Trace Minecraft Community</span>
    <span class="app-footer__sep">·</span>
    <span>World Sync Tool</span>
    <span class="app-footer__spacer"></span>
    <span class="app-footer__platform">Windows · Rsync</span>
  </footer>
</template>

<style scoped>
.app-footer {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 22px;
  border-top: 1px solid var(--border);
  background: var(--bg-panel);
  color: var(--text-muted);
  font-size: 11.5px;
  letter-spacing: 0.3px;
  flex-shrink: 0;
}

.app-footer__sep {
  opacity: 0.5;
}

.app-footer__spacer {
  flex: 1;
}

.app-footer__platform {
  color: var(--text-secondary);
}
</style>