package com.example.tasks.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.Category
import com.example.model.Task
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.util.Locale

@Stable
sealed interface TasksUiState {
    @Immutable
    data object Loading : TasksUiState

    @Immutable
    data class Success(
        val tasks: ImmutableList<Task> = persistentListOf(),
        val categories: ImmutableList<Category> = persistentListOf(),
        val locale: Locale,
    ) : TasksUiState
}