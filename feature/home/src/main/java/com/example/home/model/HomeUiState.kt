package com.example.home.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.Category
import com.example.model.HomePasswordProcessType
import com.example.model.SortTaskType
import com.example.model.Task
import com.example.model.ThemeType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalTime
import java.util.Locale

@Stable
sealed interface HomeUiState {
    @Immutable
    data object Loading : HomeUiState

    @Immutable
    data object Biometric : HomeUiState

    @Immutable
    data class Password(
        val homePasswordProcessType: HomePasswordProcessType
    ) : HomeUiState

    @Immutable
    data class Screen(
        val completedTasks: ImmutableList<Task> = persistentListOf(),
        val incompleteTasks: ImmutableList<Task> = persistentListOf(),
        val categories: ImmutableList<Category> = persistentListOf(),
        val sleepTime: LocalTime,
        val sortTaskType: SortTaskType,
        val themeType: ThemeType,
        val buildVersion: String,
        val locale: Locale,
    ) : HomeUiState
}