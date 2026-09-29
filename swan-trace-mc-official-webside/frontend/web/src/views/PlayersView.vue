<template>
  <div class="page-wrapper">

    <!-- =========================================================
         页面头部
    ========================================================== -->
    <section ref="playersSection" class="section players-section">
      <div class="container">

        <header class="page-header">

          <div class="header-main">

            <div class="title-area">

              <div class="title-badge">
                <span class="live-dot"></span>
                SWAN TRACE · COMMUNITY
              </div>

              <h1>
                鸿迹玩家
              </h1>

              <p class="subtitle">
                查看鸿迹 Minecraft Community 当前玩家状态
              </p>

            </div>


            <div class="header-actions">

              <button class="btn-refresh" type="button" @click="getPlayers" :disabled="loading">
                <span v-if="!loading" class="refresh-icon">
                  ↻
                </span>

                <span v-else class="refresh-icon spinning">
                  ↻
                </span>

                <span>
                  {{ loading ? '获取中...' : '刷新玩家' }}
                </span>
              </button>

            </div>

          </div>


          <!-- =====================================================
               在线人数概况
          ====================================================== -->
          <div class="server-status">

            <div class="status-card online-status">

              <div class="status-icon">
                ●
              </div>

              <div class="status-info">

                <span class="status-label">
                  当前在线
                </span>

                <strong>
                  {{ onlinePlayers.length }}
                  <small>/ 20</small>
                </strong>

              </div>

            </div>


            <div class="status-card">

              <div class="status-icon">
                👥
              </div>

              <div class="status-info">

                <span class="status-label">
                  玩家记录
                </span>

                <strong>
                  {{ players.length }}
                </strong>

              </div>

            </div>


            <div class="status-card">

              <div class="status-icon">
                🟢
              </div>

              <div class="status-info">

                <span class="status-label">
                  服务器状态
                </span>

                <strong class="server-online">
                  正常运行
                </strong>

              </div>

            </div>


            <div class="status-card">

              <div class="status-icon">
                🎮
              </div>

              <div class="status-info">

                <span class="status-label">
                  游戏版本
                </span>

                <strong>
                  26.2
                </strong>

              </div>

            </div>

          </div>

        </header>


        <!-- =====================================================
             社区说明
        ====================================================== -->
        <div class="community-notice">

          <div class="notice-icon">
            🕊
          </div>

          <div class="notice-content">

            <strong>
              鸿迹 · 纯净与自由
            </strong>

            <p>
              尊重每一位玩家的建筑、生存和游玩方式。
              请不要干涉其他玩家的正常游戏，共同维护一个自由、友善的社区环境。
            </p>

          </div>

        </div>


        <!-- =====================================================
             加载状态
        ====================================================== -->
        <div v-if="loading" class="loading-state">

          <div class="loading-animation">
            <div class="spinner"></div>
          </div>

          <h3>
            正在获取玩家数据
          </h3>

          <p>
            正在连接鸿迹玩家状态服务...
          </p>

        </div>


        <!-- =====================================================
             错误状态
        ====================================================== -->
        <div v-else-if="error" class="error-state">

          <div class="error-icon">
            ⚠
          </div>

          <h3>
            无法获取玩家数据
          </h3>

          <p>
            {{ error }}
          </p>

          <button class="btn-retry" type="button" @click="getPlayers">
            重新获取
          </button>

        </div>


        <!-- =====================================================
             玩家列表
        ====================================================== -->
        <div v-else class="players-area">

          <!-- ===================================================
               在线玩家
          ==================================================== -->
          <section class="player-group">

            <div class="group-header">

              <div>

                <span class="group-label">
                  ONLINE
                </span>

                <h2>
                  在线玩家
                </h2>

              </div>

              <span class="group-count online-count">
                {{ onlinePlayers.length }} 人在线
              </span>

            </div>


            <div v-if="onlinePlayers.length > 0" class="players-grid">

              <div v-for="player in onlinePlayers" :key="player.uuid" class="player-card is-online">

                <!-- ===========================================
                     玩家头像
                ============================================ -->
                <div class="player-avatar">

                  <span class="avatar-text">
                    {{
                      player.name
                        ?.charAt(0)
                        ?.toUpperCase() || '?'
                    }}
                  </span>

                  <span class="online-badge">
                    ●
                  </span>

                </div>


                <!-- ===========================================
                     玩家信息
                ============================================ -->
                <div class="player-info">

                  <div class="player-name-row">

                    <span class="player-name">
                      {{ player.name }}
                    </span>

                    <span class="online-label">
                      在线
                    </span>

                  </div>


                  <div class="player-uuid">
                    {{ shortUUID(player.uuid) }}
                  </div>


                  <div class="player-meta">

                    <span class="meta-item gamemode">
                      <span>🎮</span>
                      {{ formatGamemode(player.gamemode) }}
                    </span>


                    <span v-if="player.ping !== undefined" class="meta-item ping">
                      <span>📡</span>
                      {{ player.ping }} ms
                    </span>

                  </div>

                </div>

              </div>

            </div>


            <div v-else class="empty-state">

              <span class="empty-icon">
                🌙
              </span>

              <h3>
                当前没有玩家在线
              </h3>

              <p>
                服务器正在运行，等待玩家回来。
              </p>

            </div>

          </section>


          <!-- ===================================================
               离线玩家
          ==================================================== -->
          <section v-if="offlinePlayers.length > 0" class="player-group offline-group">

            <div class="group-header">

              <div>

                <span class="group-label">
                  COMMUNITY
                </span>

                <h2>
                  其他玩家
                </h2>

              </div>

              <span class="group-count">
                {{ offlinePlayers.length }} 人
              </span>

            </div>


            <div class="players-grid">

              <div v-for="player in offlinePlayers" :key="player.uuid" class="player-card" :class="{
                'is-banned': player.isBanned
              }">

                <!-- ===========================================
                     玩家头像
                ============================================ -->
                <div class="player-avatar">

                  <span class="avatar-text">
                    {{
                      player.name
                        ?.charAt(0)
                        ?.toUpperCase() || '?'
                    }}
                  </span>

                  <span v-if="player.isBanned" class="banned-badge">
                    🚫
                  </span>

                </div>


                <!-- ===========================================
                     玩家信息
                ============================================ -->
                <div class="player-info">

                  <div class="player-name-row">

                    <span class="player-name">
                      {{ player.name }}
                    </span>

                    <span v-if="player.isBanned" class="banned-label">
                      已封禁
                    </span>

                    <span v-else class="offline-label">
                      离线
                    </span>

                  </div>


                  <div class="player-uuid">
                    {{ shortUUID(player.uuid) }}
                  </div>


                  <div class="player-meta">

                    <span class="meta-item gamemode">
                      <span>🎮</span>
                      {{ formatGamemode(player.gamemode) }}
                    </span>

                  </div>


                  <!-- 封禁原因 -->
                  <div v-if="
                    player.isBanned &&
                    player.banReason
                  " class="ban-reason">
                    <span>⛔</span>
                    {{ player.banReason }}
                  </div>

                </div>

              </div>

            </div>

          </section>


          <!-- ===================================================
               没有任何玩家数据
          ==================================================== -->
          <div v-if="players.length === 0" class="empty-state total-empty">

            <span class="empty-icon">
              📭
            </span>

            <h3>
              暂无玩家数据
            </h3>

            <p>
              当前没有获取到玩家记录。
            </p>

            <button class="btn-retry" type="button" @click="getPlayers">
              重新获取
            </button>

          </div>

        </div>


        <!-- =====================================================
             举报提示
        ====================================================== -->
        <div class="report-section">

          <div class="report-icon">
            🚨
          </div>

          <div class="report-content">

            <span class="report-label">
              PLAYER REPORT
            </span>

            <h2>
              发现违规行为？
            </h2>

            <p>
              如果你发现玩家存在违反服务器规则的行为，
              请通过 QQ 群进行举报。
              举报时建议提供包含 F3 坐标、玩家 ID 的截图，
              条件允许时最好提供完整视频。
            </p>

            <div class="report-actions">

              <span class="report-group">
                QQ 群：<strong>661436985</strong>
              </span>

              <span class="report-divider"></span>

              <span>
                举报应基于实际证据
              </span>

            </div>

          </div>

        </div>

      </div>
    </section>


    <FooterC />

  </div>
</template>


<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref } from 'vue'
import FooterC from '@/components/FooterC.vue'
import { fetchPlayers } from '@/api/players'


// ============================================================
// 类型定义
// ============================================================

interface Player {
  name: string
  uuid: string
  isOnline: boolean
  isBanned: boolean
  gamemode:
  | 'adventure'
  | 'creative'
  | 'survival'
  | 'spectator'
  banReason?: string
  ping?: number
}


// ============================================================
// 响应式数据
// ============================================================

const players = ref<Player[]>([])

const loading = ref(false)

const error = ref('')

const playersSection = ref<HTMLElement | null>(null)


// ============================================================
// 玩家分类
// ============================================================

const onlinePlayers = computed(() => {
  return players.value.filter(
    (player) => player.isOnline && !player.isBanned
  )
})


const offlinePlayers = computed(() => {
  return players.value.filter(
    (player) => !player.isOnline || player.isBanned
  )
})


// ============================================================
// 获取玩家数据
// ============================================================

const getPlayers = async () => {

  loading.value = true

  error.value = ''

  try {

    const data = await fetchPlayers()

    if (!data || !Array.isArray(data)) {
      throw new Error('响应数据格式错误')
    }


    // --------------------------------------------------------
    // 数据规范化
    // --------------------------------------------------------

    players.value = data.map((p: any) => ({
      name: p.name || 'Unknown',

      uuid: p.uuid || '',

      isOnline: p.isOnline ?? false,

      isBanned: p.isBanned ?? false,

      gamemode:
        p.gamemode || 'survival',

      banReason:
        p.banReason || '',

      ping:
        p.ping ?? undefined
    }))


    // --------------------------------------------------------
    // 排序
    //
    // 1. 在线玩家
    // 2. 正常离线玩家
    // 3. 封禁玩家
    // 4. Unknown
    // --------------------------------------------------------

    players.value.sort((a, b) => {

      // 在线优先
      if (a.isOnline && !b.isOnline) {
        return -1
      }

      if (!a.isOnline && b.isOnline) {
        return 1
      }


      // 封禁玩家放后面
      if (a.isBanned && !b.isBanned) {
        return 1
      }

      if (!a.isBanned && b.isBanned) {
        return -1
      }


      // Unknown 放最后
      const aUnknown =
        !a.name ||
        a.name === 'Unknown'

      const bUnknown =
        !b.name ||
        b.name === 'Unknown'


      if (aUnknown && !bUnknown) {
        return 1
      }

      if (!aUnknown && bUnknown) {
        return -1
      }


      // 最后按照玩家名称排序
      return a.name.localeCompare(
        b.name
      )
    })

  } catch (err: any) {

    console.error(
      '获取鸿迹玩家数据失败:',
      err
    )

    error.value =
      err?.message ||
      '无法连接玩家状态服务，请稍后重试'

    players.value = []

  } finally {

    loading.value = false
  }
}


// ============================================================
// UUID 简化
// ============================================================

const shortUUID = (
  uuid: string
): string => {

  if (!uuid) {
    return ''
  }


  const parts =
    uuid.split('-')


  if (parts.length === 5) {

    return `${parts[0]}-${parts[1]}-...-${parts[4]}`
  }


  return uuid.length > 16
    ? `${uuid.slice(0, 8)}...`
    : uuid
}


// ============================================================
// 游戏模式
// ============================================================

const formatGamemode = (
  mode: string
): string => {

  const map: Record<string, string> = {

    adventure: '冒险',

    creative: '创造',

    survival: '生存',

    spectator: '旁观'
  }


  return map[mode] || mode
}


// ============================================================
// IntersectionObserver
// ============================================================

let observer: IntersectionObserver | null = null


// ============================================================
// 生命周期
// ============================================================

onMounted(() => {

  getPlayers()


  // ----------------------------------------------------------
  // 页面进入动画
  // ----------------------------------------------------------

  if (playersSection.value) {

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
          threshold: 0.05,

          rootMargin:
            '0px 0px -40px 0px'
        }
      )


    observer.observe(
      playersSection.value
    )
  }
})


onUnmounted(() => {

  observer?.disconnect()

  observer = null
})
</script>


<style scoped>
/* =========================================================
   页面基础
========================================================= */

* {
  box-sizing: border-box;

  margin: 0;

  padding: 0;
}


.page-wrapper {
  min-height: 100vh;

  background:
    radial-gradient(circle at 50% 0%,
      rgba(120, 150, 170, 0.06),
      transparent 40%),
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

  line-height: 1.6;
}


/* =========================================================
   Section
========================================================= */

.section {
  min-height: 100vh;

  padding:
    80px 0 100px;

  opacity: 0;

  transform:
    translateY(30px);

  transition:
    opacity 0.8s ease,
    transform 0.8s ease;
}


.section.is-visible {
  opacity: 1;

  transform:
    translateY(0);
}


.container {
  width: 100%;

  max-width: 1100px;

  margin:
    0 auto;

  padding:
    0 24px;
}


/* =========================================================
   Header
========================================================= */

.page-header {
  margin-bottom: 32px;

  padding-bottom: 30px;

  border-bottom:
    1px solid rgba(255,
      255,
      255,
      0.055);
}


.header-main {
  display: flex;

  justify-content: space-between;

  align-items: flex-end;

  gap: 30px;
}


.title-badge {
  display: inline-flex;

  align-items: center;

  gap: 8px;

  padding:
    6px 13px;

  margin-bottom: 15px;

  border:
    1px solid rgba(170,
      199,
      217,
      0.12);

  border-radius: 30px;

  background:
    rgba(170,
      199,
      217,
      0.05);

  color: #7892a3;

  font-size: 10px;

  font-weight: 700;

  letter-spacing: 1.6px;
}


.live-dot {
  width: 6px;

  height: 6px;

  border-radius: 50%;

  background: #8db9a3;

  box-shadow:
    0 0 10px rgba(141,
      185,
      163,
      0.7);
}


.page-header h1 {
  font-size:
    clamp(36px,
      5vw,
      48px);

  line-height: 1.1;

  font-weight: 800;

  letter-spacing: -1.5px;

  background:
    linear-gradient(135deg,
      #f0e6d0,
      #e8edf2,
      #aac7d9);

  -webkit-background-clip: text;

  -webkit-text-fill-color: transparent;

  background-clip: text;
}


.subtitle {
  margin-top: 10px;

  color: #788896;

  font-size: 14px;
}


.header-actions {
  flex-shrink: 0;
}


.btn-refresh {
  display: inline-flex;

  align-items: center;

  justify-content: center;

  gap: 8px;

  min-width: 125px;

  height: 42px;

  padding:
    0 18px;

  border:
    1px solid rgba(170,
      199,
      217,
      0.13);

  border-radius: 10px;

  background:
    rgba(170,
      199,
      217,
      0.055);

  color: #b8d4e3;

  font-size: 13px;

  cursor: pointer;

  transition:
    all 0.2s ease;
}


.btn-refresh:hover:not(:disabled) {
  transform:
    translateY(-2px);

  border-color:
    rgba(170,
      199,
      217,
      0.25);

  background:
    rgba(170,
      199,
      217,
      0.1);
}


.btn-refresh:disabled {
  opacity: 0.5;

  cursor: not-allowed;
}


.refresh-icon {
  font-size: 18px;

  line-height: 1;
}


.spinning {
  animation:
    spin 0.8s linear infinite;
}


@keyframes spin {

  from {
    transform:
      rotate(0deg);
  }

  to {
    transform:
      rotate(360deg);
  }
}


/* =========================================================
   Server Status
========================================================= */

.server-status {
  display: grid;

  grid-template-columns:
    repeat(4, 1fr);

  gap: 12px;

  margin-top: 28px;
}


.status-card {
  display: flex;

  align-items: center;

  gap: 14px;

  min-height: 82px;

  padding:
    17px 18px;

  background:
    rgba(255,
      255,
      255,
      0.025);

  border:
    1px solid rgba(255,
      255,
      255,
      0.055);

  border-radius: 14px;
}


.status-icon {
  width: 38px;

  height: 38px;

  display: flex;

  align-items: center;

  justify-content: center;

  flex-shrink: 0;

  border-radius: 10px;

  background:
    rgba(255,
      255,
      255,
      0.045);

  color: #8ba3b3;

  font-size: 17px;
}


.online-status .status-icon {
  color: #8db9a3;

  background:
    rgba(141,
      185,
      163,
      0.08);
}


.status-label {
  display: block;

  color: #657582;

  font-size: 10px;

  letter-spacing: 1px;
}


.status-info strong {
  display: block;

  margin-top: 2px;

  color: #dce3e8;

  font-size: 20px;

  font-weight: 700;
}


.status-info strong small {
  color: #687987;

  font-size: 11px;

  font-weight: 400;
}


.status-info .server-online {
  color: #8db9a3;

  font-size: 15px;
}


/* =========================================================
   Community Notice
========================================================= */

.community-notice {
  display: flex;

  align-items: flex-start;

  gap: 15px;

  margin-bottom: 40px;

  padding:
    18px 20px;

  border:
    1px solid rgba(216,
      199,
      165,
      0.1);

  border-radius: 14px;

  background:
    rgba(216,
      199,
      165,
      0.025);
}


.notice-icon {
  width: 38px;

  height: 38px;

  display: flex;

  align-items: center;

  justify-content: center;

  flex-shrink: 0;

  border-radius: 10px;

  background:
    rgba(216,
      199,
      165,
      0.07);

  font-size: 17px;
}


.notice-content strong {
  display: block;

  color: #d8c7a5;

  font-size: 13px;
}


.notice-content p {
  margin-top: 3px;

  color: #778692;

  font-size: 12px;

  line-height: 1.8;
}


/* =========================================================
   Loading
========================================================= */

.loading-state {
  min-height: 360px;

  display: flex;

  flex-direction: column;

  align-items: center;

  justify-content: center;

  text-align: center;
}


.loading-animation {
  width: 52px;

  height: 52px;

  display: flex;

  align-items: center;

  justify-content: center;

  margin-bottom: 20px;

  border-radius: 50%;

  background:
    rgba(170,
      199,
      217,
      0.05);
}


.spinner {
  width: 34px;

  height: 34px;

  border:
    3px solid rgba(255,
      255,
      255,
      0.07);

  border-top-color:
    #8eabbc;

  border-radius: 50%;

  animation:
    spin 0.8s linear infinite;
}


.loading-state h3 {
  font-size: 16px;

  font-weight: 600;
}


.loading-state p {
  margin-top: 6px;

  color: #687885;

  font-size: 12px;
}


/* =========================================================
   Error
========================================================= */

.error-state {
  min-height: 330px;

  display: flex;

  flex-direction: column;

  align-items: center;

  justify-content: center;

  padding: 40px;

  text-align: center;

  border:
    1px solid rgba(255,
      100,
      100,
      0.1);

  border-radius: 20px;

  background:
    rgba(255,
      70,
      70,
      0.025);
}


.error-icon {
  width: 54px;

  height: 54px;

  display: flex;

  align-items: center;

  justify-content: center;

  margin-bottom: 16px;

  border-radius: 15px;

  background:
    rgba(255,
      70,
      70,
      0.08);

  font-size: 24px;
}


.error-state h3 {
  font-size: 17px;
}


.error-state p {
  max-width: 450px;

  margin-top: 7px;

  color: #9a7777;

  font-size: 13px;
}


.btn-retry {
  margin-top: 18px;

  height: 40px;

  padding:
    0 22px;

  border:
    1px solid rgba(170,
      199,
      217,
      0.13);

  border-radius: 10px;

  background:
    rgba(170,
      199,
      217,
      0.055);

  color: #aac7d9;

  font-size: 12px;

  cursor: pointer;

  transition:
    all 0.2s ease;
}


.btn-retry:hover {
  transform:
    translateY(-2px);

  background:
    rgba(170,
      199,
      217,
      0.1);
}


/* =========================================================
   Player Group
========================================================= */

.players-area {
  display: flex;

  flex-direction: column;

  gap: 55px;
}


.player-group {
  width: 100%;
}


.group-header {
  display: flex;

  align-items: flex-end;

  justify-content: space-between;

  gap: 20px;

  margin-bottom: 22px;
}


.group-label {
  display: block;

  margin-bottom: 4px;

  color: #688090;

  font-size: 10px;

  font-weight: 700;

  letter-spacing: 2px;
}


.group-header h2 {
  font-size: 24px;

  font-weight: 700;

  color: #dfe6ea;
}


.group-count {
  padding:
    5px 12px;

  border:
    1px solid rgba(255,
      255,
      255,
      0.06);

  border-radius: 30px;

  background:
    rgba(255,
      255,
      255,
      0.025);

  color: #71818e;

  font-size: 11px;
}


.online-count {
  color: #8db9a3;

  border-color:
    rgba(141,
      185,
      163,
      0.13);

  background:
    rgba(141,
      185,
      163,
      0.05);
}


/* =========================================================
   Player Grid
========================================================= */

.players-grid {
  display: grid;

  grid-template-columns:
    repeat(auto-fill,
      minmax(300px,
        1fr));

  gap: 14px;
}


/* =========================================================
   Player Card
========================================================= */

.player-card {
  position: relative;

  display: flex;

  align-items: center;

  gap: 17px;

  min-height: 108px;

  padding:
    18px 20px;

  overflow: hidden;

  border:
    1px solid rgba(255,
      255,
      255,
      0.055);

  border-radius: 16px;

  background:
    rgba(255,
      255,
      255,
      0.025);

  transition:
    transform 0.25s ease,
    background 0.25s ease,
    border-color 0.25s ease;
}


.player-card:hover {
  transform:
    translateY(-3px);

  background:
    rgba(255,
      255,
      255,
      0.045);

  border-color:
    rgba(255,
      255,
      255,
      0.1);
}


.player-card.is-online {
  border-left:
    3px solid #6ca785;
}


.player-card.is-banned {
  border-left:
    3px solid #a96c6c;

  opacity: 0.7;
}


/* =========================================================
   Avatar
========================================================= */

.player-avatar {
  position: relative;

  width: 56px;

  height: 56px;

  display: flex;

  align-items: center;

  justify-content: center;

  flex-shrink: 0;

  border-radius: 50%;

  background:
    linear-gradient(135deg,
      #2b3b49,
      #17232d);

  border:
    1px solid rgba(255,
      255,
      255,
      0.06);

  color: #b8d4e3;

  font-size: 21px;

  font-weight: 700;

  box-shadow:
    0 5px 18px rgba(0,
      0,
      0,
      0.25);
}


.avatar-text {
  user-select: none;
}


.online-badge,
.banned-badge {
  position: absolute;

  right: -2px;

  bottom: -2px;

  width: 19px;

  height: 19px;

  display: flex;

  align-items: center;

  justify-content: center;

  border-radius: 50%;

  background:
    #11171c;

  font-size: 10px;
}


.online-badge {
  color: #70bd8f;

  text-shadow:
    0 0 10px rgba(112,
      189,
      143,
      0.7);
}


.banned-badge {
  font-size: 12px;
}


/* =========================================================
   Player Info
========================================================= */

.player-info {
  min-width: 0;

  flex: 1;
}


.player-name-row {
  display: flex;

  align-items: center;

  flex-wrap: wrap;

  gap: 7px;

  margin-bottom: 4px;
}


.player-name {
  max-width: 200px;

  overflow: hidden;

  text-overflow: ellipsis;

  white-space: nowrap;

  color: #e2e8ec;

  font-size: 16px;

  font-weight: 650;
}


.online-label,
.offline-label,
.banned-label {
  padding:
    2px 8px;

  border-radius: 30px;

  font-size: 9px;
}


.online-label {
  color: #82b998;

  background:
    rgba(130,
      185,
      152,
      0.08);

  border:
    1px solid rgba(130,
      185,
      152,
      0.12);
}


.offline-label {
  color: #687986;

  background:
    rgba(255,
      255,
      255,
      0.035);
}


.banned-label {
  color: #bd7e7e;

  background:
    rgba(189,
      126,
      126,
      0.08);
}


.player-uuid {
  display: inline-block;

  max-width: 100%;

  overflow: hidden;

  text-overflow: ellipsis;

  white-space: nowrap;

  color: #596b79;

  font-family:
    'JetBrains Mono',
    'SFMono-Regular',
    Consolas,
    monospace;

  font-size: 10px;
}


.player-meta {
  display: flex;

  flex-wrap: wrap;

  align-items: center;

  gap: 12px;

  margin-top: 8px;
}


.meta-item {
  display: inline-flex;

  align-items: center;

  gap: 4px;

  color: #748795;

  font-size: 11px;
}


.meta-item.gamemode {
  color: #8da9b9;
}


.meta-item.ping {
  color: #78ad91;
}


/* =========================================================
   Ban Reason
========================================================= */

.ban-reason {
  display: flex;

  gap: 5px;

  margin-top: 7px;

  padding:
    5px 8px;

  border-left:
    2px solid #956565;

  border-radius: 5px;

  background:
    rgba(255,
      70,
      70,
      0.04);

  color: #a77a7a;

  font-size: 10px;

  line-height: 1.5;

  word-break: break-word;
}


/* =========================================================
   Empty
========================================================= */

.empty-state {
  min-height: 230px;

  display: flex;

  flex-direction: column;

  align-items: center;

  justify-content: center;

  padding: 40px 20px;

  text-align: center;

  border:
    1px dashed rgba(255,
      255,
      255,
      0.07);

  border-radius: 18px;

  background:
    rgba(255,
      255,
      255,
      0.015);
}


.empty-icon {
  margin-bottom: 13px;

  font-size: 38px;

  opacity: 0.45;
}


.empty-state h3 {
  color: #aab6be;

  font-size: 15px;
}


.empty-state p {
  margin-top: 4px;

  color: #667783;

  font-size: 12px;
}


.total-empty {
  min-height: 320px;
}


/* =========================================================
   Report
========================================================= */

.report-section {
  display: flex;

  align-items: flex-start;

  gap: 20px;

  margin-top: 60px;

  padding:
    26px 28px;

  border:
    1px solid rgba(216,
      199,
      165,
      0.1);

  border-radius: 18px;

  background:
    linear-gradient(135deg,
      rgba(216,
        199,
        165,
        0.035),
      rgba(255,
        255,
        255,
        0.015));
}


.report-icon {
  width: 44px;

  height: 44px;

  display: flex;

  align-items: center;

  justify-content: center;

  flex-shrink: 0;

  border-radius: 12px;

  background:
    rgba(216,
      199,
      165,
      0.07);

  font-size: 20px;
}


.report-label {
  display: block;

  margin-bottom: 3px;

  color: #786f61;

  font-size: 9px;

  font-weight: 700;

  letter-spacing: 1.7px;
}


.report-content h2 {
  color: #d9d0c2;

  font-size: 18px;
}


.report-content>p {
  max-width: 800px;

  margin-top: 6px;

  color: #788692;

  font-size: 12px;

  line-height: 1.8;
}


.report-actions {
  display: flex;

  align-items: center;

  flex-wrap: wrap;

  gap: 12px;

  margin-top: 13px;

  color: #667783;

  font-size: 10px;
}


.report-group strong {
  color: #b9a988;

  font-weight: 600;
}


.report-divider {
  width: 3px;

  height: 3px;

  border-radius: 50%;

  background: #596a76;
}


/* =========================================================
   Footer Note
========================================================= */

.page-footer-note {
  padding-top: 55px;

  text-align: center;
}


.page-footer-note>span {
  color: #536572;

  font-size: 9px;

  font-weight: 700;

  letter-spacing: 3px;
}


.page-footer-note p {
  margin-top: 8px;

  color: #4f606d;

  font-size: 11px;
}


/* =========================================================
   Responsive
========================================================= */

@media (max-width: 900px) {

  .server-status {
    grid-template-columns:
      repeat(2, 1fr);
  }

}


@media (max-width: 700px) {

  .section {
    padding:
      60px 0 80px;
  }


  .header-main {
    align-items: flex-start;

    flex-direction: column;
  }


  .header-actions {
    width: 100%;
  }


  .btn-refresh {
    width: 100%;
  }


  .server-status {
    grid-template-columns: 1fr 1fr;
  }


  .players-grid {
    grid-template-columns: 1fr;
  }


  .report-section {
    padding: 22px;

    gap: 14px;
  }

}


@media (max-width: 480px) {

  .container {
    padding:
      0 16px;
  }


  .section {
    padding:
      45px 0 65px;
  }


  .page-header {
    margin-bottom: 25px;
  }


  .page-header h1 {
    font-size: 34px;
  }


  .subtitle {
    font-size: 12px;
  }


  .server-status {
    grid-template-columns: 1fr;
  }


  .status-card {
    min-height: 70px;
  }


  .community-notice {
    padding: 15px;

    gap: 11px;
  }


  .notice-content p {
    font-size: 11px;
  }


  .group-header {
    align-items: flex-start;

    flex-direction: column;

    gap: 8px;
  }


  .group-header h2 {
    font-size: 21px;
  }


  .player-card {
    min-height: 100px;

    padding:
      16px;
  }


  .player-avatar {
    width: 48px;

    height: 48px;

    font-size: 18px;
  }


  .player-name {
    max-width: 170px;

    font-size: 15px;
  }


  .report-section {
    flex-direction: column;
  }


  .report-content>p {
    font-size: 11px;
  }

}


/* =========================================================
   Reduced Motion
========================================================= */

@media (prefers-reduced-motion: reduce) {

  .section {
    opacity: 1;

    transform: none;

    transition: none;
  }


  .spinner,
  .spinning {
    animation: none;
  }


  .player-card,
  .btn-refresh,
  .btn-retry {
    transition: none;
  }

}
</style>