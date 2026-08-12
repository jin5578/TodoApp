package com.example.edit_task.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface EditTaskUiEffect {
    @Immutable
    data object Idle : EditTaskUiEffect

    @Immutable
    data class SuccessEditTask(
        val message: String,
    ) : EditTaskUiEffect
}