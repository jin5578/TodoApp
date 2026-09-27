package com.example.completedTasks.model

import androidx.compose.runtime.Immutable
import com.example.model.TaskUiModel
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Immutable
data class TaskDateGroup(
    val taskDate: LocalDate,
    val tasks: ImmutableList<TaskUiModel>,
)
