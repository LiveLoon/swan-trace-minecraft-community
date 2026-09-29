<template>
  <footer class="site-footer">
    <div class="footer-container">

      <!-- 顶部装饰 -->
      <div class="footer-decoration">
        <span class="decoration-line"></span>
        <span class="decoration-symbol">◆</span>
        <span class="decoration-line"></span>
      </div>

      <!-- 简介 -->
      <p class="footer-description">
        一个纯净、自由、公益的 Minecraft 社区。
      </p>

      <!-- 导航 -->
      <nav class="footer-nav">
        <router-link to="/">首页</router-link>
        <router-link to="/join">加入我们</router-link>
        <router-link to="/players">玩家</router-link>
        <router-link to="/downloads">存档</router-link>
        <router-link to="/disclaimer">免责声明</router-link>
        <router-link to="/about">关于</router-link>
      </nav>

      <!-- 分隔线 -->
      <div class="footer-divider"></div>

      <!-- 底部信息 -->
      <div class="footer-bottom">

        <span>
          © 2026 鸿迹 · Swan Trace
        </span>
        <span class="footer-separator">
          ·
        </span>

        <span>
          Minecraft 26.2
        </span>

        <span class="footer-separator">
          ·
        </span>

        <span>
          Community
        </span>

      </div>

      <!-- 最后一行 -->
      <p class="footer-note">
        希望这里也能成为你的一个家。
      </p>

    </div>
  </footer>
</template>

<script setup lang="ts">
</script>

<style scoped>
/* =========================================================
   页脚
   ========================================================= */

.site-footer {
  position: relative;

  width: 100%;

  padding: 48px 20px 30px;

  background:
    radial-gradient(circle at 50% 0%,
      rgba(109, 179, 242, 0.035),
      transparent 32%),
    #080a0e;

  border-top:
    1px solid rgba(255, 255, 255, 0.045);

  overflow: hidden;
}

/* 顶部细线 */

.site-footer::before {
  content: '';

  position: absolute;

  top: 0;
  left: 50%;

  width: min(520px, 70%);

  height: 1px;

  transform: translateX(-50%);

  background:
    linear-gradient(90deg,
      transparent,
      rgba(109, 179, 242, 0.22),
      transparent);
}

/* =========================================================
   容器
   ========================================================= */

.footer-container {
  width: min(1000px, 100%);

  margin: 0 auto;

  text-align: center;
}

/* =========================================================
   顶部装饰
   ========================================================= */

.footer-decoration {
  display: flex;
  align-items: center;
  justify-content: center;

  gap: 12px;

  margin-bottom: 24px;
}

.decoration-line {
  width: 50px;
  height: 1px;

  background:
    linear-gradient(90deg,
      transparent,
      rgba(109, 179, 242, 0.2),
      transparent);
}

.decoration-symbol {
  color: rgba(109, 179, 242, 0.4);

  font-size: 8px;
}

/* =========================================================
   品牌
   ========================================================= */

.footer-brand {
  display: inline-flex;
  align-items: center;

  gap: 11px;
}

.brand-info {
  display: flex;
  flex-direction: column;

  align-items: flex-start;

  text-align: left;
}

.brand-info strong {
  color: #dce5ec;

  font-size: 15px;
  font-weight: 700;

  line-height: 1.2;
}

.brand-info span {
  margin-top: 4px;

  color: #526779;

  font-family:
    'SFMono-Regular',
    Consolas,
    monospace;

  font-size: 7px;

  letter-spacing: 0.14em;
}

/* =========================================================
   简介
   ========================================================= */

.footer-description {
  margin: 17px 0 20px;

  color: #6e8293;

  font-size: 11px;

  line-height: 1.8;
}

/* =========================================================
   导航
   ========================================================= */

.footer-nav {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;

  gap: 6px 22px;
}

.footer-nav a {
  position: relative;

  color: #667b8d;

  text-decoration: none;

  font-size: 11px;

  transition:
    color 0.25s ease;
}

.footer-nav a::after {
  content: '';

  position: absolute;

  left: 50%;
  bottom: -5px;

  width: 0;
  height: 1px;

  transform: translateX(-50%);

  background: #6daedc;

  transition:
    width 0.25s ease;
}

.footer-nav a:hover {
  color: #b7c8d4;
}

.footer-nav a:hover::after {
  width: 14px;
}

/* 当前页面 */

.footer-nav a.router-link-active {
  color: #9eb8c9;
}

/* =========================================================
   分隔线
   ========================================================= */

.footer-divider {
  width: min(620px, 80%);

  height: 1px;

  margin: 25px auto 18px;

  background:
    linear-gradient(90deg,
      transparent,
      rgba(255, 255, 255, 0.06),
      transparent);
}

/* =========================================================
   底部信息
   ========================================================= */

.footer-bottom {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;

  gap: 8px;

  color: #485c6d;

  font-family:
    'SFMono-Regular',
    Consolas,
    monospace;

  font-size: 8px;

  letter-spacing: 0.04em;
}

.footer-separator {
  color: #334553;
}

/* =========================================================
   最后的话
   ========================================================= */

.footer-note {
  margin-top: 20px;

  color: #465b6c;

  font-size: 10px;

  letter-spacing: 0.04em;
}

/* =========================================================
   手机
   ========================================================= */

@media (max-width: 520px) {
  .site-footer {
    padding:
      38px 16px 25px;
  }

  .decoration-line {
    width: 32px;
  }

  .footer-description {
    font-size: 10px;
  }

  .footer-nav {
    gap: 9px 17px;
  }

  .footer-nav a {
    font-size: 10px;
  }

  .footer-bottom {
    gap: 5px;

    font-size: 7px;
  }

  .footer-separator {
    display: none;
  }

  .footer-note {
    font-size: 9px;
  }
}

/* =========================================================
   减少动画
   ========================================================= */

@media (prefers-reduced-motion: reduce) {

  .footer-nav a,
  .footer-nav a::after {
    transition: none;
  }
}
</style>
