package com.example.swantracemc.ui.pages

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.swantracemc.data.BackupFile
import com.example.swantracemc.network.ApiClient
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// ================================================================
// 排序方式
// ================================================================

enum class SortType {
    NAME_ASC,
    NAME_DESC,
    SIZE_ASC,
    SIZE_DESC,
    TIME_ASC,
    TIME_DESC
}


// ================================================================
// 存档下载页面
// ================================================================

@Composable
fun DownloadPage(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var backupFiles by remember {
        mutableStateOf<List<BackupFile>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    var sortType by rememberSaveable {
        mutableStateOf(SortType.TIME_DESC)
    }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val coroutineScope = rememberCoroutineScope()


    // ============================================================
    // 加载存档列表
    // ============================================================

    LaunchedEffect(Unit) {

        try {

            backupFiles =
                ApiClient.apiService.getBackupList()

        } catch (e: Exception) {

            error =
                e.message ?: "加载存档列表失败"

        } finally {

            isLoading = false
        }
    }


    // ============================================================
    // 获取最新文件
    // ============================================================

    fun getLatestFile(
        files: List<BackupFile>
    ): BackupFile? {

        return files.maxByOrNull {
            it.mtime.toLongOrNull() ?: 0L
        }
    }


    // ============================================================
    // 排序
    // ============================================================

    fun sortFiles(
        files: List<BackupFile>,
        type: SortType
    ): List<BackupFile> {

        return when (type) {

            SortType.NAME_ASC ->
                files.sortedBy {
                    it.name
                }

            SortType.NAME_DESC ->
                files.sortedByDescending {
                    it.name
                }

            SortType.SIZE_ASC ->
                files.sortedBy {
                    it.size
                }

            SortType.SIZE_DESC ->
                files.sortedByDescending {
                    it.size
                }

            SortType.TIME_ASC ->
                files.sortedBy {
                    it.mtime.toLongOrNull() ?: 0L
                }

            SortType.TIME_DESC ->
                files.sortedByDescending {
                    it.mtime.toLongOrNull() ?: 0L
                }
        }
    }


    // ============================================================
    // 构建显示列表
    //
    // 最新存档始终固定在第一位。
    // 其他文件按照用户选择的排序方式排列。
    // ============================================================

    fun buildDisplayList(
        files: List<BackupFile>,
        type: SortType
    ): Pair<BackupFile?, List<BackupFile>> {

        if (files.isEmpty()) {
            return null to emptyList()
        }

        val latest = getLatestFile(files)

        val rest =
            if (latest != null) {
                files.filter {
                    it != latest
                }
            } else {
                files
            }

        val sortedRest =
            sortFiles(
                rest,
                type
            )

        return latest to sortedRest
    }


    // ============================================================
    // 时间格式化
    // ============================================================

    fun formatTime(
        timestamp: String
    ): String {

        return try {

            val millis =
                timestamp.toLong()

            val sdf =
                SimpleDateFormat(
                    "yyyy-MM-dd HH:mm",
                    Locale.getDefault()
                )

            sdf.format(
                Date(millis)
            )

        } catch (e: Exception) {

            timestamp
        }
    }


    // ============================================================
    // 文件大小格式化
    // ============================================================

    fun formatSize(
        size: Long
    ): String {

        return when {

            size < 1024 ->
                "$size B"

            size < 1024 * 1024 ->
                String.format(
                    Locale.getDefault(),
                    "%.2f KB",
                    size / 1024.0
                )

            size < 1024 * 1024 * 1024 ->
                String.format(
                    Locale.getDefault(),
                    "%.2f MB",
                    size / (1024.0 * 1024)
                )

            else ->
                String.format(
                    Locale.getDefault(),
                    "%.2f GB",
                    size / (1024.0 * 1024 * 1024)
                )
        }
    }


    // ============================================================
    // 复制下载链接
    // ============================================================

    fun copyDownloadLink(
        fileName: String
    ) {

        try {

            val url =
                "${ApiClient.BASE_URL}backup/download/$fileName"

            val clipboardManager =
                context.getSystemService(
                    Context.CLIPBOARD_SERVICE
                ) as ClipboardManager

            val clip =
                ClipData.newPlainText(
                    "下载链接",
                    url
                )

            clipboardManager.setPrimaryClip(
                clip
            )

            Toast.makeText(
                context,
                "下载链接已复制",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {

            Toast.makeText(
                context,
                "复制链接失败：${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    // ============================================================
    // 排序显示名称
    // ============================================================

    fun getSortDisplay(
        type: SortType
    ): String {

        return when (type) {

            SortType.NAME_ASC ->
                "名称 A-Z"

            SortType.NAME_DESC ->
                "名称 Z-A"

            SortType.SIZE_ASC ->
                "大小 小 → 大"

            SortType.SIZE_DESC ->
                "大小 大 → 小"

            SortType.TIME_ASC ->
                "时间 旧 → 新"

            SortType.TIME_DESC ->
                "时间 新 → 旧"
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
                horizontalAlignment =
                    Alignment.CenterHorizontally,

                modifier = Modifier.padding(32.dp)
            ) {

                Text(
                    text = "无法加载存档",
                    style =
                        MaterialTheme.typography.titleMedium,
                    color =
                        MaterialTheme.colorScheme.onBackground
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = error ?: "未知错误",
                    style =
                        MaterialTheme.typography.bodySmall,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        return
    }


    // ============================================================
    // 构建列表
    // ============================================================

    val (latest, sortedRest) =
        buildDisplayList(
            backupFiles,
            sortType
        )


    // ============================================================
    // Drawer
    // ============================================================

    ModalNavigationDrawer(

        modifier = modifier,

        drawerState = drawerState,

        gesturesEnabled = false,

        drawerContent = {

            ModalDrawerSheet(

                drawerContainerColor =
                    MaterialTheme.colorScheme.surface,

                drawerContentColor =
                    MaterialTheme.colorScheme.onSurface
            ) {

                // ------------------------------------------------
                // Header
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
                        style =
                            MaterialTheme.typography.headlineSmall,
                        color =
                            MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "存档排序",
                        style =
                            MaterialTheme.typography.bodyMedium,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }


                HorizontalDivider(
                    color =
                        MaterialTheme.colorScheme.outline.copy(
                            alpha = 0.6f
                        )
                )


                // ------------------------------------------------
                // 排序选项
                // ------------------------------------------------

                SortType.entries.forEach { type ->

                    val isSelected =
                        sortType == type

                    NavigationDrawerItem(

                        label = {

                            Text(
                                text =
                                    getSortDisplay(type),

                                style =
                                    MaterialTheme.typography.bodyMedium
                            )
                        },

                        selected =
                            isSelected,

                        onClick = {

                            sortType = type

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

                        colors =
                            NavigationDrawerItemDefaults.colors(

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
                            imageVector =
                                Icons.Default.Menu,

                            contentDescription =
                                "排序方式",

                            tint =
                                MaterialTheme.colorScheme.onBackground
                        )
                    }


                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )


                    // ------------------------------------------------
                    // 标题
                    // ------------------------------------------------

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "存档",
                            style =
                                MaterialTheme.typography.titleLarge,
                            color =
                                MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            text =
                                "${backupFiles.size} 个存档 · ${getSortDisplay(sortType)}",

                            style =
                                MaterialTheme.typography.bodySmall,

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

                when {

                    // ------------------------------------------------
                    // 没有存档
                    // ------------------------------------------------

                    backupFiles.isEmpty() -> {

                        Box(
                            modifier =
                                Modifier.fillMaxSize(),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text = "没有存档文件",

                                style =
                                    MaterialTheme.typography.bodyMedium,

                                color =
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }


                    // ------------------------------------------------
                    // 存档列表
                    // ------------------------------------------------

                    else -> {

                        LazyColumn(

                            modifier =
                                Modifier.fillMaxSize(),

                            contentPadding =
                                PaddingValues(
                                    top = 8.dp,
                                    bottom = 24.dp
                                ),

                            verticalArrangement =
                                Arrangement.spacedBy(10.dp)
                        ) {

                            // 最新存档
                            if (latest != null) {

                                item {

                                    BackupFileItem(
                                        file = latest,
                                        isLatest = true,
                                        onCopyLink =
                                            ::copyDownloadLink,
                                        formatSize =
                                            ::formatSize,
                                        formatTime =
                                            ::formatTime
                                    )
                                }
                            }


                            // 其他存档
                            items(
                                items = sortedRest,
                                key = {
                                    it.name
                                }
                            ) { file ->

                                BackupFileItem(
                                    file = file,
                                    isLatest = false,
                                    onCopyLink =
                                        ::copyDownloadLink,
                                    formatSize =
                                        ::formatSize,
                                    formatTime =
                                        ::formatTime
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
// 存档卡片
// ================================================================

@Composable
private fun BackupFileItem(
    file: BackupFile,
    isLatest: Boolean,
    onCopyLink: (String) -> Unit,
    formatSize: (Long) -> String,
    formatTime: (String) -> String
) {

    val cardBorderColor = if (isLatest) {

        MaterialTheme.colorScheme.primary.copy(
            alpha = 0.4f
        )

    } else {

        MaterialTheme.colorScheme.outline.copy(
            alpha = 0.35f
        )
    }


    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                width = 1.dp,
                color = cardBorderColor,
                shape = RoundedCornerShape(14.dp)
            ),

        shape =
            RoundedCornerShape(14.dp),

        colors =
            CardDefaults.cardColors(

                containerColor =
                    if (isLatest) {

                        MaterialTheme.colorScheme.surface

                    } else {

                        MaterialTheme.colorScheme.surface
                    }
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    if (isLatest) 2.dp else 0.dp
            )
    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {


            // ====================================================
            // 左侧状态线
            // ====================================================

            Box(

                modifier = Modifier
                    .width(4.dp)
                    .height(76.dp)
                    .clip(
                        RoundedCornerShape(
                            4.dp
                        )
                    )
                    .background(

                        if (isLatest) {

                            MaterialTheme.colorScheme.primary

                        } else {

                            MaterialTheme.colorScheme.outline
                        }
                    )
            )


            Spacer(
                modifier = Modifier.width(14.dp)
            )


            // ====================================================
            // 存档信息
            // ====================================================

            Column(

                modifier = Modifier.weight(1f),

                verticalArrangement =
                    Arrangement.spacedBy(5.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(

                        text = file.name,

                        modifier =
                            Modifier.weight(1f),

                        style =
                            MaterialTheme.typography.titleMedium,

                        color =
                            MaterialTheme.colorScheme.onSurface
                    )


                    // ------------------------------------------------
                    // 最新标签
                    // ------------------------------------------------

                    if (isLatest) {

                        Spacer(
                            modifier =
                                Modifier.width(8.dp)
                        )

                        Surface(

                            shape =
                                RoundedCornerShape(6.dp),

                            color =
                                MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.15f
                                )
                        ) {

                            Text(

                                text = "最新",

                                modifier =
                                    Modifier.padding(
                                        horizontal = 7.dp,
                                        vertical = 3.dp
                                    ),

                                style =
                                    MaterialTheme.typography.labelSmall,

                                color =
                                    MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }


                // ------------------------------------------------
                // 文件信息
                // ------------------------------------------------

                Text(

                    text =
                        "大小：${formatSize(file.size)}",

                    style =
                        MaterialTheme.typography.bodySmall,

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(

                    text =
                        "修改：${formatTime(file.mtime)}",

                    style =
                        MaterialTheme.typography.bodySmall,

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }


            Spacer(
                modifier = Modifier.width(12.dp)
            )


            // ====================================================
            // 复制链接
            // ====================================================

            Button(

                onClick = {
                    onCopyLink(file.name)
                },

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            MaterialTheme.colorScheme.primary,

                        contentColor =
                            MaterialTheme.colorScheme.onPrimary
                    ),

                shape =
                    RoundedCornerShape(10.dp),

                contentPadding =
                    PaddingValues(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
            ) {

                Text(

                    text = "复制链接",

                    style =
                        MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}