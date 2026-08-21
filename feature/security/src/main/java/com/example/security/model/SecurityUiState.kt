package com.example.security.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface SecurityUiState {
    @Immutable
    data object Loading : SecurityUiState

    @Immutable
    data class Screen(
        val hasExistingPassword: Boolean,
        val hasBiometricEnabled: Boolean
    ) : SecurityUiState
}