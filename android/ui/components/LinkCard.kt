package com.stack.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stack.app.model.LinkItem
import com.stack.app.model.LinkType
import com.stack.app.ui.theme.*
import java.time.format.DateTimeFormatter

private val DateFmt = DateTimeFormatter.ofPattern("yyyy.MM.dd")

/** ホーム一覧で使うカード */
@Composable
fun LinkCard(
    item: LinkItem,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (item.isRead) 0.68f else 1f),
        shape = RoundedCornerShape(16.dp),
        color = BgCard,
        border = androidx.compose.foundation.BorderStroke(1.dp, Line2),
        onClick = onClick,
    ) {
        Column {
            ThumbnailArea(item)
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                MetaRow(item)
                Spacer(Modifier.height(8.dp))
                Text(
                    text = item.title,
                    style = AppTypography.titleLarge.copy(
                        fontWeight = if (item.isRead) FontWeight.SemiBold else FontWeight.Bold
                    ),
                    color = Ink,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = item.summary,
                    style = AppTypography.bodyMedium,
                    color = Ink3,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(12.dp))
                FooterRow(item, onFavoriteToggle)
            }
        }
    }
}

@Composable
private fun ThumbnailArea(item: LinkItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .background(thumbnailBrush(item))
    ) {
        // 種別バッジ
        TypeBadge(
            type = item.type,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
        )
        // 未読バッジ
        if (!item.isRead) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Accent)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "NEW",
                    style = AppTypography.labelSmall,
                    color = BgCard,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        // TODO: thumbnailUrl がある場合は AsyncImage に差し替える
    }
}

@Composable
private fun TypeBadge(type: LinkType, modifier: Modifier = Modifier) {
    val dotColor = when (type) {
        LinkType.NEWS -> TypeNews
        LinkType.SNS  -> TypeSns
        LinkType.RSS  -> TypeRss
    }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(BgCard.copy(alpha = 0.95f))
            .padding(horizontal = 9.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(dotColor)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = type.label,
            style = AppTypography.labelSmall,
            color = Ink,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun MetaRow(item: LinkItem) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = item.source,
            style = AppTypography.labelMedium,
            color = Ink2,
            fontWeight = FontWeight.SemiBold,
        )
        DotSep()
        Text(
            text = item.publishedAt.format(DateFmt),
            style = AppTypography.labelSmall,
            color = Muted,
            fontSize = 11.sp,
        )
        item.readingMinutes?.let {
            DotSep()
            Text(
                text = "$it min",
                style = AppTypography.labelSmall,
                color = Muted,
                fontSize = 11.sp,
            )
        }
    }
}

@Composable
private fun DotSep() {
    Text(
        text = " · ",
        color = Line,
        style = AppTypography.labelSmall,
    )
}

@Composable
private fun FooterRow(item: LinkItem, onFavoriteToggle: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            item.tags.take(3).forEach { TagPill(it) }
        }
        FavoriteButton(active = item.isFavorite, onClick = onFavoriteToggle)
    }
}

@Composable
private fun TagPill(label: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(BgApp)
            .padding(horizontal = 9.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(Accent)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = label,
            style = AppTypography.labelMedium.copy(fontSize = 11.sp),
            color = Ink3,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun FavoriteButton(active: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickableNoRipple(onClick),
        contentAlignment = Alignment.Center,
    ) {
        // 簡易: ★ 文字で代用。プロダクションでは Icon(painterResource(...)) に差し替え。
        Text(
            text = if (active) "★" else "☆",
            color = if (active) Gold else Muted,
            fontSize = 18.sp,
        )
    }
}

/** デコ用のグラデーション (画像ロード失敗・未取得時のフォールバック) */
private fun thumbnailBrush(item: LinkItem): Brush {
    // id末尾を元にバリエーション
    val variant = (item.id.toIntOrNull() ?: 0) % 5
    return when (variant) {
        0 -> Brush.linearGradient(listOf(Color(0xFF0A0A0A), Color(0xFF2D2D2D), Color(0xFFFF4D2D)))
        1 -> Brush.linearGradient(listOf(Color(0xFF2D5BFF), Color(0xFF6F8BFF), Color(0xFFB8C9FF)))
        2 -> Brush.linearGradient(listOf(Color(0xFF16A085), Color(0xFF2BC4A0), Color(0xFF8FE6CD)))
        3 -> Brush.linearGradient(listOf(Color(0xFFFF4D2D), Color(0xFFFFB347), Color(0xFFFFE0B2)))
        else -> Brush.linearGradient(listOf(Color(0xFF1A1A1A), Color(0xFF4A4A4A), Color(0xFF7A7A7A)))
    }
}

/** リップル無しのクリック (細かいボタン向け) */
@Composable
private fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier {
    return this.then(
        androidx.compose.foundation.clickable(
            indication = null,
            interactionSource = androidx.compose.runtime.remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
            onClick = onClick,
        )
    )
}
