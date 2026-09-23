package com.example.tasks.model

import androidx.compose.runtime.Immutable
import com.example.model.TaskUiModel
import kotlinx.collections.immutable.ImmutableList

enum class TaskState(
    val key: String,
) {
    PREVIOUS(
        key = "previous",
    ),
    COMPLETED_TODAY(
        key = "completedToday",
    ),
}

@Immutable
data class TaskStateGroup(
    val taskState: TaskState,
    val tasks: ImmutableList<TaskUiModel>,
)
