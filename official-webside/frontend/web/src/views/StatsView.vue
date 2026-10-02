<template>
  <div class="page-wrapper">
    <section
      ref="statsSection"
      class="section stats-section"
    >
      <div class="container">

        <!-- 页面头部 -->
        <header class="page-header">
          <div class="eyebrow">
            SWAN TRACE · PLAYER DATA
          </div>

          <div class="header-row">
            <div>
              <h1>玩家数据</h1>
              <p class="subtitle">
                记录鸿迹社区玩家在 Minecraft 26.2 中的游戏数据
              </p>
            </div>

            <div class="version-badge">
              <span class="status-dot"></span>
              Paper 26.2
            </div>
          </div>
        </header>

        <!-- 社区说明 -->
        <section class="intro-card">
          <div class="intro-icon">📊</div>

          <div class="intro-content">
            <h2>鸿迹 · 玩家数据</h2>

            <p>
              这里展示服务器记录的玩家统计数据。
              数据用于社区信息展示与游戏记录，不代表玩家之间的强制竞争。
            </p>

            <div class="intro-tags">
              <span>纯净</span>
              <span>自由</span>
              <span>社区记录</span>
              <span>Minecraft 26.2</span>
            </div>
          </div>
        </section>

        <!-- 加载统计项目 -->
        <div
          v-if="loadingStats"
          class="state-card"
        >
          <div class="spinner"></div>
          <p>正在加载玩家统计数据...</p>
        </div>

        <!-- 统计项目加载失败 -->
        <div
          v-else-if="statsError"
          class="state-card error-card"
        >
          <div class="state-icon">⚠️</div>

          <h3>统计数据加载失败</h3>
          <p>{{ statsError }}</p>

          <button
            class="retry-button"
            type="button"
            @click="fetchStatsData"
          >
            重新加载
          </button>
        </div>

        <!-- 主内容 -->
        <div v-else>

          <!-- 统计维度 -->
          <section
            v-if="categories.length"
            class="data-section"
          >
            <div class="section-title">
              <div>
                <span class="section-label">CATEGORY</span>
                <h2>数据维度</h2>
              </div>

              <span class="section-count">
                {{ categories.length }} 个维度
              </span>
            </div>

            <div class="category-tabs">
              <button
                v-for="category in categories"
                :key="category"
                type="button"
                class="category-tab"
                :class="{
                  active: selectedCategory === category
                }"
                @click="selectCategory(category)"
              >
                <span class="category-icon">
                  {{ categoryIcon(category) }}
                </span>

                <span>
                  {{ categoryDisplayName(category) }}
                </span>
              </button>
            </div>
          </section>

          <!-- 具体统计项目 -->
          <section
            v-if="currentStats.length"
            class="data-section"
          >
            <div class="section-title">
              <div>
                <span class="section-label">STATISTICS</span>
                <h2>统计项目</h2>
              </div>

              <span class="section-count">
                {{ currentStats.length }} 项
              </span>
            </div>

            <div class="stat-tabs">
              <button
                v-for="stat in currentStats"
                :key="stat.key"
                type="button"
                class="stat-tab"
                :class="{
                  active: selectedStatKey === stat.key
                }"
                @click="selectStat(stat.key)"
              >
                {{ stat.display }}
              </button>
            </div>
          </section>

          <!-- 当前统计项目 -->
          <section class="leaderboard-section">

            <div class="leaderboard-heading">
              <div>
                <span class="section-label">LEADERBOARD</span>

                <h2>
                  {{ leaderboardData?.display || '玩家排行榜' }}
                </h2>

                <p v-if="leaderboardData">
                  根据服务器当前记录的数据进行排序
                </p>
              </div>

              <div
                v-if="leaderboardData"
                class="format-badge"
              >
                {{ leaderboardData.format }}
              </div>
            </div>

            <!-- 排行榜加载 -->
            <div
              v-if="loadingLeaderboard"
              class="state-card compact"
            >
              <div class="spinner small"></div>
              <p>正在加载排行榜...</p>
            </div>

            <!-- 排行榜错误 -->
            <div
              v-else-if="leaderboardError"
              class="state-card error-card compact"
            >
              <div class="state-icon">⚠️</div>

              <p>{{ leaderboardError }}</p>

              <button
                class="retry-button"
                type="button"
                @click="retryLeaderboard"
              >
                重试
              </button>
            </div>

            <!-- 没有数据 -->
            <div
              v-else-if="
                !leaderboardData ||
                !leaderboardData.entries.length
              "
              class="state-card empty-card"
            >
              <div class="state-icon">📊</div>

              <h3>暂无统计数据</h3>

              <p>
                当前统计项目暂时没有可展示的玩家数据。
              </p>
            </div>

            <!-- 排行榜 -->
            <div
              v-else
              class="leaderboard-list"
            >

              <!-- 第一名 -->
              <div
                v-if="leaderboardData.entries[0]"
                class="featured-player"
              >
                <div class="featured-rank">
                  #1
                </div>

                <div class="featured-info">
                  <span class="featured-label">
                    当前记录最高
                  </span>

                  <strong>
                    {{ leaderboardData.entries[0].name }}
                  </strong>

                  <span class="featured-uuid">
                    {{ shortUUID(leaderboardData.entries[0].uuid) }}
                  </span>
                </div>

                <div class="featured-value">
                  {{ leaderboardData.entries[0].value_human || leaderboardData.entries[0].value }}
                </div>
              </div>

              <!-- 其余玩家 -->
              <div
                v-for="entry in leaderboardData.entries.slice(1)"
                :key="`${entry.uuid}-${entry.rank}`"
                class="leaderboard-item"
              >
                <div class="rank-number">
                  {{ entry.rank }}
                </div>

                <div class="player-info">
                  <strong class="player-name">
                    {{ entry.name }}
                  </strong>

                  <span class="player-uuid">
                    {{ shortUUID(entry.uuid) }}
                  </span>
                </div>

                <div class="player-value">
                  {{ entry.value_human || entry.value }}
                </div>
              </div>
            </div>

          </section>

          <!-- 数据说明 -->
          <section class="data-note">
            <div class="note-icon">ℹ</div>

            <div>
              <strong>关于玩家统计</strong>

              <p>
                玩家数据来自服务器统计记录。
                部分统计数据可能受到 Minecraft 版本、
                世界重置、服务器维护或数据恢复等因素影响。
              </p>

              <p>
                排行榜仅用于查看社区游戏记录，
                不代表玩家身份、权限或社区管理等级。
              </p>
            </div>
          </section>

          <!-- 社区信息 -->
          <section class="community-card">
            <div class="community-content">
              <span class="section-label">
                SWAN TRACE COMMUNITY
              </span>

              <h2>纯净 · 自由</h2>

              <p>
                鸿迹是一个个人公益 Minecraft 社区服务器。
                我们希望这里能够成为一个轻松、自由的游戏空间。
              </p>
            </div>

            <div class="community-meta">
              <div>
                <span>版本</span>
                <strong>26.2</strong>
              </div>

              <div>
                <span>最大在线</span>
                <strong>20</strong>
              </div>

              <div>
                <span>平台</span>
                <strong>Java / Bedrock</strong>
              </div>
            </div>
          </section>

        </div>

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
  ref
} from 'vue'

import FooterC from '@/components/FooterC.vue'
import {
  fetchLeaderboard,
  fetchStats
} from '@/api/stats'

/* =========================================================
 * 类型定义
 * ======================================================= */

interface StatItem {
  key: string
  display: string
  category: string
  format: string
}

interface LeaderboardEntry {
  rank: number
  uuid: string
  name: string
  value: number
  value_human?: string
}

interface LeaderboardResponse {
  stat: string
  display: string
  format: string
  entries: LeaderboardEntry[]
}

/* =========================================================
 * 响应式状态
 * ======================================================= */

const stats = ref<StatItem[]>([])

const categories = ref<string[]>([])

const selectedCategory = ref('')

const selectedStatKey = ref('')

const leaderboardData =
  ref<LeaderboardResponse | null>(null)

const loadingStats = ref(false)

const statsError = ref('')

const loadingLeaderboard = ref(false)

const leaderboardError = ref('')

const statsSection =
  ref<HTMLElement | null>(null)

let observer: IntersectionObserver | null = null

/* =========================================================
 * 当前分类下的统计项目
 * ======================================================= */

const currentStats = computed(() => {
  if (!selectedCategory.value) {
    return []
  }

  return stats.value.filter(
    (stat) =>
      stat.category === selectedCategory.value
  )
})

/* =========================================================
 * 获取统计项目
 * ======================================================= */

const fetchStatsData = async () => {
  loadingStats.value = true
  statsError.value = ''

  try {
    const data = await fetchStats()

    if (!data || !Array.isArray(data.stats)) {
      throw new Error('统计数据格式错误')
    }

    stats.value = data.stats

    // 提取分类
    const categorySet = new Set<string>()

    stats.value.forEach((stat) => {
      categorySet.add(stat.category)
    })

    categories.value =
      Array.from(categorySet)

    // 默认选择第一个分类
    if (categories.value.length > 0) {
      selectedCategory.value =
        categories.value[0]

      const firstStat =
        stats.value.find(
          (stat) =>
            stat.category ===
            selectedCategory.value
        )

      if (firstStat) {
        selectedStatKey.value =
          firstStat.key

        await fetchLeaderboardData(
          firstStat.key
        )
      }
    }
  } catch (error: any) {
    console.error(
      '获取玩家统计数据失败:',
      error
    )

    statsError.value =
      error?.message ||
      '无法加载玩家统计数据，请稍后重试'

    stats.value = []
    categories.value = []
    leaderboardData.value = null
  } finally {
    loadingStats.value = false
  }
}

/* =========================================================
 * 选择统计分类
 * ======================================================= */

const selectCategory = (category: string) => {
  if (
    selectedCategory.value === category
  ) {
    return
  }

  selectedCategory.value = category

  const firstStat =
    stats.value.find(
      (stat) =>
        stat.category === category
    )

  if (!firstStat) {
    selectedStatKey.value = ''
    leaderboardData.value = null
    return
  }

  selectedStatKey.value =
    firstStat.key

  fetchLeaderboardData(
    firstStat.key
  )
}

/* =========================================================
 * 选择具体统计项目
 * ======================================================= */

const selectStat = (key: string) => {
  if (selectedStatKey.value === key) {
    return
  }

  selectedStatKey.value = key

  fetchLeaderboardData(key)
}

/* =========================================================
 * 获取排行榜
 * ======================================================= */

const fetchLeaderboardData = async (
  key: string
) => {
  if (!key) {
    return
  }

  loadingLeaderboard.value = true
  leaderboardError.value = ''

  try {
    const data =
      await fetchLeaderboard(key)

    leaderboardData.value = data
  } catch (error: any) {
    console.error(
      '获取排行榜失败:',
      error
    )

    leaderboardError.value =
      error?.message ||
      '加载排行榜失败，请稍后重试'

    leaderboardData.value = null
  } finally {
    loadingLeaderboard.value = false
  }
}

/* =========================================================
 * 重试排行榜
 * ======================================================= */

const retryLeaderboard = () => {
  if (!selectedStatKey.value) {
    return
  }

  fetchLeaderboardData(
    selectedStatKey.value
  )
}

/* =========================================================
 * UUID 简写
 * ======================================================= */

const shortUUID = (
  uuid: string
): string => {
  if (!uuid) {
    return ''
  }

  const parts = uuid.split('-')

  if (parts.length === 5) {
    return `${parts[0]}-${parts[1]}-...-${parts[4]}`
  }

  if (uuid.length > 16) {
    return `${uuid.slice(0, 8)}...`
  }

  return uuid
}

/* =========================================================
 * 分类名称
 * ======================================================= */

const categoryDisplayName = (
  category: string
): string => {
  const map: Record<string, string> = {
    time: '时间',
    blocks: '方块',
    combat: '战斗',
    movement: '移动',
    interaction: '交互',
    progression: '成长',
    economy: '经济',
    exploration: '探索',
    misc: '其他'
  }

  return map[category] || category
}

/* =========================================================
 * 分类图标
 * ======================================================= */

const categoryIcon = (
  category: string
): string => {
  const map: Record<string, string> = {
    time: '⏱',
    blocks: '🧱',
    combat: '⚔',
    movement: '🚶',
    interaction: '🤝',
    progression: '📈',
    economy: '◇',
    exploration: '🗺',
    misc: '•'
  }

  return map[category] || '•'
}

/* =========================================================
 * 页面进入动画
 * ======================================================= */

onMounted(() => {
  fetchStatsData()

  if (!statsSection.value) {
    return
  }

  observer =
    new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add(
              'is-visible'
            )

            observer?.unobserve(
              entry.target
            )
          }
        })
      },
      {
        threshold: 0.08,
        rootMargin:
          '0px 0px -50px 0px'
      }
    )

  observer.observe(
    statsSection.value
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

.page-wrapper {
  min-height: 100vh;
  background:
    radial-gradient(
      circle at 15% 10%,
      rgba(109, 179, 242, 0.06),
      transparent 32%
    ),
    radial-gradient(
      circle at 85% 30%,
      rgba(184, 212, 227, 0.04),
      transparent 28%
    ),
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

  min-height: 100vh;
  padding: 42px 0 70px;
}

.container {
  width: min(1120px, 100%);
  margin: 0 auto;
  padding: 0 24px;
}

.section {
  opacity: 0;
  transform: translateY(28px);

  transition:
    opacity 0.8s ease,
    transform 0.8s ease;
}

.section.is-visible {
  opacity: 1;
  transform: translateY(0);
}

/* =========================================================
 * Header
 * ======================================================= */

.page-header {
  padding-bottom: 28px;
  margin-bottom: 28px;

  border-bottom:
    1px solid
    rgba(255, 255, 255, 0.07);
}

.eyebrow {
  display: inline-flex;

  color: #7894aa;

  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.18em;

  margin-bottom: 14px;
}

.header-row {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;

  gap: 20px;
}

.page-header h1 {
  font-size: clamp(32px, 5vw, 46px);
  line-height: 1.1;

  font-weight: 800;
  letter-spacing: -0.03em;

  background:
    linear-gradient(
      135deg,
      #f0e6d0,
      #b8d4e3 65%,
      #8aa3b9
    );

  -webkit-background-clip: text;
  background-clip: text;

  -webkit-text-fill-color: transparent;

  margin-bottom: 10px;
}

.subtitle {
  color: #8298aa;

  font-size: 15px;
  font-weight: 300;

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
    1px solid
    rgba(255, 255, 255, 0.08);

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
    0 0 12px
    rgba(122, 196, 154, 0.65);
}

/* =========================================================
 * Intro
 * ======================================================= */

.intro-card {
  display: flex;
  align-items: flex-start;

  gap: 18px;

  padding: 22px 24px;

  margin-bottom: 42px;

  background:
    linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.045),
      rgba(255, 255, 255, 0.018)
    );

  border:
    1px solid
    rgba(255, 255, 255, 0.07);

  border-radius: 18px;

  box-shadow:
    0 18px 50px
    rgba(0, 0, 0, 0.12);
}

.intro-icon {
  display: flex;
  align-items: center;
  justify-content: center;

  width: 46px;
  height: 46px;

  flex-shrink: 0;

  border-radius: 13px;

  background:
    rgba(109, 179, 242, 0.1);

  border:
    1px solid
    rgba(109, 179, 242, 0.15);

  font-size: 21px;
}

.intro-content h2 {
  color: #e7edf2;

  font-size: 18px;
  font-weight: 650;

  margin-bottom: 7px;
}

.intro-content p {
  max-width: 760px;

  color: #7f94a6;

  font-size: 14px;
  line-height: 1.8;
}

.intro-tags {
  display: flex;
  flex-wrap: wrap;

  gap: 7px;

  margin-top: 13px;
}

.intro-tags span {
  padding: 4px 9px;

  border-radius: 999px;

  background:
    rgba(255, 255, 255, 0.035);

  border:
    1px solid
    rgba(255, 255, 255, 0.055);

  color: #73899a;

  font-size: 11px;
}

/* =========================================================
 * Section title
 * ======================================================= */

.data-section {
  margin-bottom: 36px;
}

.section-title {
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

.section-title h2 {
  color: #dce5eb;

  font-size: 21px;
  font-weight: 650;
}

.section-count {
  color: #657c8e;

  font-size: 12px;
}

/* =========================================================
 * Category
 * ======================================================= */

.category-tabs {
  display: flex;
  flex-wrap: wrap;

  gap: 8px;
}

.category-tab {
  display: inline-flex;
  align-items: center;

  gap: 8px;

  padding: 10px 15px;

  border-radius: 11px;

  background:
    rgba(255, 255, 255, 0.025);

  border:
    1px solid
    rgba(255, 255, 255, 0.065);

  color: #8297a8;

  font-size: 13px;

  cursor: pointer;

  transition:
    background 0.2s ease,
    border-color 0.2s ease,
    color 0.2s ease,
    transform 0.2s ease;
}

.category-tab:hover {
  color: #c1d2dd;

  background:
    rgba(255, 255, 255, 0.055);

  transform: translateY(-1px);
}

.category-tab.active {
  color: #c4dbea;

  background:
    rgba(109, 179, 242, 0.1);

  border-color:
    rgba(109, 179, 242, 0.35);

  box-shadow:
    0 8px 25px
    rgba(109, 179, 242, 0.05);
}

.category-icon {
  font-size: 14px;
}

/* =========================================================
 * Stats
 * ======================================================= */

.stat-tabs {
  display: flex;
  flex-wrap: wrap;

  gap: 7px;

  padding: 15px;

  border-radius: 15px;

  background:
    rgba(255, 255, 255, 0.018);

  border:
    1px solid
    rgba(255, 255, 255, 0.05);
}

.stat-tab {
  padding: 7px 13px;

  border-radius: 8px;

  background:
    transparent;

  border:
    1px solid
    transparent;

  color: #728798;

  font-size: 12px;

  cursor: pointer;

  transition:
    all 0.2s ease;
}

.stat-tab:hover {
  color: #b7c9d5;

  background:
    rgba(255, 255, 255, 0.04);
}

.stat-tab.active {
  color: #dce7ee;

  background:
    rgba(255, 255, 255, 0.065);

  border-color:
    rgba(255, 255, 255, 0.09);
}

/* =========================================================
 * Leaderboard
 * ======================================================= */

.leaderboard-section {
  margin-top: 44px;
}

.leaderboard-heading {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;

  gap: 20px;

  padding-bottom: 18px;

  border-bottom:
    1px solid
    rgba(255, 255, 255, 0.065);

  margin-bottom: 18px;
}

.leaderboard-heading h2 {
  color: #e1e9ee;

  font-size: 25px;
  font-weight: 700;

  margin-bottom: 5px;
}

.leaderboard-heading p {
  color: #667d8f;

  font-size: 12px;
}

.format-badge {
  padding: 5px 10px;

  border-radius: 999px;

  color: #73899a;

  background:
    rgba(255, 255, 255, 0.035);

  border:
    1px solid
    rgba(255, 255, 255, 0.055);

  font-size: 11px;
}

/* =========================================================
 * 第一名突出显示
 * ======================================================= */

.featured-player {
  display: flex;
  align-items: center;

  gap: 20px;

  padding: 24px;

  margin-bottom: 12px;

  border-radius: 17px;

  background:
    linear-gradient(
      135deg,
      rgba(109, 179, 242, 0.09),
      rgba(255, 255, 255, 0.025)
    );

  border:
    1px solid
    rgba(109, 179, 242, 0.18);

  box-shadow:
    0 15px 45px
    rgba(0, 0, 0, 0.14);
}

.featured-rank {
  min-width: 55px;

  color: #b8d4e3;

  font-size: 24px;
  font-weight: 750;
}

.featured-info {
  flex: 1;

  display: flex;
  flex-direction: column;

  gap: 3px;
}

.featured-label {
  color: #5f788c;

  font-size: 10px;

  letter-spacing: 0.12em;
}

.featured-info strong {
  color: #e5edf2;

  font-size: 19px;
}

.featured-uuid {
  color: #607789;

  font-family:
    'JetBrains Mono',
    'Cascadia Code',
    monospace;

  font-size: 11px;
}

.featured-value {
  padding: 8px 14px;

  border-radius: 10px;

  color: #c4dce9;

  background:
    rgba(109, 179, 242, 0.08);

  font-size: 16px;
  font-weight: 600;

  white-space: nowrap;
}

/* =========================================================
 * 普通排行榜
 * ======================================================= */

.leaderboard-list {
  display: flex;
  flex-direction: column;

  gap: 8px;
}

.leaderboard-item {
  display: flex;
  align-items: center;

  gap: 17px;

  min-height: 64px;

  padding: 11px 18px;

  border-radius: 13px;

  background:
    rgba(255, 255, 255, 0.025);

  border:
    1px solid
    rgba(255, 255, 255, 0.045);

  transition:
    background 0.2s ease,
    border-color 0.2s ease,
    transform 0.2s ease;
}

.leaderboard-item:hover {
  background:
    rgba(255, 255, 255, 0.045);

  border-color:
    rgba(255, 255, 255, 0.08);

  transform: translateX(3px);
}

.rank-number {
  width: 36px;

  flex-shrink: 0;

  color: #63798b;

  font-size: 15px;
  font-weight: 650;

  text-align: center;

  font-variant-numeric:
    tabular-nums;
}

.player-info {
  flex: 1;

  min-width: 0;

  display: flex;
  align-items: center;
  flex-wrap: wrap;

  gap: 7px 14px;
}

.player-name {
  color: #d8e2e8;

  font-size: 15px;
  font-weight: 600;
}

.player-uuid {
  color: #5e7486;

  font-family:
    'JetBrains Mono',
    'Cascadia Code',
    monospace;

  font-size: 10px;
}

.player-value {
  padding: 5px 11px;

  border-radius: 8px;

  background:
    rgba(255, 255, 255, 0.035);

  color: #a9c1cf;

  font-size: 13px;
  font-weight: 500;

  white-space: nowrap;
}

/* =========================================================
 * State
 * ======================================================= */

.state-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  min-height: 230px;

  padding: 35px 20px;

  border-radius: 18px;

  background:
    rgba(255, 255, 255, 0.02);

  border:
    1px solid
    rgba(255, 255, 255, 0.055);

  text-align: center;

  gap: 10px;
}

.state-card.compact {
  min-height: 170px;
}

.state-card h3 {
  color: #cdd9e1;

  font-size: 16px;
}

.state-card p {
  max-width: 480px;

  color: #708698;

  font-size: 13px;
  line-height: 1.7;
}

.state-icon {
  font-size: 28px;

  opacity: 0.7;

  margin-bottom: 3px;
}

.spinner {
  width: 40px;
  height: 40px;

  border: 3px solid
    rgba(255, 255, 255, 0.06);

  border-top-color:
    #79b4dd;

  border-radius: 50%;

  animation:
    spin 0.8s linear infinite;

  margin-bottom: 7px;
}

.spinner.small {
  width: 30px;
  height: 30px;
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
  color: #b78484;
}

.retry-button {
  margin-top: 5px;

  padding: 8px 18px;

  border-radius: 9px;

  border:
    1px solid
    rgba(255, 255, 255, 0.09);

  background:
    rgba(255, 255, 255, 0.045);

  color: #b9cad5;

  font-size: 12px;

  cursor: pointer;

  transition:
    all 0.2s ease;
}

.retry-button:hover {
  background:
    rgba(255, 255, 255, 0.08);

  transform: translateY(-1px);
}

/* =========================================================
 * 数据说明
 * ======================================================= */

.data-note {
  display: flex;
  align-items: flex-start;

  gap: 13px;

  margin-top: 38px;
  padding: 18px 20px;

  border-radius: 14px;

  background:
    rgba(255, 255, 255, 0.018);

  border:
    1px solid
    rgba(255, 255, 255, 0.045);
}

.note-icon {
  display: flex;
  align-items: center;
  justify-content: center;

  width: 25px;
  height: 25px;

  flex-shrink: 0;

  border-radius: 50%;

  background:
    rgba(109, 179, 242, 0.08);

  color: #7195ae;

  font-size: 12px;
  font-weight: 700;
}

.data-note strong {
  display: block;

  color: #9eb2c0;

  font-size: 13px;

  margin-bottom: 5px;
}

.data-note p {
  color: #627889;

  font-size: 11px;
  line-height: 1.8;

  margin-bottom: 3px;
}

.data-note p:last-child {
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

  margin-top: 38px;
  padding: 25px;

  border-radius: 18px;

  background:
    linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.035),
      rgba(255, 255, 255, 0.015)
    );

  border:
    1px solid
    rgba(255, 255, 255, 0.06);
}

.community-content {
  max-width: 600px;
}

.community-content h2 {
  color: #e0e8ed;

  font-size: 22px;

  margin-bottom: 6px;
}

.community-content p {
  color: #6d8293;

  font-size: 12px;
  line-height: 1.8;
}

.community-meta {
  display: flex;

  gap: 20px;

  flex-shrink: 0;
}

.community-meta div {
  display: flex;
  flex-direction: column;

  gap: 3px;
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
    padding: 28px 0 50px;
  }

  .container {
    padding: 0 18px;
  }

  .header-row {
    align-items: flex-start;

    flex-direction: column;
  }

  .version-badge {
    margin-top: 2px;
  }

  .intro-card {
    padding: 18px;
  }

  .leaderboard-heading {
    align-items: flex-start;

    flex-direction: column;
  }

  .featured-player {
    padding: 19px;

    gap: 13px;
  }

  .featured-rank {
    min-width: 42px;

    font-size: 20px;
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
    padding: 0 14px;
  }

  .page-header h1 {
    font-size: 30px;
  }

  .subtitle {
    font-size: 13px;
  }

  .intro-card {
    flex-direction: column;

    gap: 12px;
  }

  .section-title h2 {
    font-size: 18px;
  }

  .category-tabs {
    display: grid;

    grid-template-columns:
      repeat(2, minmax(0, 1fr));
  }

  .category-tab {
    justify-content: center;

    padding: 9px 8px;
  }

  .featured-player {
    display: grid;

    grid-template-columns:
      40px 1fr;
  }

  .featured-value {
    grid-column: 1 / -1;

    justify-self: start;
  }

  .leaderboard-item {
    padding: 12px 13px;
  }

  .rank-number {
    width: 25px;
  }

  .player-info {
    gap: 4px 8px;
  }

  .player-name {
    font-size: 14px;
  }

  .player-value {
    font-size: 11px;

    padding: 4px 8px;
  }

  .community-meta {
    display: grid;

    grid-template-columns:
      repeat(2, 1fr);

    gap: 14px;
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

  .category-tab,
  .stat-tab,
  .leaderboard-item,
  .retry-button {
    transition: none;
  }
}
</style>