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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.unit.dp
import com.example.swantracemc.data.Player
import com.example.swantracemc.network.ApiClient
import kotlinx.coroutines.launch
import java.io.IOException


// ================================================================
// 玩家筛选类型
// ================================================================

enum class FilterType {
    ALL,
    ONLINE,
    OFFLINE
}


// ================================================================
// 玩家列表页面
// ================================================================

@Composable
fun PlayerListPage(
    modifier: Modifier = Modifier,
    onDrawerStateChanged: (Boolean) -> Unit = {}
) {
    var allPlayers by remember {
        mutableStateOf<List<Player>>(emptyList())
    }

    var filteredPlayers by remember {
        mutableStateOf<List<Player>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    var filterType by remember {
        mutableStateOf(FilterType.ALL)
    }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val coroutineScope = rememberCoroutineScope()


    // ============================================================
    // Drawer 状态
    // ============================================================

    LaunchedEffect(drawerState.currentValue) {
        onDrawerStateChanged(
            drawerState.currentValue == DrawerValue.Open
        )
    }


    // ============================================================
    // 加载玩家
    // ============================================================

    LaunchedEffect(Unit) {
        try {
            val players = ApiClient.apiService.getPlayerList()

            allPlayers = players.filter {
                it.name != null
            }

            filteredPlayers = allPlayers

        } catch (e: IOException) {

            error = "网络连接失败：${e.message ?: "未知错误"}"

        } catch (e: Exception) {

            error = "加载玩家失败：${e.message ?: "未知错误"}"

        } finally {
            isLoading = false
        }
    }


    // ============================================================
    // 根据筛选条件过滤玩家
    // ============================================================

    LaunchedEffect(filterType, allPlayers) {

        filteredPlayers = when (filterType) {

            FilterType.ALL ->
                allPlayers

            FilterType.ONLINE ->
                allPlayers.filter {
                    it.isOnline
                }

            FilterType.OFFLINE ->
                allPlayers.filter {
                    !it.isOnline
                }
        }
    }


    // ============================================================
    // 排序
    //
    // 在线玩家优先，然后按照名称排序。
    // ============================================================

    val sortedPlayers = remember(filteredPlayers) {

        filteredPlayers.sortedWith(
            compareBy<Player> {
                !it.isOnline
            }.thenBy {
                it.name ?: ""
            }
        )
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
                color = MaterialTheme.colorScheme.primary
            )
        }

        return
    }


    // ============================================================
    // Error
    // ============================================================

    if (error != null) {

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp)
            ) {

                Text(
                    text = "无法加载玩家",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = error ?: "未知错误",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        return
    }


    // ============================================================
    // Drawer
    // ============================================================

    ModalNavigationDrawer(
        modifier = modifier,

        drawerState = drawerState,

        // 继续保持手动打开 Drawer。
        gesturesEnabled = false,

        drawerContent = {

            ModalDrawerSheet(
                drawerContainerColor =
                    MaterialTheme.colorScheme.surface,

                drawerContentColor =
                    MaterialTheme.colorScheme.onSurface
            ) {

                // ------------------------------------------------
                // Drawer Header
                // ------------------------------------------------

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 24.dp
                        )
                ) {

                    Text(
                        text = "鸿迹",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "玩家筛选",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outline.copy(
                        alpha = 0.6f
                    )
                )


                // ------------------------------------------------
                // 在线
                // ------------------------------------------------

                PlayerFilterItem(
                    label = "在线玩家",
                    selected = filterType == FilterType.ONLINE,
                    onClick = {

                        filterType = FilterType.ONLINE

                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )


                // ------------------------------------------------
                // 离线
                // ------------------------------------------------

                PlayerFilterItem(
                    label = "离线玩家",
                    selected = filterType == FilterType.OFFLINE,
                    onClick = {

                        filterType = FilterType.OFFLINE

                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )


                HorizontalDivider(
                    modifier = Modifier.padding(
                        vertical = 8.dp
                    ),
                    color = MaterialTheme.colorScheme.outline.copy(
                        alpha = 0.6f
                    )
                )


                // ------------------------------------------------
                // 全部
                // ------------------------------------------------

                PlayerFilterItem(
                    label = "全部玩家",
                    selected = filterType == FilterType.ALL,
                    onClick = {

                        filterType = FilterType.ALL

                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    ) {


        // ========================================================
        // 页面
        // ========================================================

        Scaffold(
            modifier = Modifier.fillMaxSize(),

            containerColor =
                MaterialTheme.colorScheme.background,

            topBar = {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .background(
                            MaterialTheme.colorScheme.background
                        )
                        .padding(horizontal = 8.dp),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    // ------------------------------------------------
                    // 菜单
                    // ------------------------------------------------

                    IconButton(
                        onClick = {

                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "筛选玩家",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }


                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )


                    // ------------------------------------------------
                    // 页面标题
                    // ------------------------------------------------

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "玩家",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            text = when (filterType) {

                                FilterType.ALL ->
                                    "${sortedPlayers.size} 名玩家"

                                FilterType.ONLINE ->
                                    "${sortedPlayers.size} 名在线玩家"

                                FilterType.OFFLINE ->
                                    "${sortedPlayers.size} 名离线玩家"
                            },

                            style = MaterialTheme.typography.bodySmall,

                            color =
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        ) { innerPadding ->


            // ====================================================
            // 内容
            // ====================================================

            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                if (sortedPlayers.isEmpty()) {

                    // ------------------------------------------------
                    // 没有玩家
                    // ------------------------------------------------

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "没有符合条件的玩家",
                            style = MaterialTheme.typography.bodyMedium,
                            color =
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                } else {

                    // ------------------------------------------------
                    // 玩家列表
                    // ------------------------------------------------

                    LazyColumn(

                        modifier = Modifier.fillMaxSize(),

                        contentPadding = PaddingValues(
                            top = 8.dp,
                            bottom = 24.dp
                        ),

                        verticalArrangement =
                            Arrangement.spacedBy(10.dp)
                    ) {

                        items(
                            items = sortedPlayers,

                            key = { player ->
                                player.uuid
                                    ?: player.name
                                    ?: ""
                            }
                        ) { player ->

                            PlayerItem(
                                player = player
                            )
                        }
                    }
                }
            }
        }
    }
}


// ================================================================
// Drawer 筛选项
// ================================================================

@Composable
private fun PlayerFilterItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(

        label = {

            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        },

        selected = selected,

        onClick = onClick,

        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 10.dp,
                vertical = 3.dp
            ),

        colors = NavigationDrawerItemDefaults.colors(

            selectedContainerColor =
                MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.16f
                ),

            selectedTextColor =
                MaterialTheme.colorScheme.primary,

            unselectedTextColor =
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}


// ================================================================
// 玩家卡片
// ================================================================

@Composable
fun PlayerItem(
    player: Player
) {

    val isBedrock = player.name
        ?.startsWith(".") == true


    val displayName = player.name
        ?.removePrefix(".")
        ?.takeIf {
            it.isNotEmpty()
        }
        ?: "Unknown"


    // ============================================================
    // 状态颜色
    // ============================================================

    val statusColor = if (player.isOnline) {

        MaterialTheme.colorScheme.primary

    } else {

        MaterialTheme.colorScheme.outline
    }


    val borderColor = if (player.isOnline) {

        MaterialTheme.colorScheme.primary.copy(
            alpha = 0.35f
        )

    } else {

        MaterialTheme.colorScheme.outline.copy(
            alpha = 0.35f
        )
    }


    // ============================================================
    // Card
    // ============================================================

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            ),

        shape = RoundedCornerShape(14.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surface
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = if (player.isOnline) {
                2.dp
            } else {
                0.dp
            }
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {


            // ====================================================
            // 左侧状态条
            // ====================================================

            Box(

                modifier = Modifier
                    .width(4.dp)
                    .height(132.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 14.dp,
                            bottomStart = 14.dp
                        )
                    )
                    .background(statusColor)
            )


            // ====================================================
            // 玩家信息
            // ====================================================

            Column(

                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = 14.dp,
                        top = 14.dp,
                        end = 14.dp,
                        bottom = 14.dp
                    ),

                verticalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {


                // ==================================================
                // 名称 + 平台 + 在线状态
                // ==================================================

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        text = displayName,

                        style =
                            MaterialTheme.typography.titleMedium,

                        color =
                            MaterialTheme.colorScheme.onSurface
                    )


                    // ------------------------------------------------
                    // Bedrock
                    // ------------------------------------------------

                    if (isBedrock) {

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Surface(

                            shape =
                                RoundedCornerShape(6.dp),

                            color =
                                MaterialTheme.colorScheme.secondary.copy(
                                    alpha = 0.14f
                                )
                        ) {

                            Text(

                                text = "基岩版",

                                modifier = Modifier.padding(
                                    horizontal = 7.dp,
                                    vertical = 3.dp
                                ),

                                style =
                                    MaterialTheme.typography.labelSmall,

                                color =
                                    MaterialTheme.colorScheme.secondary
                            )
                        }
                    }


                    Spacer(
                        modifier = Modifier.weight(1f)
                    )


                    // ------------------------------------------------
                    // 在线状态
                    // ------------------------------------------------

                    PlayerStatusBadge(
                        isOnline = player.isOnline
                    )
                }


                // ==================================================
                // 分割线
                // ==================================================

                HorizontalDivider(

                    color =
                        MaterialTheme.colorScheme.outline.copy(
                            alpha = 0.35f
                        )
                )


                // ==================================================
                // UUID
                // ==================================================

                PlayerInfoRow(
                    label = "UUID",
                    value = player.uuid ?: "未知"
                )


                // ==================================================
                // 游戏模式
                // ==================================================

                PlayerInfoRow(
                    label = "游戏模式",
                    value = translateGamemode(
                        player.gamemode
                    )
                )
            }
        }
    }
}


// ================================================================
// 在线 / 离线 Badge
// ================================================================

@Composable
private fun PlayerStatusBadge(
    isOnline: Boolean
) {

    val backgroundColor = if (isOnline) {

        MaterialTheme.colorScheme.primary.copy(
            alpha = 0.15f
        )

    } else {

        MaterialTheme.colorScheme.surfaceVariant
    }


    val textColor = if (isOnline) {

        MaterialTheme.colorScheme.primary

    } else {

        MaterialTheme.colorScheme.onSurfaceVariant
    }


    Surface(

        shape = RoundedCornerShape(20.dp),

        color = backgroundColor
    ) {

        Text(

            text = if (isOnline) {
                "在线"
            } else {
                "离线"
            },

            modifier = Modifier.padding(
                horizontal = 9.dp,
                vertical = 4.dp
            ),

            style = MaterialTheme.typography.labelSmall,

            color = textColor
        )
    }
}


// ================================================================
// 玩家信息行
// ================================================================

@Composable
private fun PlayerInfoRow(
    label: String,
    value: String
) {

    Row(

        modifier = Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(

            text = label,

            modifier = Modifier.width(72.dp),

            style =
                MaterialTheme.typography.bodySmall,

            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )


        Text(

            text = value,

            style =
                MaterialTheme.typography.bodySmall,

            color =
                MaterialTheme.colorScheme.onSurface
        )
    }
}


// ================================================================
// Minecraft 游戏模式翻译
// ================================================================

private fun translateGamemode(
    gamemode: String?
): String {

    return when (gamemode?.lowercase()) {

        "survival" ->
            "生存"

        "creative" ->
            "创造"

        "adventure" ->
            "冒险"

        "spectator" ->
            "旁观"

        "hardcore" ->
            "极限"

        else ->
            gamemode
                ?.replaceFirstChar {
                    if (it.isLowerCase()) {
                        it.titlecase()
                    } else {
                        it.toString()
                    }
                }
                ?: "未知"
    }
}