package com.example.designSystem.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope

val LocalSnackbarHostState = staticCompositionLocalOf<SnackbarHostState> {
    error("LocalSnackbarHostState not provided. Wrap this composable with CompositionLocalProvider(LocalSnackbarHostState provides ...).")
}

val LocalSnackbarScope = staticCompositionLocalOf<CoroutineScope> {
    error("LocalSnackbarScope not provided. Wrap this composable with CompositionLocalProvider(LocalSnackbarScope provides ...).")
}
