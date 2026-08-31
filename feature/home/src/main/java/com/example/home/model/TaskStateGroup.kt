package com.example.home.model

import androidx.compose.runtime.Immutable
import com.example.model.Task
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class TaskStateGroup(
    val taskState: TaskState,
    val tasks: ImmutableList<Task>
)