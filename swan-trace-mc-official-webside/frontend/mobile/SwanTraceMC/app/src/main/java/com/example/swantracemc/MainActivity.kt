package com.example.swantracemc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import com.example.swantracemc.ui.pages.HomePage
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.swantracemc.ui.pages.AboutPage
import com.example.swantracemc.ui.pages.DownloadPage
import com.example.swantracemc.ui.pages.PlayerListPage
import com.example.swantracemc.ui.pages.StatsPage
import com.example.swantracemc.ui.theme.SwanTraceTheme
import kotlinx.coroutines.launch

/**
 * 鸿迹（Swan Trace）Minecraft Community
 *
 * Android 客户端底部导航。
 *
 * 当前包含：
 *
 * 1. 玩家
 * 2. 数据
 * 3. 存档
 * 4. 关于
 */
enum class AppDestinations(
    val label: String,
    val icon: ImageVector
) {
    /**
     * 首页 / 服务器信息
     */
    HOME(
        label = "首页",
        icon = Icons.Default.Home
    ),

    /**
     * 玩家列表
     */
    PLAYERS(
        label = "玩家",
        icon = Icons.Default.Apps
    ),

    /**
     * 玩家数据 / 服务器数据
     */
    STATS(
        label = "数据",
        icon = Icons.Default.AddChart
    ),

    /**
     * 世界存档
     */
    DOWNLOAD(
        label = "存档",
        icon = Icons.Default.FileDownload
    ),

    /**
     * 关于鸿迹
     */
    ABOUT(
        label = "关于",
        icon = Icons.Default.Info
    )
}

/**
 * 鸿迹 App 主界面
 */
@Composable
fun SwanTraceApp() {

    /*
     * 当前选中的底部导航
     *
     * 使用 rememberSaveable，
     * 屏幕旋转等情况下尽可能保留当前页面。
     */
    var currentDestination by rememberSaveable {
        mutableStateOf(AppDestinations.HOME)
    }

    /*
     * HorizontalPager
     *
     * 每一个底部导航对应一个页面。
     */
    val pagerState = rememberPagerState(
        pageCount = {
            AppDestinations.entries.size
        }
    )

    /*
     * 用于控制 Pager 页面切换。
     */
    val coroutineScope = rememberCoroutineScope()

    /*
     * 监听 Pager 的滑动。
     *
     * 用户手指左右滑动页面时，
     * 自动同步底部导航的选中状态。
     */
    LaunchedEffect(pagerState) {

        snapshotFlow {
            pagerState.currentPage
        }.collect { page ->

            currentDestination =
                AppDestinations.entries[page]
        }
    }

    /*
     * =========================================================
     * Scaffold
     * =========================================================
     */
    Scaffold(

        modifier = Modifier.fillMaxSize(),

        /*
         * 鸿迹统一深色背景。
         *
         * 与网站：
         *
         * #0D0F14
         *
         * 保持一致。
         */
        containerColor = SwanTraceColors.Background,

        /*
         * 底部导航栏
         */
        bottomBar = {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(
                        SwanTraceColors.Background
                    )
                    .navigationBarsPadding()
            ) {

                NavigationBar(

                    modifier = Modifier.fillMaxWidth(),

                    /*
                     * 深色导航栏
                     */
                    containerColor =
                        SwanTraceColors.NavigationBar,

                    contentColor =
                        SwanTraceColors.TextPrimary,

                    /*
                     * 使用自定义 Insets，
                     * 避免导航栏额外产生空白。
                     */
                    windowInsets = WindowInsets(
                        0,
                        0,
                        0,
                        0
                    )
                ) {

                    /*
                     * 创建底部导航项目
                     */
                    AppDestinations.entries.forEachIndexed {
                            index,
                            destination ->

                        val isSelected =
                            destination == currentDestination

                        NavigationBarItem(

                            selected = isSelected,

                            /*
                             * 点击导航
                             */
                            onClick = {

                                coroutineScope.launch {

                                    pagerState.animateScrollToPage(
                                        index
                                    )
                                }

                                currentDestination =
                                    destination
                            },

                            /*
                             * 图标
                             */
                            icon = {

                                Icon(
                                    imageVector =
                                        destination.icon,

                                    contentDescription =
                                        destination.label
                                )
                            },

                            /*
                             * 导航文字
                             */
                            label = {
                                androidx.compose.material3.Text(
                                    text =
                                        destination.label
                                )
                            },

                            /*
                             * 鸿迹导航颜色
                             */
                            colors =
                                NavigationBarItemDefaults.colors(

                                    /*
                                     * 当前选中图标
                                     */
                                    selectedIconColor =
                                        SwanTraceColors.TextPrimary,

                                    /*
                                     * 当前选中文字
                                     */
                                    selectedTextColor =
                                        SwanTraceColors.TextPrimary,

                                    /*
                                     * 当前选中项目背景
                                     */
                                    indicatorColor =
                                        SwanTraceColors.NavigationIndicator,

                                    /*
                                     * 未选中图标
                                     */
                                    unselectedIconColor =
                                        SwanTraceColors.TextSecondary,

                                    /*
                                     * 未选中文字
                                     */
                                    unselectedTextColor =
                                        SwanTraceColors.TextSecondary
                                )
                        )
                    }
                }
            }
        }

    ) { innerPadding ->

        /*
         * =====================================================
         * 页面区域
         * =====================================================
         *
         * HorizontalPager：
         *
         * 玩家 ← → 数据 ← → 存档 ← → 关于
         */
        HorizontalPager(

            state = pagerState,

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) { page ->

            when (AppDestinations.entries[page]) {
                /**
                 * 首页
                 */
                AppDestinations.HOME -> {
                    HomePage(
                        Modifier.fillMaxSize()
                    )
                }
                /*
                 * 玩家
                 */
                AppDestinations.PLAYERS -> {
                    PlayerListPage(
                        Modifier.fillMaxSize()
                    )
                }

                /*
                 * 数据
                 */
                AppDestinations.STATS -> {
                    StatsPage(
                        Modifier.fillMaxSize()
                    )
                }

                /*
                 * 存档
                 */
                AppDestinations.DOWNLOAD -> {
                    DownloadPage(
                        Modifier.fillMaxSize()
                    )
                }

                /*
                 * 关于
                 */
                AppDestinations.ABOUT -> {
                    AboutPage(
                        Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

/**
 * =========================================================
 * 鸿迹颜色
 * =========================================================
 *
 * 与网站 Swan Trace 的深色视觉保持一致。
 */
object SwanTraceColors {

    /**
     * 页面背景
     *
     * 网站：
     * #0D0F14
     */
    val Background =
        Color(0xFF0D0F14)

    /**
     * 底部导航栏
     */
    val NavigationBar =
        Color(0xFF101319)

    /**
     * 导航选中指示器
     */
    val NavigationIndicator =
        Color(0xFF263D50)

    /**
     * 主文字
     */
    val TextPrimary =
        Color(0xFFE8EDF2)

    /**
     * 次级文字
     */
    val TextSecondary =
        Color(0xFF8295A6)

    /**
     * 鸿迹蓝
     */
    val SwanBlue =
        Color(0xFF6DB3F2)

    /**
     * 暖白
     */
    val WarmWhite =
        Color(0xFFF0E6D0)

    /**
     * 卡片背景
     */
    val Card =
        Color(0xFF12161D)

    /**
     * 卡片边框
     */
    val Border =
        Color(0xFF252D36)
}

/**
 * =========================================================
 * MainActivity
 * =========================================================
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        /*
         * 开启 Edge-to-Edge。
         */
        enableEdgeToEdge()

        setContent {

            /*
             * 鸿迹主题。
             *
             * 注意：
             *
             * 这里已经从：
             *
             * JustMCTheme
             *
             * 修改为：
             *
             * SwanTraceTheme
             */
            SwanTraceTheme {

                SwanTraceApp()
            }
        }
    }
}

