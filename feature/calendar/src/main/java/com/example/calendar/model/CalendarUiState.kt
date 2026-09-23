package com.example.calendar.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.Category
import com.example.model.SortByType
import com.example.model.TaskUiModel
import com.example.model.TimePickerType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.util.Locale

@Stable
sealed interface CalendarUiState {
    @Immutable
    data object Loading : CalendarUiState

    @Immutable
    data class Screen(
        val calendarTasks: ImmutableList<TaskUiModel> = persistentListOf(),
        val tasks: ImmutableList<TaskUiModel> = persistentListOf(),
        val categories: ImmutableList<Category> = persistentListOf(),
        val sortByType: SortByType,
        val locale: Locale,
        val timePickerType: TimePickerType,
        val selectedDate: LocalDate,
        val selectedCategoryId: Long,
    ) : CalendarUiState
}