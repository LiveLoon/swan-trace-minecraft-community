package com.example.swantracemc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.swantracemc.data.TopEntry


@Composable
fun TopEntryItem(
    entry: TopEntry,
    rank: Int
) {
    val isTopThree = rank in 1..3

    val accentColor = when (rank) {
        1 -> MaterialTheme.colorScheme.primary
        2 -> MaterialTheme.colorScheme.secondary
        3 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.outline
    }

    val cardColor = if (isTopThree) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.surface
    }

    val borderColor = if (isTopThree) {
        accentColor.copy(alpha = 0.45f)
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 5.dp
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isTopThree) 2.dp else 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 14.dp,
                    vertical = 13.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ====================================================
            // 排名
            // ====================================================

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        accentColor.copy(
                            alpha = if (isTopThree) {
                                0.14f
                            } else {
                                0.08f
                            }
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "#$rank",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isTopThree) {
                        accentColor
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            // ====================================================
            // 前三名图标
            // ====================================================

            if (isTopThree) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "第 $rank 名",
                    tint = accentColor,
                    modifier = Modifier.size(25.dp)
                )

                Spacer(
                    modifier = Modifier.width(10.dp)
                )
            }

            // ====================================================
            // 玩家信息
            // ====================================================

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = entry.name ?: "Unknown",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = shortenUuid(entry.uuid),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            // ====================================================
            // 数据
            // ====================================================

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = entry.value_human,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isTopThree) {
                        accentColor
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )

                if (isTopThree) {
                    Text(
                        text = "TOP $rank",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}


private fun shortenUuid(
    uuid: String
): String {
    return if (uuid.length > 14) {
        "${uuid.take(8)}...${uuid.takeLast(6)}"
    } else {
        uuid
    }
}