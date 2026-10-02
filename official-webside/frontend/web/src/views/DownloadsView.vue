<template>
  <div class="page-wrapper">
    <section ref="downloadSection" class="section download-section">
      <div class="container">

        <!-- =====================================================
             页面头部
        ====================================================== -->
        <header class="page-header">
          <div class="eyebrow">
            SWAN TRACE · WORLD ARCHIVE
          </div>

          <div class="header-row">
            <div>
              <h1>世界存档</h1>

              <p class="subtitle">
                鸿迹 Minecraft Community 世界备份与历史存档
              </p>
            </div>

            <div class="version-badge">
              <span class="status-dot"></span>
              Minecraft 26.2
            </div>
          </div>

          <div class="header-actions">
            <button class="action-button" type="button" @click="fetchFiles" :disabled="loading">
              <span v-if="!loading">↻ 刷新列表</span>
              <span v-else>正在加载...</span>
            </button>

            <button class="action-button" type="button" @click="toggleSortOrder">
              <span v-if="sortOrder === 'asc'">
                名称升序
              </span>

              <span v-else>
                名称降序
              </span>
            </button>

            <span v-if="files.length" class="file-count">
              {{ files.length }} 个存档
            </span>
          </div>
        </header>

        <!-- =====================================================
             备份说明
        ====================================================== -->
        <section class="archive-intro">

          <div class="archive-icon">
            💾
          </div>

          <div class="archive-content">
            <h2>服务器世界备份</h2>

            <p>
              鸿迹会对服务器世界进行周期性备份，
              用于服务器维护、数据恢复以及历史存档保留。
            </p>

            <div class="archive-tags">
              <span>周期备份</span>
              <span>历史保留</span>
              <span>Paper 26.2</span>
              <span>社区公益</span>
            </div>
          </div>

        </section>

        <!-- =====================================================
             加载
        ====================================================== -->
        <div v-if="loading" class="state-card">
          <div class="spinner"></div>

          <p>
            正在获取世界备份列表...
          </p>
        </div>

        <!-- =====================================================
             错误
        ====================================================== -->
        <div v-else-if="error" class="state-card error-card">
          <div class="state-icon">
            ⚠️
          </div>

          <h3>
            无法获取备份列表
          </h3>

          <p>
            {{ error }}
          </p>

          <button class="retry-button" type="button" @click="fetchFiles">
            重新加载
          </button>
        </div>

        <!-- =====================================================
             文件列表
        ====================================================== -->
        <section v-else class="archive-section">

          <!-- 列表标题 -->
          <div class="section-heading">
            <div>
              <span class="section-label">
                WORLD BACKUPS
              </span>

              <h2>
                世界存档列表
              </h2>
            </div>

            <span v-if="files.length" class="section-count">
              {{ files.length }} 个文件
            </span>
          </div>

          <!-- ===================================================
               文件列表
          ==================================================== -->
          <div v-if="sortedFiles.length" class="file-list">

            <article v-for="file in sortedFiles" :key="file.name" class="file-card" :class="{
              'latest-file':
                file.name === 'world_latest.zip'
            }">

              <!-- 文件图标 -->
              <div class="file-icon">
                <span v-if="file.name === 'world_latest.zip'">
                  🌍
                </span>

                <span v-else>
                  📦
                </span>
              </div>

              <!-- 文件信息 -->
              <div class="file-info">

                <div class="file-title-row">
                  <div class="file-name">
                    {{ file.name }}
                  </div>

                  <span v-if="file.name === 'world_latest.zip'" class="latest-badge">
                    最新备份
                  </span>
                </div>

                <div class="file-meta">
                  <span>
                    {{ formatSize(file.size) }}
                  </span>

                  <span class="meta-separator">
                    ·
                  </span>

                  <span>
                    {{ formatDate(file.mtime) }}
                  </span>
                </div>

              </div>

              <!-- =================================================
                   下载区域
              ================================================== -->
              <div class="download-area">

                <!-- 空闲 -->
                <template v-if="
                  getFileStatus(file.name) === 'idle'
                ">
                  <button class="download-button" type="button" @click="downloadFile(file)">
                    <span>↓</span>
                    下载
                  </button>
                </template>

                <!-- 下载中 -->
                <template v-else-if="
                  getFileStatus(file.name) ===
                  'downloading'
                ">
                  <div class="progress-container">

                    <div class="progress-top">
                      <span>
                        正在下载
                      </span>

                      <strong>
                        {{ getFileProgress(file.name) }}%
                      </strong>
                    </div>

                    <div class="progress-bar">
                      <div class="progress-fill" :style="{
                        width:
                          getFileProgress(
                            file.name
                          ) + '%'
                      }"></div>
                    </div>

                  </div>
                </template>

                <!-- 下载完成 -->
                <template v-else-if="
                  getFileStatus(file.name) ===
                  'completed'
                ">
                  <div class="download-result">
                    <span class="completed-text">
                      ✓ 已完成
                    </span>

                    <button class="secondary-button" type="button" @click="reDownload(file)">
                      再次下载
                    </button>
                  </div>
                </template>

                <!-- 下载失败 -->
                <template v-else-if="
                  getFileStatus(file.name) ===
                  'error'
                ">
                  <div class="download-result">
                    <span class="error-text">
                      下载失败
                    </span>

                    <button class="retry-download" type="button" @click="reDownload(file)">
                      重试
                    </button>
                  </div>
                </template>

              </div>

            </article>

          </div>

          <!-- ===================================================
               空状态
          ==================================================== -->
          <div v-else class="empty-state">
            <div class="empty-icon">
              📭
            </div>

            <h3>
              暂无世界备份
            </h3>

            <p>
              当前暂时没有可以下载的服务器世界存档。
            </p>
          </div>

        </section>

        <!-- =====================================================
             备份说明
        ====================================================== -->
        <section class="notice-card">

          <div class="notice-icon">
            ℹ
          </div>

          <div class="notice-content">
            <h3>
              关于世界备份
            </h3>

            <p>
              鸿迹会保留服务器世界的周期性备份，
              在版本更新、服务器维护或出现数据问题时，
              备份可用于辅助恢复。
            </p>

            <p>
              备份文件并不代表绝对的数据恢复保证。
              世界数据可能因为服务器故障、存储损坏、
              维护操作或其他不可预见情况而无法恢复。
            </p>

            <p>
              历史备份的保留时间可能根据服务器存储空间
              和维护情况进行调整。
            </p>
          </div>

        </section>

        <!-- =====================================================
             社区信息
        ====================================================== -->
        <section class="community-card">

          <div class="community-content">
            <span class="section-label">
              SWAN TRACE COMMUNITY
            </span>

            <h2>
              保存世界，也保存我们的记录
            </h2>

            <p>
              鸿迹从一个简单的个人公益服务器开始，
              希望这些世界数据能够记录大家共同留下的痕迹。
            </p>
          </div>

          <div class="community-meta">

            <div>
              <span>服务器版本</span>
              <strong>26.2</strong>
            </div>

            <div>
              <span>世界边界</span>
              <strong>50000</strong>
            </div>

            <div>
              <span>服务器性质</span>
              <strong>公益社区</strong>
            </div>

          </div>

        </section>

      </div>
    </section>

    <FooterC />
  </div>
</template>

<script setup lang="ts">
import {
  computed,
  onMounted,
  onUnmounted,
  reactive,
  ref
} from 'vue'

import FooterC from '@/components/FooterC.vue'

import {
  downloadBackupFile,
  fetchBackupFiles
} from '@/api/backups'

/* =========================================================
 * 类型
 * ======================================================= */

interface FileItem {
  name: string
  type: string
  mtime: string
  size: number
}

interface DownloadState {
  status:
  | 'idle'
  | 'downloading'
  | 'completed'
  | 'error'

  progress: number
}

/* =========================================================
 * 下载状态
 * ======================================================= */

const downloadStates =
  reactive<Record<string, DownloadState>>({})

const getFileStatus = (
  fileName: string
): DownloadState['status'] => {
  return (
    downloadStates[fileName]?.status ||
    'idle'
  )
}

const getFileProgress = (
  fileName: string
): number => {
  return (
    downloadStates[fileName]?.progress ||
    0
  )
}

const resetFileState = (
  fileName: string
) => {
  if (!downloadStates[fileName]) {
    return
  }

  downloadStates[fileName].status = 'idle'
  downloadStates[fileName].progress = 0
}

/* =========================================================
 * 页面状态
 * ======================================================= */

const files = ref<FileItem[]>([])

const loading = ref(false)

const error = ref('')

const sortOrder =
  ref<'asc' | 'desc'>('desc')

const downloadSection =
  ref<HTMLElement | null>(null)

let observer:
  IntersectionObserver | null = null

/* =========================================================
 * 排序
 *
 * world_latest.zip 永远置顶
 * ======================================================= */

const sortedFiles = computed(() => {
  const latestFile =
    files.value.find(
      (file) =>
        file.name === 'world_latest.zip'
    )

  const otherFiles =
    files.value.filter(
      (file) =>
        file.name !== 'world_latest.zip'
    )

  const sortedOthers =
    [...otherFiles]

  sortedOthers.sort((a, b) => {
    const nameA =
      a.name.toLowerCase()

    const nameB =
      b.name.toLowerCase()

    if (sortOrder.value === 'asc') {
      return nameA.localeCompare(nameB)
    }

    return nameB.localeCompare(nameA)
  })

  return latestFile
    ? [latestFile, ...sortedOthers]
    : sortedOthers
})

/* =========================================================
 * 获取备份列表
 * ======================================================= */

const fetchFiles = async () => {
  loading.value = true
  error.value = ''

  try {
    const data =
      await fetchBackupFiles()

    if (!Array.isArray(data)) {
      throw new Error(
        '响应数据格式错误'
      )
    }

    files.value = data

    /*
     * 刷新列表后清空下载状态。
     * 因为服务端文件列表可能已经发生变化。
     */
    Object.keys(
      downloadStates
    ).forEach((key) => {
      delete downloadStates[key]
    })
  } catch (err: any) {
    console.error(
      '获取世界备份列表失败:',
      err
    )

    error.value =
      err?.message ||
      '无法获取备份列表，请稍后重试'

    files.value = []
  } finally {
    loading.value = false
  }
}

/* =========================================================
 * 排序
 * ======================================================= */

const toggleSortOrder = () => {
  sortOrder.value =
    sortOrder.value === 'asc'
      ? 'desc'
      : 'asc'
}

/* =========================================================
 * 下载文件
 * ======================================================= */

const downloadFile = async (
  file: FileItem
) => {
  if (!downloadStates[file.name]) {
    downloadStates[file.name] = {
      status: 'idle',
      progress: 0
    }
  }

  /*
   * 如果之前已经完成，
   * 允许重新开始下载。
   */
  if (
    downloadStates[file.name].status ===
    'completed'
  ) {
    resetFileState(file.name)
  }

  downloadStates[file.name].status =
    'downloading'

  downloadStates[file.name].progress = 0

  try {
    const blob =
      await downloadBackupFile(
        file.name,
        (percent) => {
          if (
            downloadStates[file.name]
          ) {
            downloadStates[
              file.name
            ].progress = percent
          }
        }
      )

    /*
     * 创建浏览器下载
     */
    const objectUrl =
      URL.createObjectURL(blob)

    const link =
      document.createElement('a')

    link.href = objectUrl

    link.download = file.name

    document.body.appendChild(link)

    link.click()

    document.body.removeChild(link)

    URL.revokeObjectURL(objectUrl)

    /*
     * 更新状态
     */
    if (
      downloadStates[file.name]
    ) {
      downloadStates[
        file.name
      ].status = 'completed'

      downloadStates[
        file.name
      ].progress = 100
    }
  } catch (err) {
    console.error(
      '下载世界备份失败:',
      err
    )

    if (
      downloadStates[file.name]
    ) {
      downloadStates[
        file.name
      ].status = 'error'
    }
  }
}

/* =========================================================
 * 重新下载
 * ======================================================= */

const reDownload = (
  file: FileItem
) => {
  resetFileState(file.name)

  downloadFile(file)
}

/* =========================================================
 * 文件大小格式化
 * ======================================================= */

const formatSize = (
  bytes: number
): string => {
  if (!bytes || bytes <= 0) {
    return '0 B'
  }

  const units = [
    'B',
    'KB',
    'MB',
    'GB',
    'TB'
  ]

  const k = 1024

  const index = Math.floor(
    Math.log(bytes) /
    Math.log(k)
  )

  const safeIndex = Math.min(
    index,
    units.length - 1
  )

  const size =
    bytes /
    Math.pow(
      k,
      safeIndex
    )

  if (safeIndex === 0) {
    return `${Math.round(size)} ${units[safeIndex]}`
  }

  return `${size.toFixed(2)} ${units[safeIndex]}`
}

/* =========================================================
 * 日期格式化
 * ======================================================= */

const formatDate = (
  dateStr: string
): string => {
  const timestamp =
    Number(dateStr)

  const date =
    Number.isNaN(timestamp)
      ? new Date(dateStr)
      : new Date(timestamp)

  if (
    Number.isNaN(
      date.getTime()
    )
  ) {
    return dateStr
  }

  return date.toLocaleString(
    'zh-CN',
    {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
    }
  )
}

/* =========================================================
 * 生命周期
 * ======================================================= */

onMounted(() => {
  fetchFiles()

  if (!downloadSection.value) {
    return
  }

  observer =
    new IntersectionObserver(
      (entries) => {
        entries.forEach(
          (entry) => {
            if (
              entry.isIntersecting
            ) {
              entry.target.classList.add(
                'is-visible'
              )

              observer?.unobserve(
                entry.target
              )
            }
          }
        )
      },
      {
        threshold: 0.08,
        rootMargin:
          '0px 0px -50px 0px'
      }
    )

  observer.observe(
    downloadSection.value
  )
})

onUnmounted(() => {
  observer?.disconnect()
})
</script>

<style scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

/* =========================================================
 * 页面
 * ======================================================= */

.page-wrapper {
  min-height: 100vh;

  background:
    radial-gradient(circle at 15% 8%,
      rgba(109, 179, 242, 0.055),
      transparent 32%),
    radial-gradient(circle at 85% 25%,
      rgba(184, 212, 227, 0.035),
      transparent 28%),
    #0d0f14;

  color: #e8edf2;

  font-family:
    'Segoe UI',
    'PingFang SC',
    'Microsoft YaHei',
    Roboto,
    system-ui,
    -apple-system,
    sans-serif;

  padding: 42px 0 70px;
}

.container {
  width: min(1120px, 100%);

  margin: 0 auto;

  padding: 0 24px;
}

/* =========================================================
 * 页面动画
 * ======================================================= */

.section {
  opacity: 0;

  transform:
    translateY(28px);

  transition:
    opacity 0.8s ease,
    transform 0.8s ease;
}

.section.is-visible {
  opacity: 1;

  transform:
    translateY(0);
}

/* =========================================================
 * Header
 * ======================================================= */

.page-header {
  padding-bottom: 28px;

  margin-bottom: 28px;

  border-bottom:
    1px solid rgba(255, 255, 255, 0.07);
}

.eyebrow {
  color: #60788b;

  font-size: 10px;
  font-weight: 700;

  letter-spacing: 0.2em;

  margin-bottom: 13px;
}

.header-row {
  display: flex;

  align-items: flex-end;

  justify-content: space-between;

  gap: 20px;
}

.page-header h1 {
  font-size:
    clamp(32px, 5vw, 46px);

  line-height: 1.1;

  font-weight: 800;

  letter-spacing: -0.03em;

  background:
    linear-gradient(135deg,
      #f0e6d0,
      #b8d4e3 65%,
      #8aa3b9);

  -webkit-background-clip: text;

  background-clip: text;

  -webkit-text-fill-color: transparent;

  margin-bottom: 9px;
}

.subtitle {
  color: #8298aa;

  font-size: 15px;

  line-height: 1.7;
}

.version-badge {
  display: flex;

  align-items: center;

  gap: 8px;

  padding: 9px 14px;

  border-radius: 999px;

  background:
    rgba(255, 255, 255, 0.035);

  border:
    1px solid rgba(255, 255, 255, 0.08);

  color: #a8bdcc;

  font-size: 12px;

  white-space: nowrap;
}

.status-dot {
  width: 7px;
  height: 7px;

  border-radius: 50%;

  background: #7ac49a;

  box-shadow:
    0 0 12px rgba(122, 196, 154, 0.65);
}

/* =========================================================
 * Header Actions
 * ======================================================= */

.header-actions {
  display: flex;

  flex-wrap: wrap;

  align-items: center;

  gap: 9px;

  margin-top: 20px;
}

.action-button {
  padding: 8px 14px;

  border-radius: 9px;

  background:
    rgba(255, 255, 255, 0.035);

  border:
    1px solid rgba(255, 255, 255, 0.07);

  color: #a5bac8;

  font-size: 12px;

  cursor: pointer;

  transition:
    background 0.2s ease,
    border-color 0.2s ease,
    transform 0.2s ease;
}

.action-button:hover:not(:disabled) {
  background:
    rgba(255, 255, 255, 0.07);

  border-color:
    rgba(255, 255, 255, 0.12);

  transform:
    translateY(-1px);
}

.action-button:disabled {
  opacity: 0.45;

  cursor: not-allowed;
}

.file-count {
  padding: 5px 10px;

  border-radius: 999px;

  background:
    rgba(255, 255, 255, 0.025);

  border:
    1px solid rgba(255, 255, 255, 0.05);

  color: #657c8d;

  font-size: 11px;
}

/* =========================================================
 * Archive Intro
 * ======================================================= */

.archive-intro {
  display: flex;

  align-items: flex-start;

  gap: 17px;

  padding: 22px 24px;

  margin-bottom: 40px;

  border-radius: 17px;

  background:
    linear-gradient(135deg,
      rgba(255, 255, 255, 0.04),
      rgba(255, 255, 255, 0.016));

  border:
    1px solid rgba(255, 255, 255, 0.065);
}

.archive-icon {
  width: 45px;
  height: 45px;

  display: flex;

  align-items: center;
  justify-content: center;

  flex-shrink: 0;

  border-radius: 12px;

  background:
    rgba(109, 179, 242, 0.08);

  border:
    1px solid rgba(109, 179, 242, 0.13);

  font-size: 20px;
}

.archive-content h2 {
  color: #dce5eb;

  font-size: 18px;

  margin-bottom: 6px;
}

.archive-content p {
  color: #73899a;

  font-size: 13px;

  line-height: 1.8;

  max-width: 750px;
}

.archive-tags {
  display: flex;

  flex-wrap: wrap;

  gap: 7px;

  margin-top: 12px;
}

.archive-tags span {
  padding: 4px 9px;

  border-radius: 999px;

  background:
    rgba(255, 255, 255, 0.03);

  border:
    1px solid rgba(255, 255, 255, 0.05);

  color: #687e8f;

  font-size: 10px;
}

/* =========================================================
 * Section Heading
 * ======================================================= */

.archive-section {
  margin-top: 5px;
}

.section-heading {
  display: flex;

  align-items: flex-end;

  justify-content: space-between;

  gap: 20px;

  margin-bottom: 16px;
}

.section-label {
  display: block;

  color: #60788b;

  font-size: 10px;

  font-weight: 700;

  letter-spacing: 0.18em;

  margin-bottom: 5px;
}

.section-heading h2 {
  color: #dce5eb;

  font-size: 21px;

  font-weight: 650;
}

.section-count {
  color: #657c8e;

  font-size: 12px;
}

/* =========================================================
 * File List
 * ======================================================= */

.file-list {
  display: flex;

  flex-direction: column;

  gap: 9px;
}

.file-card {
  display: flex;

  align-items: center;

  gap: 16px;

  min-height: 78px;

  padding: 14px 18px;

  border-radius: 14px;

  background:
    rgba(255, 255, 255, 0.025);

  border:
    1px solid rgba(255, 255, 255, 0.05);

  transition:
    background 0.25s ease,
    border-color 0.25s ease,
    transform 0.25s ease,
    box-shadow 0.25s ease;
}

.file-card:hover {
  background:
    rgba(255, 255, 255, 0.045);

  border-color:
    rgba(255, 255, 255, 0.09);

  transform:
    translateX(3px);

  box-shadow:
    0 10px 35px rgba(0, 0, 0, 0.12);
}

/* 最新备份 */

.file-card.latest-file {
  background:
    linear-gradient(135deg,
      rgba(109, 179, 242, 0.06),
      rgba(255, 255, 255, 0.02));

  border-color:
    rgba(109, 179, 242, 0.14);
}

.file-card.latest-file:hover {
  border-color:
    rgba(109, 179, 242, 0.25);
}

/* =========================================================
 * File Icon
 * ======================================================= */

.file-icon {
  width: 43px;
  height: 43px;

  display: flex;

  align-items: center;
  justify-content: center;

  flex-shrink: 0;

  border-radius: 11px;

  background:
    rgba(255, 255, 255, 0.035);

  border:
    1px solid rgba(255, 255, 255, 0.055);

  font-size: 19px;
}

.latest-file .file-icon {
  background:
    rgba(109, 179, 242, 0.08);

  border-color:
    rgba(109, 179, 242, 0.12);
}

/* =========================================================
 * File Info
 * ======================================================= */

.file-info {
  flex: 1;

  min-width: 0;
}

.file-title-row {
  display: flex;

  align-items: center;

  flex-wrap: wrap;

  gap: 8px;
}

.file-name {
  color: #dce5eb;

  font-size: 14px;

  font-weight: 550;

  word-break: break-all;
}

.latest-badge {
  padding: 3px 7px;

  border-radius: 999px;

  background:
    rgba(109, 179, 242, 0.08);

  border:
    1px solid rgba(109, 179, 242, 0.13);

  color: #82b4d5;

  font-size: 9px;

  white-space: nowrap;
}

.file-meta {
  display: flex;

  flex-wrap: wrap;

  align-items: center;

  gap: 7px;

  margin-top: 5px;

  color: #657b8c;

  font-size: 11px;
}

.meta-separator {
  color: #3f5362;
}

/* =========================================================
 * Download
 * ======================================================= */

.download-area {
  flex-shrink: 0;

  min-width: 150px;

  display: flex;

  align-items: flex-end;

  justify-content: center;
}

.download-button {
  display: inline-flex;

  align-items: center;
  justify-content: center;

  gap: 6px;

  min-width: 94px;

  padding: 8px 15px;

  border: 0;

  border-radius: 9px;

  background:
    linear-gradient(135deg,
      #6db3f2,
      #4a8bc2);

  color: white;

  font-size: 12px;

  font-weight: 550;

  cursor: pointer;

  box-shadow:
    0 5px 18px rgba(109, 179, 242, 0.12);

  transition:
    transform 0.2s ease,
    box-shadow 0.2s ease;
}

.download-button:hover {
  transform:
    translateY(-1px);

  box-shadow:
    0 8px 25px rgba(109, 179, 242, 0.22);
}

/* =========================================================
 * Progress
 * ======================================================= */

.progress-container {
  width: 160px;
}

.progress-top {
  display: flex;

  align-items: center;

  justify-content: space-between;

  margin-bottom: 6px;

  color: #71889a;

  font-size: 10px;
}

.progress-top strong {
  color: #8fcbff;

  font-weight: 600;
}

.progress-bar {
  width: 100%;

  height: 5px;

  overflow: hidden;

  border-radius: 999px;

  background:
    rgba(255, 255, 255, 0.07);
}

.progress-fill {
  height: 100%;

  border-radius: inherit;

  background:
    linear-gradient(90deg,
      #6db3f2,
      #4a8bc2);

  transition:
    width 0.25s ease;
}

/* =========================================================
 * Download Result
 * ======================================================= */

.download-result {
  display: flex;

  align-items: center;

  justify-content: flex-end;

  flex-wrap: wrap;

  gap: 8px;
}

.completed-text {
  color: #70bd92;

  font-size: 11px;

  white-space: nowrap;
}

.error-text {
  color: #d67d7d;

  font-size: 11px;

  white-space: nowrap;
}

.secondary-button,
.retry-download {
  padding: 5px 9px;

  border-radius: 7px;

  background:
    rgba(255, 255, 255, 0.035);

  border:
    1px solid rgba(255, 255, 255, 0.07);

  color: #91a8b8;

  font-size: 10px;

  cursor: pointer;

  transition:
    all 0.2s ease;
}

.secondary-button:hover {
  background:
    rgba(255, 255, 255, 0.07);

  color: #c0d0da;
}

.retry-download {
  color: #d88a8a;

  border-color:
    rgba(220, 100, 100, 0.16);
}

.retry-download:hover {
  background:
    rgba(220, 100, 100, 0.08);
}

/* =========================================================
 * State
 * ======================================================= */

.state-card {
  min-height: 240px;

  display: flex;

  flex-direction: column;

  align-items: center;

  justify-content: center;

  gap: 10px;

  padding: 30px 20px;

  border-radius: 18px;

  background:
    rgba(255, 255, 255, 0.02);

  border:
    1px solid rgba(255, 255, 255, 0.055);

  text-align: center;
}

.state-card p {
  color: #718798;

  font-size: 13px;
}

.state-card h3 {
  color: #cdd9e1;

  font-size: 16px;
}

.state-icon {
  font-size: 29px;

  opacity: 0.7;

  margin-bottom: 2px;
}

.spinner {
  width: 40px;
  height: 40px;

  border: 3px solid rgba(255, 255, 255, 0.06);

  border-top-color:
    #6db3f2;

  border-radius: 50%;

  animation:
    spin 0.8s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.error-card {
  background:
    rgba(255, 80, 80, 0.035);

  border-color:
    rgba(255, 100, 100, 0.1);
}

.error-card p {
  color: #b97f7f;
}

.retry-button {
  margin-top: 5px;

  padding: 8px 17px;

  border-radius: 9px;

  background:
    rgba(255, 255, 255, 0.045);

  border:
    1px solid rgba(255, 255, 255, 0.08);

  color: #afc1cd;

  font-size: 12px;

  cursor: pointer;

  transition:
    all 0.2s ease;
}

.retry-button:hover {
  background:
    rgba(255, 255, 255, 0.08);

  transform:
    translateY(-1px);
}

/* =========================================================
 * Empty
 * ======================================================= */

.empty-state {
  display: flex;

  flex-direction: column;

  align-items: center;

  justify-content: center;

  min-height: 220px;

  padding: 30px;

  border-radius: 17px;

  border:
    1px dashed rgba(255, 255, 255, 0.08);

  background:
    rgba(255, 255, 255, 0.015);

  text-align: center;
}

.empty-icon {
  font-size: 36px;

  opacity: 0.55;

  margin-bottom: 6px;
}

.empty-state h3 {
  color: #b7c6d0;

  font-size: 15px;

  margin-bottom: 5px;
}

.empty-state p {
  color: #647a8a;

  font-size: 12px;
}

/* =========================================================
 * Notice
 * ======================================================= */

.notice-card {
  display: flex;

  align-items: flex-start;

  gap: 13px;

  margin-top: 36px;

  padding: 18px 20px;

  border-radius: 14px;

  background:
    rgba(255, 255, 255, 0.018);

  border:
    1px solid rgba(255, 255, 255, 0.045);
}

.notice-icon {
  display: flex;

  align-items: center;
  justify-content: center;

  width: 25px;
  height: 25px;

  flex-shrink: 0;

  border-radius: 50%;

  background:
    rgba(109, 179, 242, 0.08);

  color: #7395ab;

  font-size: 12px;

  font-weight: 700;
}

.notice-content h3 {
  color: #9eb2c0;

  font-size: 13px;

  margin-bottom: 6px;
}

.notice-content p {
  color: #617788;

  font-size: 11px;

  line-height: 1.8;

  margin-bottom: 3px;
}

.notice-content p:last-child {
  margin-bottom: 0;
}

/* =========================================================
 * Community
 * ======================================================= */

.community-card {
  display: flex;

  align-items: center;

  justify-content: space-between;

  gap: 30px;

  margin-top: 36px;

  padding: 25px;

  border-radius: 18px;

  background:
    linear-gradient(135deg,
      rgba(255, 255, 255, 0.035),
      rgba(255, 255, 255, 0.015));

  border:
    1px solid rgba(255, 255, 255, 0.06);
}

.community-content {
  max-width: 620px;
}

.community-content h2 {
  color: #dfe7ec;

  font-size: 21px;

  margin-bottom: 7px;
}

.community-content p {
  color: #6d8293;

  font-size: 12px;

  line-height: 1.8;
}

.community-meta {
  display: flex;

  gap: 22px;

  flex-shrink: 0;
}

.community-meta div {
  display: flex;

  flex-direction: column;

  gap: 4px;
}

.community-meta span {
  color: #5d7384;

  font-size: 10px;
}

.community-meta strong {
  color: #a9bfcc;

  font-size: 12px;
}

/* =========================================================
 * Responsive
 * ======================================================= */

@media (max-width: 768px) {
  .page-wrapper {
    padding:
      28px 0 50px;
  }

  .container {
    padding:
      0 18px;
  }

  .header-row {
    align-items: flex-start;

    flex-direction: column;
  }

  .version-badge {
    margin-top: 2px;
  }

  .archive-intro {
    padding: 18px;
  }

  .file-card {
    align-items: flex-start;

    flex-wrap: wrap;

    padding: 15px;
  }

  .file-info {
    padding-top: 2px;
  }

  .download-area {
    width: 100%;

    min-width: 0;

    margin-left: 59px;

    justify-content: flex-start;
  }

  .download-button {
    min-width: 120px;
  }

  .progress-container {
    width: 100%;
  }

  .download-result {
    justify-content: flex-start;
  }

  .community-card {
    align-items: flex-start;

    flex-direction: column;
  }

  .community-meta {
    width: 100%;

    justify-content: space-between;
  }
}

@media (max-width: 480px) {
  .page-wrapper {
    padding-top: 22px;
  }

  .container {
    padding:
      0 14px;
  }

  .page-header h1 {
    font-size: 30px;
  }

  .subtitle {
    font-size: 13px;
  }

  .header-actions {
    display: grid;

    grid-template-columns:
      1fr 1fr;
  }

  .action-button {
    width: 100%;
  }

  .file-count {
    grid-column:
      1 / -1;

    text-align: center;
  }

  .archive-intro {
    flex-direction: column;

    gap: 12px;
  }

  .section-heading {
    align-items: flex-start;

    flex-direction: column;

    gap: 5px;
  }

  .file-card {
    flex-direction: column;

    align-items: stretch;

    gap: 11px;
  }

  .file-icon {
    align-self: flex-start;
  }

  .download-area {
    width: 100%;

    margin-left: 0;

    align-items: stretch;
  }

  .download-button {
    width: 100%;
  }

  .download-result {
    justify-content: space-between;
  }

  .community-meta {
    display: grid;

    grid-template-columns:
      repeat(2, 1fr);

    gap: 15px;
  }
}

@media (prefers-reduced-motion: reduce) {
  .section {
    opacity: 1;

    transform: none;

    transition: none;
  }

  .spinner {
    animation: none;
  }

  .file-card,
  .action-button,
  .download-button,
  .retry-button {
    transition: none;
  }
}
</style>