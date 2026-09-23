package com.example.setting.model

import androidx.compose.ui.graphics.Color
import com.example.design_system.theme.onPrimaryDeepSpace
import com.example.design_system.theme.onPrimaryEmber
import com.example.design_system.theme.onPrimaryMeadow
import com.example.design_system.theme.onPrimaryMidnight
import com.example.design_system.theme.onPrimaryOcean
import com.example.design_system.theme.onPrimarySunRise
import com.example.design_system.theme.onSurfaceMidnight
import com.example.design_system.theme.onSurfaceSunRise
import com.example.design_system.theme.primaryDeepSpace
import com.example.design_system.theme.primaryEmber
import com.example.design_system.theme.primaryMeadow
import com.example.design_system.theme.primaryMidnight
import com.example.design_system.theme.primaryOcean
import com.example.design_system.theme.primarySunRise
import com.example.design_system.theme.surfaceMidnight
import com.example.design_system.theme.surfaceSunRise

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
