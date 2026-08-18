package com.example.add_task.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.Category
import com.example.model.TimePickerType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.util.Locale

@Stable
sealed interface AddTaskUiState {
    @Immutable
    data object Loading : AddTaskUiState

    @Immutable
    data class Screen(
        val date: LocalDate,
        val locale: Locale,
        val timePickerType: TimePickerType,
        val categories: ImmutableList<Category> = persistentListOf()
    ) : AddTaskUiState
}