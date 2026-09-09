package com.example.main.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.design_system.R
import com.example.navigation.Route

internal enum class MainTab(
    val route: Route,
    @DrawableRes val iconResId: Int,
    @StringRes val titleResId: Int,
) {
    HOME(
        route = Route.Home,
        iconResId = R.drawable.svg_home,
        titleResId = R.string.home,
    ),
    CALENDAR(
        route = Route.Calendar,
        iconResId = R.drawable.svg_calendar,
        titleResId = R.string.calendar,
    ),
    SETTING(
        route = Route.Setting,
        iconResId = R.drawable.svg_setting,
        titleResId = R.string.settings,
    ),
}