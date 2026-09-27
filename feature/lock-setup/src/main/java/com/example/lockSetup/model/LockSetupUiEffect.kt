package com.example.lockSetup.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface LockSetupUiEffect {
    @Immutable
    data object Idle : LockSetupUiEffect

    @Immutable
    data object SuccessSetupPassword : LockSetupUiEffect

    @Immutable
    data object SuccessRemovePassword : LockSetupUiEffect
}
