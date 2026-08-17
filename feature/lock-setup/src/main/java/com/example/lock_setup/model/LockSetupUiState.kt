package com.example.lock_setup.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.LockSetupProcessType
import java.util.Locale

@Stable
sealed interface LockSetupUiState {
    @Immutable
    data object Loading : LockSetupUiState

    @Immutable
    data class Success(
        val lockSetupProcessType: LockSetupProcessType,
        val newInputPassword: String,
        val locale: Locale
    ) : LockSetupUiState
}