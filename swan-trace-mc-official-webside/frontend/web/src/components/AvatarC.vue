<template>
  <div class="brand-mark" :style="avatarStyle">
    <img src="@/assets/navigator.jpg" alt="鸿迹" class="brand-logo" />
  </div>
</template>

<script lang="ts" setup>
import { computed, type CSSProperties } from 'vue'

interface Props {
  /**
   * 头像尺寸
   * - number：按 px 处理，如 40
   * - string：按原样使用，如 '2.5rem'、'48px'
   * 不传时使用默认尺寸（36px，移动端 34px）
   */
  size?: number | string
}

const props = withDefaults(defineProps<Props>(), {
  size: undefined
})

/**
 * 转为 CSS 长度
 */
const toCssSize = (value: number | string): string =>
  typeof value === 'number' ? `${value}px` : value

/**
 * 通过 CSS 变量注入尺寸，避免直接写 inline 的 width/height
 * 破坏媒体查询里的默认行为
 */
const avatarStyle = computed<CSSProperties>(() => {
  if (props.size === undefined || props.size === null || props.size === '') {
    return {}
  }

  return {
    '--avatar-size': toCssSize(props.size)
  } as CSSProperties
})
</script>

<style scoped>
.brand-mark {
  width: var(--avatar-size, 36px);
  height: var(--avatar-size, 36px);
  flex-shrink: 0;

  display: flex;
  align-items: center;
  justify-content: center;

  background: rgba(30, 41, 59, 0.8);
  border: 1px solid rgba(96, 165, 250, 0.35);
  border-radius: 9px;
  box-shadow:
    0 2px 8px rgba(0, 0, 0, 0.25),
    inset 0 0 0 1px rgba(255, 255, 255, 0.04);

  overflow: hidden;
}

.brand-logo {
  width: 100%;
  height: 100%;
  display: block;
  object-fit: cover;
  border-radius: 8px;
}

@media (max-width: 480px) {
  .brand-mark {
    width: var(--avatar-size, 34px);
    height: var(--avatar-size, 34px);
  }
}
</style>