<template>
    <div class="server-page">
        <!-- =========================================
         页面头部
    ========================================== -->

        <section class="page-header">
            <div>
                <div class="eyebrow">
                    SERVER INFORMATION
                </div>

                <h1>服务器信息</h1>

                <p>
                    查看 Swan Trace Minecraft 服务器的运行状态与系统信息。
                </p>
            </div>

            <button class="refresh-btn" :disabled="loading" @click="loadAll">
                <span class="refresh-icon" :class="{ spinning: loading }">
                    ↻
                </span>

                {{ loading ? '刷新中...' : '刷新' }}
            </button>
        </section>

        <!-- =========================================
         错误提示
    ========================================== -->

        <div v-if="error" class="error-banner">
            <span>!</span>

            <div>
                <strong>
                    获取服务器信息失败
                </strong>

                <p>
                    {{ error }}
                </p>
            </div>
        </div>

        <!-- =========================================
         MOTD / 服务器状态
    ========================================== -->

        <section class="section">
            <div class="section-title">
                <div>
                    <span class="section-eyebrow">
                        SERVER
                    </span>

                    <h2>服务器状态</h2>
                </div>

                <div class="online-status" :class="{
                    online: !error
                }">
                    <span></span>

                    {{ error ? '无法连接' : '服务器在线' }}
                </div>
            </div>

            <div class="motd-card">
                <div class="motd-content">
                    <div class="server-icon">
                        S
                    </div>

                    <div>
                        <div class="motd-label">
                            SERVER MOTD
                        </div>

                        <div class="motd" v-html="formattedMotd"></div>
                    </div>
                </div>

                <div class="motd-address">
                    <span>
                        服务器地址
                    </span>

                    <strong>
                        {{ serverHost }}:{{ serverInfo?.port ?? 25565 }}
                    </strong>
                </div>
            </div>

            <!-- 基础信息 -->
            <div class="info-grid">
                <div class="info-card">
                    <div class="info-icon">
                        👥
                    </div>

                    <div>
                        <span>
                            最大玩家数
                        </span>

                        <strong>
                            {{ serverInfo?.maxPlayerCount ?? '-' }}
                        </strong>
                    </div>
                </div>

                <div class="info-card">
                    <div class="info-icon">
                        🔐
                    </div>

                    <div>
                        <span>
                            白名单
                        </span>

                        <strong>
                            {{
                                serverInfo?.whitelist
                                    ? '已开启'
                            : '未开启'
                            }}
                        </strong>
                    </div>
                </div>

                <div class="info-card">
                    <div class="info-icon">
                        ⏱
                    </div>

                    <div>
                        <span>
                            运行时间
                        </span>

                        <strong>
                            {{ formatDuration(serverInfo?.uptime) }}
                        </strong>
                    </div>
                </div>

                <div class="info-card">
                    <div class="info-icon">
                        🌍
                    </div>

                    <div>
                        <span>
                            游戏时间
                        </span>

                        <strong>
                            {{ formatGameTime(serverInfo?.ingameTime) }}
                        </strong>
                    </div>
                </div>
            </div>
        </section>

        <!-- =========================================
         实时监控
    ========================================== -->

        <section class="section">
            <div class="section-title">
                <div>
                    <span class="section-eyebrow">
                        MONITOR
                    </span>

                    <h2>实时监控</h2>
                </div>

                <span class="update-time">
                    {{ lastUpdateText }}
                </span>
            </div>

            <div class="monitor-grid">
                <!-- TPS -->
                <div class="monitor-card">
                    <div class="monitor-header">
                        <div class="monitor-icon tps">
                            ⚡
                        </div>

                        <span>
                            TPS
                        </span>
                    </div>

                    <div class="monitor-value">
                        {{ serverMonitor?.tps?.toFixed(1) ?? '-' }}
                    </div>

                    <div class="monitor-sub">
                        <div class="progress">
                            <div class="progress-bar" :style="{
                                width: `${tpsPercent}%`
                            }"></div>
                        </div>

                        <span>
                            {{ tpsStatus }}
                        </span>
                    </div>
                </div>

                <!-- CPU -->
                <div class="monitor-card">
                    <div class="monitor-header">
                        <div class="monitor-icon cpu">
                            ◉
                        </div>

                        <span>
                            CPU
                        </span>
                    </div>

                    <div class="monitor-value">
                        {{ serverMonitor?.cpu?.toFixed(1) ?? '-' }}%
                    </div>

                    <div class="monitor-sub">
                        <div class="progress">
                            <div class="progress-bar" :style="{
                                width: `${clamp(serverMonitor?.cpu ?? 0)}%`
                            }"></div>
                        </div>

                        <span>
                            CPU 使用率
                        </span>
                    </div>
                </div>

                <!-- 内存 -->
                <div class="monitor-card">
                    <div class="monitor-header">
                        <div class="monitor-icon memory">
                            ▣
                        </div>

                        <span>
                            内存
                        </span>
                    </div>

                    <div class="monitor-value">
                        {{ serverMonitor?.memory?.toFixed(1) ?? '-' }}%
                    </div>

                    <div class="monitor-sub">
                        <div class="progress">
                            <div class="progress-bar" :style="{
                                width: `${clamp(serverMonitor?.memory ?? 0)}%`
                            }"></div>
                        </div>

                        <span>
                            内存使用率
                        </span>
                    </div>
                </div>
            </div>
        </section>

        <!-- =========================================
         系统信息
    ========================================== -->

        <section class="section">
            <div class="section-title">
                <div>
                    <span class="section-eyebrow">
                        SYSTEM
                    </span>

                    <h2>系统信息</h2>
                </div>
            </div>

            <div class="system-card">
                <!-- CPU -->
                <div class="system-row">
                    <div class="system-label">
                        <span class="system-icon">
                            CPU
                        </span>

                        <span>
                            处理器
                        </span>
                    </div>

                    <div class="system-value">
                        <strong>
                            {{ serverInfo?.system.cpuName ?? '-' }}
                        </strong>

                        <small>
                            {{ serverInfo?.system.cpuCore ?? '-' }} 核 /
                            {{ serverInfo?.system.cpuThread ?? '-' }} 线程
                        </small>
                    </div>
                </div>

                <!-- Java -->
                <div class="system-row">
                    <div class="system-label">
                        <span class="system-icon">
                            ☕
                        </span>

                        <span>
                            Java
                        </span>
                    </div>

                    <div class="system-value">
                        <strong>
                            Java {{ serverInfo?.system.java ?? '-' }}
                        </strong>
                    </div>
                </div>

                <!-- OS -->
                <div class="system-row">
                    <div class="system-label">
                        <span class="system-icon">
                            OS
                        </span>

                        <span>
                            操作系统
                        </span>
                    </div>

                    <div class="system-value">
                        <strong>
                            {{ serverInfo?.system.os ?? '-' }}
                        </strong>
                    </div>
                </div>

                <!-- 架构 -->
                <div class="system-row">
                    <div class="system-label">
                        <span class="system-icon">
                            #
                        </span>

                        <span>
                            系统架构
                        </span>
                    </div>

                    <div class="system-value">
                        <strong>
                            {{ serverInfo?.system.arch ?? '-' }}
                        </strong>
                    </div>
                </div>

                <!-- 内存 -->
                <div class="system-row">
                    <div class="system-label">
                        <span class="system-icon">
                            RAM
                        </span>

                        <span>
                            系统内存
                        </span>
                    </div>

                    <div class="system-value">
                        <strong>
                            {{ formatBytes(serverInfo?.system.memory) }}
                        </strong>

                        <small>
                            {{
                                serverInfo?.system.memory
                                    ? `${(
                                        serverInfo.system.memory /
                                        1024 /
                                        1024 /
                                        1024
                                    ).toFixed(2)} GiB`
                            : ''
                            }}
                        </small>
                    </div>
                </div>

                <!-- GPU -->
                <div class="system-row">
                    <div class="system-label">
                        <span class="system-icon">
                            GPU
                        </span>

                        <span>
                            GPU
                        </span>
                    </div>

                    <div class="system-value">
                        <strong v-if="
                            serverInfo?.system.gpus?.length
                        ">
                            {{
                                serverInfo.system.gpus.join(', ')
                            }}
                        </strong>

                        <strong v-else>
                            未检测到 GPU
                        </strong>
                    </div>
                </div>
            </div>
        </section>

        <!-- =========================================
         服务器端口
    ========================================== -->

        <section class="section bottom-section">
            <div class="connection-card">
                <div>
                    <span class="connection-label">
                        MINECRAFT SERVER PORT
                    </span>

                    <strong>
                        {{ serverInfo?.port ?? '-' }}
                    </strong>
                </div>

                <div class="connection-status">
                    <span></span>

                    服务正常运行
                </div>
            </div>
        </section>
    </div>
</template>

<script setup lang="ts">
import {
    computed,
    onMounted,
    onUnmounted,
    ref,
} from 'vue'

import {
    fetchServerInfo,
    fetchServerMonitor,
    type ServerInfoResponse,
    type ServerMonitorResponse,
} from '@/api/server'

// ============================================================
// 状态
// ============================================================

const serverInfo =
    ref<ServerInfoResponse | null>(null)

const serverMonitor =
    ref<ServerMonitorResponse | null>(null)

const loading = ref(false)

const error = ref('')

const lastUpdate = ref<Date | null>(null)

let monitorTimer:
    ReturnType<typeof setInterval> | null = null

// ============================================================
// 服务器地址
// ============================================================

const serverHost =
    window.location.hostname === 'localhost'
        ? 'localhost'
        : window.location.hostname

// ============================================================
// 加载数据
// ============================================================

const loadAll = async () => {
    loading.value = true
    error.value = ''

    try {
        const [info, monitor] =
            await Promise.all([
                fetchServerInfo(),
                fetchServerMonitor(),
            ])

        if (info.code !== 200) {
            throw new Error(
                info.error ||
                '获取服务器信息失败'
            )
        }

        if (monitor.code !== 200) {
            throw new Error(
                monitor.error ||
                '获取服务器监控失败'
            )
        }

        serverInfo.value = info

        serverMonitor.value = monitor

        lastUpdate.value = new Date()
    } catch (err: any) {
        console.error(
            '获取服务器信息失败:',
            err
        )

        error.value =
            err?.response?.data?.error ||
            err?.message ||
            '无法连接服务器 API'
    } finally {
        loading.value = false
    }
}

// ============================================================
// MOTD
// ============================================================

const formattedMotd = computed(() => {
    if (!serverInfo.value?.motd) {
        return 'Swan Trace Minecraft Community'
    }

    return convertMinecraftColor(
        serverInfo.value.motd
    )
})

/**
 * Minecraft § 颜色代码转 HTML
 */
const convertMinecraftColor = (
    text: string
) => {
    const colors: Record<
        string,
        string
    > = {
        '0': '#000000',
        '1': '#0000AA',
        '2': '#00AA00',
        '3': '#00AAAA',
        '4': '#AA0000',
        '5': '#AA00AA',
        '6': '#FFAA00',
        '7': '#AAAAAA',
        '8': '#555555',
        '9': '#5555FF',
        a: '#55FF55',
        b: '#55FFFF',
        c: '#FF5555',
        d: '#FF55FF',
        e: '#FFFF55',
        f: '#FFFFFF',
    }

    let html = ''

    let currentColor = ''

    let bold = false

    let italic = false

    let underline = false

    let strikethrough = false

    let obfuscated = false

    const parts = text.split('')

    for (
        let i = 0;
        i < parts.length;
        i++
    ) {
        const char = parts[i]

        if (
            char === '§' &&
            i + 1 < parts.length
        ) {
            const code =
                parts[++i].toLowerCase()

            if (colors[code]) {
                currentColor =
                    colors[code]

                bold = false
                italic = false
                underline = false
                strikethrough = false
                obfuscated = false

                continue
            }

            switch (code) {
                case 'l':
                    bold = true
                    break

                case 'o':
                    italic = true
                    break

                case 'n':
                    underline = true
                    break

                case 'm':
                    strikethrough = true
                    break

                case 'k':
                    obfuscated = true
                    break

                case 'r':
                    currentColor = ''

                    bold = false
                    italic = false
                    underline = false
                    strikethrough = false
                    obfuscated = false

                    break
            }

            continue
        }

        let style = ''

        if (currentColor) {
            style += `color:${currentColor};`
        }

        if (bold) {
            style += 'font-weight:700;'
        }

        if (italic) {
            style += 'font-style:italic;'
        }

        if (underline) {
            style += 'text-decoration:underline;'
        }

        if (strikethrough) {
            style += 'text-decoration:line-through;'
        }

        if (obfuscated) {
            style += 'filter:blur(2px);'
        }

        const escaped =
            char
                .replace(/&/g, '&amp;')
                .replace(/</g, '&lt;')
                .replace(/>/g, '&gt;')
                .replace(
                    /"/g,
                    '&quot;'
                )

        if (style) {
            html += `<span style="${style}">${escaped}</span>`
        } else {
            html += escaped
        }
    }

    return html
}

// ============================================================
// TPS
// ============================================================

const tpsPercent = computed(() => {
    const tps =
        serverMonitor.value?.tps ?? 0

    return Math.min(
        100,
        Math.max(
            0,
            (tps / 20) * 100
        )
    )
})

const tpsStatus = computed(() => {
    const tps =
        serverMonitor.value?.tps ?? 0

    if (tps >= 19.5) {
        return '运行稳定'
    }

    if (tps >= 18) {
        return '轻微负载'
    }

    if (tps >= 15) {
        return '存在负载'
    }

    return '性能异常'
})

// ============================================================
// 时间
// ============================================================

const formatDuration = (
    milliseconds?: number
) => {
    if (
        milliseconds === undefined ||
        milliseconds === null
    ) {
        return '-'
    }

    let seconds =
        Math.floor(
            milliseconds / 1000
        )

    const days =
        Math.floor(
            seconds / 86400
        )

    seconds %= 86400

    const hours =
        Math.floor(
            seconds / 3600
        )

    seconds %= 3600

    const minutes =
        Math.floor(
            seconds / 60
        )

    seconds %= 60

    const parts: string[] = []

    if (days > 0) {
        parts.push(`${days} 天`)
    }

    if (
        hours > 0 ||
        days > 0
    ) {
        parts.push(`${hours} 小时`)
    }

    if (
        minutes > 0 ||
        hours > 0 ||
        days > 0
    ) {
        parts.push(`${minutes} 分钟`)
    }

    parts.push(`${seconds} 秒`)

    return parts.join(' ')
}

/**
 * 游戏时间
 *
 * Minecraft 一天 = 24000 ticks
 */
const formatGameTime = (
    ticks?: number
) => {
    if (
        ticks === undefined ||
        ticks === null
    ) {
        return '-'
    }

    const day =
        Math.floor(
            ticks / 24000
        )

    const time =
        ticks % 24000

    const hours =
        Math.floor(
            (time + 6000) / 1000
        ) % 24

    const minutes =
        Math.floor(
            ((time + 6000) % 1000) *
            60 /
            1000
        )

    return `第 ${day} 天 ${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}`
}

// ============================================================
// 文件大小
// ============================================================

const formatBytes = (
    bytes?: number
) => {
    if (
        bytes === undefined ||
        bytes === null ||
        bytes <= 0
    ) {
        return '-'
    }

    const units = [
        'B',
        'KiB',
        'MiB',
        'GiB',
        'TiB',
    ]

    let value = bytes

    let index = 0

    while (
        value >= 1024 &&
        index < units.length - 1
    ) {
        value /= 1024

        index++
    }

    return `${value.toFixed(
        index === 0 ? 0 : 2
    )} ${units[index]}`
}

// ============================================================
// 数值限制
// ============================================================

const clamp = (
    value: number
) => {
    return Math.min(
        100,
        Math.max(0, value)
    )
}

// ============================================================
// 更新时间
// ============================================================

const lastUpdateText =
    computed(() => {
        if (!lastUpdate.value) {
            return '尚未更新'
        }

        return `更新于 ${lastUpdate.value.toLocaleTimeString()}`
    })

// ============================================================
// 生命周期
// ============================================================

onMounted(() => {
    loadAll()

    // 每 10 秒更新实时监控
    monitorTimer = setInterval(
        async () => {
            try {
                const data =
                    await fetchServerMonitor()

                if (data.code === 200) {
                    serverMonitor.value =
                        data

                    lastUpdate.value =
                        new Date()
                }
            } catch (err) {
                console.error(
                    '更新服务器监控失败:',
                    err
                )
            }
        },
        10000
    )
})

onUnmounted(() => {
    if (monitorTimer) {
        clearInterval(
            monitorTimer
        )

        monitorTimer = null
    }
})
</script>

<style scoped>
.server-page {
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

/* =========================================
   Header
========================================= */

.page-header {
    max-width: 1400px;

    margin: 0 auto 36px;

    display: flex;

    align-items: flex-end;

    justify-content: space-between;

    gap: 30px;
}

.eyebrow,
.section-eyebrow {
    color: #71809a;

    font-size: 11px;

    font-weight: 700;

    letter-spacing: 0.18em;
}

.page-header h1 {
    margin: 10px 0 0;

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

/* =========================================
   Refresh
========================================= */

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

/* =========================================
   Error
========================================= */

.error-banner {
    max-width: 1400px;

    margin: 0 auto 24px;

    padding: 15px 18px;

    display: flex;

    gap: 13px;

    border: 1px solid rgba(200, 80, 80, 0.25);

    border-radius: 14px;

    background:
        rgba(130, 40, 40, 0.1);

    color: #e89494;
}

.error-banner>span {
    width: 26px;
    height: 26px;

    display: grid;

    place-items: center;

    flex-shrink: 0;

    border-radius: 50%;

    background:
        rgba(200, 80, 80, 0.15);
}

.error-banner strong {
    font-size: 13px;
}

.error-banner p {
    margin: 3px 0 0;

    color: #a97575;

    font-size: 12px;
}

/* =========================================
   Section
========================================= */

.section {
    max-width: 1400px;

    margin: 0 auto 42px;
}

.section-title {
    margin-bottom: 18px;

    display: flex;

    align-items: flex-end;

    justify-content: space-between;
}

.section-title h2 {
    margin: 6px 0 0;

    font-size: 25px;

    letter-spacing: -0.02em;
}

.online-status {
    display: flex;

    align-items: center;

    gap: 7px;

    color: #778398;

    font-size: 12px;
}

.online-status span {
    width: 7px;
    height: 7px;

    border-radius: 50%;

    background: #596579;
}

.online-status.online {
    color: #65d8a3;
}

.online-status.online span {
    background: #65d8a3;

    box-shadow:
        0 0 10px rgba(101, 216, 163, 0.5);
}

.update-time {
    color: #596579;

    font-size: 11px;
}

/* =========================================
   MOTD
========================================= */

.motd-card {
    min-height: 150px;

    padding: 26px;

    display: flex;

    align-items: center;

    justify-content: space-between;

    gap: 30px;

    border: 1px solid #1d2635;

    border-radius: 18px;

    background:
        linear-gradient(135deg,
            rgba(24, 32, 48, 0.96),
            rgba(12, 17, 26, 0.96));
}

.motd-content {
    display: flex;

    align-items: center;

    gap: 18px;
}

.server-icon {
    width: 64px;
    height: 64px;

    display: grid;

    place-items: center;

    flex-shrink: 0;

    border-radius: 16px;

    background:
        linear-gradient(135deg,
            #334568,
            #1a2438);

    color: #a9bcff;

    font-size: 28px;

    font-weight: 800;

    box-shadow:
        0 12px 30px rgba(0, 0, 0, 0.25);
}

.motd-label {
    margin-bottom: 7px;

    color: #596579;

    font-size: 10px;

    font-weight: 700;

    letter-spacing: 0.15em;
}

.motd {
    font-size: 20px;

    font-weight: 600;

    line-height: 1.5;
}

.motd-address {
    text-align: right;
}

.motd-address span {
    display: block;

    margin-bottom: 5px;

    color: #596579;

    font-size: 10px;
}

.motd-address strong {
    color: #aab6c8;

    font-size: 14px;

    font-family:
        'JetBrains Mono',
        monospace;
}

/* =========================================
   基础信息
========================================= */

.info-grid {
    margin-top: 16px;

    display: grid;

    grid-template-columns:
        repeat(4, 1fr);

    gap: 16px;
}

.info-card {
    padding: 20px;

    display: flex;

    align-items: center;

    gap: 13px;

    border: 1px solid #1d2635;

    border-radius: 15px;

    background: rgba(15,
            20,
            30,
            0.75);
}

.info-icon {
    width: 42px;
    height: 42px;

    display: grid;

    place-items: center;

    flex-shrink: 0;

    border-radius: 11px;

    background: #151d2c;

    font-size: 17px;
}

.info-card span {
    display: block;

    margin-bottom: 5px;

    color: #596579;

    font-size: 11px;
}

.info-card strong {
    color: #dce3ed;

    font-size: 14px;
}

/* =========================================
   Monitor
========================================= */

.monitor-grid {
    display: grid;

    grid-template-columns:
        repeat(3, 1fr);

    gap: 18px;
}

.monitor-card {
    padding: 24px;

    border: 1px solid #1d2635;

    border-radius: 17px;

    background:
        linear-gradient(145deg,
            rgba(18, 24, 35, 0.96),
            rgba(11, 15, 23, 0.96));
}

.monitor-header {
    display: flex;

    align-items: center;

    gap: 10px;

    color: #8b96a8;

    font-size: 13px;
}

.monitor-icon {
    width: 38px;
    height: 38px;

    display: grid;

    place-items: center;

    border-radius: 10px;

    background: #151d2c;
}

.monitor-icon.tps {
    color: #75dca9;
}

.monitor-icon.cpu {
    color: #91a6e9;
}

.monitor-icon.memory {
    color: #d4a2e8;
}

.monitor-value {
    margin-top: 20px;

    font-size: 36px;

    font-weight: 700;

    letter-spacing: -0.04em;
}

.monitor-sub {
    margin-top: 18px;

    display: flex;

    align-items: center;

    gap: 10px;
}

.progress {
    height: 5px;

    flex: 1;

    overflow: hidden;

    border-radius: 999px;

    background: #1c2533;
}

.progress-bar {
    height: 100%;

    border-radius: inherit;

    background:
        linear-gradient(90deg,
            #526c9e,
            #7895d6);

    transition: width 0.5s ease;
}

.monitor-sub>span {
    color: #596579;

    font-size: 10px;

    white-space: nowrap;
}

/* =========================================
   System
========================================= */

.system-card {
    overflow: hidden;

    border: 1px solid #1d2635;

    border-radius: 17px;

    background: rgba(15,
            20,
            30,
            0.75);
}

.system-row {
    min-height: 70px;

    padding: 15px 22px;

    display: flex;

    align-items: center;

    justify-content: space-between;

    gap: 30px;

    border-bottom: 1px solid #19212e;
}

.system-row:last-child {
    border-bottom: 0;
}

.system-label {
    display: flex;

    align-items: center;

    gap: 12px;

    color: #8b96a8;

    font-size: 13px;
}

.system-icon {
    width: 38px;
    height: 38px;

    display: grid;

    place-items: center;

    border-radius: 9px;

    background: #151d2a;

    color: #8195c8;

    font-size: 9px;

    font-weight: 700;
}

.system-value {
    max-width: 70%;

    text-align: right;
}

.system-value strong {
    display: block;

    color: #dce3ed;

    font-size: 13px;

    font-weight: 500;

    word-break: break-word;
}

.system-value small {
    display: block;

    margin-top: 3px;

    color: #596579;

    font-size: 10px;
}

/* =========================================
   Connection
========================================= */

.bottom-section {
    margin-bottom: 0;
}

.connection-card {
    padding: 22px 26px;

    display: flex;

    align-items: center;

    justify-content: space-between;

    border: 1px solid #1d2635;

    border-radius: 16px;

    background:
        linear-gradient(135deg,
            rgba(18, 26, 39, 0.95),
            rgba(12, 17, 25, 0.95));
}

.connection-label {
    display: block;

    margin-bottom: 6px;

    color: #596579;

    font-size: 10px;

    font-weight: 700;

    letter-spacing: 0.12em;
}

.connection-card strong {
    color: #dce3ed;

    font-size: 23px;

    font-family:
        'JetBrains Mono',
        monospace;
}

.connection-status {
    display: flex;

    align-items: center;

    gap: 8px;

    color: #65d8a3;

    font-size: 12px;
}

.connection-status span {
    width: 7px;
    height: 7px;

    border-radius: 50%;

    background: #65d8a3;

    box-shadow:
        0 0 10px rgba(101, 216, 163, 0.5);
}

/* =========================================
   Animation
========================================= */

@keyframes spin {
    to {
        transform: rotate(360deg);
    }
}

/* =========================================
   Responsive
========================================= */

@media (max-width: 1100px) {
    .info-grid {
        grid-template-columns:
            repeat(2, 1fr);
    }
}

@media (max-width: 900px) {
    .monitor-grid {
        grid-template-columns: 1fr;
    }

    .motd-card {
        align-items: flex-start;

        flex-direction: column;
    }

    .motd-address {
        text-align: left;
    }
}

@media (max-width: 760px) {
    .server-page {
        padding:
            90px 20px 60px;
    }

    .page-header {
        align-items: flex-start;

        flex-direction: column;
    }

    .info-grid {
        grid-template-columns: 1fr;
    }

    .system-row {
        align-items: flex-start;

        flex-direction: column;

        gap: 10px;
    }

    .system-value {
        max-width: 100%;

        text-align: left;
    }

    .connection-card {
        align-items: flex-start;

        flex-direction: column;

        gap: 15px;
    }
}
</style>
