package com.example.design_system.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.staticCompositionLocalOf

val LocalHideBottomBar = staticCompositionLocalOf<MutableState<Boolean>> {
    error("LocalHideBottomBar not provided. Wrap this composable with CompositionLocalProvider(LocalHideBottomBar provides ...).")
}