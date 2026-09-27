package com.example.setting.model

import androidx.compose.ui.graphics.Color
import com.example.designSystem.theme.onPrimaryDeepSpace
import com.example.designSystem.theme.onPrimaryEmber
import com.example.designSystem.theme.onPrimaryMeadow
import com.example.designSystem.theme.onPrimaryMidnight
import com.example.designSystem.theme.onPrimaryOcean
import com.example.designSystem.theme.onPrimarySunRise
import com.example.designSystem.theme.onSurfaceMidnight
import com.example.designSystem.theme.onSurfaceSunRise
import com.example.designSystem.theme.primaryDeepSpace
import com.example.designSystem.theme.primaryEmber
import com.example.designSystem.theme.primaryMeadow
import com.example.designSystem.theme.primaryMidnight
import com.example.designSystem.theme.primaryOcean
import com.example.designSystem.theme.primarySunRise
import com.example.designSystem.theme.surfaceMidnight
import com.example.designSystem.theme.surfaceSunRise

enum class ThemeColor(
    val backgroundColor: Color,
    val textColor: Color,
    val dividerColor: Color,
) {
    SYSTEM_THEME(
        backgroundColor = surfaceSunRise,
        textColor = onSurfaceSunRise,
        dividerColor = primarySunRise,
    ),
    SUN_RISE_THEME(
        backgroundColor = primarySunRise,
        textColor = onPrimarySunRise,
        dividerColor = primarySunRise,
    ),
    OCEAN_THEME(
        backgroundColor = primaryOcean,
        textColor = onPrimaryOcean,
        dividerColor = primaryOcean,
    ),
    MEADOW_THEME(
        backgroundColor = primaryMeadow,
        textColor = onPrimaryMeadow,
        dividerColor = primaryMeadow,
    ),
    MIDNIGHT_THEME(
        backgroundColor = primaryMidnight,
        textColor = onPrimaryMidnight,
        dividerColor = primaryMidnight,
    ),
    DEEP_SPACE_THEME(
        backgroundColor = primaryDeepSpace,
        textColor = onPrimaryDeepSpace,
        dividerColor = primaryDeepSpace,
    ),
    EMBER_THEME(
        backgroundColor = primaryEmber,
        textColor = onPrimaryEmber,
        dividerColor = primaryEmber,
    ),
    DARK_SYSTEM_THEME(
        backgroundColor = surfaceMidnight,
        textColor = onSurfaceMidnight,
        dividerColor = primaryMidnight,
    ),
}
