package com.example.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.model.LanguageType
import com.example.model.ThemeType
import com.example.model.TimePickerType
import com.example.setting.model.SettingUiState
import com.example.utils.openUrl
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    navigateInfo: () -> Unit,
    navigateManageCategories: () -> Unit,
    navigateLockSetup: () -> Unit,
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    SettingContent(
        uiState = uiState,
        navigateManageCategories = navigateManageCategories,
        navigateLockSetup = navigateLockSetup,
        popBackStack = popBackStack,
        openUrl = { url -> openUrl(context = context, url = url) },
        onLanguageTypeChanged = viewModel::updateLanguageType,
        onThemeTypeChanged = viewModel::updateThemeType,
        onTimePickerTypeChanged = viewModel::updateTimePickerType,
    )
}

@Composable
private fun SettingContent(
    uiState: SettingUiState,
    navigateManageCategories: () -> Unit,
    navigateLockSetup: () -> Unit,
    popBackStack: () -> Unit,
    openUrl: (String) -> Unit,
    onLanguageTypeChanged: (LanguageType) -> Unit,
    onThemeTypeChanged: (ThemeType) -> Unit,
    onTimePickerTypeChanged: (TimePickerType) -> Unit,
) {
    when (uiState) {
        is SettingUiState.Loading -> Loading()
        is SettingUiState.Screen -> SettingScreen(
            languageType = uiState.languageType,
            themeType = uiState.themeType,
            timePickerType = uiState.timePickerType,
            buildVersion = uiState.buildVersion,
            hasExistingPassword = uiState.hasExistingPassword,
            navigateManageCategories = navigateManageCategories,
            navigateLockSetup = navigateLockSetup,
            popBackStack = popBackStack,
            openUrl = openUrl,
            onLanguageTypeChanged = onLanguageTypeChanged,
            onThemeTypeChanged = onThemeTypeChanged,
            onTimePickerTypeChanged = onTimePickerTypeChanged,
        )
    }
}