package com.example.swantracemc.ui.pages

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.ColumnScope
import com.example.swantracemc.R
// ================================================================
// 关于页面栏目
// ================================================================

enum class AboutSection {
    INTRODUCTION,
    DISCLAIMER
}


// ================================================================
// 关于页面
// ================================================================

@Composable
fun AboutPage(
    modifier: Modifier = Modifier
) {
    var currentSection by remember {
        mutableStateOf(AboutSection.INTRODUCTION)
    }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val coroutineScope = rememberCoroutineScope()


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
                        style =
                            MaterialTheme.typography.headlineSmall,
                        color =
                            MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Swan Trace · 关于",
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
                // 服务器介绍
                // ------------------------------------------------

                NavigationDrawerItem(

                    label = {

                        Text(
                            text = "服务器介绍",
                            style =
                                MaterialTheme.typography.bodyMedium
                        )
                    },

                    selected =
                        currentSection ==
                                AboutSection.INTRODUCTION,

                    onClick = {

                        currentSection =
                            AboutSection.INTRODUCTION

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


                // ------------------------------------------------
                // 免责声明
                // ------------------------------------------------

                NavigationDrawerItem(

                    label = {

                        Text(
                            text = "免责声明",
                            style =
                                MaterialTheme.typography.bodyMedium
                        )
                    },

                    selected =
                        currentSection ==
                                AboutSection.DISCLAIMER,

                    onClick = {

                        currentSection =
                            AboutSection.DISCLAIMER

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
    ) {


        // ========================================================
        // 主内容
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
                                "导航菜单",

                            tint =
                                MaterialTheme.colorScheme.onBackground
                        )
                    }


                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )


                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(

                            text =
                                when (currentSection) {

                                    AboutSection.INTRODUCTION ->
                                        "关于"

                                    AboutSection.DISCLAIMER ->
                                        "免责声明"
                                },

                            style =
                                MaterialTheme.typography.titleLarge,

                            color =
                                MaterialTheme.colorScheme.onBackground
                        )


                        Text(

                            text =
                                when (currentSection) {

                                    AboutSection.INTRODUCTION ->
                                        "鸿迹 · Swan Trace Minecraft Community"

                                    AboutSection.DISCLAIMER ->
                                        "服务协议与责任说明"
                                },

                            style =
                                MaterialTheme.typography.bodySmall,

                            color =
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        ) { innerPadding ->

            LazyColumn(

                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),

                contentPadding =
                    PaddingValues(
                        top = 8.dp,
                        bottom = 24.dp
                    ),

                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                when (currentSection) {

                    AboutSection.INTRODUCTION ->
                        introductionItems()

                    AboutSection.DISCLAIMER ->
                        disclaimerItems()
                }
            }
        }
    }
}


// ================================================================
// 服务器介绍
// ================================================================

private fun LazyListScope.introductionItems() {

    // ============================================================
    // 介绍
    // ============================================================

    item {

        AboutCard {

            Text(
                text = "介绍",
                style =
                    MaterialTheme.typography.titleLarge,
                color =
                    MaterialTheme.colorScheme.onSurface
            )

            Text(
                text =
                    "鸿迹（Swan Trace）Minecraft Community 是一个个人公益性质的 Minecraft 社区服务器。",

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    MaterialTheme.colorScheme.onSurface
            )

            Text(
                text =
                    "我们希望保留接近原版 Minecraft 的游戏体验，让玩家可以自由地建立自己的世界。",

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )


            Image(

                painter =
                    painterResource(
                        id = R.drawable.image_1
                    ),

                contentDescription =
                    "鸿迹 Minecraft 社区截图",

                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),

                contentScale =
                    ContentScale.Crop
            )


            Text(
                text =
                    "关注抖音查看更多 · 抖音号：43650753863",

                style =
                    MaterialTheme.typography.bodySmall,

                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }


    // ============================================================
    // 名片
    // ============================================================

    item {

        AboutCard {

            Text(
                text = "社区信息",
                style =
                    MaterialTheme.typography.titleLarge,
                color =
                    MaterialTheme.colorScheme.onSurface
            )


            TableRow(
                label = "QQ群",
                value = "661436985（入群获取最新公告）"
            )


            // ----------------------------------------------------
            // 服务器地址图片
            // ----------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Spacer(
                    modifier =
                        Modifier.weight(0.4f)
                )


                Image(

                    painter =
                        painterResource(
                            id = R.drawable.image_3
                        ),

                    contentDescription =
                        "服务器地址列表",

                    modifier = Modifier
                        .weight(0.6f)
                        .height(200.dp)
                        .padding(vertical = 4.dp),

                    contentScale =
                        ContentScale.Fit
                )
            }


            TableRow(
                label = "游戏版本",
                value =
                    "目前最新 26.2（纯净版，随官方最新版本迭代）"
            )

            TableRow(
                label = "正版验证",
                value = "需要正版 Minecraft 账户"
            )

            TableRow(
                label = "在线模式",
                value = "开启（正版登录）"
            )

            TableRow(
                label = "最大同时在线",
                value = "20 人"
            )

            TableRow(
                label = "运行时间",
                value = "24 小时 × 7 天"
            )

            TableRow(
                label = "社区性质",
                value = "个人公益 · 免费"
            )
        }
    }


    // ============================================================
    // 社区原则
    // ============================================================

    item {

        AboutCard {

            Text(
                text = "社区原则",
                style =
                    MaterialTheme.typography.titleLarge,
                color =
                    MaterialTheme.colorScheme.onSurface
            )


            BulletItem(
                title = "纯净",
                description =
                    "无客户端模组、无商业化，保持接近原版的 Minecraft 游戏体验。"
            )


            BulletItem(
                title = "自由",
                description =
                    "建筑、破坏、红石、探险……服主不干涉正常游戏行为，只要不影响服务器运行和其他玩家的游玩体验。"
            )
        }
    }


    // ============================================================
    // 服务器规则
    // ============================================================

    item {

        AboutCard {

            Text(
                text = "服务器规则和封禁规则",
                style =
                    MaterialTheme.typography.titleLarge,
                color =
                    MaterialTheme.colorScheme.onSurface
            )


            NumberedItem(
                number = 1,
                title = "禁止恶意破坏服务器运行",
                description =
                    "严禁 DDoS 攻击、漏洞利用、刷屏/炸服等任何危害服务器稳定性的行为。情况严重者将追究法律责任。"
            )


            NumberedItem(
                number = 2,
                title = "尊重他人，友善交流",
                description =
                    "群内/游戏内禁止辱骂、歧视、政治敏感内容。"
            )


            NumberedItem(
                number = 3,
                title = "适度的红石",
                description =
                    "为避免卡服，请避免建造超大规模高频红石。"
            )


            NumberedItem(
                number = 4,
                title = "领地与建筑",
                description =
                    "请勿恶意毁坏其他玩家明确标记的工程（家、建筑、红石机械、刷怪塔等）（使用告示牌声明）。"
            )


            NumberedItem(
                number = 5,
                title = "客户端修改与作弊",
                description =
                    "禁止使用透视、开挂、飞行。一旦发现，并且证据存在，直接封禁。"
            )


            NumberedItem(
                number = 6,
                title = "挂机（AFK）与实体压迫",
                description =
                    "严禁使用自动按键精灵（连点器）或物理卡键进行 24 小时不间断挂机（如钓鱼、挂机池）。若因挂机导致服务器资源耗尽或卡顿，管理员有权直接踢出或清除该玩家实体和资源。\n\n单次加载范围内（刷怪塔/养殖场）如出现服务器因为实体大规模存在导致服务器卡顿，管理员有权在未通知的情况下直接清除实体和刷怪塔/养殖场以保 TPS。"
            )
        }
    }


    // ============================================================
    // 举报与执行
    // ============================================================

    item {

        AboutCard {

            Text(
                text = "举报与执行",
                style =
                    MaterialTheme.typography.titleLarge,
                color =
                    MaterialTheme.colorScheme.onSurface
            )


            BulletItem(
                title = "有效证据",
                description =
                    "所有举报必须附带包含坐标（F3界面）和破坏者ID的截图或录屏，否则无法受理。"
            )


            BulletItem(
                title = "最终解释权",
                description =
                    "对于以上未明确列出、但明显利用漏洞“钻空子”且严重影响他人体验的行为，同样。"
            )
        }
    }


    // ============================================================
    // 插件 / 维护 / 加入 / 组织 / FAQ
    // ============================================================

    item {

        AboutCard {

            SectionHeading("服务器插件")

            Text(
                text =
                    "根据大家投票决定是否需要添加某些插件（如：领地划分插件）。\n见群公告。",

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )


            SectionSpacer()


            SectionHeading("维护与更新")

            BulletItem(
                title = "版本更新",
                description =
                    "服务器根据需求，每次大版本升级前会在群内预告，并保留旧版备份供过渡。"
            )

            BulletItem(
                title = "重启维护",
                description =
                    "若遇异常崩溃或计划维护，会在群内提前 30 分钟通知。"
            )


            SectionSpacer()


            SectionHeading("加入我们")

            NumberedItem(
                number = 1,
                title = "加群",
                description =
                    "QQ 搜索群号 661436985，或扫描下方二维码（群内提供）。"
            )

            NumberedItem(
                number = 2,
                title = "验证正版",
                description =
                    "启动器登录正版账号，添加服务器地址。"
            )


            SectionSpacer()


            SectionHeading("本服玩家组织")

            Text(
                text =
                    "服务器中，玩家组建的组织名、Logo 图标、坐标位置、内容介绍，会展示在下面。\n\n目前暂无组织（如果需要添加，联系腐竹）。",

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )


            SectionSpacer()


            SectionHeading("常见问题")

            QnA(
                question = "Q：为什么需要正版？",
                answer =
                    "A：保证玩家身份唯一，方便我们追溯违规行为。"
            )

            QnA(
                question = "Q：服务器卡顿怎么办？",
                answer =
                    "A：低成本服务器性能有限，20 人满员时可能略有延迟。建议避免高峰期集中加载新区块。"
            )

            QnA(
                question = "Q：我能当管理/OP吗？",
                answer =
                    "A：本服无 OP 制度，暂不招募管理员。但你的合理建议会认真听取。"
            )

            QnA(
                question = "Q：世界边界和存档会重置吗？",
                answer =
                    "A：目前边界限制为 50000，也不会轻易重置。若未来因版本大更需重置，会提前提醒。"
            )
        }
    }


    // ============================================================
    // 最后的话
    // ============================================================

    item {

        AboutCard {

            Text(
                text = "最后的话",
                style =
                    MaterialTheme.typography.titleLarge,
                color =
                    MaterialTheme.colorScheme.onSurface
            )


            Text(
                text =
                    "这个服务器始于一份下班后的闲暇，也希望能成为你的世界里的另一个家。",

                style =
                    MaterialTheme.typography.bodyLarge.copy(
                        fontStyle = FontStyle.Italic
                    ),

                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


// ================================================================
// 免责声明
// ================================================================

private fun LazyListScope.disclaimerItems() {

    item {

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(
                    width = 1.dp,
                    color =
                        MaterialTheme.colorScheme.error.copy(
                            alpha = 0.35f
                        ),
                    shape =
                        RoundedCornerShape(14.dp)
                ),

            shape =
                RoundedCornerShape(14.dp),

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

                modifier =
                    Modifier.padding(16.dp),

                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {


                // ------------------------------------------------
                // 标题
                // ------------------------------------------------

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Box(

                        modifier = Modifier
                            .width(4.dp)
                            .height(30.dp)
                            .clip(
                                RoundedCornerShape(4.dp)
                            )
                            .background(
                                MaterialTheme.colorScheme.error
                            )
                    )


                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )


                    Text(
                        text = "免责声明",
                        style =
                            MaterialTheme.typography.headlineSmall,
                        color =
                            MaterialTheme.colorScheme.onSurface
                    )
                }


                Text(
                    text =
                        "版本：26.2  |  生效日期：2026年6月28日",

                    style =
                        MaterialTheme.typography.bodySmall,

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )


                Text(
                    text =
                        "适用服务器：鸿迹（Swan Trace）Minecraft Community  |  运营方：LiveLoon",

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        MaterialTheme.colorScheme.onSurface
                )


                Text(
                    text =
                        "欢迎来到本服！在您开始游戏之前，请仔细阅读以下免责条款。您一旦连接本服并进入游戏，即表示您已完全理解并同意本协议的全部内容。如您不同意任何条款，请立即断开连接并退出游戏。",

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )


                DisclaimerDivider()


                // ------------------------------------------------
                // 1
                // ------------------------------------------------

                SectionTitle(
                    text = "1. 服务性质声明"
                )

                DisclaimerText(
                    text =
                        "本服为个人公益性质的免费Minecraft服务器，非商业运营，不向玩家收取任何费用，同样不提供任何担保或服务承诺。服主保留随时修改、暂停、终止服务器运行的权利，但会尽量提前通知。"
                )


                // ------------------------------------------------
                // 2
                // ------------------------------------------------

                SectionTitle(
                    text = "2. 免责范围"
                )

                DisclaimerText(
                    text =
                        "在适用法律允许的最大范围内，服主对以下情况不承担任何责任（包括但不限于）："
                )


                val liabilityItems = listOf(

                    "服务器稳定性：因网络波动、硬件故障、电力中断、第三方攻击（如DDoS）、游戏版本更新兼容性问题等导致的服务器暂时不可用、延迟增大、回档或崩溃。",

                    "游戏数据丢失：因服务器意外宕机、存档损坏、备份失败或人为误操作导致的玩家建筑、物品、进度等任何游戏内数据的丢失或损坏。（尽管服主会每周备份，但不保证备份的完整性和及时性。）",

                    "玩家间纠纷：玩家之间的PVP、破坏、欺诈、言语冲突等行为引发的任何损失或心理伤害。服主不介入调解，但保留对违反服务器规则者采取封禁措施的权力。",

                    "第三方软件及账号安全：因玩家使用非官方启动器、作弊模组、外挂等导致的账号被盗、电脑中毒或个人信息泄露，服主概不负责。正版验证由Mojang/Microsoft提供，服主不存储您的密码或凭证。",

                    "法律风险：玩家在游戏内或群内发布违反中国法律法规的内容（如色情、暴力、赌博、政治敏感等），由该玩家自行承担全部法律责任。",

                    "个人损失：玩家因游玩本服而产生的任何费用和损失，由该玩家自行承担全部。"
                )


                liabilityItems.forEach { item ->

                    BulletText(
                        text = item
                    )
                }


                // ------------------------------------------------
                // 3
                // ------------------------------------------------

                SectionTitle(
                    text = "3. 玩家责任与义务"
                )


                val responsibilityItems = listOf(

                    "您承诺使用正版Minecraft账户登录，并自行保管好您的账号密码。",

                    "您同意自行承担因游玩本服而产生的任何费用和损失。",

                    "您应遵守国家法律法规及本服的服务器规则和封禁规则（详见服务器介绍文档），不进行任何破坏服务器运行或干扰其他玩家的恶意行为。",

                    "您已知晓本服为公益性质，服主不对游戏体验或服务质量作出任何明示或暗示的保证。"
                )


                responsibilityItems.forEach { item ->

                    BulletText(
                        text = item
                    )
                }


                // ------------------------------------------------
                // 4
                // ------------------------------------------------

                SectionTitle(
                    text = "4. 个人信息处理"
                )

                DisclaimerText(
                    text =
                        "本服仅记录您的游戏ID、IP地址及登录时间，用于维护服务器安全和统计人数，不会将这些信息用于商业目的或转交给第三方（法律强制要求除外）。群聊内个人隐私信息请自行保护，服主不承担群内交流引发的信息泄露责任。"
                )


                // ------------------------------------------------
                // 5
                // ------------------------------------------------

                SectionTitle(
                    text = "5. 协议修改与解释"
                )

                DisclaimerText(
                    text =
                        "服主保留随时修订本免责协议的权利，修订后的协议将在群内公告或服务器登录提示中公布，并即时生效。您持续使用本服即视为接受修订内容。本协议的最终解释权归服主所有。"
                )


                // ------------------------------------------------
                // 6
                // ------------------------------------------------

                SectionTitle(
                    text = "6. 联系与反馈"
                )

                DisclaimerText(
                    text =
                        "如您对本协议有任何疑问，或发现服务器存在安全隐患，欢迎通过QQ群 661436985 联系服主。服主将在工作之余尽力回复。"
                )


                // ------------------------------------------------
                // 图片
                // ------------------------------------------------

                Image(

                    painter =
                        painterResource(
                            id = R.drawable.image_2
                        ),

                    contentDescription =
                        "鸿迹 Minecraft 社区截图",

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        ),

                    contentScale =
                        ContentScale.Crop
                )
            }
        }
    }
}


// ================================================================
// 通用 About Card
// ================================================================

@Composable
private fun AboutCard(
    content: @Composable ColumnScope.() -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),

        shape =
            RoundedCornerShape(14.dp),

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

            modifier =
                Modifier.padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(8.dp),

            content = content
        )
    }
}


// ================================================================
// 表格行
// ================================================================

@Composable
private fun TableRow(
    label: String,
    value: String
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),

        verticalAlignment =
            Alignment.Top
    ) {

        Text(

            text = label,

            style =
                MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),

            color =
                MaterialTheme.colorScheme.onSurface,

            modifier =
                Modifier.weight(0.4f)
        )


        Text(

            text = value,

            style =
                MaterialTheme.typography.bodyMedium,

            color =
                MaterialTheme.colorScheme.onSurfaceVariant,

            modifier =
                Modifier.weight(0.6f)
        )
    }
}


// ================================================================
// Bullet
// ================================================================

@Composable
private fun BulletItem(
    title: String,
    description: String
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),

        verticalAlignment =
            Alignment.Top
    ) {

        Text(

            text = "•",

            style =
                MaterialTheme.typography.bodyMedium,

            fontWeight =
                FontWeight.Bold,

            color =
                MaterialTheme.colorScheme.primary,

            modifier =
                Modifier.padding(end = 8.dp)
        )


        Column {

            Text(

                text = title,

                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontWeight =
                            FontWeight.SemiBold
                    ),

                color =
                    MaterialTheme.colorScheme.onSurface
            )


            Text(

                text = description,

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


// ================================================================
// 编号项目
// ================================================================

@Composable
private fun NumberedItem(
    number: Int,
    title: String,
    description: String
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),

        verticalAlignment =
            Alignment.Top
    ) {

        Surface(

            modifier =
                Modifier.size(24.dp),

            shape =
                RoundedCornerShape(6.dp),

            color =
                MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.14f
                )
        ) {

            Box(
                contentAlignment =
                    Alignment.Center
            ) {

                Text(

                    text = number.toString(),

                    style =
                        MaterialTheme.typography.labelSmall,

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        MaterialTheme.colorScheme.primary
                )
            }
        }


        Spacer(
            modifier = Modifier.width(10.dp)
        )


        Column {

            Text(

                text = title,

                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontWeight =
                            FontWeight.SemiBold
                    ),

                color =
                    MaterialTheme.colorScheme.onSurface
            )


            Text(

                text = description,

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


// ================================================================
// FAQ
// ================================================================

@Composable
private fun QnA(
    question: String,
    answer: String
) {

    Column(
        modifier =
            Modifier.padding(vertical = 4.dp)
    ) {

        Text(

            text = question,

            style =
                MaterialTheme.typography.bodyMedium.copy(
                    fontWeight =
                        FontWeight.SemiBold
                ),

            color =
                MaterialTheme.colorScheme.onSurface
        )


        Text(

            text = answer,

            style =
                MaterialTheme.typography.bodyMedium,

            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


// ================================================================
// 小标题
// ================================================================

@Composable
private fun SectionHeading(
    text: String
) {

    Text(

        text = text,

        style =
            MaterialTheme.typography.titleMedium,

        color =
            MaterialTheme.colorScheme.onSurface
    )
}


// ================================================================
// Section 间距
// ================================================================

@Composable
private fun SectionSpacer() {

    Spacer(
        modifier =
            Modifier.height(8.dp)
    )
}


// ================================================================
// 免责声明标题
// ================================================================

@Composable
private fun SectionTitle(
    text: String
) {

    Text(

        text = text,

        style =
            MaterialTheme.typography.titleMedium,

        fontWeight =
            FontWeight.SemiBold,

        color =
            MaterialTheme.colorScheme.error
    )
}


// ================================================================
// 免责声明正文
// ================================================================

@Composable
private fun DisclaimerText(
    text: String
) {

    Text(

        text = text,

        style =
            MaterialTheme.typography.bodyMedium,

        color =
            MaterialTheme.colorScheme.onSurfaceVariant
    )
}


// ================================================================
// 免责声明 Bullet
// ================================================================

@Composable
private fun BulletText(
    text: String
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 4.dp,
                top = 2.dp,
                bottom = 2.dp
            ),

        verticalAlignment =
            Alignment.Top
    ) {

        Text(

            text = "•",

            style =
                MaterialTheme.typography.bodyMedium,

            color =
                MaterialTheme.colorScheme.error,

            modifier =
                Modifier.padding(end = 8.dp)
        )


        Text(

            text = text,

            style =
                MaterialTheme.typography.bodyMedium,

            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


// ================================================================
// 免责声明分隔线
// ================================================================

@Composable
private fun DisclaimerDivider() {

    HorizontalDivider(

        modifier =
            Modifier.padding(
                vertical = 4.dp
            ),

        color =
            MaterialTheme.colorScheme.outline.copy(
                alpha = 0.5f
            )
    )
}