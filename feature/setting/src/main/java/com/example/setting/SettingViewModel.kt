package com.example.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetSettingDataUseCase
import com.example.domain.UpdateLanguageUseCase
import com.example.domain.UpdateThemeTypeUseCase
import com.example.domain.UpdateTimePickerTypeUseCase
import com.example.model.LanguageType
import com.example.model.ThemeType
import com.example.model.TimePickerType
import com.example.setting.model.SettingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
@Inject
constructor(
    private val getSettingDataUseCase: GetSettingDataUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateThemeTypeUseCase: UpdateThemeTypeUseCase,
    private val updateTimePickerTypeUseCase: UpdateTimePickerTypeUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<SettingUiState> =
        MutableStateFlow(value = SettingUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchSettingUiState()
    }

    private fun fetchSettingUiState() = viewModelScope.launch {
        getSettingDataUseCase()
            .map { settingSystem ->
                SettingUiState.Screen(
                    languageType = settingSystem.languageType,
                    themeType = settingSystem.themeType,
                    sleepTime = settingSystem.sleepTime,
                    timePickerType = settingSystem.timePickerType,
                    buildVersion = settingSystem.buildVersion,
                    hasExistingPassword = settingSystem.hasExistingPassword,
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
    }

    fun updateLanguageType(languageType: LanguageType) = viewModelScope.launch {
        updateLanguageUseCase(languageType = languageType)
    }

    fun updateThemeType(themeType: ThemeType) = viewModelScope.launch {
        updateThemeTypeUseCase(themeType = themeType)
    }

    fun updateTimePickerType(timePickerType: TimePickerType) = viewModelScope.launch {
        updateTimePickerTypeUseCase(timePickerType = timePickerType)
    }
}
