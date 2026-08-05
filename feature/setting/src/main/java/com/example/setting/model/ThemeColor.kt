package com.example.setting.model

import androidx.compose.ui.graphics.Color
import com.example.design_system.theme.onPrimaryCharcoalBlack
import com.example.design_system.theme.onPrimaryDeepForestGreen
import com.example.design_system.theme.onPrimaryMidnightBlue
import com.example.design_system.theme.onPrimaryMistGray
import com.example.design_system.theme.onPrimarySkyBlue
import com.example.design_system.theme.onPrimarySunRise
import com.example.design_system.theme.onSurfaceMidnightBlue
import com.example.design_system.theme.onSurfaceSunRise
import com.example.design_system.theme.primaryCharcoalBlack
import com.example.design_system.theme.primaryDeepForestGreen
import com.example.design_system.theme.primaryMidnightBlue
import com.example.design_system.theme.primaryMistGray
import com.example.design_system.theme.primarySkyBlue
import com.example.design_system.theme.primarySunRise
import com.example.design_system.theme.surfaceMidnightBlue
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
    SKY_BLUE_THEME(
        backgroundColor = primarySkyBlue,
        textColor = onPrimarySkyBlue,
        dividerColor = primarySkyBlue,
    ),
    MIST_GRAY_THEME(
        backgroundColor = primaryMistGray,
        textColor = onPrimaryMistGray,
        dividerColor = primaryMistGray,
    ),
    MIDNIGHT_BLUE_THEME(
        backgroundColor = primaryMidnightBlue,
        textColor = onPrimaryMidnightBlue,
        dividerColor = primaryMidnightBlue,
    ),
    CHARCOAL_BLACK_THEME(
        backgroundColor = primaryCharcoalBlack,
        textColor = onPrimaryCharcoalBlack,
        dividerColor = primaryCharcoalBlack,
    ),
    DEEP_FOREST_GREEN_THEME(
        backgroundColor = primaryDeepForestGreen,
        textColor = onPrimaryDeepForestGreen,
        dividerColor = primaryDeepForestGreen,
    ),
    DARK_SYSTEM_THEME(
        backgroundColor = surfaceMidnightBlue,
        textColor = onSurfaceMidnightBlue,
        dividerColor = primaryMidnightBlue,
    ),
}