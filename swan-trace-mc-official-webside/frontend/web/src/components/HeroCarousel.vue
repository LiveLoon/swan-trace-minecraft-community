<template>
    <div class="hero-bg">
        <div v-for="(img, index) in displayImages" :key="index" class="hero-slide"
            :class="{ active: currentIndex === index }">
            <img :src="img" alt="鸿迹 Minecraft 社区服务器背景" />
        </div>

        <div class="hero-overlay"></div>
    </div>
</template>

<script lang="ts" setup>
import { computed, onMounted, onUnmounted, ref, watch } from 'vue'

/**
 * 组件内置默认背景图
 */
const defaultImages: string[] = [
    new URL('@/assets/hero.jpg', import.meta.url).href,
    new URL('@/assets/hero2.jpg', import.meta.url).href,
    new URL('@/assets/3e77aef66e7d79f5d19a72fd926e1479.jpg', import.meta.url).href,
    new URL('@/assets/98cfad912188f8d3365022a0c10cd51f.jpg', import.meta.url).href,
    new URL('@/assets/392a7ed6277c45f498e761495fd83ed1.jpg', import.meta.url).href,
    new URL('@/assets/1948b1673983a775fa284385cfe1a666.jpg', import.meta.url).href,
    new URL('@/assets/93973a092eaefd8a91b3cb787411a56f.jpg', import.meta.url).href,
    new URL('@/assets/b30c41c24f7c1624bbcc437902fd401d.jpg', import.meta.url).href,
]

interface Props {
    /**
     * 可选：自定义轮播图片地址列表
     * 不传时使用组件内置默认图片
     */
    images?: string[]

    /**
     * 切换间隔（毫秒），默认 4000
     */
    interval?: number
}

const props = withDefaults(defineProps<Props>(), {
    images: undefined,
    interval: 4000,
})

/**
 * 优先使用外部传入的图片，否则回落到内置默认图
 */
const displayImages = computed<string[]>(() =>
    props.images && props.images.length > 0 ? props.images : defaultImages,
)

const currentIndex = ref(0)

let timer: number | null = null

const stopTimer = () => {
    if (timer !== null) {
        clearInterval(timer)
        timer = null
    }
}

const startTimer = () => {
    stopTimer()

    if (displayImages.value.length <= 1) return

    timer = window.setInterval(() => {
        currentIndex.value =
            (currentIndex.value + 1) % displayImages.value.length
    }, props.interval)
}

watch(
    () => displayImages.value,
    () => {
        currentIndex.value = 0
        startTimer()
    },
    { deep: true },
)

watch(
    () => props.interval,
    () => startTimer(),
)

onMounted(startTimer)
onUnmounted(stopTimer)
</script>

<style scoped>
.hero-bg {
    position: absolute;
    inset: 0;

    overflow: hidden;

    z-index: 0;
}

.hero-slide {
    position: absolute;
    inset: 0;

    opacity: 0;

    transition: opacity 1.2s ease-in-out;
}

.hero-slide.active {
    opacity: 1;
}

.hero-slide img {
    width: 100%;
    height: 100%;

    object-fit: cover;

    display: block;

    filter: saturate(0.8);
}

.hero-overlay {
    position: absolute;
    inset: 0;

    background:
        linear-gradient(180deg,
            rgba(5, 8, 12, 0.55),
            rgba(5, 8, 12, 0.76)),
        radial-gradient(circle at center,
            transparent 0%,
            rgba(4, 7, 11, 0.55) 80%);
}
</style>