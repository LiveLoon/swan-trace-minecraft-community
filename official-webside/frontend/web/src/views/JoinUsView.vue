<template>
  <div class="page-wrapper">

    <!-- =========================================================
         Hero / 页面头部
    ========================================================== -->
    <section class="hero-section">
      <div class="hero-background"></div>
      <div class="hero-glow hero-glow-left"></div>
      <div class="hero-glow hero-glow-right"></div>

      <div class="container hero-container">

        <div class="hero-badge">
          <span class="status-dot"></span>
          SWAN TRACE · MINECRAFT COMMUNITY
        </div>

        <h1>
          加入鸿迹
          <span>成为社区的一员</span>
        </h1>

        <p class="hero-description">
          鸿迹（Swan Trace）是一个由玩家共同建设、
          共同维护的 Minecraft 公益社区。
          <br />
          加入 QQ 群，获取服务器地址并参与社区交流。
        </p>

        <button class="hero-button" @click="scrollToJoin">
          <span>加入 QQ 群</span>
          <span class="arrow">↓</span>
        </button>

      </div>
    </section>


    <!-- =========================================================
         加入方式
    ========================================================== -->
    <section ref="joinSection" id="join-section" class="section join-section">
      <div class="container">

        <div class="section-header centered">
          <span class="section-label">JOIN US</span>

          <h2>加入社区</h2>

          <p>
            扫描二维码或搜索 QQ 群号加入鸿迹社区。
          </p>
        </div>


        <div class="join-layout">

          <!-- 左侧说明 -->
          <div class="join-info">

            <div class="info-card highlight-card">

              <div class="info-icon">
                🎮
              </div>

              <div>
                <span class="card-label">
                  COMMUNITY
                </span>

                <h3>
                  先加入社区，再进入服务器
                </h3>

                <p>
                  鸿迹的服务器地址不会直接公开在网站页面。
                  加入 QQ 群后，可以获取当前服务器地址以及
                  最新的服务器运行信息。
                </p>
              </div>

            </div>


            <div class="info-card">

              <div class="info-icon">
                📡
              </div>

              <div>
                <span class="card-label">
                  SERVER ADDRESS
                </span>

                <h3>
                  服务器地址
                </h3>

                <p>
                  请加入 QQ 群后获取服务器地址。
                  如果服务器地址发生变化，也会通过社区渠道进行通知。
                </p>
              </div>

            </div>


            <div class="info-card">

              <div class="info-icon">
                🔧
              </div>

              <div>
                <span class="card-label">
                  CONFIGURATION
                </span>

                <h3>
                  插件与配置
                </h3>

                <p>
                  服务器插件、配置等社区公共事项，
                  按照社区治理规则进行讨论和投票。
                </p>
              </div>

            </div>


            <div class="info-card">

              <div class="info-icon">
                🚨
              </div>

              <div>
                <span class="card-label">
                  REPORT
                </span>

                <h3>
                  玩家举报
                </h3>

                <p>
                  如果遇到违反服务器规则的行为，
                  可以通过 QQ 群进行反馈和举报。
                </p>
              </div>

            </div>

          </div>


          <!-- 右侧二维码 -->
          <div class="qr-area">

            <div class="qr-wrapper">

              <div class="qr-header">
                <span class="qr-status"></span>
                <span>鸿迹 Minecraft Community</span>
              </div>

              <div class="qr-image-wrapper">
                <img src="/src/assets/join-us.jpg" alt="鸿迹 Minecraft 社区 QQ 群二维码" class="qr-image" />
              </div>

              <div class="qr-content">

                <span class="qr-label">
                  QQ GROUP
                </span>

                <div class="group-number">
                  661436985
                </div>

                <p>
                  扫描二维码或搜索群号加入
                </p>

              </div>

              <button class="copy-button" type="button" @click="copyGroupNumber">
                <span v-if="!copied">
                  复制 QQ 群号
                </span>

                <span v-else>
                  ✓ 已复制
                </span>
              </button>

            </div>

          </div>

        </div>

      </div>
    </section>


    <!-- =========================================================
         加入后可以获得什么
    ========================================================== -->
    <section class="section community-section">
      <div class="container">

        <div class="section-header centered">
          <span class="section-label">
            AFTER JOINING
          </span>

          <h2>
            加入之后
          </h2>

          <p>
            QQ 群不仅是服务器地址的获取渠道，也是鸿迹社区的重要交流入口。
          </p>
        </div>


        <div class="feature-grid">

          <article class="feature-card">
            <div class="feature-number">
              01
            </div>

            <div class="feature-icon">
              🌐
            </div>

            <h3>
              获取服务器地址
            </h3>

            <p>
              加入社区后获取当前服务器连接地址，
              并及时了解服务器地址变更。
            </p>
          </article>


          <article class="feature-card">
            <div class="feature-number">
              02
            </div>

            <div class="feature-icon">
              📢
            </div>

            <h3>
              获取服务器公告
            </h3>

            <p>
              了解版本更新、计划维护以及其他重要社区通知。
            </p>
          </article>


          <article class="feature-card">
            <div class="feature-number">
              03
            </div>

            <div class="feature-icon">
              🗳
            </div>

            <h3>
              参与社区治理
            </h3>

            <p>
              符合条件的正常玩家可以参与服务器相关事项的投票。
            </p>
          </article>


          <article class="feature-card">
            <div class="feature-number">
              04
            </div>

            <div class="feature-icon">
              🛠
            </div>

            <h3>
              提交申请与反馈
            </h3>

            <p>
              可以提交配置申请、问题反馈以及玩家举报等内容。
            </p>
          </article>

        </div>

      </div>
    </section>


    <!-- =========================================================
         举报说明
    ========================================================== -->
    <section class="section report-section">
      <div class="container">

        <div class="report-box">

          <div class="report-icon">
            ⚠
          </div>

          <div class="report-content">

            <span class="section-label">
              PLAYER REPORT
            </span>

            <h2>
              玩家举报
            </h2>

            <p>
              如果你发现其他玩家存在违反服务器规则的行为，
              可以进入 QQ 群进行举报。
            </p>


            <div class="report-requirements">

              <div class="requirement">
                <span class="requirement-icon">📍</span>

                <div>
                  <strong>提供坐标</strong>
                  <p>
                    截图应尽量包含 F3 信息和事件发生位置。
                  </p>
                </div>
              </div>


              <div class="requirement">
                <span class="requirement-icon">👤</span>

                <div>
                  <strong>提供玩家 ID</strong>
                  <p>
                    尽量明确涉及事件的玩家游戏 ID。
                  </p>
                </div>
              </div>


              <div class="requirement">
                <span class="requirement-icon">🎥</span>

                <div>
                  <strong>视频优先</strong>
                  <p>
                    条件允许时建议提供完整的视频证据。
                  </p>
                </div>
              </div>

            </div>

          </div>

        </div>

      </div>
    </section>


    <!-- =========================================================
         加入前须知
    ========================================================== -->
    <section class="section notice-section">
      <div class="container">

        <div class="section-header">
          <div>
            <span class="section-label">
              BEFORE JOINING
            </span>

            <h2>
              加入前须知
            </h2>
          </div>

          <span class="badge">
            请认真阅读
          </span>
        </div>


        <div class="notice-grid">

          <article class="notice-card">

            <div class="notice-icon">
              🌱
            </div>

            <h3>
              公益服务器
            </h3>

            <p>
              鸿迹是个人公益性质的 Minecraft 社区，
              不以商业化运营为目的。
            </p>

          </article>


          <article class="notice-card">

            <div class="notice-icon">
              🕊
            </div>

            <h3>
              尊重自由
            </h3>

            <p>
              尊重其他玩家的建筑、生存和游玩方式，
              不干涉他人的正常游戏。
            </p>

          </article>


          <article class="notice-card">

            <div class="notice-icon">
              ⚖
            </div>

            <h3>
              遵守规则
            </h3>

            <p>
              社区成员应遵守服务器规则和基本底线，
              共同维护正常的游戏环境。
            </p>

          </article>


          <article class="notice-card">

            <div class="notice-icon">
              🤝
            </div>

            <h3>
              理性交流
            </h3>

            <p>
              发生争议时尽量通过正常渠道沟通，
              避免辱骂、挑衅和恶意冲突。
            </p>

          </article>

        </div>

      </div>
    </section>


    <!-- =========================================================
         社区治理
    ========================================================== -->
    <section class="section voting-section">
      <div class="container">

        <div class="section-header centered">
          <span class="section-label">
            COMMUNITY VOTING
          </span>

          <h2>
            社区共同决定
          </h2>

          <p>
            鸿迹部分服务器事项由符合条件的玩家通过投票参与决定。
          </p>
        </div>


        <div class="voting-layout">

          <div class="voting-card">

            <div class="voting-icon">
              🗳
            </div>

            <h3>
              谁可以参与？
            </h3>

            <p>
              正常玩家需要满足社区规定的参与条件，
              包括已经加入服务器并拥有相应的游戏记录。
            </p>

            <div class="condition-list">

              <div>
                <span>✓</span>
                可以正常加入服务器
              </div>

              <div>
                <span>✓</span>
                后台存在对应玩家记录
              </div>

              <div>
                <span>✓</span>
                累计游玩时间达到 1 小时
              </div>

            </div>

          </div>


          <div class="voting-card">

            <div class="voting-icon">
              ⚙
            </div>

            <h3>
              可以讨论什么？
            </h3>

            <p>
              社区投票可以用于服务器运行过程中的公共事项。
            </p>

            <div class="condition-list">

              <div>
                <span>✓</span>
                服务器配置
              </div>

              <div>
                <span>✓</span>
                插件相关事项
              </div>

              <div>
                <span>✓</span>
                管理员选举
              </div>

              <div>
                <span>✓</span>
                管理命令相关事项
              </div>

              <div>
                <span>✓</span>
                回档、恢复或重置等事项
              </div>

            </div>

          </div>

        </div>


        <div class="voting-bottom-notice">
          <span>!</span>

          <p>
            社区投票不能突破服务器的基本原则和底线。
            规则中的核心原则、安全底线以及必要的管理权限
            不会因为普通投票而被任意取消。
          </p>
        </div>

      </div>
    </section>

    <!-- =========================================================
         开源项目
    ========================================================== -->
    <section class="section project-section">
      <div class="container">

        <div class="project-box">

          <div class="project-content">

            <span class="section-label">
              OPEN SOURCE
            </span>

            <h2>
              项目公开
            </h2>

            <p>
              鸿迹社区相关项目持续进行开源管理。
              你可以通过 GitHub 了解服务器项目以及世界同步工具。
            </p>

            <div class="project-links">

              <a href="https://github.com/LiveLoon/swan-trace-minecraft-community" target="_blank"
                rel="noopener noreferrer" class="project-link primary">
                <span>📦</span>
                <span>
                  <strong>鸿迹社区项目</strong>
                  <small>GitHub Repository</small>
                </span>
              </a>


              <a href="https://github.com/LiveLoon/world-sync-mc-paper-plugin" target="_blank" rel="noopener noreferrer"
                class="project-link">
                <span>🔄</span>
                <span>
                  <strong>WorldSync</strong>
                  <small>世界同步工具</small>
                </span>
              </a>

            </div>

          </div>


          <div class="project-decoration">
            <div class="cube cube-1"></div>
            <div class="cube cube-2"></div>
            <div class="cube cube-3"></div>
          </div>

        </div>

      </div>
    </section>


    <!-- =========================================================
         抖音 / 社区渠道
    ========================================================== -->
    <section class="section channels-section">
      <div class="container">

        <div class="section-header centered">

          <span class="section-label">
            COMMUNITY CHANNELS
          </span>

          <h2>
            社区渠道
          </h2>

          <p>
            通过不同渠道了解鸿迹的最新动态。
          </p>

        </div>


        <div class="channel-grid">

          <div class="channel-card">

            <div class="channel-icon qq-icon">
              QQ
            </div>

            <div>
              <span>
                QQ 群
              </span>

              <strong>
                661436985
              </strong>

              <p>
                服务器地址、公告、申请、举报及社区交流
              </p>
            </div>

          </div>


          <div class="channel-card">

            <div class="channel-icon douyin-icon">
              ♪
            </div>

            <div>
              <span>
                抖音
              </span>

              <strong>
                43650753863
              </strong>

              <p>
                鸿迹社区相关内容与服务器动态
              </p>
            </div>

          </div>

        </div>

      </div>
    </section>

    <!-- =========================================================
         Footer
    ========================================================== -->
    <FooterC />

  </div>
</template>


<script setup lang="ts">
import FooterC from '@/components/FooterC.vue'
import { onMounted, onUnmounted, ref } from 'vue'


/* =========================================================
   状态
========================================================= */

const joinSection = ref<HTMLElement | null>(null)

const copied = ref(false)

let observer: IntersectionObserver | null = null


/* =========================================================
   滚动到加入区域
========================================================= */

const scrollToJoin = () => {
  joinSection.value?.scrollIntoView({
    behavior: 'smooth',
    block: 'start'
  })
}


/* =========================================================
   复制 QQ 群号
========================================================= */

const copyGroupNumber = async () => {
  const groupNumber = '661436985'

  try {
    await navigator.clipboard.writeText(groupNumber)

    copied.value = true

    window.setTimeout(() => {
      copied.value = false
    }, 2000)
  } catch (error) {
    console.error('复制 QQ 群号失败:', error)
  }
}


/* =========================================================
   滚动显现动画
========================================================= */

onMounted(() => {
  const sections = document.querySelectorAll(
    '.section, .final-section'
  )

  if (!sections.length) return

  observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add('is-visible')

          observer?.unobserve(entry.target)
        }
      })
    },
    {
      threshold: 0.08,
      rootMargin: '0px 0px -50px 0px'
    }
  )

  sections.forEach((section) => {
    observer?.observe(section)
  })
})


onUnmounted(() => {
  observer?.disconnect()
})
</script>


<style scoped>
/* =========================================================
   Global
========================================================= */

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

.page-wrapper {
  min-height: 100vh;

  font-family:
    'Segoe UI',
    'PingFang SC',
    'Microsoft YaHei',
    Roboto,
    system-ui,
    -apple-system,
    sans-serif;

  color: #e8edf2;

  background:
    radial-gradient(circle at 50% 0%,
      rgba(100, 130, 150, 0.08),
      transparent 35%),
    #0d0f14;

  line-height: 1.7;

  overflow: hidden;
}

.container {
  width: 100%;
  max-width: 1120px;

  margin: 0 auto;

  padding-left: 24px;
  padding-right: 24px;
}


/* =========================================================
   Hero
========================================================= */

.hero-section {
  position: relative;

  min-height: 650px;

  display: flex;
  align-items: center;

  overflow: hidden;

  border-bottom:
    1px solid rgba(255, 255, 255, 0.05);
}

.hero-background {
  position: absolute;
  inset: 0;

  background-image:
    linear-gradient(rgba(13, 15, 20, 0.75),
      rgba(13, 15, 20, 0.98)),
    radial-gradient(circle at center,
      rgba(150, 190, 210, 0.08),
      transparent 55%);

  pointer-events: none;
}

.hero-glow {
  position: absolute;

  width: 500px;
  height: 500px;

  border-radius: 50%;

  filter: blur(100px);

  opacity: 0.12;

  pointer-events: none;
}

.hero-glow-left {
  left: -300px;
  top: 100px;

  background: #7094a8;
}

.hero-glow-right {
  right: -300px;
  bottom: -200px;

  background: #d8c7a5;
}

.hero-container {
  position: relative;
  z-index: 2;
}

.hero-content {
  max-width: 850px;
  padding: 100px 0;
}

.hero-badge {
  display: inline-flex;
  align-items: center;
  gap: 9px;

  padding: 7px 15px;

  border:
    1px solid rgba(170, 199, 217, 0.16);

  border-radius: 30px;

  color: #aac7d9;

  background:
    rgba(170, 199, 217, 0.07);

  font-size: 12px;
  letter-spacing: 1.5px;
}

.status-dot {
  width: 7px;
  height: 7px;

  border-radius: 50%;

  background: #8db9a3;

  box-shadow:
    0 0 12px rgba(141, 185, 163, 0.7);
}

.hero-content h1 {
  margin-top: 28px;

  font-size: clamp(52px, 9vw, 94px);

  line-height: 1.05;

  font-weight: 800;

  letter-spacing: -4px;

  background:
    linear-gradient(135deg,
      #f0e6d0 0%,
      #e8edf2 45%,
      #aac7d9 100%);

  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;

  background-clip: text;
}

.hero-content h1 span {
  display: block;

  margin-top: 12px;

  font-size: clamp(24px, 4vw, 42px);

  letter-spacing: 0;

  opacity: 0.75;
}

.hero-description {
  max-width: 720px;

  margin-top: 28px;

  color: #9ba8b4;

  font-size: 17px;

  line-height: 2;
}

.hero-actions {
  display: flex;
  flex-wrap: wrap;

  gap: 12px;

  margin-top: 38px;
}

.hero-button,
.secondary-button {
  min-height: 48px;

  padding: 0 24px;

  display: inline-flex;
  align-items: center;
  justify-content: center;

  border-radius: 12px;

  font-size: 15px;
  font-weight: 600;

  text-decoration: none;

  cursor: pointer;

  transition:
    transform 0.2s ease,
    background 0.2s ease,
    border-color 0.2s ease;
}

.hero-button {
  gap: 14px;

  color: #101317;

  background: #e8e0d1;

  border: 1px solid #e8e0d1;
}

.hero-button:hover {
  transform: translateY(-2px);

  background: #f2eadc;
}

.hero-button .arrow {
  font-size: 18px;
}

.hero-note {
  display: flex;
  align-items: center;
  flex-wrap: wrap;

  gap: 13px;

  margin-top: 26px;

  color: #687887;

  font-size: 13px;
}

.hero-note i {
  width: 3px;
  height: 3px;

  border-radius: 50%;

  background: #53616e;
}


/* =========================================================
   Section
========================================================= */

.section,
.final-section {
  opacity: 0;

  transform: translateY(35px);

  transition:
    opacity 0.8s ease,
    transform 0.8s ease;
}

.section.is-visible,
.final-section.is-visible {
  opacity: 1;

  transform: translateY(0);
}

.section {
  padding: 100px 0;
}

.section-header {
  display: flex;

  align-items: flex-end;
  justify-content: space-between;

  gap: 24px;

  margin-bottom: 48px;
}

.section-header.centered {
  display: block;

  max-width: 760px;

  margin-left: auto;
  margin-right: auto;

  text-align: center;
}

.section-label {
  display: block;

  margin-bottom: 8px;

  color: #7892a3;

  font-size: 11px;
  font-weight: 700;

  letter-spacing: 2px;
}

.section-header h2 {
  font-size: clamp(30px, 5vw, 42px);

  line-height: 1.2;

  font-weight: 750;

  color: #e8edf2;
}

.section-header p {
  margin-top: 15px;

  color: #788796;

  font-size: 15px;
}

.badge {
  display: inline-block;

  flex-shrink: 0;

  padding: 5px 14px;

  color: #aac7d9;

  background: rgba(170, 199, 217, 0.08);

  border:
    1px solid rgba(170, 199, 217, 0.13);

  border-radius: 30px;

  font-size: 12px;
}


/* =========================================================
   Join
========================================================= */

.join-section {
  background:
    linear-gradient(180deg,
      rgba(255, 255, 255, 0.01),
      transparent);
}

.join-layout {
  display: grid;

  grid-template-columns:
    minmax(0, 1fr) minmax(360px, 450px);

  gap: 70px;

  align-items: center;
}

.join-info {
  display: flex;

  flex-direction: column;

  gap: 14px;
}

.info-card {
  display: flex;

  gap: 18px;

  padding: 22px;

  background:
    rgba(255, 255, 255, 0.025);

  border:
    1px solid rgba(255, 255, 255, 0.055);

  border-radius: 16px;

  transition:
    transform 0.2s ease,
    border-color 0.2s ease,
    background 0.2s ease;
}

.info-card:hover {
  transform: translateX(4px);

  border-color:
    rgba(170, 199, 217, 0.18);

  background:
    rgba(255, 255, 255, 0.04);
}

.highlight-card {
  border-color:
    rgba(216, 199, 165, 0.15);

  background:
    rgba(216, 199, 165, 0.035);
}

.info-icon {
  width: 42px;
  height: 42px;

  flex-shrink: 0;

  display: flex;
  align-items: center;
  justify-content: center;

  border-radius: 12px;

  background:
    rgba(255, 255, 255, 0.05);

  font-size: 20px;
}

.card-label {
  display: block;

  margin-bottom: 4px;

  color: #718392;

  font-size: 10px;

  font-weight: 700;

  letter-spacing: 1.5px;
}

.info-card h3 {
  color: #e6ebef;

  font-size: 17px;

  margin-bottom: 5px;
}

.info-card p {
  color: #7f8c99;

  font-size: 13px;

  line-height: 1.8;
}


/* =========================================================
   QR
========================================================= */

.qr-area {
  display: flex;

  justify-content: center;
}

.qr-wrapper {
  width: 100%;

  max-width: 420px;

  padding: 25px;

  text-align: center;

  background:
    rgba(255, 255, 255, 0.035);

  border:
    1px solid rgba(255, 255, 255, 0.075);

  border-radius: 24px;

  box-shadow:
    0 25px 70px rgba(0, 0, 0, 0.35);
}

.qr-header {
  display: flex;

  align-items: center;
  justify-content: center;

  gap: 8px;

  margin-bottom: 18px;

  color: #8796a4;

  font-size: 12px;
}

.qr-status {
  width: 6px;
  height: 6px;

  border-radius: 50%;

  background: #8db9a3;
}

.qr-image-wrapper {
  padding: 12px;

  background: #ffffff;

  border-radius: 16px;

  overflow: hidden;
}

.qr-image {
  display: block;

  width: 100%;

  max-height: 500px;

  object-fit: contain;

  border-radius: 8px;
}

.qr-content {
  padding: 20px 0 16px;
}

.qr-label {
  color: #718392;

  font-size: 10px;

  font-weight: 700;

  letter-spacing: 2px;
}

.group-number {
  margin-top: 4px;

  color: #f0e6d0;

  font-size: 30px;

  font-weight: 700;

  letter-spacing: 2px;
}

.qr-content p {
  margin-top: 3px;

  color: #71808e;

  font-size: 13px;
}

.copy-button {
  width: 100%;

  height: 46px;

  border-radius: 11px;

  border:
    1px solid rgba(170, 199, 217, 0.15);

  color: #b8d4e3;

  background:
    rgba(170, 199, 217, 0.07);

  cursor: pointer;

  font-size: 13px;

  transition:
    background 0.2s ease,
    border-color 0.2s ease;
}

.copy-button:hover {
  background:
    rgba(170, 199, 217, 0.12);

  border-color:
    rgba(170, 199, 217, 0.25);
}


/* =========================================================
   Feature
========================================================= */

.feature-grid {
  display: grid;

  grid-template-columns:
    repeat(4, 1fr);

  gap: 14px;
}

.feature-card {
  position: relative;

  min-height: 250px;

  padding: 28px;

  overflow: hidden;

  background:
    rgba(255, 255, 255, 0.025);

  border:
    1px solid rgba(255, 255, 255, 0.055);

  border-radius: 18px;

  transition:
    transform 0.25s ease,
    border-color 0.25s ease;
}

.feature-card:hover {
  transform: translateY(-5px);

  border-color:
    rgba(170, 199, 217, 0.18);
}

.feature-number {
  position: absolute;

  top: 20px;
  right: 22px;

  color: rgba(255, 255, 255, 0.08);

  font-size: 28px;

  font-weight: 800;
}

.feature-icon {
  font-size: 28px;

  margin-bottom: 28px;
}

.feature-card h3 {
  margin-bottom: 10px;

  color: #e6ebef;

  font-size: 17px;
}

.feature-card p {
  color: #788693;

  font-size: 13px;

  line-height: 1.9;
}


/* =========================================================
   Report
========================================================= */

.report-section {
  background:
    rgba(255, 255, 255, 0.012);
}

.report-box {
  display: grid;

  grid-template-columns: auto 1fr;

  gap: 28px;

  padding: 45px;

  border:
    1px solid rgba(216, 199, 165, 0.12);

  border-radius: 22px;

  background:
    linear-gradient(135deg,
      rgba(216, 199, 165, 0.045),
      rgba(255, 255, 255, 0.015));
}

.report-icon {
  width: 54px;
  height: 54px;

  display: flex;
  align-items: center;
  justify-content: center;

  border-radius: 15px;

  background:
    rgba(216, 199, 165, 0.08);

  color: #d8c7a5;

  font-size: 24px;
}

.report-content h2 {
  margin-top: 2px;

  font-size: 32px;
}

.report-content>p {
  margin-top: 12px;

  color: #7d8a97;

  font-size: 14px;
}

.report-requirements {
  display: grid;

  grid-template-columns:
    repeat(3, 1fr);

  gap: 12px;

  margin-top: 30px;
}

.requirement {
  display: flex;

  gap: 12px;

  padding: 18px;

  border-radius: 13px;

  background:
    rgba(255, 255, 255, 0.025);
}

.requirement-icon {
  font-size: 18px;
}

.requirement strong {
  display: block;

  margin-bottom: 4px;

  color: #dfe5e9;

  font-size: 13px;
}

.requirement p {
  color: #71808e;

  font-size: 12px;

  line-height: 1.7;
}


/* =========================================================
   Notice
========================================================= */

.notice-grid {
  display: grid;

  grid-template-columns:
    repeat(4, 1fr);

  gap: 14px;
}

.notice-card {
  padding: 28px 24px;

  border:
    1px solid rgba(255, 255, 255, 0.055);

  background:
    rgba(255, 255, 255, 0.02);

  border-radius: 17px;
}

.notice-icon {
  margin-bottom: 22px;

  font-size: 25px;
}

.notice-card h3 {
  margin-bottom: 10px;

  font-size: 16px;

  color: #e4e9ed;
}

.notice-card p {
  color: #778593;

  font-size: 13px;

  line-height: 1.85;
}


/* =========================================================
   Voting
========================================================= */

.voting-layout {
  display: grid;

  grid-template-columns:
    repeat(2, 1fr);

  gap: 16px;
}

.voting-card {
  padding: 34px;

  background:
    rgba(255, 255, 255, 0.025);

  border:
    1px solid rgba(255, 255, 255, 0.055);

  border-radius: 20px;
}

.voting-icon {
  font-size: 28px;

  margin-bottom: 18px;
}

.voting-card h3 {
  font-size: 20px;

  margin-bottom: 10px;
}

.voting-card>p {
  color: #788693;

  font-size: 13px;

  line-height: 1.8;
}

.condition-list {
  display: flex;

  flex-direction: column;

  gap: 9px;

  margin-top: 25px;
}

.condition-list div {
  display: flex;

  gap: 9px;

  align-items: center;

  color: #9aa6b0;

  font-size: 13px;
}

.condition-list span {
  color: #8db9a3;
}

.voting-bottom-notice {
  display: flex;

  align-items: flex-start;

  gap: 13px;

  margin-top: 16px;

  padding: 18px 20px;

  border-radius: 14px;

  background:
    rgba(170, 199, 217, 0.04);

  border:
    1px solid rgba(170, 199, 217, 0.08);
}

.voting-bottom-notice>span {
  width: 22px;
  height: 22px;

  display: flex;
  align-items: center;
  justify-content: center;

  flex-shrink: 0;

  border-radius: 50%;

  background:
    rgba(170, 199, 217, 0.1);

  color: #aac7d9;

  font-size: 12px;
}

.voting-bottom-notice p {
  color: #798794;

  font-size: 12px;

  line-height: 1.8;
}


/* =========================================================
   Management
========================================================= */

.management-grid {
  display: grid;

  grid-template-columns:
    repeat(2, 1fr);

  gap: 16px;
}

.management-card {
  padding: 32px;

  border:
    1px solid rgba(255, 255, 255, 0.055);

  border-radius: 20px;

  background:
    rgba(255, 255, 255, 0.025);
}

.owner-card {
  border-color:
    rgba(216, 199, 165, 0.13);
}

.management-top {
  display: flex;

  align-items: center;

  gap: 15px;

  margin-bottom: 22px;
}

.management-avatar {
  width: 52px;
  height: 52px;

  display: flex;
  align-items: center;
  justify-content: center;

  border-radius: 14px;

  background:
    rgba(216, 199, 165, 0.1);

  color: #d8c7a5;

  font-size: 20px;

  font-weight: 700;
}

.admin-avatar {
  color: #aac7d9;

  background:
    rgba(170, 199, 217, 0.1);
}

.management-role {
  color: #6f7e8c;

  font-size: 10px;

  font-weight: 700;

  letter-spacing: 1.5px;
}

.management-top h3 {
  margin-top: 2px;

  font-size: 19px;
}

.management-card>p {
  color: #7c8996;

  font-size: 13px;

  line-height: 1.9;
}

.management-tags {
  display: flex;

  flex-wrap: wrap;

  gap: 7px;

  margin-top: 20px;
}

.management-tags span {
  padding: 4px 9px;

  border-radius: 6px;

  background:
    rgba(255, 255, 255, 0.04);

  color: #73818e;

  font-size: 10px;
}


/* =========================================================
   Project
========================================================= */

.project-box {
  position: relative;

  display: grid;

  grid-template-columns: 1fr 300px;

  gap: 40px;

  padding: 45px;

  overflow: hidden;

  border:
    1px solid rgba(170, 199, 217, 0.1);

  border-radius: 22px;

  background:
    linear-gradient(135deg,
      rgba(170, 199, 217, 0.045),
      rgba(255, 255, 255, 0.015));
}

.project-content {
  position: relative;

  z-index: 2;
}

.project-content h2 {
  margin-bottom: 12px;

  font-size: 32px;
}

.project-content>p {
  max-width: 650px;

  color: #788693;

  font-size: 14px;
}

.project-links {
  display: flex;

  flex-wrap: wrap;

  gap: 10px;

  margin-top: 28px;
}

.project-link {
  display: flex;

  align-items: center;

  gap: 12px;

  min-width: 210px;

  padding: 14px 17px;

  color: #b5c0c8;

  text-decoration: none;

  border:
    1px solid rgba(255, 255, 255, 0.07);

  border-radius: 12px;

  background:
    rgba(255, 255, 255, 0.025);

  transition:
    transform 0.2s ease,
    border-color 0.2s ease;
}

.project-link:hover {
  transform: translateY(-2px);

  border-color:
    rgba(170, 199, 217, 0.2);
}

.project-link.primary {
  border-color:
    rgba(216, 199, 165, 0.15);
}

.project-link>span:first-child {
  font-size: 20px;
}

.project-link strong {
  display: block;

  color: #dce3e8;

  font-size: 13px;
}

.project-link small {
  display: block;

  margin-top: 2px;

  color: #697885;

  font-size: 10px;
}

.project-decoration {
  position: relative;

  min-height: 180px;
}

.cube {
  position: absolute;

  width: 100px;
  height: 100px;

  border:
    1px solid rgba(170, 199, 217, 0.1);

  transform:
    rotate(45deg);

  background:
    rgba(170, 199, 217, 0.025);
}

.cube-1 {
  top: 20px;
  right: 100px;
}

.cube-2 {
  top: 70px;
  right: 25px;

  width: 70px;
  height: 70px;
}

.cube-3 {
  top: 100px;
  right: 150px;

  width: 45px;
  height: 45px;
}


/* =========================================================
   Channels
========================================================= */

.channel-grid {
  display: grid;

  grid-template-columns:
    repeat(2, 1fr);

  gap: 16px;

  max-width: 850px;

  margin: 0 auto;
}

.channel-card {
  display: flex;

  align-items: center;

  gap: 20px;

  padding: 25px;

  border:
    1px solid rgba(255, 255, 255, 0.055);

  border-radius: 17px;

  background:
    rgba(255, 255, 255, 0.025);
}

.channel-icon {
  width: 52px;
  height: 52px;

  display: flex;
  align-items: center;
  justify-content: center;

  flex-shrink: 0;

  border-radius: 14px;

  font-size: 18px;

  font-weight: 800;
}

.qq-icon {
  color: #b8d4e3;

  background:
    rgba(170, 199, 217, 0.09);
}

.douyin-icon {
  color: #e6e6e6;

  background:
    rgba(255, 255, 255, 0.07);
}

.channel-card span {
  display: block;

  color: #71808e;

  font-size: 11px;
}

.channel-card strong {
  display: block;

  margin-top: 2px;

  color: #e3e8eb;

  font-size: 18px;
}

.channel-card p {
  margin-top: 4px;

  color: #697885;

  font-size: 11px;
}


/* =========================================================
   Final
========================================================= */

.final-section {
  padding: 130px 0;

  text-align: center;

  border-top:
    1px solid rgba(255, 255, 255, 0.04);
}

.final-content {
  max-width: 760px;

  margin: 0 auto;
}

.final-label {
  color: #687b89;

  font-size: 11px;

  font-weight: 700;

  letter-spacing: 3px;
}

.final-quote {
  margin-top: 25px;

  color: rgba(216, 199, 165, 0.35);

  font-family: Georgia, serif;

  font-size: 70px;

  line-height: 0.8;
}

.final-content h2 {
  margin-top: 12px;

  font-size: clamp(28px, 5vw, 42px);

  color: #e8edf2;
}

.final-content>p {
  margin-top: 20px;

  color: #788693;

  font-size: 14px;

  line-height: 2;
}

.final-divider {
  width: 45px;
  height: 1px;

  margin: 30px auto;

  background:
    rgba(216, 199, 165, 0.35);
}

.final-sign {
  color: #687885;

  font-size: 11px;

  letter-spacing: 1px;
}


/* =========================================================
   Responsive
========================================================= */

@media (max-width: 900px) {

  .join-layout {
    grid-template-columns: 1fr;

    gap: 40px;
  }

  .qr-area {
    justify-content: center;
  }

  .feature-grid,
  .notice-grid {
    grid-template-columns:
      repeat(2, 1fr);
  }

  .report-requirements {
    grid-template-columns: 1fr;
  }

  .project-box {
    grid-template-columns: 1fr;
  }

  .project-decoration {
    display: none;
  }
}


@media (max-width: 700px) {

  .section {
    padding: 70px 0;
  }

  .hero-section {
    min-height: 580px;
  }

  .hero-content {
    padding: 70px 0;
  }

  .hero-content h1 {
    letter-spacing: -2px;
  }

  .hero-description {
    font-size: 15px;
  }

  .section-header {
    display: block;
  }

  .section-header .badge {
    margin-top: 15px;
  }

  .feature-grid,
  .notice-grid,
  .voting-layout,
  .management-grid,
  .channel-grid {
    grid-template-columns: 1fr;
  }

  .report-box {
    grid-template-columns: 1fr;

    padding: 28px;
  }

  .project-box {
    padding: 30px;
  }

  .qr-wrapper {
    max-width: 420px;
  }
}


@media (max-width: 480px) {

  .container {
    padding-left: 16px;
    padding-right: 16px;
  }

  .hero-content h1 {
    font-size: 48px;
  }

  .hero-content h1 span {
    font-size: 23px;
  }

  .hero-actions {
    flex-direction: column;
  }

  .hero-button,
  .secondary-button {
    width: 100%;
  }

  .hero-note {
    gap: 8px;
  }

  .info-card {
    padding: 18px;
  }

  .qr-wrapper {
    padding: 18px;
  }

  .qr-image-wrapper {
    padding: 8px;
  }

  .group-number {
    font-size: 26px;
  }

  .feature-card {
    min-height: auto;
  }

  .report-box {
    padding: 22px;
  }

  .management-card {
    padding: 24px;
  }

  .project-links {
    flex-direction: column;
  }

  .project-link {
    width: 100%;
    min-width: 0;
  }

  .final-section {
    padding: 90px 0;
  }
}


/* =========================================================
   Reduced Motion
========================================================= */

@media (prefers-reduced-motion: reduce) {

  .section,
  .final-section {
    opacity: 1;
    transform: none;

    transition: none;
  }

  .hero-button,
  .info-card,
  .feature-card,
  .project-link {
    transition: none;
  }
}
</style>