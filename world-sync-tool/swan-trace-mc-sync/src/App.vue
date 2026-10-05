<script setup>
import { onMounted, onUnmounted } from 'vue'
import AppHeader from './components/AppHeader.vue'
import AppFooter from './components/AppFooter.vue'
import ConfigPanel from './components/ConfigPanel.vue'
import LogPanel from './components/LogPanel.vue'
import { useSyncTask } from './composables/useSyncTask'

const { initialize, dispose } = useSyncTask()

onMounted(() => {
  initialize()
})

onUnmounted(() => {
  dispose()
})
</script>

<template>
  <div class="app-shell">
    <AppHeader />

    <main class="workspace">
      <ConfigPanel />
      <LogPanel />
    </main>

    <AppFooter />
  </div>
</template>

<style scoped>
.app-shell {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: var(--bg-root);
  color: var(--text-primary);
  overflow: hidden;
}

.workspace {
  flex: 1;
  min-height: 0;
  display: grid;
  grid-template-columns: minmax(360px, 440px) minmax(0, 1fr);
  gap: 16px;
  padding: 16px 18px;
}

@media (max-width: 1024px) {
  .workspace {
    grid-template-columns: 1fr;
    overflow-y: auto;
  }
}
</style>