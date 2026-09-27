package com.example.completedTasks.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.util.Locale

@Stable
sealed interface CompletedTasksUiState {
    @Immutable
    data object Loading : CompletedTasksUiState

    @Immutable
    data class Screen(
        val taskDateGroups: ImmutableList<TaskDateGroup> = persistentListOf(),
        val locale: Locale,
    ) : CompletedTasksUiState
}
