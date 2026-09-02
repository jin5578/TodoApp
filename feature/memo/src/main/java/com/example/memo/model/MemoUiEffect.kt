package com.example.memo.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface MemoUiEffect {
    @Immutable
    data object Idle : MemoUiEffect

    @Immutable
    data object SuccessUpdateMemo : MemoUiEffect
}