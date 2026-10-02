<template>
  <nav class="navbar">
    <div class="navbar-container">
      <!-- 左侧品牌 -->
      <router-link to="/" class="brand" @click="closeMenu">
        <AvatarC />
        <div class="brand-text">
          <span class="brand-name">鸿迹</span>
          <span class="brand-en">SWAN TRACE</span>
        </div>
      </router-link>

      <!-- 桌面端导航 -->
      <ul class="nav-menu" :class="{ 'is-open': isMenuOpen }">
        <li v-for="item in navItems" :key="item.name" class="nav-item">
          <router-link :to="item.path" class="nav-link" active-class="is-active" exact-active-class="is-exact-active"
            @click="closeMenu">
            <span class="nav-name">{{ item.name }}</span>

            <span v-if="item.badge" class="nav-badge">
              {{ item.badge }}
            </span>
          </router-link>
        </li>
      </ul>

      <!-- 右侧状态 -->
      <div class="nav-status">
        <span class="status-dot"></span>
        <span class="status-text">26.2</span>
      </div>

      <!-- 手机端菜单按钮 -->
      <button class="hamburger" :class="{ 'is-open': isMenuOpen }" @click="toggleMenu" aria-label="切换菜单"
        :aria-expanded="isMenuOpen">
        <span class="bar"></span>
        <span class="bar"></span>
        <span class="bar"></span>
      </button>
    </div>

    <!-- 手机端菜单遮罩 -->
    <div v-if="isMenuOpen" class="mobile-backdrop" @click="closeMenu"></div>
  </nav>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import AvatarC from './AvatarC.vue'

/**
 * 导航菜单
 *
 * 如果以后新增独立的：
 * /plugins
 * /config
 *
 * 可以直接修改这里的 path。
 */
const navItems = [
  { name: '首页', path: '/' },
  { name: '服务器信息', path: '/server' },
  { name: '加入我们', path: '/join' },
  { name: '玩家', path: '/players' },
  { name: '数据', path: '/stats' },
  { name: '存档', path: '/downloads' },
  { name: '申请', path: '/join', badge: '社区' },
  { name: '插件信息', path: '/plugins' },
  { name: '免责声明', path: '/disclaimer' },
  { name: '关于', path: '/about' }
]

/**
 * 手机端菜单状态
 */
const isMenuOpen = ref(false)

/**
 * 切换菜单
 */
const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value
}

/**
 * 关闭菜单
 */
const closeMenu = () => {
  isMenuOpen.value = false
}
</script>

<style scoped>
/* =========================================================
   基础
   ========================================================= */

* {
  box-sizing: border-box;
}

.navbar {
  position: sticky;
  top: 0;
  z-index: 1000;

  width: 100%;

  background:
    linear-gradient(180deg,
      rgba(13, 15, 20, 0.96),
      rgba(13, 15, 20, 0.88));

  border-bottom: 1px solid rgba(255, 255, 255, 0.06);

  backdrop-filter: blur(18px);
  -webkit-backdrop-filter: blur(18px);
}

/* 顶部非常细的装饰线 */

.navbar::before {
  content: '';

  position: absolute;
  top: 0;
  left: 0;
  right: 0;

  height: 1px;

  background:
    linear-gradient(90deg,
      transparent,
      rgba(109, 179, 242, 0.35),
      transparent);

  pointer-events: none;
}

/* =========================================================
   容器
   ========================================================= */

.navbar-container {
  position: relative;

  display: flex;
  align-items: center;

  max-width: 1280px;
  height: 68px;

  margin: 0 auto;
  padding: 0 24px;
}

/* =========================================================
   品牌
   ========================================================= */

.brand {
  display: flex;
  align-items: center;
  gap: 10px;

  flex-shrink: 0;

  color: #e8edf2;
  text-decoration: none;

  margin-right: 34px;

  transition:
    opacity 0.25s ease,
    transform 0.25s ease;
}

.brand:hover {
  opacity: 0.9;
  transform: translateY(-1px);
}

/* 品牌文字 */

.brand-text {
  display: flex;
  flex-direction: column;

  line-height: 1;
}

.brand-name {
  font-size: 16px;
  font-weight: 700;

  color: #e8edf2;

  letter-spacing: 0.5px;
}

.brand-en {
  margin-top: 4px;

  font-size: 8px;
  font-weight: 600;

  color: #6f879d;

  letter-spacing: 1.8px;
}

/* =========================================================
   导航菜单
   ========================================================= */

.nav-menu {
  display: flex;
  align-items: center;

  gap: 4px;

  margin: 0;
  padding: 0;

  list-style: none;
}

.nav-item {
  display: flex;
  align-items: center;
}

.nav-link {
  position: relative;

  display: flex;
  align-items: center;
  gap: 6px;

  height: 40px;

  padding: 0 12px;

  color: #8ea1b3;

  text-decoration: none;

  font-size: 14px;
  font-weight: 500;

  border-radius: 9px;

  transition:
    color 0.25s ease,
    background 0.25s ease;
}

/* hover */

.nav-link:hover {
  color: #e8edf2;

  background: rgba(255, 255, 255, 0.045);
}

/* 当前页面 */

.nav-link.is-active,
.nav-link.is-exact-active {
  color: #e8edf2;

  background:
    linear-gradient(135deg,
      rgba(109, 179, 242, 0.11),
      rgba(109, 179, 242, 0.04));
}

/* 当前页面底部装饰 */

.nav-link.is-active::after,
.nav-link.is-exact-active::after {
  content: '';

  position: absolute;

  left: 50%;
  bottom: 4px;

  width: 16px;
  height: 2px;

  transform: translateX(-50%);

  border-radius: 10px;

  background: #6db3f2;

  box-shadow:
    0 0 8px rgba(109, 179, 242, 0.4);
}

/* =========================================================
   导航 Badge
   ========================================================= */

.nav-badge {
  display: inline-flex;
  align-items: center;

  height: 16px;

  padding: 0 5px;

  border-radius: 4px;

  font-size: 8px;
  font-weight: 700;

  color: #8fbddd;

  background: rgba(109, 179, 242, 0.08);

  border: 1px solid rgba(109, 179, 242, 0.12);

  letter-spacing: 0.2px;
}

/* =========================================================
   右侧状态
   ========================================================= */

.nav-status {
  display: flex;
  align-items: center;
  gap: 7px;

  margin-left: auto;

  padding-left: 18px;

  color: #708397;

  font-size: 12px;
}

.status-dot {
  width: 7px;
  height: 7px;

  border-radius: 50%;

  background: #76b5d8;

  box-shadow:
    0 0 0 3px rgba(118, 181, 216, 0.08),
    0 0 10px rgba(118, 181, 216, 0.35);

  animation: statusPulse 2.5s ease-in-out infinite;
}

.status-text {
  letter-spacing: 0.5px;
}

/* 状态呼吸 */

@keyframes statusPulse {

  0%,
  100% {
    opacity: 0.55;
  }

  50% {
    opacity: 1;
  }
}

/* =========================================================
   汉堡按钮
   ========================================================= */

.hamburger {
  display: none;

  position: relative;

  flex-direction: column;
  justify-content: center;
  align-items: center;

  width: 40px;
  height: 40px;

  margin-left: auto;

  padding: 0;

  border: 1px solid rgba(255, 255, 255, 0.07);
  border-radius: 9px;

  background: rgba(255, 255, 255, 0.025);

  cursor: pointer;
}

.hamburger .bar {
  display: block;

  width: 18px;
  height: 2px;

  margin: 2px 0;

  border-radius: 2px;

  background: #b9c9d7;

  transition:
    transform 0.3s ease,
    opacity 0.3s ease;
}

/* 汉堡 -> X */

.hamburger.is-open .bar:nth-child(1) {
  transform: translateY(6px) rotate(45deg);
}

.hamburger.is-open .bar:nth-child(2) {
  opacity: 0;
}

.hamburger.is-open .bar:nth-child(3) {
  transform: translateY(-6px) rotate(-45deg);
}

/* =========================================================
   手机端遮罩
   ========================================================= */

.mobile-backdrop {
  display: none;
}

/* =========================================================
   平板 / 手机
   ========================================================= */

@media (max-width: 980px) {
  .navbar-container {
    height: 62px;

    padding: 0 18px;
  }

  .brand {
    margin-right: 16px;
  }

  .brand-name {
    font-size: 15px;
  }

  .brand-en {
    font-size: 7px;
  }

  .nav-link {
    padding: 0 8px;
    font-size: 13px;
  }

  .nav-status {
    padding-left: 10px;
  }
}

/* =========================================================
   手机端
   ========================================================= */

@media (max-width: 768px) {
  .navbar {
    background:
      linear-gradient(180deg,
        rgba(13, 15, 20, 0.98),
        rgba(13, 15, 20, 0.94));
  }

  .navbar-container {
    height: 62px;
  }

  /* 手机显示汉堡 */

  .hamburger {
    display: flex;
  }

  /* 隐藏桌面状态 */

  .nav-status {
    display: none;
  }

  /* 手机导航 */

  .nav-menu {
    position: absolute;

    top: 62px;
    left: 12px;
    right: 12px;

    display: flex;
    flex-direction: column;
    align-items: stretch;

    gap: 4px;

    padding: 10px;

    background:
      rgba(16, 19, 25, 0.98);

    border: 1px solid rgba(255, 255, 255, 0.07);

    border-radius: 14px;

    box-shadow:
      0 18px 45px rgba(0, 0, 0, 0.45),
      0 0 30px rgba(0, 0, 0, 0.2);

    backdrop-filter: blur(20px);
    -webkit-backdrop-filter: blur(20px);

    max-height: 0;
    opacity: 0;

    overflow: hidden;

    pointer-events: none;

    transform: translateY(-8px);

    transition:
      max-height 0.35s ease,
      opacity 0.25s ease,
      transform 0.35s ease;
  }

  .nav-menu.is-open {
    max-height: 620px;

    opacity: 1;

    pointer-events: auto;

    transform: translateY(0);
  }

  .nav-item {
    width: 100%;
  }

  .nav-link {
    display: flex;

    justify-content: center;

    width: 100%;
    height: 44px;

    padding: 0 16px;

    border-radius: 9px;

    font-size: 15px;
  }

  .nav-link:hover,
  .nav-link.is-active,
  .nav-link.is-exact-active {
    background: rgba(109, 179, 242, 0.07);
  }

  .nav-link.is-active::after,
  .nav-link.is-exact-active::after {
    left: 8px;
    bottom: 50%;

    width: 2px;
    height: 16px;

    transform: translateY(50%);
  }

  .nav-badge {
    position: absolute;
    right: 16px;
  }

  /* 遮罩 */

  .mobile-backdrop {
    display: block;

    position: fixed;

    inset: 62px 0 0;

    background: rgba(0, 0, 0, 0.3);

    z-index: -1;
  }
}

/* =========================================================
   小屏幕
   ========================================================= */

@media (max-width: 480px) {
  .navbar-container {
    padding: 0 14px;
  }

  .brand-name {
    font-size: 14px;
  }

  .brand-en {
    font-size: 7px;
    letter-spacing: 1.4px;
  }
}

/* =========================================================
   减少动画
   ========================================================= */

@media (prefers-reduced-motion: reduce) {

  .navbar *,
  .navbar::before {
    animation: none !important;
    transition: none !important;
  }
}
</style>