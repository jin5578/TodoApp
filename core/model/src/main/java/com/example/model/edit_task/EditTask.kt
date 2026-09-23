package com.example.model.edit_task

import com.example.model.Category
import com.example.model.Task

data class EditTask(
    val task: Task,
    val categories: List<Category>,
    val editTaskSystem: EditTaskSystem,
)
