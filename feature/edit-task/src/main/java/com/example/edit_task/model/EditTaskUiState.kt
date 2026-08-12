package com.example.edit_task.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.Category
import com.example.model.Task
import com.example.model.TimePickerType
import kotlinx.collections.immutable.ImmutableList
import java.util.Locale

@Stable
sealed interface EditTaskUiState {
    @Immutable
    data object Loading : EditTaskUiState

    @Immutable
    data class Success(
        val task: Task,
        val locale: Locale,
        val timePickerType: TimePickerType,
        val categories: ImmutableList<Category>
    ) : EditTaskUiState
}