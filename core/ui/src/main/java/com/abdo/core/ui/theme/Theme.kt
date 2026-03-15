package com.abdo.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ProductionManagerTheme(
    themeId: Int = 0,
    darkMode: Boolean = false,
    content: @Composable () -> Unit
) {
    val theme = AppTheme.entries.find { it.id == themeId } ?: AppTheme.OCEAN_BLUE
    val colorScheme = getColorScheme(theme, darkMode)
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
