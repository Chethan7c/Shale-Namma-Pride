// Shale-Namma Pride - Jetpack Compose Design Tokens & Theme
// File: app/src/main/java/com/shalenamma/pride/ui/theme/DesignTokens.kt

package com.shalenamma.pride.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

// ============================================================================
// COLOR PALETTE
// ============================================================================

object Colors {
    // Primary Colors
    val TrustBlue = Color(0xFF1F5AA0)
    val SuccessGreen = Color(0xFF4CAF50)
    val WarningOrange = Color(0xFFFF9800)

    // Neutral Colors
    val White = Color(0xFFFFFFFF)
    val LightGray = Color(0xFFF5F5F5)
    val LightGrayBorder = Color(0xFFE0E0E0)
    val MediumGray = Color(0xFF9E9E9E)
    val DarkGray = Color(0xFF333333)
    val DarkCharcoal = Color(0xFF212121)

    // Status Colors
    val ErrorRed = Color(0xFFF44336)
    val WarningAmber = Color(0xFFFF9800)
    val InfoBlue = Color(0xFF2196F3)

    // Transparent variants
    val RippleColor = TrustBlue.copy(alpha = 0.24f)
    val DisabledAlpha = Color.Black.copy(alpha = 0.38f)
    val ScrimColor = Color.Black.copy(alpha = 0.32f)
}

// ============================================================================
// MATERIAL 3 COLOR SCHEME
// ============================================================================

val LightColorScheme = lightColorScheme(
    primary = Colors.TrustBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDEE9FF),
    onPrimaryContainer = Color(0xFF001A4D),

    secondary = Colors.SuccessGreen,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFC8E6C9),
    onSecondaryContainer = Color(0xFF1B5E20),

    tertiary = Colors.WarningOrange,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFE0B2),
    onTertiaryContainer = Color(0xFF663D00),

    background = Color.White,
    onBackground = Colors.DarkCharcoal,

    surface = Color.White,
    onSurface = Colors.DarkCharcoal,
    surfaceVariant = Colors.LightGray,
    onSurfaceVariant = Colors.MediumGray,

    outline = Colors.LightGrayBorder,
    outlineVariant = Colors.MediumGray,

    error = Colors.ErrorRed,
    onError = Color.White,
    errorContainer = Color(0xFFFDE8E8),
    onErrorContainer = Color(0xFF8B0000),

    scrim = Colors.ScrimColor
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFB8D5FF),
    onPrimary = Color(0xFF003366),
    primaryContainer = Color(0xFF0D4099),
    onPrimaryContainer = Color(0xFFDEE9FF),

    secondary = Color(0xFFA8D5A8),
    onSecondary = Color(0xFF003300),
    secondaryContainer = Color(0xFF2E7D32),
    onSecondaryContainer = Color(0xFFC8E6C9),

    tertiary = Color(0xFFFFD699),
    onTertiary = Color(0xFF331A00),
    tertiaryContainer = Color(0xFF994D00),
    onTertiaryContainer = Color(0xFFFFE0B2),

    background = Color(0xFF121212),
    onBackground = Color.White,

    surface = Color(0xFF121212),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF383838),
    onSurfaceVariant = Color(0xFFC4C7C5),

    outline = Color(0xFF8E918F),
    outlineVariant = Color(0xFF4A4D4B),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF660005),
    errorContainer = Color(0xFF930006),
    onErrorContainer = Color(0xFFFFDAD6),

    scrim = Colors.ScrimColor
)

// ============================================================================
// TYPOGRAPHY
// ============================================================================

object TypographyTokens {
    // Type Sizes
    val H1Size = 32.sp
    val H1LineHeight = 40.sp

    val H2Size = 28.sp
    val H2LineHeight = 36.sp

    val H3Size = 24.sp
    val H3LineHeight = 32.sp

    val H4Size = 20.sp
    val H4LineHeight = 28.sp

    val Body1Size = 16.sp
    val Body1LineHeight = 24.sp

    val Body2Size = 14.sp
    val Body2LineHeight = 20.sp

    val ButtonSize = 14.sp
    val ButtonLineHeight = 20.sp

    val CaptionSize = 12.sp
    val CaptionLineHeight = 16.sp
}

val ShaleNammaTypography = Typography(
    displayLarge = TextStyle(
        fontSize = TypographyTokens.H1Size,
        fontWeight = FontWeight.Bold,
        lineHeight = TypographyTokens.H1LineHeight,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontSize = TypographyTokens.H2Size,
        fontWeight = FontWeight.Bold,
        lineHeight = TypographyTokens.H2LineHeight,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontSize = TypographyTokens.H3Size,
        fontWeight = FontWeight.SemiBold,
        lineHeight = TypographyTokens.H3LineHeight,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontSize = TypographyTokens.H4Size,
        fontWeight = FontWeight.SemiBold,
        lineHeight = TypographyTokens.H4LineHeight,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontSize = TypographyTokens.Body1Size,
        fontWeight = FontWeight.Normal,
        lineHeight = TypographyTokens.Body1LineHeight,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontSize = TypographyTokens.Body2Size,
        fontWeight = FontWeight.Normal,
        lineHeight = TypographyTokens.Body2LineHeight,
        letterSpacing = 0.25.sp
    ),
    labelLarge = TextStyle(
        fontSize = TypographyTokens.ButtonSize,
        fontWeight = FontWeight.SemiBold,
        lineHeight = TypographyTokens.ButtonLineHeight,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontSize = TypographyTokens.CaptionSize,
        fontWeight = FontWeight.Normal,
        lineHeight = TypographyTokens.CaptionLineHeight,
        letterSpacing = 0.sp
    )
)

// ============================================================================
// SPACING (8dp GRID SYSTEM)
// ============================================================================

object Spacing {
    val xs = 4.dp      // Icon spacing, tight layouts
    val sm = 8.dp      // Component padding, small gaps
    val md = 16.dp     // Card padding, section gaps
    val lg = 24.dp     // Major section spacing
    val xl = 32.dp     // Screen-level spacing
    val xxl = 48.dp    // Large layout sections
}

object LayoutDimensions {
    val ScreenPaddingHorizontal = Spacing.md    // 16dp
    val ScreenPaddingVertical = Spacing.md       // 16dp
    val SafeAreaTop = 24.dp                      // Status bar
    val SafeAreaBottom = 56.dp                   // Nav bar

    // Component heights
    val ButtonHeight = 48.dp
    val InputFieldHeight = 56.dp
    val TopAppBarHeight = 56.dp
    val BottomNavHeight = 56.dp
    val CardElevation = 2.dp

    // Card spacing
    val CardPadding = Spacing.md
    val CardMargin = Spacing.sm
    val CardCornerRadius = 12.dp

    // Touch target
    val MinTouchTarget = 48.dp
    val MinTouchTargetSpacing = Spacing.sm

    // Icons
    val IconSmall = 16.dp
    val IconStandard = 24.dp
    val IconLarge = 32.dp
    val IconXL = 48.dp
}

// ============================================================================
// SHALE NAMMA THEME
// ============================================================================

@Composable
fun ShaleNammaPrideTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ShaleNammaTypography,
        content = content
    )
}
