package com.stack.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Ink,
    onPrimary = BgCard,
    secondary = Accent,
    onSecondary = BgCard,
    background = BgApp,
    onBackground = Ink,
    surface = BgCard,
    onSurface = Ink,
    surfaceVariant = Line2,
    onSurfaceVariant = Ink3,
    outline = Line,
)

@Composable
fun StackTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        content = content,
    )
}
