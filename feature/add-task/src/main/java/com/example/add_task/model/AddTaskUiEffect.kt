package com.example.add_task.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface AddTaskUiEffect {
    @Immutable
    data object Idle : AddTaskUiEffect

    @Immutable
    data class SuccessInsertTask(
        val message: String
    ) : AddTaskUiEffect
}