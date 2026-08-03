package com.example.design_system.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.model.ThemeType

val SunRiseColorScheme = lightColorScheme(
    primary = primarySunRise,
    onPrimary = onPrimarySunRise,
    primaryContainer = primaryContainerSunRise,
    onPrimaryContainer = onPrimaryContainerSunRise,
    inversePrimary = inversePrimarySunRise,
    secondary = secondarySunRise,
    onSecondary = onSecondarySunRise,
    secondaryContainer = secondaryContainerSunRise,
    onSecondaryContainer = onSecondaryContainerSunRise,
    tertiary = tertiarySunRise,
    onTertiary = onTertiarySunRise,
    tertiaryContainer = tertiaryContainerSunRise,
    onTertiaryContainer = onTertiaryContainerSunRise,
    background = backgroundSunRise,
    onBackground = onBackgroundSunRise,
    surface = surfaceSunRise,
    onSurface = onSurfaceSunRise,
    surfaceVariant = surfaceVariantSunRise,
    onSurfaceVariant = onSurfaceVariantSunRise,
    inverseSurface = inverseSurfaceSunRise,
    inverseOnSurface = inverseOnSurfaceSunRise,
    error = errorSunRise,
    onError = onErrorSunRise,
    errorContainer = errorContainerSunRise,
    onErrorContainer = onErrorContainerSunRise,
    outline = outlineSunRise,
    outlineVariant = outlineVariantSunRise,
    scrim = scrimSunRise,
    surfaceBright = surfaceBrightSunRise,
    surfaceContainer = surfaceContainerSunRise,
    surfaceContainerHigh = surfaceContainerHighSunRise,
    surfaceContainerHighest = surfaceContainerHighestSunRise,
    surfaceContainerLow = surfaceContainerLowSunRise,
    surfaceContainerLowest = surfaceContainerLowestSunRise,
    surfaceDim = surfaceDimSunRise
)

val SkyBlueColorScheme = lightColorScheme(
    primary = primarySkyBlue,
    onPrimary = onPrimarySkyBlue,
    primaryContainer = primaryContainerSkyBlue,
    onPrimaryContainer = onPrimaryContainerSkyBlue,
    inversePrimary = inversePrimarySkyBlue,
    secondary = secondarySkyBlue,
    onSecondary = onSecondarySkyBlue,
    secondaryContainer = secondaryContainerSkyBlue,
    onSecondaryContainer = onSecondaryContainerSkyBlue,
    tertiary = tertiarySkyBlue,
    onTertiary = onTertiarySkyBlue,
    tertiaryContainer = tertiaryContainerSkyBlue,
    onTertiaryContainer = onTertiaryContainerSkyBlue,
    background = backgroundSkyBlue,
    onBackground = onBackgroundSkyBlue,
    surface = surfaceSkyBlue,
    onSurface = onSurfaceSkyBlue,
    surfaceVariant = surfaceVariantSkyBlue,
    onSurfaceVariant = onSurfaceVariantSkyBlue,
    inverseSurface = inverseSurfaceSkyBlue,
    inverseOnSurface = inverseOnSurfaceSkyBlue,
    error = errorSkyBlue,
    onError = onErrorSkyBlue,
    errorContainer = errorContainerSkyBlue,
    onErrorContainer = onErrorContainerSkyBlue,
    outline = outlineSkyBlue,
    outlineVariant = outlineVariantSkyBlue,
    scrim = scrimSkyBlue,
    surfaceBright = surfaceBrightSkyBlue,
    surfaceContainer = surfaceContainerSkyBlue,
    surfaceContainerHigh = surfaceContainerHighSkyBlue,
    surfaceContainerHighest = surfaceContainerHighestSkyBlue,
    surfaceContainerLow = surfaceContainerLowSkyBlue,
    surfaceContainerLowest = surfaceContainerLowestSkyBlue,
    surfaceDim = surfaceDimSkyBlue
)

val MistGrayColorScheme = lightColorScheme(
    primary = primaryMistGray,
    onPrimary = onPrimaryMistGray,
    primaryContainer = primaryContainerMistGray,
    onPrimaryContainer = onPrimaryContainerMistGray,
    inversePrimary = inversePrimaryMistGray,
    secondary = secondaryMistGray,
    onSecondary = onSecondaryMistGray,
    secondaryContainer = secondaryContainerMistGray,
    onSecondaryContainer = onSecondaryContainerMistGray,
    tertiary = tertiaryMistGray,
    onTertiary = onTertiaryMistGray,
    tertiaryContainer = tertiaryContainerMistGray,
    onTertiaryContainer = onTertiaryContainerMistGray,
    background = backgroundMistGray,
    onBackground = onBackgroundMistGray,
    surface = surfaceMistGray,
    onSurface = onSurfaceMistGray,
    surfaceVariant = surfaceVariantMistGray,
    onSurfaceVariant = onSurfaceVariantMistGray,
    inverseSurface = inverseSurfaceMistGray,
    inverseOnSurface = inverseOnSurfaceMistGray,
    error = errorMistGray,
    onError = onErrorMistGray,
    errorContainer = errorContainerMistGray,
    onErrorContainer = onErrorContainerMistGray,
    outline = outlineMistGray,
    outlineVariant = outlineVariantMistGray,
    scrim = scrimMistGray,
    surfaceBright = surfaceBrightMistGray,
    surfaceContainer = surfaceContainerMistGray,
    surfaceContainerHigh = surfaceContainerHighMistGray,
    surfaceContainerHighest = surfaceContainerHighestMistGray,
    surfaceContainerLow = surfaceContainerLowMistGray,
    surfaceContainerLowest = surfaceContainerLowestMistGray,
    surfaceDim = surfaceDimMistGray
)

val MidnightBlueColorScheme = darkColorScheme(
    primary = primaryMidnightBlue,
    onPrimary = onPrimaryMidnightBlue,
    primaryContainer = primaryContainerMidnightBlue,
    onPrimaryContainer = onPrimaryContainerMidnightBlue,
    inversePrimary = inversePrimaryMidnightBlue,
    secondary = secondaryMidnightBlue,
    onSecondary = onSecondaryMidnightBlue,
    secondaryContainer = secondaryContainerMidnightBlue,
    onSecondaryContainer = onSecondaryContainerMidnightBlue,
    tertiary = tertiaryMidnightBlue,
    onTertiary = onTertiaryMidnightBlue,
    tertiaryContainer = tertiaryContainerMidnightBlue,
    onTertiaryContainer = onTertiaryContainerMidnightBlue,
    background = backgroundMidnightBlue,
    onBackground = onBackgroundMidnightBlue,
    surface = surfaceMidnightBlue,
    onSurface = onSurfaceMidnightBlue,
    surfaceVariant = surfaceVariantMidnightBlue,
    onSurfaceVariant = onSurfaceVariantMidnightBlue,
    inverseSurface = inverseSurfaceMidnightBlue,
    inverseOnSurface = inverseOnSurfaceMidnightBlue,
    error = errorMidnightBlue,
    onError = onErrorMidnightBlue,
    errorContainer = errorContainerMidnightBlue,
    onErrorContainer = onErrorContainerMidnightBlue,
    outline = outlineMidnightBlue,
    outlineVariant = outlineVariantMidnightBlue,
    scrim = scrimMidnightBlue,
    surfaceBright = surfaceBrightMidnightBlue,
    surfaceContainer = surfaceContainerMidnightBlue,
    surfaceContainerHigh = surfaceContainerHighMidnightBlue,
    surfaceContainerHighest = surfaceContainerHighestMidnightBlue,
    surfaceContainerLow = surfaceContainerLowMidnightBlue,
    surfaceContainerLowest = surfaceContainerLowestMidnightBlue,
    surfaceDim = surfaceDimMidnightBlue
)

val CharcoalBlackColorScheme = darkColorScheme(
    primary = primaryCharcoalBlack,
    onPrimary = onPrimaryCharcoalBlack,
    primaryContainer = primaryContainerCharcoalBlack,
    onPrimaryContainer = onPrimaryContainerCharcoalBlack,
    inversePrimary = inversePrimaryCharcoalBlack,
    secondary = secondaryCharcoalBlack,
    onSecondary = onSecondaryCharcoalBlack,
    secondaryContainer = secondaryContainerCharcoalBlack,
    onSecondaryContainer = onSecondaryContainerCharcoalBlack,
    tertiary = tertiaryCharcoalBlack,
    onTertiary = onTertiaryCharcoalBlack,
    tertiaryContainer = tertiaryContainerCharcoalBlack,
    onTertiaryContainer = onTertiaryContainerCharcoalBlack,
    background = backgroundCharcoalBlack,
    onBackground = onBackgroundCharcoalBlack,
    surface = surfaceCharcoalBlack,
    onSurface = onSurfaceCharcoalBlack,
    surfaceVariant = surfaceVariantCharcoalBlack,
    onSurfaceVariant = onSurfaceVariantCharcoalBlack,
    inverseSurface = inverseSurfaceCharcoalBlack,
    inverseOnSurface = inverseOnSurfaceCharcoalBlack,
    error = errorCharcoalBlack,
    onError = onErrorCharcoalBlack,
    errorContainer = errorContainerCharcoalBlack,
    onErrorContainer = onErrorContainerCharcoalBlack,
    outline = outlineCharcoalBlack,
    outlineVariant = outlineVariantCharcoalBlack,
    scrim = scrimCharcoalBlack,
    surfaceBright = surfaceBrightCharcoalBlack,
    surfaceContainer = surfaceContainerCharcoalBlack,
    surfaceContainerHigh = surfaceContainerHighCharcoalBlack,
    surfaceContainerHighest = surfaceContainerHighestCharcoalBlack,
    surfaceContainerLow = surfaceContainerLowCharcoalBlack,
    surfaceContainerLowest = surfaceContainerLowestCharcoalBlack,
    surfaceDim = surfaceDimCharcoalBlack
)

val DeepForestGreenColorScheme = darkColorScheme(
    primary = primaryDeepForestGreen,
    onPrimary = onPrimaryDeepForestGreen,
    primaryContainer = primaryContainerDeepForestGreen,
    onPrimaryContainer = onPrimaryContainerDeepForestGreen,
    inversePrimary = inversePrimaryDeepForestGreen,
    secondary = secondaryDeepForestGreen,
    onSecondary = onSecondaryDeepForestGreen,
    secondaryContainer = secondaryContainerDeepForestGreen,
    onSecondaryContainer = onSecondaryContainerDeepForestGreen,
    tertiary = tertiaryDeepForestGreen,
    onTertiary = onTertiaryDeepForestGreen,
    tertiaryContainer = tertiaryContainerDeepForestGreen,
    onTertiaryContainer = onTertiaryContainerDeepForestGreen,
    background = backgroundDeepForestGreen,
    onBackground = onBackgroundDeepForestGreen,
    surface = surfaceDeepForestGreen,
    onSurface = onSurfaceDeepForestGreen,
    surfaceVariant = surfaceVariantDeepForestGreen,
    onSurfaceVariant = onSurfaceVariantDeepForestGreen,
    inverseSurface = inverseSurfaceDeepForestGreen,
    inverseOnSurface = inverseOnSurfaceDeepForestGreen,
    error = errorDeepForestGreen,
    onError = onErrorDeepForestGreen,
    errorContainer = errorContainerDeepForestGreen,
    onErrorContainer = onErrorContainerDeepForestGreen,
    outline = outlineDeepForestGreen,
    outlineVariant = outlineVariantDeepForestGreen,
    scrim = scrimDeepForestGreen,
    surfaceBright = surfaceBrightDeepForestGreen,
    surfaceContainer = surfaceContainerDeepForestGreen,
    surfaceContainerHigh = surfaceContainerHighDeepForestGreen,
    surfaceContainerHighest = surfaceContainerHighestDeepForestGreen,
    surfaceContainerLow = surfaceContainerLowDeepForestGreen,
    surfaceContainerLowest = surfaceContainerLowestDeepForestGreen,
    surfaceDim = surfaceDimDeepForestGreen
)

val LocalDarkTheme = compositionLocalOf { true }

@Composable
fun TodoTheme(
    theme: ThemeType = ThemeType.SYSTEM,
    content: @Composable () -> Unit,
) {
    val colorScheme = when (theme) {
        ThemeType.SYSTEM -> {
            if (isSystemInDarkTheme())
                MidnightBlueColorScheme
            else
                SunRiseColorScheme
        }

        ThemeType.SUN_RISE -> SunRiseColorScheme
        ThemeType.SKY_BLUE -> SkyBlueColorScheme
        ThemeType.MIST_GRAY -> MistGrayColorScheme
        ThemeType.MIDNIGHT_BLUE -> MidnightBlueColorScheme
        ThemeType.CHARCOAL_BLACK -> CharcoalBlackColorScheme
        else -> DeepForestGreenColorScheme
    }

    val isLightKey = colorScheme == SunRiseColorScheme ||
            colorScheme == SkyBlueColorScheme ||
            colorScheme == MistGrayColorScheme

    if (!LocalInspectionMode.current) {
        val view = LocalView.current
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController =
                WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = isLightKey
            insetsController.isAppearanceLightNavigationBars = isLightKey
        }
    }

    CompositionLocalProvider(
        LocalDarkTheme provides isSystemInDarkTheme(),
        LocalTypography provides Typography,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

object TodoTheme {
    val typography: TodoTypography
        @Composable
        get() = LocalTypography.current
}