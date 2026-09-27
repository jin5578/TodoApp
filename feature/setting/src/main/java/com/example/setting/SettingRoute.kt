package com.example.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designSystem.component.Loading
import com.example.designSystem.utils.LocalSnackbarHostState
import com.example.designSystem.utils.toErrorMessage
import com.example.model.LanguageType
import com.example.model.ThemeType
import com.example.model.TimePickerType
import com.example.setting.model.SettingUiState
import com.example.utils.openUrl
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    navigateManageCategories: () -> Unit,
    navigateSecurity: () -> Unit,
    navigateGithubAuth: () -> Unit,
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val snackbarHostState = LocalSnackbarHostState.current
    val contextResources = LocalResources.current

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackbarHostState.showSnackbar(
                message = throwable.toErrorMessage(resources = contextResources),
            )
        }
    }

    SettingContent(
        uiState = uiState,
        openUrl = { url -> openUrl(context = context, url = url) },
        onLanguageTypeChanged = viewModel::updateLanguageType,
        onThemeTypeChanged = viewModel::updateThemeType,
        onTimePickerTypeChanged = viewModel::updateTimePickerType,
        navigateManageCategories = navigateManageCategories,
        navigateSecurity = navigateSecurity,
        navigateGithubAuth = navigateGithubAuth,
        popBackStack = popBackStack,
    )
}

@Composable
private fun SettingContent(
    uiState: SettingUiState,
    openUrl: (String) -> Unit,
    onLanguageTypeChanged: (LanguageType) -> Unit,
    onThemeTypeChanged: (ThemeType) -> Unit,
    onTimePickerTypeChanged: (TimePickerType) -> Unit,
    navigateManageCategories: () -> Unit,
    navigateSecurity: () -> Unit,
    navigateGithubAuth: () -> Unit,
    popBackStack: () -> Unit,
) {
    when (uiState) {
        is SettingUiState.Loading -> Loading()

        is SettingUiState.Screen -> SettingScreen(
            languageType = uiState.languageType,
            themeType = uiState.themeType,
            timePickerType = uiState.timePickerType,
            buildVersion = uiState.buildVersion,
            hasExistingPassword = uiState.hasExistingPassword,
            openUrl = openUrl,
            onLanguageTypeChanged = onLanguageTypeChanged,
            onThemeTypeChanged = onThemeTypeChanged,
            onTimePickerTypeChanged = onTimePickerTypeChanged,
            navigateManageCategories = navigateManageCategories,
            navigateSecurity = navigateSecurity,
            navigateGithubAuth = navigateGithubAuth,
            popBackStack = popBackStack,
        )
    }
}
