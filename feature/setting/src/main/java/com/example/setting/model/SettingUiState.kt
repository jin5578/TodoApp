package com.example.setting.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.LanguageType
import com.example.model.ThemeType
import com.example.model.TimePickerType
import java.time.LocalTime

@Stable
sealed interface SettingUiState {
    @Immutable
    data object Loading : SettingUiState

    @Immutable
    data class Success(
        val languageType: LanguageType,
        val themeType: ThemeType,
        val sleepTime: LocalTime,
        val timePickerType: TimePickerType,
        val buildVersion: String,
        val hasExistingPassword: Boolean,
    ) : SettingUiState
}