package com.example.search_task.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.TaskUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.util.Locale

@Stable
sealed interface SearchTaskUiState {
    @Immutable
    data object Loading : SearchTaskUiState

    @Immutable
    data class Screen(
        val tasks: ImmutableList<TaskUiModel> = persistentListOf(),
        val locale: Locale,
    ) : SearchTaskUiState
}
