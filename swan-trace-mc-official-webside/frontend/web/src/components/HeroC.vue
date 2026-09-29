```vue
<template>
  <div class="home-page">
    <!-- =====================================================
         Hero
         ===================================================== -->
    <section class="hero">
      <!-- 背景 -->
      <div class="hero-bg"></div>

      <!-- 网格 -->
      <div class="hero-grid"></div>

      <!-- 光晕 -->
      <div class="hero-glow hero-glow-left"></div>
      <div class="hero-glow hero-glow-right"></div>

      <!-- 内容 -->
      <div class="hero-content">

        <!-- 顶部标识 -->
        <div class="hero-eyebrow">
          <span class="eyebrow-line"></span>

          <span>
            SWAN TRACE · MINECRAFT COMMUNITY
          </span>

          <span class="eyebrow-line"></span>
        </div>

        <!-- Logo -->
        <div class="hero-mark">
          <span>S</span>
        </div>

        <!-- 标题 -->
        <h1 class="hero-title">
          <span class="title-cn">鸿迹</span>

          <span class="title-en">
            SWAN TRACE
          </span>
        </h1>

        <!-- 描述 -->
        <p class="hero-sub">
          鸿迹（Swan Trace）Minecraft Community
          <br />

          <span>
            一个纯净、自由、公益的 Minecraft 社区。
          </span>
        </p>

        <!-- 标签 -->
        <div class="hero-tags">
          <span>纯净</span>
          <span>自由</span>
          <span>公益</span>
          <span>Minecraft 26.2</span>
        </div>

        <!-- 按钮 -->
        <div class="hero-actions">
          <button
            class="btn-primary btn-join"
            @click="router.push('/join')"
          >
            <span class="btn-icon">⚡</span>
            <span>加入我们</span>
          </button>

          <button
            class="btn-secondary"
            @click="router.push('/about')"
          >
            了解鸿迹
            <span>→</span>
          </button>
        </div>

        <!-- 状态 -->
        <div class="hero-status">
          <span class="status-dot"></span>

          <span>
            COMMUNITY · MINECRAFT 26.2
          </span>

          <span class="status-divider"></span>

          <span>
            24/7
          </span>
        </div>

      </div>

      <!-- 底部滚动提示 -->
      <div class="hero-scroll">
        <span class="scroll-line"></span>

        <span class="scroll-text">
          SCROLL TO EXPLORE
        </span>

        <span class="scroll-arrow">
          ↓
        </span>
      </div>

      <!-- 底部渐变 -->
      <div class="hero-bottom-fade"></div>
    </section>
  </div>
</template>

<script setup lang="ts">
import router from '@/router'
</script>

<style scoped>
/* =========================================================
   基础
   ========================================================= */

* {
  box-sizing: border-box;
}

.home-page {
  width: 100%;

  background: #0d0f14;

  color: #e8edf2;
}

/* =========================================================
   Hero
   ========================================================= */

.hero {
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  min-height: calc(100vh - 68px);

  padding: 80px 24px 120px;

  overflow: hidden;

  background:
    radial-gradient(
      circle at 50% 35%,
      rgba(109, 179, 242, 0.07),
      transparent 34%
    ),
    #0d0f14;
}

/* =========================================================
   背景
   ========================================================= */

.hero-bg {
  position: absolute;

  inset: 0;

  background:
    radial-gradient(
      circle at 20% 35%,
      rgba(88, 134, 168, 0.07),
      transparent 28%
    ),
    radial-gradient(
      circle at 80% 25%,
      rgba(109, 179, 242, 0.045),
      transparent 25%
    );

  pointer-events: none;
}

/* =========================================================
   网格
   ========================================================= */

.hero-grid {
  position: absolute;

  inset: 0;

  opacity: 0.18;

  background-image:
    linear-gradient(
      rgba(255, 255, 255, 0.025) 1px,
      transparent 1px
    ),
    linear-gradient(
      90deg,
      rgba(255, 255, 255, 0.025) 1px,
      transparent 1px
    );

  background-size: 60px 60px;

  mask-image:
    linear-gradient(
      to bottom,
      black,
      transparent 75%
    );

  -webkit-mask-image:
    linear-gradient(
      to bottom,
      black,
      transparent 75%
    );

  pointer-events: none;
}

/* =========================================================
   光晕
   ========================================================= */

.hero-glow {
  position: absolute;

  width: 420px;
  height: 420px;

  border-radius: 50%;

  filter: blur(90px);

  opacity: 0.09;

  pointer-events: none;
}

.hero-glow-left {
  left: -250px;
  top: 20%;

  background: #6daedc;
}

.hero-glow-right {
  right: -250px;
  top: 10%;

  background: #8daec4;
}

/* =========================================================
   Hero 内容
   ========================================================= */

.hero-content {
  position: relative;
  z-index: 2;

  width: min(900px, 100%);

  text-align: center;

  animation:
    heroEnter 0.9s ease-out both;
}

@keyframes heroEnter {
  from {
    opacity: 0;
    transform: translateY(22px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* =========================================================
   顶部英文
   ========================================================= */

.hero-eyebrow {
  display: flex;
  align-items: center;
  justify-content: center;

  gap: 12px;

  margin-bottom: 28px;

  color: #607588;

  font-family:
    'SFMono-Regular',
    Consolas,
    monospace;

  font-size: 9px;
  font-weight: 700;

  letter-spacing: 0.22em;
}

.eyebrow-line {
  width: 42px;
  height: 1px;

  background:
    linear-gradient(
      90deg,
      transparent,
      rgba(109, 179, 242, 0.3)
    );
}

.eyebrow-line:last-child {
  background:
    linear-gradient(
      90deg,
      rgba(109, 179, 242, 0.3),
      transparent
    );
}

/* =========================================================
   Logo
   ========================================================= */

.hero-mark {
  display: flex;
  align-items: center;
  justify-content: center;

  width: 76px;
  height: 76px;

  margin: 0 auto 22px;

  border-radius: 22px;

  border:
    1px solid rgba(109, 179, 242, 0.18);

  background:
    linear-gradient(
      135deg,
      rgba(109, 179, 242, 0.12),
      rgba(255, 255, 255, 0.025)
    );

  box-shadow:
    0 0 45px rgba(109, 179, 242, 0.07),
    inset 0 0 25px rgba(255, 255, 255, 0.025);

  animation:
    logoFloat 4s ease-in-out infinite;
}

.hero-mark span {
  color: #dce8ef;

  font-family:
    Georgia,
    'Times New Roman',
    serif;

  font-size: 32px;
  font-weight: 700;
}

@keyframes logoFloat {
  0%,
  100% {
    transform: translateY(0);
  }

  50% {
    transform: translateY(-4px);
  }
}

/* =========================================================
   标题
   ========================================================= */

.hero-title {
  display: flex;
  flex-direction: column;

  margin: 0;

  line-height: 1;
}

.title-cn {
  background:
    linear-gradient(
      135deg,
      #f0e6d0 0%,
      #dbe7ed 48%,
      #9bb8cc 100%
    );

  -webkit-background-clip: text;
  background-clip: text;

  -webkit-text-fill-color: transparent;

  font-size: clamp(58px, 9vw, 104px);

  font-weight: 800;

  letter-spacing: -0.06em;
}

.title-en {
  margin-top: 13px;

  color: #70879a;

  font-family:
    'SFMono-Regular',
    Consolas,
    monospace;

  font-size: clamp(11px, 1.5vw, 15px);

  font-weight: 600;

  letter-spacing: 0.42em;

  padding-left: 0.42em;
}

/* =========================================================
   描述
   ========================================================= */

.hero-sub {
  margin: 30px auto 0;

  color: #b0bfca;

  font-size: 17px;

  line-height: 1.9;

  letter-spacing: 0.01em;
}

.hero-sub span {
  color: #7f94a5;

  font-size: 14px;
}

/* =========================================================
   标签
   ========================================================= */

.hero-tags {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;

  gap: 8px;

  margin-top: 22px;
}

.hero-tags span {
  padding: 7px 12px;

  border:
    1px solid rgba(255, 255, 255, 0.06);

  border-radius: 20px;

  background:
    rgba(255, 255, 255, 0.018);

  color: #72879a;

  font-size: 10px;
}

/* =========================================================
   按钮
   ========================================================= */

.hero-actions {
  display: flex;
  justify-content: center;
  align-items: center;

  gap: 10px;

  margin-top: 30px;
}

.hero button {
  font-family: inherit;

  cursor: pointer;
}

/* 主按钮 */

.btn-primary {
  display: inline-flex;
  align-items: center;
  justify-content: center;

  gap: 8px;

  min-width: 142px;
  height: 44px;

  padding: 0 20px;

  border: 1px solid rgba(142, 194, 229, 0.2);

  border-radius: 9px;

  background:
    linear-gradient(
      135deg,
      rgba(109, 179, 242, 0.16),
      rgba(109, 179, 242, 0.07)
    );

  color: #dceaf2;

  font-size: 13px;
  font-weight: 600;

  box-shadow:
    0 8px 25px rgba(0, 0, 0, 0.16);

  transition:
    transform 0.25s ease,
    border-color 0.25s ease,
    background 0.25s ease,
    box-shadow 0.25s ease;
}

.btn-primary:hover {
  transform: translateY(-2px);

  border-color:
    rgba(142, 194, 229, 0.35);

  background:
    linear-gradient(
      135deg,
      rgba(109, 179, 242, 0.23),
      rgba(109, 179, 242, 0.1)
    );

  box-shadow:
    0 12px 30px rgba(0, 0, 0, 0.24),
    0 0 25px rgba(109, 179, 242, 0.07);
}

.btn-icon {
  color: #7fbbe4;
}

/* 次按钮 */

.btn-secondary {
  display: inline-flex;
  align-items: center;
  justify-content: center;

  gap: 8px;

  height: 44px;

  padding: 0 19px;

  border:
    1px solid rgba(255, 255, 255, 0.07);

  border-radius: 9px;

  background:
    rgba(255, 255, 255, 0.018);

  color: #8498a9;

  font-size: 13px;

  transition:
    color 0.25s ease,
    border-color 0.25s ease,
    background 0.25s ease;
}

.btn-secondary:hover {
  color: #d9e4eb;

  border-color:
    rgba(255, 255, 255, 0.13);

  background:
    rgba(255, 255, 255, 0.035);
}

/* =========================================================
   状态
   ========================================================= */

.hero-status {
  display: flex;
  align-items: center;
  justify-content: center;

  gap: 8px;

  margin-top: 28px;

  color: #536b7e;

  font-family:
    'SFMono-Regular',
    Consolas,
    monospace;

  font-size: 8px;

  letter-spacing: 0.13em;
}

.status-dot {
  width: 6px;
  height: 6px;

  border-radius: 50%;

  background: #79b9df;

  box-shadow:
    0 0 8px rgba(121, 185, 223, 0.4);

  animation:
    statusPulse 2.5s ease-in-out infinite;
}

@keyframes statusPulse {
  0%,
  100% {
    opacity: 0.45;
  }

  50% {
    opacity: 1;
  }
}

.status-divider {
  width: 18px;
  height: 1px;

  background:
    rgba(255, 255, 255, 0.08);
}

/* =========================================================
   滚动提示
   ========================================================= */

.hero-scroll {
  position: absolute;

  z-index: 3;

  left: 50%;
  bottom: 28px;

  transform: translateX(-50%);

  display: flex;
  align-items: center;
  flex-direction: column;

  gap: 7px;

  color: #4e6273;

  pointer-events: none;
}

.scroll-line {
  width: 1px;
  height: 25px;

  background:
    linear-gradient(
      to bottom,
      rgba(109, 179, 242, 0.45),
      transparent
    );
}

.scroll-text {
  font-family:
    'SFMono-Regular',
    Consolas,
    monospace;

  font-size: 7px;

  letter-spacing: 0.2em;

  white-space: nowrap;
}

.scroll-arrow {
  font-size: 12px;

  animation:
    scrollArrow 2s ease-in-out infinite;
}

@keyframes scrollArrow {
  0%,
  100% {
    transform: translateY(0);
    opacity: 0.45;
  }

  50% {
    transform: translateY(4px);
    opacity: 1;
  }
}

/* =========================================================
   底部渐变
   ========================================================= */

.hero-bottom-fade {
  position: absolute;

  left: 0;
  right: 0;
  bottom: 0;

  height: 130px;

  background:
    linear-gradient(
      to bottom,
      transparent,
      #0d0f14
    );

  pointer-events: none;
}

/* =========================================================
   平板
   ========================================================= */

@media (max-width: 768px) {
  .hero {
    min-height: calc(100vh - 62px);

    padding:
      70px
      18px
      100px;
  }

  .hero-eyebrow {
    font-size: 8px;

    letter-spacing: 0.16em;
  }

  .hero-mark {
    width: 68px;
    height: 68px;

    border-radius: 19px;
  }

  .hero-mark span {
    font-size: 28px;
  }

  .hero-sub {
    font-size: 15px;
  }

  .hero-sub span {
    font-size: 13px;
  }

  .hero-scroll {
    bottom: 20px;
  }
}

/* =========================================================
   手机
   ========================================================= */

@media (max-width: 520px) {
  .hero {
    min-height: calc(100vh - 62px);

    padding:
      55px
      16px
      90px;
  }

  .hero-eyebrow {
    gap: 8px;

    font-size: 7px;

    letter-spacing: 0.11em;
  }

  .eyebrow-line {
    width: 22px;
  }

  .hero-mark {
    width: 60px;
    height: 60px;

    margin-bottom: 18px;

    border-radius: 17px;
  }

  .hero-mark span {
    font-size: 25px;
  }

  .title-cn {
    font-size: 62px;
  }

  .title-en {
    margin-top: 10px;

    font-size: 9px;

    letter-spacing: 0.3em;

    padding-left: 0.3em;
  }

  .hero-sub {
    margin-top: 24px;

    font-size: 14px;

    line-height: 1.8;
  }

  .hero-sub span {
    font-size: 12px;
  }

  .hero-tags {
    margin-top: 18px;
  }

  .hero-tags span {
    padding: 6px 9px;

    font-size: 9px;
  }

  .hero-actions {
    flex-direction: column;

    width: 100%;

    margin-top: 26px;
  }

  .btn-primary,
  .btn-secondary {
    width: min(280px, 100%);
  }

  .hero-status {
    margin-top: 22px;

    font-size: 7px;
  }

  .hero-grid {
    background-size: 42px 42px;
  }

  .hero-glow {
    width: 250px;
    height: 250px;

    filter: blur(65px);
  }
}

/* =========================================================
   减少动画
   ========================================================= */

@media (prefers-reduced-motion: reduce) {
  .hero-content,
  .hero-mark,
  .status-dot,
  .scroll-arrow {
    animation: none;
  }

  .btn-primary,
  .btn-secondary {
    transition: none;
  }
}
</style>