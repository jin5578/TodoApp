package com.example.memo.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import java.time.LocalDateTime
import java.util.Locale

@Stable
sealed interface MemoUiState {
    @Immutable
    data object Loading : MemoUiState

    @Immutable
    data class Screen(
        val memoTitle: String,
        val memoContent: String,
        val memoUpdatedAt: LocalDateTime?,
        val locale: Locale
    ) : MemoUiState
}