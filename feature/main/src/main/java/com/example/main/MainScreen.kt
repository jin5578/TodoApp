package com.example.main

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.design_system.utils.LocalHideBottomBar
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.design_system.utils.LocalSnackbarScope
import com.example.main.component.MainBottomNavigationBar
import com.example.main.navigation.MainNavHost
import com.example.main.navigation.MainNavigator
import com.example.main.navigation.rememberMainNavigator
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun MainRoute(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()
    val hideBottomBar = remember { mutableStateOf(value = false) }

    MainScreen(
        navigator = navigator,
        snackbarHostState = snackbarHostState,
        snackbarScope = snackbarScope,
        hideBottomBar = hideBottomBar,
    )
}

@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    snackbarHostState: SnackbarHostState,
    snackbarScope: CoroutineScope,
    hideBottomBar: MutableState<Boolean>,
) {
    CompositionLocalProvider(
        LocalSnackbarHostState provides snackbarHostState,
        LocalSnackbarScope provides snackbarScope,
        LocalHideBottomBar provides hideBottomBar,
    ) {
        Scaffold(
            modifier = modifier,
            content = { innerPadding ->
                MainNavHost(
                    modifier = Modifier
                        .padding(paddingValues = innerPadding)
                        .consumeWindowInsets(paddingValues = innerPadding),
                    navigator = navigator,
                )
            },
            bottomBar = {
                val currentTab = navigator.currentTab
                if (currentTab != null && !hideBottomBar.value) {
                    MainBottomNavigationBar(
                        selectedTab = currentTab,
                        onTabClick = { tab -> navigator.navigateTab(tab = tab) },
                    )
                }
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        )
    }
}