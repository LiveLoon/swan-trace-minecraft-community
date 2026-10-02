<script setup lang="ts">
import { ref, computed, nextTick, onMounted, onUnmounted } from "vue";

import {
  startSync as startSyncService,
  cancelSync as cancelSyncService,
  onSyncLog,
  onSyncStatus,
} from "../services/sync";
import { open } from "@tauri-apps/plugin-dialog";
import {
  loadConfig,
  saveConfig,
  type AppConfig,
} from "../services/config";

// 左上角 Logo：图片放在 src/assets/logo.png
// 如果你的图片在别的路径，改这里即可
import logoUrl from "../assets/logo.png";

const server = ref("127.0.0.1");
const port = ref<number>(873);
const moduleName = ref("world");
const username = ref("swan_trace_mc");
const password = ref("123456");

const worldPath = ref("/home/liveloon-remote/Public/local-world");

const progress = ref(0);
const status = ref("等待同步");
const currentFile = ref("");
const logs = ref<string[]>([]);
const syncing = ref(false);

const logsEl = ref<HTMLElement | null>(null);

let unlistenLog: (() => void) | undefined;
let unlistenStatus: (() => void) | undefined;

/** 状态对应的视觉样式 */
const statusKind = computed(() => {
  switch (status.value) {
    case "同步完成":
      return "success";
    case "同步失败":
      return "error";
    case "正在同步":
    case "正在启动":
      return "running";
    default:
      return "idle";
  }
});

const progressStatus = computed(() => {
  if (status.value === "同步失败") return "exception";
  if (status.value === "同步完成") return "success";
  return undefined;
});

async function scrollLogsToBottom() {
  await nextTick();
  const el = logsEl.value;
  if (el) el.scrollTop = el.scrollHeight;
}

function clearLogs() {
  logs.value = [];
}

async function selectWorld() {
  const selected = await open({
    directory: true,
    multiple: false,
    title: "选择本地世界目录",
  });

  if (typeof selected === "string") {
    worldPath.value = selected;
    await saveCurrentConfig();
  }
}

function getCurrentConfig(): AppConfig {
  return {
    server: server.value,
    port: port.value,
    moduleName: moduleName.value,
    username: username.value,
    password: password.value,
    worldPath: worldPath.value,
  };
}

async function saveCurrentConfig() {
  await saveConfig(getCurrentConfig());
}

onMounted(async () => {
  unlistenLog = await onSyncLog((message) => {
    logs.value.push(message);

    // 从 rsync --progress 输出中提取百分比
    const match = message.match(/(\d{1,3})%/);
    if (match) {
      progress.value = Math.min(100, Number(match[1]));
    }

    // 尝试显示当前文件名
    const lines = message.split(/\r?\n/).filter(Boolean);
    if (lines.length > 0) {
      currentFile.value = lines[lines.length - 1];
    }

    // 避免日志无限增长
    if (logs.value.length > 500) {
      logs.value.splice(0, logs.value.length - 500);
    }

    void scrollLogsToBottom();
  });

  unlistenStatus = await onSyncStatus((value) => {
    status.value = value;

    switch (value) {
      // 正在执行
      case "正在启动":
      case "正在同步":
        syncing.value = true;
        break;

      // 正在取消，继续等待后端进程退出
      case "正在取消":
        syncing.value = true;
        break;

      // 同步成功
      case "同步完成":
        progress.value = 100;
        syncing.value = false;
        break;

      // 同步失败
      case "同步失败":
      case "启动失败":
      case "同步结束":
        syncing.value = false;
        break;

      // 用户主动取消
      case "已取消":
        syncing.value = false;
        break;

      default:
        break;
    }

    void scrollLogsToBottom();
  });

  const config = await loadConfig();

  server.value = config.server;
  port.value = config.port;
  moduleName.value = config.moduleName;
  username.value = config.username;
  worldPath.value = config.worldPath;
});

onUnmounted(() => {
  unlistenLog?.();
  unlistenStatus?.();
});

async function startSync() {
  if (syncing.value) return;

  if (!server.value || !username.value || !password.value || !worldPath.value) {
    status.value = "请填写完整配置";
    return;
  }

  try {
    progress.value = 0;
    currentFile.value = "";
    status.value = "正在启动";
    syncing.value = true;
    logs.value.push("正在请求启动同步...");
    void scrollLogsToBottom();

    await saveCurrentConfig();
    await startSyncService({
      server: server.value,
      port: port.value,
      moduleName: moduleName.value,
      user: username.value,
      password: password.value,
      localPath: worldPath.value,
    });
  } catch (error) {
    status.value = "同步失败";
    syncing.value = false;
    logs.value.push(`启动失败：${String(error)}`);
    void scrollLogsToBottom();
  } finally {
    // 无论成功、失败还是取消，只要命令结束就解除 loading
    syncing.value = false;
  }
}

async function cancelSync() {
  if (!syncing.value) return;

  try {
    status.value = "正在取消";

    logs.value.push("正在请求取消同步...");
    void scrollLogsToBottom();

    await cancelSyncService();

    logs.value.push("已请求取消同步，等待进程退出...");
    void scrollLogsToBottom();

  } catch (error) {
    logs.value.push(`取消失败：${String(error)}`);

    // 取消请求失败，恢复原来的运行状态
    if (status.value === "正在取消") {
      status.value = "正在同步";
    }

    void scrollLogsToBottom();
  }
}
</script>

<template>
  <div class="page">
    <div class="workspace">
      <!-- ==================== 左侧栏 ==================== -->
      <aside class="sidebar">
        <!-- 品牌 / Logo -->
        <section class="brand-card">
          <div class="brand">
            <img class="brand__logo" :src="logoUrl" alt="Swan Trace" />

            <div class="brand__text">
              <h1>鸿迹世界存档同步</h1>
              <p>你的同步，让这个世界更加安全：》</p>
            </div>
          </div>

          <span class="badge" :class="`badge--${statusKind}`">
            <i class="dot" />
            {{ status }}
          </span>
        </section>

        <!-- 服务器配置 -->
        <el-card class="panel" shadow="never">
          <template #header>
            <div class="panel-title">
              <span class="panel-title__text">服务器配置</span>
              <span class="panel-title__hint">Base on rsync daemon</span>
            </div>
          </template>

          <el-form label-position="top">
            <el-form-item label="服务器IP地址或域名">
              <el-input v-model="server" placeholder="127.0.0.1" />
            </el-form-item>

            <el-form-item label="端口">
              <el-input-number v-model="port" :min="1" :max="65535" :step="1" controls-position="right"
                style="width: 100%" />
            </el-form-item>

            <el-form-item label="模块名">
              <el-input v-model="moduleName" placeholder="world" />
            </el-form-item>

            <el-form-item label="用户名">
              <el-input v-model="username" placeholder="swan_trace_mc" />
            </el-form-item>

            <el-form-item label="密码" class="mb-0">
              <el-input v-model="password" type="password" show-password placeholder="请输入 rsync 密码" />
            </el-form-item>
          </el-form>
        </el-card>

        <!-- 世界目录 -->
        <el-card class="panel" shadow="never">
          <template #header>
            <div class="panel-title">
              <span class="panel-title__text">本地同步位置</span>
              <span class="panel-title__hint">本地目录</span>
            </div>
          </template>

          <div class="path-row">
            <el-input v-model="worldPath" placeholder="/path/to/your/world" />
            <el-button class="choose" @click="selectWorld">选择</el-button>
          </div>
        </el-card>
      </aside>

      <!-- ==================== 右侧主区 ==================== -->
      <main class="main">
        <!-- 同步控制 -->
        <el-card class="panel" shadow="never">
          <template #header>
            <div class="panel-title">
              <span class="panel-title__text">同步进度</span>
              <span class="panel-title__hint">{{ progress }}%</span>
            </div>
          </template>

          <div class="sync-actions">

            <el-button type="primary" size="large" :loading="syncing" :disabled="syncing" @click="startSync">
              {{
                status === "正在取消"
                  ? "正在取消…"
                  : syncing
              ? "同步中…"
              : "开始同步"
              }}
            </el-button>

            <el-button type="danger" size="large" plain :disabled="!syncing || status === '正在取消'"
              :loading="status === '正在取消'" @click="cancelSync">
              {{ status === "正在取消" ? "正在取消…" : "取消同步" }}
            </el-button>
          </div>

          <el-progress class="bar" :percentage="progress" :stroke-width="12" :show-text="false"
            :status="progressStatus" />

          <div class="meta">
            <div class="meta-row">
              <span class="meta-label">状态</span>
              <span class="meta-value">{{ status }}</span>
            </div>

            <div class="meta-row">
              <span class="meta-label">当前输出</span>
              <span class="output" :title="currentFile">
                {{ currentFile || "—" }}
              </span>
            </div>
          </div>
        </el-card>

        <!-- 日志（撑满剩余高度） -->
        <el-card class="panel panel--fill" shadow="never">
          <template #header>
            <div class="panel-title">
              <span class="panel-title__text">同步日志</span>

              <div class="panel-title__right">
                <span class="panel-title__hint">{{ logs.length }} 条</span>
                <el-button link size="small" @click="clearLogs">清空</el-button>
              </div>
            </div>
          </template>

          <div ref="logsEl" class="logs">
            <p v-if="logs.length === 0" class="logs__empty">
              暂无日志，开始同步后将在此显示输出…
            </p>

            <p v-for="(log, index) in logs" :key="index">{{ log }}</p>
          </div>
        </el-card>
      </main>
    </div>
  </div>
</template>

<style scoped>
/* ==================== 页面骨架 ==================== */
.page {
  box-sizing: border-box;
  display: flex;
  height: 100vh;
  padding: 18px;
  color: #0f172a;
  background:
    radial-gradient(1100px 480px at 8% -12%, #e0e7ff 0%, transparent 60%),
    radial-gradient(900px 420px at 102% -4%, #dbeafe 0%, transparent 55%),
    #f8fafc;
}

.workspace {
  display: flex;
  gap: 16px;
  width: 100%;
  max-width: 1320px;
  min-height: 0;
  margin: 0 auto;
}

/* ---------- 左侧栏 ---------- */
.sidebar {
  display: flex;
  flex: none;
  flex-direction: column;
  gap: 14px;
  width: 350px;
  min-height: 0;
  padding-right: 4px;
  overflow-y: auto;
}

.sidebar::-webkit-scrollbar {
  width: 6px;
}

.sidebar::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 3px;
}

/* ---------- 右侧主区 ---------- */
.main {
  display: flex;
  flex: 1;
  flex-direction: column;
  gap: 14px;
  min-width: 0;
  min-height: 0;
}

/* ==================== 品牌区 ==================== */
.brand-card {
  display: flex;
  flex-direction: column;
  gap: 14px;
  padding: 18px;
  background: #fff;
  border: 1px solid rgba(148, 163, 184, 0.18);
  border-radius: 16px;
  box-shadow: 0 16px 34px -24px rgba(15, 23, 42, 0.5);
}

.brand {
  display: flex;
  align-items: center;
  gap: 12px;
}

.brand__logo {
  flex: none;
  width: 48px;
  height: 48px;
  object-fit: cover;
  background: linear-gradient(135deg, #6366f1, #8b5cf6);
  border-radius: 14px;
  box-shadow: 0 10px 20px -8px rgba(99, 102, 241, 0.85);
}

.brand__text {
  min-width: 0;
}

.brand__text h1 {
  margin: 0;
  font-size: 17px;
  font-weight: 700;
  letter-spacing: 0.3px;
  color: #111827;
}

.brand__text p {
  margin: 3px 0 0;
  font-size: 10px;
  letter-spacing: 1.4px;
  text-transform: uppercase;
  color: #94a3b8;
}

/* ==================== 状态徽章 ==================== */
.badge {
  display: inline-flex;
  align-self: flex-start;
  align-items: center;
  gap: 8px;
  padding: 5px 13px;
  font-size: 12.5px;
  font-weight: 600;
  white-space: nowrap;
  color: #475569;
  background: #f1f5f9;
  border: 1px solid #e2e8f0;
  border-radius: 999px;
}

.badge .dot {
  width: 7px;
  height: 7px;
  background: currentColor;
  border-radius: 50%;
}

.badge--running {
  color: #2563eb;
  background: #eff6ff;
  border-color: #bfdbfe;
}

.badge--running .dot {
  animation: pulse 1.2s ease-in-out infinite;
}

.badge--success {
  color: #059669;
  background: #ecfdf5;
  border-color: #a7f3d0;
}

.badge--error {
  color: #dc2626;
  background: #fef2f2;
  border-color: #fecaca;
}

@keyframes pulse {

  0%,
  100% {
    opacity: 1;
    transform: scale(1);
  }

  50% {
    opacity: 0.3;
    transform: scale(0.7);
  }
}

/* ==================== 卡片 ==================== */
.panel {
  overflow: hidden;
  border: 1px solid rgba(148, 163, 184, 0.18);
  border-radius: 16px;
  box-shadow: 0 16px 34px -24px rgba(15, 23, 42, 0.5);
}

.panel :deep(.el-card__header) {
  padding: 13px 18px;
  background: linear-gradient(180deg, #fcfdff, #f8fafc);
  border-bottom: 1px solid #f1f5f9;
}

.panel :deep(.el-card__body) {
  padding: 18px;
}

/* 填充剩余高度的卡片（日志） */
.panel--fill {
  display: flex;
  flex: 1;
  flex-direction: column;
  min-height: 0;
}

.panel--fill :deep(.el-card__header) {
  flex: none;
}

.panel--fill :deep(.el-card__body) {
  display: flex;
  flex: 1;
  min-height: 0;
  padding: 14px;
}

.panel-title {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.panel-title__text {
  position: relative;
  padding-left: 12px;
  font-size: 14px;
  font-weight: 700;
  color: #1e293b;
}

.panel-title__text::before {
  position: absolute;
  top: 50%;
  left: 0;
  width: 4px;
  height: 14px;
  background: linear-gradient(180deg, #6366f1, #8b5cf6);
  border-radius: 2px;
  transform: translateY(-50%);
  content: "";
}

.panel-title__hint {
  font-size: 12px;
  color: #94a3b8;
}

.panel-title__right {
  display: flex;
  align-items: center;
  gap: 10px;
}

/* ==================== 表单 ==================== */
.panel :deep(.el-form-item) {
  margin-bottom: 14px;
}

.panel :deep(.el-form-item__label) {
  padding-bottom: 4px;
  font-size: 12.5px;
  font-weight: 600;
  color: #64748b;
}

.mb-0 {
  margin-bottom: 0 !important;
}

/* ==================== 目录选择 ==================== */
.path-row {
  display: flex;
  gap: 10px;
  align-items: center;
}

.path-row .el-input {
  flex: 1;
  min-width: 0;
}

.choose {
  flex: none;
  height: 32px;
  border-radius: 9px;
}

/* ==================== 同步控制 ==================== */
.sync-actions {
  display: flex;
  gap: 12px;
}

.sync-actions .el-button {
  min-width: 138px;
  height: 42px;
  font-weight: 600;
  letter-spacing: 0.5px;
  border-radius: 12px;
}

.sync-actions .el-button+.el-button {
  margin-left: 0;
}

.bar {
  margin-top: 20px;
}

.bar :deep(.el-progress-bar__outer) {
  background: #eef2f7;
  border-radius: 999px;
}

/* ==================== 状态信息 ==================== */
.meta {
  display: flex;
  flex-direction: column;
  gap: 9px;
  margin-top: 16px;
}

.meta-row {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 13px;
}

.meta-label {
  flex: none;
  width: 64px;
  color: #94a3b8;
}

.meta-value {
  font-weight: 600;
  color: #1e293b;
}

.output {
  flex: 1;
  min-width: 0;
  padding: 6px 10px;
  overflow: hidden;
  font-family: ui-monospace, SFMono-Regular, Menlo, monospace;
  font-size: 12.5px;
  color: #475569;
  text-overflow: ellipsis;
  white-space: nowrap;
  background: #f8fafc;
  border: 1px solid #eef2f7;
  border-radius: 8px;
}

/* ==================== 日志终端 ==================== */
.logs {
  flex: 1;
  min-height: 0;
  padding: 14px 16px;
  overflow-y: auto;
  font-family: ui-monospace, SFMono-Regular, Menlo, monospace;
  font-size: 12.5px;
  line-height: 1.65;
  color: #cbd5e1;
  background: #0f172a;
  border-radius: 12px;
  scroll-behavior: smooth;
}

.logs p {
  margin: 0 0 2px;
  overflow-wrap: anywhere;
  white-space: pre-wrap;
}

.logs p:not(.logs__empty):last-child {
  color: #a5f3fc;
}

.logs__empty {
  color: #475569;
}

.logs::-webkit-scrollbar {
  width: 8px;
}

.logs::-webkit-scrollbar-thumb {
  background: #334155;
  border-radius: 4px;
}

.logs::-webkit-scrollbar-track {
  background: transparent;
}

/* ==================== 响应式 ==================== */
@media (max-width: 900px) {
  .page {
    height: auto;
    min-height: 100vh;
    padding: 16px 14px 40px;
  }

  .workspace {
    flex-direction: column;
  }

  .sidebar {
    width: 100%;
    overflow: visible;
  }

  .main {
    min-height: 0;
  }

  .panel--fill {
    flex: none;
  }

  .logs {
    flex: none;
    height: 220px;
  }
}

@media (max-width: 480px) {
  .path-row {
    flex-direction: column;
    align-items: stretch;
  }

  .sync-actions {
    flex-direction: column;
  }

  .sync-actions .el-button {
    width: 100%;
  }
}
</style>