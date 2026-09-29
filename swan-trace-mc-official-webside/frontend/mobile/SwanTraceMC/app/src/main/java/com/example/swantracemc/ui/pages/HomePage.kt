package com.example.swantracemc.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import com.example.swantracemc.data.ServerInfo
import com.example.swantracemc.data.ServerMonitor
import com.example.swantracemc.network.ApiClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException


// ================================================================
// 首页
// ================================================================

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    onDrawerStateChanged: (Boolean) -> Unit = {}
) {

    var serverInfo by remember {
        mutableStateOf<ServerInfo?>(null)
    }

    var serverMonitor by remember {
        mutableStateOf<ServerMonitor?>(null)
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var refreshing by remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    val coroutineScope =
        rememberCoroutineScope()


    // ============================================================
    // 加载服务器数据
    // ============================================================

    suspend fun loadServerData(
        showLoading: Boolean
    ) {

        if (showLoading) {
            isLoading = true
        }

        error = null

        try {

            val info =
                ApiClient.apiService
                    .getServerInfo()

            val monitor =
                ApiClient.apiService
                    .getServerMonitor()

            if (info.code != 200) {

                throw Exception(
                    info.error
                        ?.takeIf {
                            it.isNotBlank()
                        }
                        ?: "服务器信息获取失败"
                )
            }

            if (monitor.code != 200) {

                throw Exception(
                    monitor.error
                        ?.takeIf {
                            it.isNotBlank()
                        }
                        ?: "服务器监控信息获取失败"
                )
            }

            serverInfo = info

            serverMonitor = monitor

        } catch (e: IOException) {

            error =
                "网络连接失败：${e.message ?: "无法连接服务器"}"

        } catch (e: Exception) {

            error =
                e.message
                    ?: "加载服务器信息失败"

        } finally {

            if (showLoading) {
                isLoading = false
            }

            refreshing = false
        }
    }


    // ============================================================
    // 初始加载
    // ============================================================

    LaunchedEffect(Unit) {

        loadServerData(
            showLoading = true
        )
    }


    // ============================================================
    // 自动刷新监控数据
    //
    // 每 5 秒刷新一次。
    // ============================================================

    LaunchedEffect(Unit) {

        while (true) {

            delay(5000)

            try {

                val monitor =
                    ApiClient.apiService
                        .getServerMonitor()

                if (monitor.code == 200) {

                    serverMonitor =
                        monitor
                }

            } catch (_: Exception) {
                // 自动刷新失败不覆盖当前页面
            }
        }
    }


    // ============================================================
    // Loading
    // ============================================================

    if (isLoading) {

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator(
                color =
                    MaterialTheme.colorScheme.primary
            )
        }

        return
    }


    // ============================================================
    // Error
    // ============================================================

    if (
        error != null &&
        serverInfo == null
    ) {

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text = "无法加载服务器信息",
                    style =
                        MaterialTheme.typography.titleMedium,
                    color =
                        MaterialTheme.colorScheme.onBackground
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text =
                        error ?: "未知错误",
                    style =
                        MaterialTheme.typography.bodySmall,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Surface(
                    modifier = Modifier.clip(
                        RoundedCornerShape(10.dp)
                    ),
                    color =
                        MaterialTheme.colorScheme.primary
                ) {

                    Text(
                        text = "重新加载",

                        modifier = Modifier
                            .padding(
                                horizontal = 20.dp,
                                vertical = 11.dp
                            ),

                        color =
                            MaterialTheme.colorScheme.onPrimary,

                        style =
                            MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        return
    }


    val info =
        serverInfo ?: return

    val monitor =
        serverMonitor


    // ============================================================
    // 页面
    // ============================================================

    Scaffold(

        modifier = modifier.fillMaxSize(),

        containerColor =
            MaterialTheme.colorScheme.background,


    ) { innerPadding ->


        // ========================================================
        // 内容
        // ========================================================

        LazyColumn(

            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),

            contentPadding =
                PaddingValues(
                    top = 8.dp,
                    bottom = 28.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {


            // ====================================================
            // MOTD
            // ====================================================

            item {

                ServerMotdCard(
                    motd = info.motd,
                    whitelist = info.whitelist
                )
            }


            // ====================================================
            // 实时状态
            // ====================================================

            item {

                SectionTitle(
                    title = "服务器状态",
                    subtitle = "实时运行数据"
                )
            }


            item {

                ServerStatusCard(
                    monitor = monitor
                )
            }


            // ====================================================
            // 玩家
            // ====================================================

            item {

                PlayerCapacityCard(
                    maxPlayerCount =
                        info.maxPlayerCount
                )
            }


            // ====================================================
            // 服务器信息
            // ====================================================

            item {

                SectionTitle(
                    title = "服务器信息",
                    subtitle = "Minecraft Server"
                )
            }


            item {

                ServerInfoCard(
                    info = info
                )
            }


            // ====================================================
            // 系统信息
            // ====================================================

            item {

                SectionTitle(
                    title = "系统信息",
                    subtitle = "Server Runtime"
                )
            }


            item {

                SystemInfoCard(
                    info = info
                )
            }
        }
    }
}


// ================================================================
// MOTD
// ================================================================

@Composable
private fun ServerMotdCard(
    motd: String?,
    whitelist: Boolean
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                width = 1.dp,
                color =
                    MaterialTheme.colorScheme.outline.copy(
                        alpha = 0.35f
                    ),
                shape =
                    RoundedCornerShape(16.dp)
            ),

        shape =
            RoundedCornerShape(16.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surface
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                ServerStatusDot(
                    online = true
                )

                Spacer(
                    modifier =
                        Modifier.width(9.dp)
                )

                Text(
                    text = "SERVER ONLINE",
                    style =
                        MaterialTheme.typography.labelMedium,
                    color =
                        MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier =
                        Modifier.weight(1f)
                )

                Surface(
                    shape =
                        RoundedCornerShape(8.dp),
                    color =
                        if (whitelist) {
                            MaterialTheme.colorScheme.secondary
                                .copy(alpha = 0.14f)
                        } else {
                            MaterialTheme.colorScheme.primary
                                .copy(alpha = 0.14f)
                        }
                ) {

                    Text(
                        text =
                            if (whitelist) {
                                "白名单"
                            } else {
                                "公开"
                            },

                        modifier =
                            Modifier.padding(
                                horizontal = 9.dp,
                                vertical = 5.dp
                            ),

                        style =
                            MaterialTheme.typography.labelSmall,

                        color =
                            if (whitelist) {
                                MaterialTheme.colorScheme.secondary
                            } else {
                                MaterialTheme.colorScheme.primary
                            }
                    )
                }
            }


            Text(
                text =
                    cleanMinecraftText(
                        motd
                    ),

                style =
                    MaterialTheme.typography.headlineSmall,

                color =
                    MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


// ================================================================
// 服务器实时状态
// ================================================================

@Composable
private fun ServerStatusCard(
    monitor: ServerMonitor?
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.35f
                ),
                RoundedCornerShape(16.dp)
            ),

        shape =
            RoundedCornerShape(16.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surface
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                MonitorValue(
                    modifier =
                        Modifier.weight(1f),
                    label = "TPS",
                    value =
                        monitor?.tps
                            ?.let {
                                String.format(
                                    "%.1f",
                                    it
                                )
                            }
                            ?: "-",
                    suffix = ""
                )

                MonitorValue(
                    modifier =
                        Modifier.weight(1f),
                    label = "CPU",
                    value =
                        monitor?.cpu
                            ?.let {
                                String.format(
                                    "%.1f",
                                    it
                                )
                            }
                            ?: "-",
                    suffix = "%"
                )

                MonitorValue(
                    modifier =
                        Modifier.weight(1f),
                    label = "内存",
                    value =
                        monitor?.memory
                            ?.let {
                                String.format(
                                    "%.1f",
                                    it
                                )
                            }
                            ?: "-",
                    suffix = "%"
                )
            }
        }
    }
}


// ================================================================
// 玩家容量
// ================================================================

@Composable
private fun PlayerCapacityCard(
    maxPlayerCount: Int
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.35f
                ),
                RoundedCornerShape(16.dp)
            ),

        shape =
            RoundedCornerShape(16.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surface
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    text = "玩家容量",
                    style =
                        MaterialTheme.typography.titleMedium,
                    color =
                        MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                Text(
                    text =
                        "最大允许在线玩家",
                    style =
                        MaterialTheme.typography.bodySmall,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }


            Text(
                text =
                    "${maxPlayerCount} 人",

                style =
                    MaterialTheme.typography.titleLarge,

                color =
                    MaterialTheme.colorScheme.primary
            )
        }
    }
}


// ================================================================
// 服务器信息
// ================================================================

@Composable
private fun ServerInfoCard(
    info: ServerInfo
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.35f
                ),
                RoundedCornerShape(16.dp)
            ),

        shape =
            RoundedCornerShape(16.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surface
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            InfoRow(
                label = "端口",
                value = info.port.toString()
            )

            InfoRow(
                label = "游戏时间",
                value =
                    formatGameTime(
                        info.ingameTime
                    )
            )

            InfoRow(
                label = "运行时间",
                value =
                    formatUptime(
                        info.uptime
                    )
            )

            InfoRow(
                label = "白名单",
                value =
                    if (info.whitelist) {
                        "已开启"
                    } else {
                        "未开启"
                    }
            )
        }
    }
}


// ================================================================
// 系统信息
// ================================================================

@Composable
private fun SystemInfoCard(
    info: ServerInfo
) {

    val system =
        info.system

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.35f
                ),
                RoundedCornerShape(16.dp)
            ),

        shape =
            RoundedCornerShape(16.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surface
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            InfoRow(
                label = "CPU",
                value =
                    system.cpuName
                        ?: "未知"
            )

            InfoRow(
                label = "CPU 核心",
                value =
                    "${system.cpuCore} 核 / ${system.cpuThread} 线程"
            )

            InfoRow(
                label = "架构",
                value =
                    system.arch
                        ?: "未知"
            )

            InfoRow(
                label = "Java",
                value =
                    system.java
                        ?: "未知"
            )

            InfoRow(
                label = "内存",
                value =
                    formatBytes(
                        system.memory
                    )
            )

            InfoRow(
                label = "操作系统",
                value =
                    system.os
                        ?: "未知"
            )

            InfoRow(
                label = "GPU",
                value =
                    system.gpus
                        ?.joinToString(", ")
                        ?.takeIf {
                            it.isNotBlank()
                        }
                        ?: "未知"
            )
        }
    }
}


// ================================================================
// Monitor 数值
// ================================================================

@Composable
private fun MonitorValue(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    suffix: String
) {

    Surface(
        modifier = modifier,

        shape =
            RoundedCornerShape(12.dp),

        color =
            MaterialTheme.colorScheme.surfaceVariant
                .copy(alpha = 0.45f)
    ) {

        Column(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 13.dp
            )
        ) {

            Text(
                text = label,
                style =
                    MaterialTheme.typography.labelSmall,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier =
                    Modifier.height(5.dp)
            )

            Row(
                verticalAlignment =
                    Alignment.Bottom
            ) {

                Text(
                    text = value,
                    style =
                        MaterialTheme.typography.titleLarge,
                    color =
                        MaterialTheme.colorScheme.onSurface
                )

                if (suffix.isNotEmpty()) {

                    Spacer(
                        modifier =
                            Modifier.width(2.dp)
                    )

                    Text(
                        text = suffix,
                        style =
                            MaterialTheme.typography.labelSmall,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}


// ================================================================
// 信息行
// ================================================================

@Composable
private fun InfoRow(
    label: String,
    value: String
) {

    Column(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        Text(
            text = label,
            style =
                MaterialTheme.typography.labelSmall,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier =
                Modifier.height(3.dp)
        )

        Text(
            text = value,
            style =
                MaterialTheme.typography.bodyMedium,
            color =
                MaterialTheme.colorScheme.onSurface,
            maxLines = 3,
            overflow =
                TextOverflow.Ellipsis
        )
    }
}


// ================================================================
// Section 标题
// ================================================================

@Composable
private fun SectionTitle(
    title: String,
    subtitle: String
) {

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 4.dp)
    ) {

        Text(
            text = title,
            style =
                MaterialTheme.typography.titleMedium,
            color =
                MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = subtitle,
            style =
                MaterialTheme.typography.bodySmall,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


// ================================================================
// 状态圆点
// ================================================================

@Composable
private fun ServerStatusDot(
    online: Boolean
) {

    Spacer(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(
                if (online) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                }
            )
    )
}


// ================================================================
// Minecraft MOTD 简单处理
// ================================================================

private fun cleanMinecraftText(
    text: String?
): String {

    if (text.isNullOrBlank()) {
        return "鸿迹 Swan Trace Minecraft Community"
    }

    return text.replace(
        Regex("§[0-9a-fk-orA-FK-OR]"),
        ""
    )
}


// ================================================================
// 字节大小
// ================================================================

private fun formatBytes(
    bytes: Long
): String {

    if (bytes <= 0) {
        return "0 B"
    }

    val units =
        arrayOf(
            "B",
            "KiB",
            "MiB",
            "GiB",
            "TiB"
        )

    var value =
        bytes.toDouble()

    var index = 0

    while (
        value >= 1024 &&
        index < units.lastIndex
    ) {

        value /= 1024

        index++
    }

    return if (index == 0) {

        "${value.toLong()} ${units[index]}"

    } else {

        String.format(
            "%.2f %s",
            value,
            units[index]
        )
    }
}


// ================================================================
// 游戏时间
// ================================================================

private fun formatGameTime(
    ticks: Long
): String {

    // Minecraft 20 ticks = 1 秒
    val totalSeconds =
        ticks / 20

    val days =
        totalSeconds / 86400

    val hours =
        (totalSeconds % 86400) / 3600

    val minutes =
        (totalSeconds % 3600) / 60

    return when {

        days > 0 ->
            "${days}天 ${hours}小时 ${minutes}分钟"

        hours > 0 ->
            "${hours}小时 ${minutes}分钟"

        else ->
            "${minutes}分钟"
    }
}


// ================================================================
// 服务器运行时间
// ================================================================

private fun formatUptime(
    milliseconds: Long
): String {

    val totalSeconds =
        milliseconds / 1000

    val days =
        totalSeconds / 86400

    val hours =
        (totalSeconds % 86400) / 3600

    val minutes =
        (totalSeconds % 3600) / 60

    return when {

        days > 0 ->
            "${days}天 ${hours}小时 ${minutes}分钟"

        hours > 0 ->
            "${hours}小时 ${minutes}分钟"

        else ->
            "${minutes}分钟"
    }
}