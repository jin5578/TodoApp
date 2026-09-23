package com.example.model.completed_tasks

import com.example.model.Task

data class CompletedTasks(
    val tasks: List<Task>,
    val completedTasksSystem: CompletedTasksSystem,
)
