package com.example.lock_setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.CheckPasswordUseCase
import com.example.domain.GetLockSetupDataUseCase
import com.example.domain.UpdatePasswordUseCase
import com.example.lock_setup.model.LockSetupUiEffect
import com.example.lock_setup.model.LockSetupUiState
import com.example.model.LockSetupProcessType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockSetupViewModel @Inject constructor(
    private val getLockSetupDataUseCase: GetLockSetupDataUseCase,
    private val updatePasswordUsecase: UpdatePasswordUseCase,
    private val checkPasswordUseCase: CheckPasswordUseCase
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<LockSetupUiState> =
        MutableStateFlow(value = LockSetupUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<LockSetupUiEffect> =
        MutableSharedFlow()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        fetchLockSetupUiState()
    }

    private fun fetchLockSetupUiState() =
        viewModelScope.launch {
            getLockSetupDataUseCase().map { lockSetupSystem ->
                val lockSetupProcessType =
                    if (lockSetupSystem.hasExistingPassword)
                        LockSetupProcessType.ENTER_EXISTING_PASSWORD
                    else
                        LockSetupProcessType.ENTER_NEW_PASSWORD
                LockSetupUiState.Success(
                    lockSetupProcessType = lockSetupProcessType,
                    newInputPassword = "",
                    locale = lockSetupSystem.locale
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
        }

    fun updateNewInputPassword(password: String) {
        _uiState.update { currentState ->
            if (currentState is LockSetupUiState.Success) {
                currentState.copy(
                    lockSetupProcessType = LockSetupProcessType.CONFIRM_NEW_PASSWORD,
                    newInputPassword = password
                )
            } else {
                currentState
            }
        }
    }

    fun updatePassword(password: String) =
        viewModelScope.launch {
            updatePasswordUsecase(password = password)
            _uiEffect.emit(value = LockSetupUiEffect.SuccessSetupPassword)
        }

    fun checkPassword(password: String) =
        viewModelScope.launch {
            val isPasswordMatched =
                checkPasswordUseCase(password = password).first()
            _uiState.update { currentState ->
                if (currentState is LockSetupUiState.Success) {
                    val lockSetupProcessType =
                        if (isPasswordMatched)
                            LockSetupProcessType.ENTER_NEW_PASSWORD
                        else
                            LockSetupProcessType.EXISTING_PASSWORD_MISMATCHED
                    currentState.copy(lockSetupProcessType = lockSetupProcessType)
                } else {
                    currentState
                }
            }
        }
}