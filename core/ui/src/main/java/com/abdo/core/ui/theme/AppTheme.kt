package com.abdo.core.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

enum class AppTheme(
    val id: Int,
    val nameAr: String,
    val emoji: String,
    val seedColor: Color
) {
    OCEAN_BLUE(0, "أزرق محيطي", "🌊", Color(0xFF1565C0)),
    FOREST_GREEN(1, "أخضر طبيعي", "🌿", Color(0xFF2E7D32)),
    SOFT_ROSE(2, "وردي ناعم", "🌸", Color(0xFFC2185B)),
    WARM_GRAY(3, "رمادي دافئ", "🪨", Color(0xFF546E7A)),
    DYNAMIC(4, "مع الموبايل", "📱", Color(0xFF6750A4))
}

fun getColorScheme(theme: AppTheme, darkMode: Boolean): ColorScheme {
    return when (theme) {
        AppTheme.OCEAN_BLUE -> if (darkMode) darkBlueScheme else lightBlueScheme
        AppTheme.FOREST_GREEN -> if (darkMode) darkGreenScheme else lightGreenScheme
        AppTheme.SOFT_ROSE -> if (darkMode) darkRoseScheme else lightRoseScheme
        AppTheme.WARM_GRAY -> if (darkMode) darkGrayScheme else lightGrayScheme
        AppTheme.DYNAMIC -> if (darkMode) darkBlueScheme else lightBlueScheme
    }
}

// 🌊 Ocean Blue
private val lightBlueScheme = lightColorScheme(
    primary = Color(0xFF1565C0),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD6E4FF),
    onPrimaryContainer = Color(0xFF001847),
    secondary = Color(0xFF0097A7),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFB2EBF2),
    onSecondaryContainer = Color(0xFF001F24),
    background = Color(0xFFF8FAFF),
    onBackground = Color(0xFF1A1C2A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1C2A),
    surfaceVariant = Color(0xFFEEF2FF),
    error = Color(0xFFBA1A1A)
)
private val darkBlueScheme = darkColorScheme(
    primary = Color(0xFF9DBDFF),
    onPrimary = Color(0xFF002E6E),
    primaryContainer = Color(0xFF1565C0),
    onPrimaryContainer = Color(0xFFD6E4FF),
    secondary = Color(0xFF80DEEA),
    onSecondary = Color(0xFF00363D),
    secondaryContainer = Color(0xFF004F58),
    onSecondaryContainer = Color(0xFFB2EBF2),
    background = Color(0xFF0D1117),
    onBackground = Color(0xFFE2E8FF),
    surface = Color(0xFF161B22),
    onSurface = Color(0xFFE2E8FF),
    surfaceVariant = Color(0xFF1E2433),
    error = Color(0xFFFFB4AB)
)

// 🌿 Forest Green
private val lightGreenScheme = lightColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB8F0BC),
    onPrimaryContainer = Color(0xFF002108),
    secondary = Color(0xFF558B2F),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCCFF90),
    onSecondaryContainer = Color(0xFF102000),
    background = Color(0xFFF6FBF4),
    onBackground = Color(0xFF1A1C1A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1C1A),
    surfaceVariant = Color(0xFFEDF7ED),
    error = Color(0xFFBA1A1A)
)
private val darkGreenScheme = darkColorScheme(
    primary = Color(0xFF8BC34A),
    onPrimary = Color(0xFF1B3700),
    primaryContainer = Color(0xFF2E7D32),
    onPrimaryContainer = Color(0xFFB8F0BC),
    secondary = Color(0xFFAED581),
    onSecondary = Color(0xFF223600),
    secondaryContainer = Color(0xFF33691E),
    onSecondaryContainer = Color(0xFFCCFF90),
    background = Color(0xFF0D120D),
    onBackground = Color(0xFFDCEDDC),
    surface = Color(0xFF141A14),
    onSurface = Color(0xFFDCEDDC),
    surfaceVariant = Color(0xFF1A231A),
    error = Color(0xFFFFB4AB)
)

// 🌸 Soft Rose
private val lightRoseScheme = lightColorScheme(
    primary = Color(0xFFC2185B),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFD9E3),
    onPrimaryContainer = Color(0xFF3E001D),
    secondary = Color(0xFFAD1457),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFD6E4),
    onSecondaryContainer = Color(0xFF3D001B),
    background = Color(0xFFFFF8F9),
    onBackground = Color(0xFF201A1B),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF201A1B),
    surfaceVariant = Color(0xFFFFF0F3),
    error = Color(0xFFBA1A1A)
)
private val darkRoseScheme = darkColorScheme(
    primary = Color(0xFFFFB0C8),
    onPrimary = Color(0xFF650030),
    primaryContainer = Color(0xFFC2185B),
    onPrimaryContainer = Color(0xFFFFD9E3),
    secondary = Color(0xFFFFB3C8),
    onSecondary = Color(0xFF5E1132),
    secondaryContainer = Color(0xFF7D2949),
    onSecondaryContainer = Color(0xFFFFD6E4),
    background = Color(0xFF120D0E),
    onBackground = Color(0xFFEDD9DC),
    surface = Color(0xFF1A1214),
    onSurface = Color(0xFFEDD9DC),
    surfaceVariant = Color(0xFF251519),
    error = Color(0xFFFFB4AB)
)

// 🪨 Warm Gray
private val lightGrayScheme = lightColorScheme(
    primary = Color(0xFF546E7A),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFCFE4EC),
    onPrimaryContainer = Color(0xFF071E26),
    secondary = Color(0xFF607D8B),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD6E8F0),
    onSecondaryContainer = Color(0xFF0D1F27),
    background = Color(0xFFF7F9FA),
    onBackground = Color(0xFF191C1D),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF191C1D),
    surfaceVariant = Color(0xFFEDF1F3),
    error = Color(0xFFBA1A1A)
)
private val darkGrayScheme = darkColorScheme(
    primary = Color(0xFFB0CBD4),
    onPrimary = Color(0xFF1B3540),
    primaryContainer = Color(0xFF546E7A),
    onPrimaryContainer = Color(0xFFCFE4EC),
    secondary = Color(0xFFB2CDD8),
    onSecondary = Color(0xFF1E3540),
    secondaryContainer = Color(0xFF3D5560),
    onSecondaryContainer = Color(0xFFD6E8F0),
    background = Color(0xFF0E1214),
    onBackground = Color(0xFFDDE3E6),
    surface = Color(0xFF161A1C),
    onSurface = Color(0xFFDDE3E6),
    surfaceVariant = Color(0xFF1E2528),
    error = Color(0xFFFFB4AB)
)
