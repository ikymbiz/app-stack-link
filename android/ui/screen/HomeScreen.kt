package com.stack.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stack.app.model.LinkItem
import com.stack.app.model.SampleLinks
import com.stack.app.ui.components.LinkCard
import com.stack.app.ui.theme.*

/* -------------------------------------------------------------------------- */
/* HomeScreen                                                                  */
/* -------------------------------------------------------------------------- */

data class FilterChipModel(val label: String, val count: Int? = null, val selected: Boolean = false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    items: List<LinkItem> = SampleLinks.items,
    onAddClick: () -> Unit = {},
) {
    var activeFilter by remember { mutableStateOf("すべて") }

    val chips = remember(items) {
        listOf(
            FilterChipModel("すべて", items.size),
            FilterChipModel("未読", items.count { !it.isRead }),
            FilterChipModel("⭐ あとで読む"),
            FilterChipModel("#AI"),
            FilterChipModel("#デザイン"),
            FilterChipModel("#プロダクト"),
        )
    }

    Scaffold(
        containerColor = BgApp,
        bottomBar = { BottomNav() },
        floatingActionButton = { AddFab(onAddClick) },
        floatingActionButtonPosition = FabPosition.End,
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(top = 0.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            item { TopHeader() }
            item { StatsRow(total = items.size, unread = items.count { !it.isRead }, weekly = 8) }
            item {
                FilterChips(
                    chips = chips.map { it.copy(selected = it.label == activeFilter) },
                    onSelect = { activeFilter = it },
                )
            }
            item { Spacer(Modifier.height(4.dp)) }
            items(items, key = { it.id }) { link ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    LinkCard(
                        item = link,
                        onClick = { /* TODO: 詳細画面へ遷移 */ },
                        onFavoriteToggle = { /* TODO: ViewModel経由でstate更新 */ },
                    )
                }
            }
        }
    }
}

/* -------------------------------------------------------------------------- */
/* Top Header                                                                  */
/* -------------------------------------------------------------------------- */

@Composable
private fun TopHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // ブランドマーク
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Ink),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                "S",
                color = Accent,
                style = AppTypography.headlineMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
            )
        }
        Spacer(Modifier.width(8.dp))
        Text(
            "Stack",
            style = AppTypography.headlineMedium,
            color = Ink,
        )
        Spacer(Modifier.weight(1f))
        IconButtonOutlined(label = "🔍") { /* TODO */ }
        Spacer(Modifier.width(6.dp))
        IconButtonOutlined(label = "≡") { /* TODO */ }
    }
}

@Composable
private fun IconButtonOutlined(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(BgCard)
            .border(1.dp, Line, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, color = Ink2, fontSize = 16.sp)
    }
}

/* -------------------------------------------------------------------------- */
/* Stats Row                                                                   */
/* -------------------------------------------------------------------------- */

@Composable
private fun StatsRow(total: Int, unread: Int, weekly: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                "あなたの本棚",
                style = AppTypography.displayLarge,
                color = Ink,
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Accent)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    "${total}件 ・ 未読 $unread ・ 今週 +$weekly",
                    style = AppTypography.labelMedium,
                    color = Ink3,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
        SortButton()
    }
}

@Composable
private fun SortButton() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(BgCard)
            .border(1.dp, Line, RoundedCornerShape(100.dp))
            .clickable { /* TODO */ }
            .padding(horizontal = 12.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            "発信日順 ▾",
            style = AppTypography.labelLarge,
            color = Ink2,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

/* -------------------------------------------------------------------------- */
/* Filter Chips                                                                */
/* -------------------------------------------------------------------------- */

@Composable
private fun FilterChips(
    chips: List<FilterChipModel>,
    onSelect: (String) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items(chips, key = { it.label }) { chip ->
            FilterChipItem(chip = chip, onClick = { onSelect(chip.label) })
        }
    }
}

@Composable
private fun FilterChipItem(chip: FilterChipModel, onClick: () -> Unit) {
    val bg = if (chip.selected) Ink else BgCard
    val fg = if (chip.selected) BgCard else Ink2
    val borderColor = if (chip.selected) Ink else Line

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(bg)
            .border(1.dp, borderColor, RoundedCornerShape(100.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            chip.label,
            style = AppTypography.labelLarge,
            color = fg,
            fontWeight = FontWeight.SemiBold,
        )
        chip.count?.let {
            Spacer(Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(if (chip.selected) Color.White.copy(alpha = 0.15f) else BgApp)
                    .padding(horizontal = 6.dp, vertical = 1.dp),
            ) {
                Text(
                    it.toString(),
                    style = AppTypography.labelSmall,
                    color = if (chip.selected) BgCard else Muted,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

/* -------------------------------------------------------------------------- */
/* Bottom Nav                                                                  */
/* -------------------------------------------------------------------------- */

@Composable
private fun BottomNav() {
    Surface(
        color = BgCard.copy(alpha = 0.92f),
        tonalElevation = 0.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Line, RoundedCornerShape(0.dp)) // 上線
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            NavItem(label = "ホーム",  emoji = "🏠", active = true)
            NavItem(label = "RSS",    emoji = "📡", active = false)
            NavItem(label = "既読",   emoji = "✓",  active = false)
            NavItem(label = "設定",   emoji = "⚙",  active = false)
        }
    }
}

@Composable
private fun NavItem(label: String, emoji: String, active: Boolean) {
    val color = if (active) Ink else Muted
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { /* TODO: ナビゲーション */ },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(emoji, fontSize = 20.sp, color = color)
        Spacer(Modifier.height(4.dp))
        Text(
            label,
            color = color,
            style = AppTypography.labelSmall.copy(fontSize = 10.5.sp),
            fontWeight = FontWeight.SemiBold,
        )
    }
}

/* -------------------------------------------------------------------------- */
/* FAB                                                                         */
/* -------------------------------------------------------------------------- */

@Composable
private fun AddFab(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(end = 4.dp, bottom = 4.dp)
            .size(56.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Brush.linearGradient(listOf(Accent, AccentSoft)))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text("+", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
    }
}
