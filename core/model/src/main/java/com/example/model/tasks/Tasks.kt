package com.example.model.tasks

import com.example.model.Category
import com.example.model.Task

data class Tasks(
    val tasks: List<Task>,
    val categories: List<Category>,
    val tasksSystem: TasksSystem,
)