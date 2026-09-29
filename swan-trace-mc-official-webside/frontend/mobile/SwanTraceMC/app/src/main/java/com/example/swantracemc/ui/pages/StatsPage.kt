package com.example.swantracemc.ui.pages

import androidx.annotation.StringRes
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.unit.dp
import com.example.swantracemc.R
import com.example.swantracemc.data.StatType
import com.example.swantracemc.data.TopResponse
import com.example.swantracemc.network.ApiClient
import com.example.swantracemc.ui.components.TopEntryItem
import kotlinx.coroutines.launch

@Composable
fun StatsPage(
    modifier: Modifier = Modifier
) {
    var statTypes by remember {
        mutableStateOf<List<StatType>>(emptyList())
    }

    var selectedKey by remember {
        mutableStateOf<String?>(null)
    }

    var topData by remember {
        mutableStateOf<TopResponse?>(null)
    }

    var isLoadingTypes by remember {
        mutableStateOf(true)
    }

    var isLoadingTop by remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val coroutineScope = rememberCoroutineScope()

    // ============================================================
    // 加载统计类型
    // ============================================================

    LaunchedEffect(Unit) {
        try {
            val response = ApiClient.apiService.getStats()

            statTypes = response.stats

            if (statTypes.isNotEmpty()) {
                selectedKey = statTypes.first().key
            }
        } catch (e: Exception) {
            error = e.message ?: "Unknown error"
        } finally {
            isLoadingTypes = false
        }
    }

    // ============================================================
    // 根据统计类型加载排行榜
    // ============================================================

    LaunchedEffect(selectedKey) {
        val key = selectedKey ?: return@LaunchedEffect

        isLoadingTop = true
        error = null

        try {
            topData = ApiClient.apiService.getTop(key)
        } catch (e: Exception) {
            error = e.message ?: "Unknown error"
        } finally {
            isLoadingTop = false
        }
    }

    // ============================================================
    // 加载统计类型
    // ============================================================

    if (isLoadingTypes) {
        StatsLoading(
            modifier = modifier
        )
        return
    }

    // ============================================================
    // 加载统计类型失败
    // ============================================================

    if (error != null && statTypes.isEmpty()) {
        StatsError(
            modifier = modifier,
            message = error ?: "Unknown error"
        )
        return
    }

    // ============================================================
    // 没有统计类型
    // ============================================================

    if (statTypes.isEmpty()) {
        StatsEmpty(
            modifier = modifier
        )
        return
    }

    // ============================================================
    // 当前选中的统计
    // ============================================================

    val selectedStat = statTypes.find {
        it.key == selectedKey
    }

    val selectedStatName = selectedStat?.let {
        androidx.compose.ui.res.stringResource(
            getStatStringRes(it.key)
        )
    } ?: androidx.compose.ui.res.stringResource(
        R.string.stats_unknown
    )

    // ============================================================
    // Drawer
    // ============================================================

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,

        // 保留原来的行为：
        // 只能通过顶部菜单按钮打开。
        gesturesEnabled = false,

        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface,
                drawerContentColor = MaterialTheme.colorScheme.onSurface
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
                        text = "统计数据",
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
                // 统计类型列表
                // ------------------------------------------------

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        vertical = 12.dp
                    )
                ) {
                    itemsIndexed(
                        items = statTypes
                    ) { _, statType ->

                        val isSelected =
                            statType.key == selectedKey

                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = androidx.compose.ui.res.stringResource(
                                        getStatStringRes(statType.key)
                                    ),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },

                            selected = isSelected,

                            onClick = {
                                selectedKey = statType.key

                                coroutineScope.launch {
                                    drawerState.close()
                                }
                            },

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
                                    MaterialTheme.colorScheme.onSurfaceVariant,

                                unselectedIconColor =
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }
    ) {

        // ========================================================
        // 主页面
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

                    verticalAlignment = Alignment.CenterVertically
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
                            contentDescription =
                                androidx.compose.ui.res.stringResource(
                                    R.string.open_stats_category
                                ),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Spacer(
                        modifier = Modifier.size(4.dp)
                    )

                    // ------------------------------------------------
                    // 标题
                    // ------------------------------------------------

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "统计",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            text = selectedStatName,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        ) { innerPadding ->

            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                when {

                    // ==================================================
                    // 加载排行榜
                    // ==================================================

                    isLoadingTop -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    // ==================================================
                    // 错误
                    // ==================================================

                    error != null -> {
                        StatsError(
                            modifier = Modifier.fillMaxSize(),
                            message = error ?: "Unknown error"
                        )
                    }

                    // ==================================================
                    // 没有数据
                    // ==================================================

                    topData == null ||
                            topData!!.entries.isEmpty() -> {

                        StatsEmpty(
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // ==================================================
                    // 排行榜
                    // ==================================================

                    else -> {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),

                            contentPadding = PaddingValues(
                                start = 16.dp,
                                end = 16.dp,
                                top = 8.dp,
                                bottom = 24.dp
                            ),

                            verticalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            itemsIndexed(
                                items = topData!!.entries
                            ) { index, entry ->

                                TopEntryItem(
                                    entry = entry,
                                    rank = index + 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


// ================================================================
// Loading
// ================================================================

@Composable
private fun StatsLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}


// ================================================================
// Error
// ================================================================

@Composable
private fun StatsError(
    modifier: Modifier = Modifier,
    message: String
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "无法加载统计数据",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


// ================================================================
// Empty
// ================================================================

@Composable
private fun StatsEmpty(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = androidx.compose.ui.res.stringResource(
                R.string.no_stat_data
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


// ================================================================
// 统计 Key -> Android String Resource
// ================================================================

@StringRes
fun getStatStringRes(key: String): Int {
    return when (key) {

        "playtime" ->
            R.string.stats_playtime

        "blocks_broken" ->
            R.string.stats_blocks_broken

        "blocks_placed" ->
            R.string.stats_blocks_placed

        "mob_kills" ->
            R.string.stats_mob_kills

        "player_kills" ->
            R.string.stats_player_kills

        "deaths" ->
            R.string.stats_deaths

        "damage_dealt" ->
            R.string.stats_damage_dealt

        "damage_taken" ->
            R.string.stats_damage_taken

        "distance_walked" ->
            R.string.stats_distance_walked

        "distance_sprinted" ->
            R.string.stats_distance_sprinted

        "distance_swam" ->
            R.string.stats_distance_swam

        "distance_flown" ->
            R.string.stats_distance_flown

        "distance_total" ->
            R.string.stats_distance_total

        "items_crafted" ->
            R.string.stats_items_crafted

        "items_dropped" ->
            R.string.stats_items_dropped

        "items_picked_up" ->
            R.string.stats_items_picked_up

        "chests_opened" ->
            R.string.stats_chests_opened

        "beds_slept_in" ->
            R.string.stats_beds_slept_in

        "xp_earned" ->
            R.string.stats_xp_earned

        "levels_gained" ->
            R.string.stats_levels_gained

        "money_earned" ->
            R.string.stats_money_earned

        "money_spent" ->
            R.string.stats_money_spent

        "money_balance" ->
            R.string.stats_money_balance

        "netherite_mined" ->
            R.string.stats_netherite_mined

        "diamond_mined" ->
            R.string.stats_diamond_mined

        "boss_kills" ->
            R.string.stats_boss_kills

        "emerald_mined" ->
            R.string.stats_emerald_mined

        "lapis_mined" ->
            R.string.stats_lapis_mined

        "redstone_mined" ->
            R.string.stats_redstone_mined

        "iron_mined" ->
            R.string.stats_iron_mined

        "gold_mined" ->
            R.string.stats_gold_mined

        "dragon_kills" ->
            R.string.stats_dragon_kills

        "wither_kills" ->
            R.string.stats_wither_kills

        "warden_kills" ->
            R.string.stats_warden_kills

        "breeze_kills" ->
            R.string.stats_breeze_kills

        "bogged_kills" ->
            R.string.stats_bogged_kills

        "nether_visits" ->
            R.string.stats_nether_visits

        "end_visits" ->
            R.string.stats_end_visits

        "netherite_ingots_crafted" ->
            R.string.stats_netherite_ingots_crafted

        "god_apples_crafted" ->
            R.string.stats_god_apples_crafted

        "maces_crafted" ->
            R.string.stats_maces_crafted

        "elytra_picked_up" ->
            R.string.stats_elytra_picked_up

        "trial_keys_picked_up" ->
            R.string.stats_trial_keys_picked_up

        "ominous_trial_keys_picked_up" ->
            R.string.stats_ominous_trial_keys_picked_up

        "beds_entered" ->
            R.string.stats_beds_entered

        "fish_caught" ->
            R.string.stats_fish_caught

        else ->
            R.string.stats_unknown
    }
}