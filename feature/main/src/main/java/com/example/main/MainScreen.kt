package com.example.main

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import com.example.design_system.R as DesignSystemR

@Composable
internal fun MainRoute(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val localContextResources = LocalResources.current

    val onShowErrorSnackbar: (throwable: Throwable?) -> Unit = { throwable ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = when (throwable) {
                    is UnknownHostException -> localContextResources.getString(
                        DesignSystemR.string.error_message_unknown
                    )

                    else -> localContextResources.getString(DesignSystemR.string.error_message_network)
                }
            )
        }
    }

    val onShowMessageSnackbar: (message: String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = message)
        }
    }

    MainScreen(
        navigator = navigator,
        snackbarHostState = snackbarHostState,
        onShowErrorSnackbar = onShowErrorSnackbar,
        onShowMessageSnackbar = onShowMessageSnackbar,
    )
}

@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    snackbarHostState: SnackbarHostState,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        content = { _ ->
            MainNavHost(
                navigator = navigator,
                onShowErrorSnackbar = onShowErrorSnackbar,
                onShowMessageSnackbar = onShowMessageSnackbar,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    )
}