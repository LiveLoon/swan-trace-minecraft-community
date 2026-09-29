package com.example.swantracemc.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * 鸿迹（Swan Trace）Minecraft Community
 *
 * Android App 全局颜色定义
 *
 * 设计方向：
 * - 深色
 * - 蓝灰
 * - 克制
 * - 与鸿迹官网视觉保持一致
 */

// ============================================================
// 基础背景
// ============================================================

/**
 * App 主背景
 *
 * 对应鸿迹官网主背景：
 * #0D0F14
 */
val SwanTraceBackground = Color(0xFF0D0F14)

/**
 * 页面稍浅一级的背景
 */
val SwanTraceSurface = Color(0xFF101319)

/**
 * 卡片背景
 */
val SwanTraceCard = Color(0xFF12161D)

/**
 * 更高层级的 Surface
 *
 * 用于 Dialog、BottomSheet 等。
 */
val SwanTraceSurfaceElevated = Color(0xFF171C23)


// ============================================================
// 边框 / 分割线
// ============================================================

/**
 * 卡片边框
 */
val SwanTraceBorder = Color(0xFF252D36)

/**
 * 更弱的分割线
 */
val SwanTraceDivider = Color(0xFF1C222A)


// ============================================================
// 品牌色
// ============================================================

/**
 * 鸿迹蓝
 *
 * 用于：
 * - Button
 * - Selected Navigation
 * - Link
 * - 重要交互
 */
val SwanTraceBlue = Color(0xFF6DB3F2)

/**
 * 深一点的品牌蓝
 *
 * 用于 pressed / selected / background 等场景。
 */
val SwanTraceBlueDark = Color(0xFF426F98)

/**
 * 蓝灰色
 *
 * 用于辅助 UI。
 */
val SwanTraceBlueGray = Color(0xFF8295A6)


// ============================================================
// 文字
// ============================================================

/**
 * 主文字
 */
val SwanTraceTextPrimary = Color(0xFFE8EDF2)

/**
 * 次级文字
 */
val SwanTraceTextSecondary = Color(0xFF9AA8B5)

/**
 * 弱化文字
 */
val SwanTraceTextTertiary = Color(0xFF687684)

/**
 * 暖白
 *
 * 用于品牌标题、重要信息或者少量强调。
 */
val SwanTraceWarmWhite = Color(0xFFF0E6D0)


// ============================================================
// 状态颜色
// ============================================================

/**
 * 成功 / 在线
 */
val SwanTraceSuccess = Color(0xFF72C18A)

/**
 * 警告
 */
val SwanTraceWarning = Color(0xFFE0B45A)

/**
 * 错误
 */
val SwanTraceError = Color(0xFFD66A6A)

/**
 * 信息
 */
val SwanTraceInfo = Color(0xFF6DB3F2)


// ============================================================
// Navigation Bar
// ============================================================

/**
 * 底部导航栏背景
 */
val SwanTraceNavigationBar = Color(0xFF101319)

/**
 * 当前选中项目的 Indicator
 */
val SwanTraceNavigationIndicator = Color(0xFF263D50)


// ============================================================
// 特殊用途
// ============================================================

/**
 * 半透明遮罩使用的基础颜色。
 *
 * 实际使用时通常配合 alpha：
 *
 * Color.Black.copy(alpha = 0.5f)
 */
val SwanTraceOverlay = Color(0xFF000000)

/**
 * 白色
 *
 * 用于图标、按钮等特殊场景。
 */
val SwanTraceWhite = Color(0xFFFFFFFF)

/**
 * 黑色
 */
val SwanTraceBlack = Color(0xFF000000)