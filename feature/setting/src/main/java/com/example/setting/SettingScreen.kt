package com.example.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.model.LanguageType
import com.example.model.ThemeType
import com.example.model.TimePickerType
import com.example.setting.component.SettingCategory
import com.example.setting.component.SettingLanguageContent
import com.example.setting.component.SettingThemeContent
import com.example.setting.component.SettingTimePickerContent
import com.example.setting.model.BottomSheetType
import com.example.setting.model.CategoryItemUiState
import kotlinx.collections.immutable.persistentListOf
import com.example.design_system.R as DesignSystemR

private const val ABOUT_URL =
    "https://intelligent-party-142.notion.site/TODO-1109ff809974806cb274f0b95d4a71d4?pvs=4"
private const val GITHUB_URL = "https://github.com/jin5578"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingScreen(
    modifier: Modifier = Modifier,
    languageType: LanguageType,
    themeType: ThemeType,
    timePickerType: TimePickerType,
    buildVersion: String,
    hasExistingPassword: Boolean,
    navigateManageCategories: () -> Unit,
    navigateSecurity: () -> Unit,
    popBackStack: () -> Unit,
    openUrl: (String) -> Unit,
    onLanguageTypeChanged: (LanguageType) -> Unit,
    onThemeTypeChanged: (ThemeType) -> Unit,
    onTimePickerTypeChanged: (TimePickerType) -> Unit
) {
    val scrollState = rememberScrollState()

    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(value = BottomSheetType.IDLE) }

    val infoCategory = persistentListOf(
        CategoryItemUiState(
            titleResId = DesignSystemR.string.about,
            iconResId = DesignSystemR.drawable.svg_information,
            onClick = { openUrl(ABOUT_URL) }
        ),
        CategoryItemUiState(
            titleResId = DesignSystemR.string.github,
            iconResId = DesignSystemR.drawable.svg_github,
            onClick = { openUrl(GITHUB_URL) }
        )
    )

    val systemCategory = persistentListOf(
        CategoryItemUiState(
            titleResId = DesignSystemR.string.language,
            iconResId =
                if (languageType == LanguageType.KOREAN) DesignSystemR.drawable.svg_korean
                else DesignSystemR.drawable.svg_english,
            onClick = { showBottomSheet = BottomSheetType.LANGUAGE }
        ),
        CategoryItemUiState(
            titleResId = DesignSystemR.string.theme,
            iconResId = DesignSystemR.drawable.svg_theme,
            onClick = { showBottomSheet = BottomSheetType.THEME }
        ),
        CategoryItemUiState(
            titleResId = DesignSystemR.string.time_picker,
            iconResId = DesignSystemR.drawable.svg_clock,
            onClick = { showBottomSheet = BottomSheetType.TIME_PICKER }
        ),
        CategoryItemUiState(
            titleResId = DesignSystemR.string.category,
            iconResId = DesignSystemR.drawable.svg_category,
            onClick = navigateManageCategories
        ),
        CategoryItemUiState(
            titleResId = DesignSystemR.string.password_and_security,
            iconResId =
                if (hasExistingPassword) DesignSystemR.drawable.svg_lock
                else DesignSystemR.drawable.svg_unlock,
            onClick = navigateSecurity
        ),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {},
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = modifier.size(size = 24.dp),
                            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (showBottomSheet != BottomSheetType.IDLE) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = BottomSheetType.IDLE },
                sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                Box() {
                    when (showBottomSheet) {
                        BottomSheetType.LANGUAGE -> {
                            SettingLanguageContent(
                                languageType = languageType,
                                onSelect = onLanguageTypeChanged,
                            )
                        }

                        BottomSheetType.THEME -> {
                            SettingThemeContent(
                                themeType = themeType,
                                onSelect = onThemeTypeChanged,
                            )
                        }

                        else -> {
                            SettingTimePickerContent(
                                timePickerType = timePickerType,
                                onSelect = onTimePickerTypeChanged,
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(top = 40.dp, bottom = 20.dp)
                .verticalScroll(state = scrollState),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                text = stringResource(
                    id = DesignSystemR.string.settings
                ),
                style = TodoTheme.typography.bold_20,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(height = 40.dp))

            SettingCategory(
                titleResId = DesignSystemR.string.info,
                category = infoCategory,
            )

            Spacer(modifier = Modifier.height(height = 20.dp))

            SettingCategory(
                titleResId = DesignSystemR.string.system_setting,
                category = systemCategory,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingScreenPreview() {
    TodoTheme {
        SettingScreen(
            languageType = LanguageType.KOREAN,
            themeType = ThemeType.SUN_RISE,
            timePickerType = TimePickerType.SCROLL_TIME_PICKER,
            buildVersion = "1.0.0",
            hasExistingPassword = true,
            navigateManageCategories = {},
            navigateSecurity = {},
            popBackStack = {},
            openUrl = {},
            onLanguageTypeChanged = {},
            onThemeTypeChanged = {},
            onTimePickerTypeChanged = {}
        )
    }
}