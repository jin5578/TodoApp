package com.example.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetSecurityDataUseCase
import com.example.domain.UpdateBiometricEnabledUseCase
import com.example.security.model.SecurityUiState
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
class SecurityViewModel
@Inject
constructor(
    private val getSecurityDataUseCase: GetSecurityDataUseCase,
    private val updateBiometricEnabledUseCase: UpdateBiometricEnabledUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<SecurityUiState> =
        MutableStateFlow(value = SecurityUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchSecurityUiState()
    }

    private fun fetchSecurityUiState() = viewModelScope.launch {
        getSecurityDataUseCase()
            .map { securitySystem ->
                SecurityUiState.Screen(
                    hasExistingPassword = securitySystem.hasExistingPassword,
                    hasBiometricEnabled = securitySystem.hasBiometricEnabled,
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
    }

    fun updateBiometricEnabled(isChecked: Boolean) = viewModelScope.launch {
        updateBiometricEnabledUseCase(enabled = isChecked)
    }
}
