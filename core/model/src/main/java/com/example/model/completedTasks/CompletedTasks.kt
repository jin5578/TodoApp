package com.example.model.completedTasks

import com.example.model.Task

data class CompletedTasks(
    val tasks: List<Task>,
    val completedTasksSystem: CompletedTasksSystem,
)
