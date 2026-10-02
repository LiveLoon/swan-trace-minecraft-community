package com.example.swantracemc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

/**
 * 鸿迹（Swan Trace）主题配色
 *
 * App 采用固定深色主题，不跟随系统浅色/动态配色。
 */
private val SwanTraceDarkColorScheme = darkColorScheme(

    // ========================================================
    // 品牌色
    // ========================================================

    primary = SwanTraceBlue,
    onPrimary = SwanTraceWhite,

    secondary = SwanTraceBlueGray,
    onSecondary = SwanTraceWhite,

    tertiary = SwanTraceWarmWhite,
    onTertiary = SwanTraceBlack,

    // ========================================================
    // 背景
    // ========================================================

    background = SwanTraceBackground,
    onBackground = SwanTraceTextPrimary,

    surface = SwanTraceSurface,
    onSurface = SwanTraceTextPrimary,

    surfaceVariant = SwanTraceCard,
    onSurfaceVariant = SwanTraceTextSecondary,

    // ========================================================
    // 边框
    // ========================================================

    outline = SwanTraceBorder,

    // ========================================================
    // 错误状态
    // ========================================================

    error = SwanTraceError,
    onError = SwanTraceWhite
)

/**
 * 鸿迹（Swan Trace）全局主题。
 *
 * 设计：
 * - 固定深色
 * - 深色背景
 * - 蓝灰色品牌体系
 * - 暖白色少量强调
 * - 禁用 Material You 动态颜色
 */
@Composable
fun SwanTraceTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SwanTraceDarkColorScheme,
        typography = Typography,
        content = content
    )
}