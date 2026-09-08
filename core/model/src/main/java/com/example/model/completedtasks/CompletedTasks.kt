package com.example.model.completedtasks

import com.example.model.Task

data class CompletedTasks(
    val tasks: List<Task>,
    val completedTasksSystem: CompletedTasksSystem
)