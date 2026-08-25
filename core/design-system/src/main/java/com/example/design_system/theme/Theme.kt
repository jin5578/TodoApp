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

val OceanColorScheme = lightColorScheme(
    primary = primaryOcean,
    onPrimary = onPrimaryOcean,
    primaryContainer = primaryContainerOcean,
    onPrimaryContainer = onPrimaryContainerOcean,
    inversePrimary = inversePrimaryOcean,
    secondary = secondaryOcean,
    onSecondary = onSecondaryOcean,
    secondaryContainer = secondaryContainerOcean,
    onSecondaryContainer = onSecondaryContainerOcean,
    tertiary = tertiaryOcean,
    onTertiary = onTertiaryOcean,
    tertiaryContainer = tertiaryContainerOcean,
    onTertiaryContainer = onTertiaryContainerOcean,
    background = backgroundOcean,
    onBackground = onBackgroundOcean,
    surface = surfaceOcean,
    onSurface = onSurfaceOcean,
    surfaceVariant = surfaceVariantOcean,
    onSurfaceVariant = onSurfaceVariantOcean,
    inverseSurface = inverseSurfaceOcean,
    inverseOnSurface = inverseOnSurfaceOcean,
    error = errorOcean,
    onError = onErrorOcean,
    errorContainer = errorContainerOcean,
    onErrorContainer = onErrorContainerOcean,
    outline = outlineOcean,
    outlineVariant = outlineVariantOcean,
    scrim = scrimOcean,
    surfaceBright = surfaceBrightOcean,
    surfaceContainer = surfaceContainerOcean,
    surfaceContainerHigh = surfaceContainerHighOcean,
    surfaceContainerHighest = surfaceContainerHighestOcean,
    surfaceContainerLow = surfaceContainerLowOcean,
    surfaceContainerLowest = surfaceContainerLowestOcean,
    surfaceDim = surfaceDimOcean
)

val MeadowColorScheme = lightColorScheme(
    primary = primaryMeadow,
    onPrimary = onPrimaryMeadow,
    primaryContainer = primaryContainerMeadow,
    onPrimaryContainer = onPrimaryContainerMeadow,
    inversePrimary = inversePrimaryMeadow,
    secondary = secondaryMeadow,
    onSecondary = onSecondaryMeadow,
    secondaryContainer = secondaryContainerMeadow,
    onSecondaryContainer = onSecondaryContainerMeadow,
    tertiary = tertiaryMeadow,
    onTertiary = onTertiaryMeadow,
    tertiaryContainer = tertiaryContainerMeadow,
    onTertiaryContainer = onTertiaryContainerMeadow,
    background = backgroundMeadow,
    onBackground = onBackgroundMeadow,
    surface = surfaceMeadow,
    onSurface = onSurfaceMeadow,
    surfaceVariant = surfaceVariantMeadow,
    onSurfaceVariant = onSurfaceVariantMeadow,
    inverseSurface = inverseSurfaceMeadow,
    inverseOnSurface = inverseOnSurfaceMeadow,
    error = errorMeadow,
    onError = onErrorMeadow,
    errorContainer = errorContainerMeadow,
    onErrorContainer = onErrorContainerMeadow,
    outline = outlineMeadow,
    outlineVariant = outlineVariantMeadow,
    scrim = scrimMeadow,
    surfaceBright = surfaceBrightMeadow,
    surfaceContainer = surfaceContainerMeadow,
    surfaceContainerHigh = surfaceContainerHighMeadow,
    surfaceContainerHighest = surfaceContainerHighestMeadow,
    surfaceContainerLow = surfaceContainerLowMeadow,
    surfaceContainerLowest = surfaceContainerLowestMeadow,
    surfaceDim = surfaceDimMeadow
)

val MidnightColorScheme = darkColorScheme(
    primary = primaryMidnight,
    onPrimary = onPrimaryMidnight,
    primaryContainer = primaryContainerMidnight,
    onPrimaryContainer = onPrimaryContainerMidnight,
    inversePrimary = inversePrimaryMidnight,
    secondary = secondaryMidnight,
    onSecondary = onSecondaryMidnight,
    secondaryContainer = secondaryContainerMidnight,
    onSecondaryContainer = onSecondaryContainerMidnight,
    tertiary = tertiaryMidnight,
    onTertiary = onTertiaryMidnight,
    tertiaryContainer = tertiaryContainerMidnight,
    onTertiaryContainer = onTertiaryContainerMidnight,
    background = backgroundMidnight,
    onBackground = onBackgroundMidnight,
    surface = surfaceMidnight,
    onSurface = onSurfaceMidnight,
    surfaceVariant = surfaceVariantMidnight,
    onSurfaceVariant = onSurfaceVariantMidnight,
    inverseSurface = inverseSurfaceMidnight,
    inverseOnSurface = inverseOnSurfaceMidnight,
    error = errorMidnight,
    onError = onErrorMidnight,
    errorContainer = errorContainerMidnight,
    onErrorContainer = onErrorContainerMidnight,
    outline = outlineMidnight,
    outlineVariant = outlineVariantMidnight,
    scrim = scrimMidnight,
    surfaceBright = surfaceBrightMidnight,
    surfaceContainer = surfaceContainerMidnight,
    surfaceContainerHigh = surfaceContainerHighMidnight,
    surfaceContainerHighest = surfaceContainerHighestMidnight,
    surfaceContainerLow = surfaceContainerLowMidnight,
    surfaceContainerLowest = surfaceContainerLowestMidnight,
    surfaceDim = surfaceDimMidnight
)

val DeepSpaceColorScheme = darkColorScheme(
    primary = primaryDeepSpace,
    onPrimary = onPrimaryDeepSpace,
    primaryContainer = primaryContainerDeepSpace,
    onPrimaryContainer = onPrimaryContainerDeepSpace,
    inversePrimary = inversePrimaryDeepSpace,
    secondary = secondaryDeepSpace,
    onSecondary = onSecondaryDeepSpace,
    secondaryContainer = secondaryContainerDeepSpace,
    onSecondaryContainer = onSecondaryContainerDeepSpace,
    tertiary = tertiaryDeepSpace,
    onTertiary = onTertiaryDeepSpace,
    tertiaryContainer = tertiaryContainerDeepSpace,
    onTertiaryContainer = onTertiaryContainerDeepSpace,
    background = backgroundDeepSpace,
    onBackground = onBackgroundDeepSpace,
    surface = surfaceDeepSpace,
    onSurface = onSurfaceDeepSpace,
    surfaceVariant = surfaceVariantDeepSpace,
    onSurfaceVariant = onSurfaceVariantDeepSpace,
    inverseSurface = inverseSurfaceDeepSpace,
    inverseOnSurface = inverseOnSurfaceDeepSpace,
    error = errorDeepSpace,
    onError = onErrorDeepSpace,
    errorContainer = errorContainerDeepSpace,
    onErrorContainer = onErrorContainerDeepSpace,
    outline = outlineDeepSpace,
    outlineVariant = outlineVariantDeepSpace,
    scrim = scrimDeepSpace,
    surfaceBright = surfaceBrightDeepSpace,
    surfaceContainer = surfaceContainerDeepSpace,
    surfaceContainerHigh = surfaceContainerHighDeepSpace,
    surfaceContainerHighest = surfaceContainerHighestDeepSpace,
    surfaceContainerLow = surfaceContainerLowDeepSpace,
    surfaceContainerLowest = surfaceContainerLowestDeepSpace,
    surfaceDim = surfaceDimDeepSpace
)

val EmberColorScheme = darkColorScheme(
    primary = primaryEmber,
    onPrimary = onPrimaryEmber,
    primaryContainer = primaryContainerEmber,
    onPrimaryContainer = onPrimaryContainerEmber,
    inversePrimary = inversePrimaryEmber,
    secondary = secondaryEmber,
    onSecondary = onSecondaryEmber,
    secondaryContainer = secondaryContainerEmber,
    onSecondaryContainer = onSecondaryContainerEmber,
    tertiary = tertiaryEmber,
    onTertiary = onTertiaryEmber,
    tertiaryContainer = tertiaryContainerEmber,
    onTertiaryContainer = onTertiaryContainerEmber,
    background = backgroundEmber,
    onBackground = onBackgroundEmber,
    surface = surfaceEmber,
    onSurface = onSurfaceEmber,
    surfaceVariant = surfaceVariantEmber,
    onSurfaceVariant = onSurfaceVariantEmber,
    inverseSurface = inverseSurfaceEmber,
    inverseOnSurface = inverseOnSurfaceEmber,
    error = errorEmber,
    onError = onErrorEmber,
    errorContainer = errorContainerEmber,
    onErrorContainer = onErrorContainerEmber,
    outline = outlineEmber,
    outlineVariant = outlineVariantEmber,
    scrim = scrimEmber,
    surfaceBright = surfaceBrightEmber,
    surfaceContainer = surfaceContainerEmber,
    surfaceContainerHigh = surfaceContainerHighEmber,
    surfaceContainerHighest = surfaceContainerHighestEmber,
    surfaceContainerLow = surfaceContainerLowEmber,
    surfaceContainerLowest = surfaceContainerLowestEmber,
    surfaceDim = surfaceDimEmber
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
                MidnightColorScheme
            else
                SunRiseColorScheme
        }

        ThemeType.SUN_RISE -> SunRiseColorScheme
        ThemeType.OCEAN -> OceanColorScheme
        ThemeType.MEADOW -> MeadowColorScheme
        ThemeType.MIDNIGHT -> MidnightColorScheme
        ThemeType.DEEP_SPACE -> DeepSpaceColorScheme
        else -> EmberColorScheme
    }

    val isLightKey = colorScheme == SunRiseColorScheme ||
            colorScheme == OceanColorScheme ||
            colorScheme == MeadowColorScheme

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