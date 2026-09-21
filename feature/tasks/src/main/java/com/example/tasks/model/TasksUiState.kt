package com.example.tasks.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.Category
import com.example.model.SortByType
import com.example.model.TimePickerType
import com.example.model.open_weather.WeatherInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.util.Locale

@Stable
sealed interface TasksUiState {
    @Immutable
    data object Loading : TasksUiState

    @Immutable
    data object Biometric : TasksUiState

    @Immutable
    data class Password(
        val tasksPasswordProcessType: TasksPasswordProcessType
    ) : TasksUiState

    @Immutable
    data class Screen(
        val taskStateGroups: ImmutableList<TaskStateGroup> = persistentListOf(),
        val categories: ImmutableList<Category> = persistentListOf(),
        val weatherInfo: WeatherInfo?,
        val sortByType: SortByType,
        val locale: Locale,
        val timePickerType: TimePickerType,
        val isVisibleCompletedTask: Boolean,
    ) : TasksUiState
}