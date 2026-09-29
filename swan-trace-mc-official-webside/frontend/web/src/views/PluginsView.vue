<template>
    <div class="plugins-page">
        <!-- 页面头部 -->
        <section class="page-header">
            <div>
                <div class="eyebrow">
                    SERVER PLUGINS
                </div>

                <h1>服务器插件</h1>

                <p>
                    查看 SwanTrace Minecraft 服务器当前运行的插件。
                </p>
            </div>

            <button class="refresh-btn" :disabled="loading" @click="loadPlugins">
                <span class="refresh-icon" :class="{ spinning: loading }">
                    ↻
                </span>

                {{ loading ? '加载中...' : '刷新' }}
            </button>
        </section>

        <!-- 统计数据 -->
        <section class="stats">
            <div class="stat-card">
                <div class="stat-icon">
                    🧩
                </div>

                <div>
                    <div class="stat-value">
                        {{ plugins.length }}
                    </div>

                    <div class="stat-label">
                        插件总数
                    </div>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon">
                    ✓
                </div>

                <div>
                    <div class="stat-value">
                        {{ enabledCount }}
                    </div>

                    <div class="stat-label">
                        已启用
                    </div>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon">
                    ⌕
                </div>

                <div>
                    <div class="stat-value">
                        {{ filteredPlugins.length }}
                    </div>

                    <div class="stat-label">
                        当前显示
                    </div>
                </div>
            </div>
        </section>

        <!-- 搜索和筛选 -->
        <section class="toolbar">
            <div class="search-box">
                <span class="search-icon">
                    ⌕
                </span>

                <input v-model="search" type="text" placeholder="搜索插件名称、版本、描述、作者..." />

                <button v-if="search" class="clear-btn" @click="search = ''">
                    ×
                </button>
            </div>

            <div class="filters">
                <button :class="{
                    active: statusFilter === 'all'
                }" @click="statusFilter = 'all'">
                    全部
                </button>

                <button :class="{
                    active: statusFilter === 'enabled'
                }" @click="statusFilter = 'enabled'">
                    已启用
                </button>

                <button :class="{
                    active: statusFilter === 'disabled'
                }" @click="statusFilter = 'disabled'">
                    已禁用
                </button>
            </div>
        </section>

        <!-- 加载状态 -->
        <div v-if="loading && plugins.length === 0" class="state">
            <div class="loader"></div>

            <p>
                正在获取服务器插件...
            </p>
        </div>

        <!-- 错误状态 -->
        <div v-else-if="error" class="state error-state">
            <div class="state-icon">
                !
            </div>

            <h3>
                获取插件失败
            </h3>

            <p>
                {{ error }}
            </p>

            <button class="retry-btn" @click="loadPlugins">
                重新加载
            </button>
        </div>

        <!-- 空数据 -->
        <div v-else-if="filteredPlugins.length === 0" class="state">
            <div class="state-icon">
                ⌕
            </div>

            <h3>
                没有找到插件
            </h3>

            <p>
                {{
                    search
                        ? '尝试更换搜索关键词。'
                        : '服务器暂时没有插件数据。'
                }}
            </p>
        </div>

        <!-- 插件列表 -->
        <section v-else class="plugin-grid">
            <article v-for="plugin in filteredPlugins" :key="plugin.fileName" class="plugin-card">
                <!-- 插件标题 -->
                <div class="plugin-top">
                    <div class="plugin-logo">
                        {{ getPluginInitial(plugin.name) }}
                    </div>

                    <div class="plugin-title">
                        <div class="title-row">
                            <h2>
                                {{ plugin.name }}
                            </h2>

                            <span class="status" :class="plugin.enabled
                                    ? 'enabled'
                                    : 'disabled'
                                ">
                                <span class="status-dot"></span>

                                {{
                                    plugin.enabled
                                        ? '运行中'
                                : '已禁用'
                                }}
                            </span>
                        </div>

                        <span class="version">
                            v{{ plugin.version }}
                        </span>
                    </div>
                </div>

                <!-- 插件描述 -->
                <p class="description">
                    {{
                        plugin.description ||
                    '暂无插件描述。'
                    }}
                </p>

                <!-- 作者 -->
                <div v-if="
                    plugin.authors &&
                    plugin.authors.length > 0
                " class="authors">
                    <span class="label">
                        作者
                    </span>

                    <div class="author-list">
                        <span v-for="author in plugin.authors" :key="author" class="author">
                            {{ author }}
                        </span>
                    </div>
                </div>

                <!-- 插件信息 -->
                <div class="plugin-info">
                    <div>
                        <span>
                            文件
                        </span>

                        <strong :title="plugin.fileName">
                            {{ plugin.fileName }}
                        </strong>
                    </div>

                    <div>
                        <span>
                            大小
                        </span>

                        <strong>
                            {{ formatSize(plugin.size) }}
                        </strong>
                    </div>

                    <div>
                        <span>
                            加载状态
                        </span>

                        <strong>
                            {{
                                plugin.loaded
                                    ? '已加载'
                            : '未加载'
                            }}
                        </strong>
                    </div>
                </div>

                <!-- 底部 -->
                <div class="plugin-footer">
                    <span class="plugin-type">
                        Minecraft Plugin
                    </span>

                    <a v-if="plugin.website" :href="normalizeUrl(plugin.website)" target="_blank"
                        rel="noopener noreferrer">
                        官方网站 ↗
                    </a>
                </div>
            </article>
        </section>
    </div>
</template>

<script setup lang="ts">
import {
    computed,
    onMounted,
    ref,
} from 'vue'

import {
    fetchServerPlugins,
    type PluginInfo,
} from '@/api/plugins'

// ==============================
// 状态
// ==============================

const plugins = ref<PluginInfo[]>([])

const loading = ref(false)

const error = ref('')

const search = ref('')

const statusFilter = ref<
    'all' | 'enabled' | 'disabled'
>('all')

// ==============================
// 获取服务器插件
// ==============================

const loadPlugins = async () => {
    loading.value = true
    error.value = ''

    try {
        const data = await fetchServerPlugins()

        if (data.code !== 200) {
            throw new Error(
                data.error || '获取插件失败'
            )
        }

        plugins.value = data.plugins || []
    } catch (err: any) {
        console.error(
            '获取服务器插件失败:',
            err
        )

        error.value =
            err?.response?.data?.error ||
            err?.message ||
            '无法连接服务器'
    } finally {
        loading.value = false
    }
}

// ==============================
// 已启用插件数量
// ==============================

const enabledCount = computed(() => {
    return plugins.value.filter(
        plugin => plugin.enabled
    ).length
})

// ==============================
// 搜索 + 状态过滤
// ==============================

const filteredPlugins = computed(() => {
    const keyword = search.value
        .trim()
        .toLowerCase()

    return plugins.value.filter(plugin => {
        // 状态筛选
        if (
            statusFilter.value === 'enabled' &&
            !plugin.enabled
        ) {
            return false
        }

        if (
            statusFilter.value === 'disabled' &&
            plugin.enabled
        ) {
            return false
        }

        // 没有搜索关键词
        if (!keyword) {
            return true
        }

        // 搜索内容
        const text = [
            plugin.name,
            plugin.version,
            plugin.description,
            plugin.fileName,
            ...(plugin.authors || []),
        ]
            .join(' ')
            .toLowerCase()

        return text.includes(keyword)
    })
})

// ==============================
// 插件首字母
// ==============================

const getPluginInitial = (
    name: string
) => {
    if (!name) {
        return '?'
    }

    return name
        .charAt(0)
        .toUpperCase()
}

// ==============================
// 文件大小格式化
// ==============================

const formatSize = (
    size: number
) => {
    if (!size || size <= 0) {
        return '0 B'
    }

    const units = [
        'B',
        'KB',
        'MB',
        'GB',
    ]

    let value = size
    let unitIndex = 0

    while (
        value >= 1024 &&
        unitIndex < units.length - 1
    ) {
        value /= 1024
        unitIndex++
    }

    return `${value.toFixed(
        unitIndex === 0 ? 0 : 2
    )} ${units[unitIndex]}`
}

// ==============================
// URL 格式化
// ==============================

const normalizeUrl = (
    url: string
) => {
    if (!url) {
        return '#'
    }

    if (
        url.startsWith('http://') ||
        url.startsWith('https://')
    ) {
        return url
    }

    return `https://${url}`
}

// ==============================
// 页面加载
// ==============================

onMounted(() => {
    loadPlugins()
})
</script>

<style scoped>
.plugins-page {
    min-height: 100vh;

    padding: 110px 6vw 80px;

    background:
        radial-gradient(circle at 10% 10%,
            rgba(70, 100, 255, 0.08),
            transparent 30%),
        radial-gradient(circle at 90% 20%,
            rgba(0, 220, 180, 0.06),
            transparent 30%),
        #080b12;

    color: #f4f7fb;
}

/* ==============================
   页面头部
   ============================== */

.page-header {
    max-width: 1400px;

    margin: 0 auto 36px;

    display: flex;
    align-items: flex-end;
    justify-content: space-between;

    gap: 30px;
}

.eyebrow {
    margin-bottom: 10px;

    color: #71809a;

    font-size: 12px;
    font-weight: 700;

    letter-spacing: 0.18em;
}

.page-header h1 {
    margin: 0;

    font-size: clamp(34px,
            5vw,
            58px);

    line-height: 1.05;

    letter-spacing: -0.04em;
}

.page-header p {
    margin: 16px 0 0;

    color: #8994a7;

    font-size: 15px;
}

/* ==============================
   刷新按钮
   ============================== */

.refresh-btn {
    flex-shrink: 0;

    padding: 12px 20px;

    border: 1px solid #283244;

    border-radius: 12px;

    background: rgba(20,
            26,
            38,
            0.8);

    color: #e8edf5;

    cursor: pointer;

    transition: 0.2s;
}

.refresh-btn:hover {
    border-color: #46536b;

    background: #151c29;
}

.refresh-btn:disabled {
    opacity: 0.6;

    cursor: not-allowed;
}

.refresh-icon {
    display: inline-block;

    margin-right: 7px;
}

.spinning {
    animation:
        spin 0.8s linear infinite;
}

/* ==============================
   统计卡片
   ============================== */

.stats {
    max-width: 1400px;

    margin: 0 auto 24px;

    display: grid;

    grid-template-columns:
        repeat(3, 1fr);

    gap: 16px;
}

.stat-card {
    display: flex;

    align-items: center;

    gap: 16px;

    padding: 20px;

    border: 1px solid #1d2635;

    border-radius: 16px;

    background: rgba(15,
            20,
            30,
            0.75);

    backdrop-filter: blur(10px);
}

.stat-icon {
    width: 44px;
    height: 44px;

    display: grid;

    place-items: center;

    border-radius: 12px;

    background: #151d2c;

    color: #8da4ff;

    font-size: 20px;
}

.stat-value {
    font-size: 25px;

    font-weight: 700;
}

.stat-label {
    margin-top: 3px;

    color: #68758b;

    font-size: 13px;
}

/* ==============================
   搜索工具栏
   ============================== */

.toolbar {
    max-width: 1400px;

    margin: 0 auto 24px;

    display: flex;

    gap: 16px;
}

.search-box {
    position: relative;

    flex: 1;

    display: flex;

    align-items: center;
}

.search-icon {
    position: absolute;

    left: 15px;

    color: #68758b;

    font-size: 20px;
}

.search-box input {
    width: 100%;

    padding: 13px 45px;

    border: 1px solid #1d2635;

    border-radius: 12px;

    outline: none;

    background: #0f141e;

    color: #f4f7fb;

    font-size: 14px;
}

.search-box input:focus {
    border-color: #465b87;
}

.search-box input::placeholder {
    color: #596579;
}

.clear-btn {
    position: absolute;

    right: 12px;

    width: 28px;
    height: 28px;

    border: 0;

    border-radius: 50%;

    background: #1d2635;

    color: #aab4c4;

    cursor: pointer;
}

/* ==============================
   筛选按钮
   ============================== */

.filters {
    display: flex;

    gap: 6px;

    padding: 4px;

    border: 1px solid #1d2635;

    border-radius: 12px;

    background: #0f141e;
}

.filters button {
    padding: 9px 14px;

    border: 0;

    border-radius: 8px;

    background: transparent;

    color: #78859a;

    cursor: pointer;
}

.filters button:hover {
    color: #dce3ee;
}

.filters button.active {
    background: #202b3e;

    color: #f4f7fb;
}

/* ==============================
   插件列表
   ============================== */

.plugin-grid {
    max-width: 1400px;

    margin: 0 auto;

    display: grid;

    grid-template-columns:
        repeat(3,
            minmax(0, 1fr));

    gap: 18px;
}

.plugin-card {
    min-width: 0;

    padding: 22px;

    border: 1px solid #1d2635;

    border-radius: 18px;

    background:
        linear-gradient(145deg,
            rgba(18,
                24,
                35,
                0.96),
            rgba(11,
                15,
                23,
                0.96));

    transition:
        transform 0.2s,
        border-color 0.2s,
        box-shadow 0.2s;
}

.plugin-card:hover {
    transform: translateY(-3px);

    border-color: #334158;

    box-shadow:
        0 18px 50px rgba(0, 0, 0, 0.25);
}

/* ==============================
   插件顶部
   ============================== */

.plugin-top {
    display: flex;

    align-items: center;

    gap: 14px;
}

.plugin-logo {
    flex-shrink: 0;

    width: 50px;
    height: 50px;

    display: grid;

    place-items: center;

    border-radius: 13px;

    background:
        linear-gradient(135deg,
            #27334a,
            #161d2c);

    color: #a8baff;

    font-size: 20px;

    font-weight: 700;
}

.plugin-title {
    min-width: 0;

    flex: 1;
}

.title-row {
    display: flex;

    align-items: center;

    gap: 9px;

    flex-wrap: wrap;
}

.title-row h2 {
    margin: 0;

    font-size: 18px;

    line-height: 1.3;
}

.version {
    display: block;

    margin-top: 4px;

    color: #68758b;

    font-size: 12px;
}

/* ==============================
   状态
   ============================== */

.status {
    display: inline-flex;

    align-items: center;

    gap: 5px;

    padding: 3px 7px;

    border-radius: 999px;

    font-size: 10px;

    font-weight: 600;
}

.status-dot {
    width: 5px;
    height: 5px;

    border-radius: 50%;
}

.status.enabled {
    background:
        rgba(44,
            180,
            120,
            0.1);

    color: #65d8a3;
}

.status.enabled .status-dot {
    background: #65d8a3;
}

.status.disabled {
    background:
        rgba(180,
            120,
            120,
            0.1);

    color: #d68e8e;
}

.status.disabled .status-dot {
    background: #d68e8e;
}

/* ==============================
   描述
   ============================== */

.description {
    min-height: 48px;

    margin: 20px 0;

    color: #8b96a8;

    font-size: 13px;

    line-height: 1.65;
}

/* ==============================
   作者
   ============================== */

.authors {
    margin-bottom: 18px;
}

.label {
    display: block;

    margin-bottom: 7px;

    color: #596579;

    font-size: 11px;
}

.author-list {
    display: flex;

    flex-wrap: wrap;

    gap: 5px;
}

.author {
    padding: 4px 7px;

    border-radius: 6px;

    background: #151c28;

    color: #8f9bad;

    font-size: 11px;
}

/* ==============================
   插件信息
   ============================== */

.plugin-info {
    padding: 13px 0;

    border-top: 1px solid #1a2230;

    border-bottom: 1px solid #1a2230;

    display: grid;

    gap: 9px;
}

.plugin-info>div {
    display: flex;

    justify-content: space-between;

    gap: 15px;

    min-width: 0;
}

.plugin-info span {
    color: #596579;

    font-size: 11px;
}

.plugin-info strong {
    overflow: hidden;

    color: #8e9bad;

    font-size: 11px;

    font-weight: 500;

    text-overflow: ellipsis;

    white-space: nowrap;
}

/* ==============================
   底部
   ============================== */

.plugin-footer {
    display: flex;

    align-items: center;

    justify-content: space-between;

    padding-top: 15px;
}

.plugin-type {
    color: #4f5b6e;

    font-size: 10px;
}

.plugin-footer a {
    color: #8299d6;

    font-size: 11px;

    text-decoration: none;
}

.plugin-footer a:hover {
    color: #a7b9ee;
}

/* ==============================
   状态页面
   ============================== */

.state {
    max-width: 600px;

    margin: 100px auto;

    text-align: center;
}

.state-icon {
    width: 56px;
    height: 56px;

    margin: 0 auto 16px;

    display: grid;

    place-items: center;

    border-radius: 16px;

    background: #151c28;

    color: #8190a8;

    font-size: 24px;
}

.state h3 {
    margin: 0 0 8px;
}

.state p {
    color: #68758b;

    font-size: 13px;
}

.error-state .state-icon {
    background:
        rgba(200,
            80,
            80,
            0.1);

    color: #d98484;
}

.retry-btn {
    margin-top: 10px;

    padding: 9px 16px;

    border: 1px solid #303b4f;

    border-radius: 9px;

    background: #151d2b;

    color: #dbe2ee;

    cursor: pointer;
}

/* ==============================
   Loading
   ============================== */

.loader {
    width: 32px;
    height: 32px;

    margin: 0 auto 18px;

    border: 3px solid #202a3a;

    border-top-color: #8498d5;

    border-radius: 50%;

    animation:
        spin 0.8s linear infinite;
}

@keyframes spin {
    to {
        transform: rotate(360deg);
    }
}

/* ==============================
   Responsive
   ============================== */

@media (max-width: 1100px) {
    .plugin-grid {
        grid-template-columns:
            repeat(2,
                minmax(0, 1fr));
    }
}

@media (max-width: 760px) {
    .plugins-page {
        padding:
            90px 20px 60px;
    }

    .page-header {
        align-items: flex-start;

        flex-direction: column;
    }

    .stats {
        grid-template-columns: 1fr;
    }

    .toolbar {
        flex-direction: column;
    }

    .filters {
        overflow-x: auto;
    }

    .filters button {
        flex: 1;

        white-space: nowrap;
    }

    .plugin-grid {
        grid-template-columns: 1fr;
    }
}
</style>
