package com.example.model.edittask

import com.example.model.Category
import com.example.model.Task
import com.example.model.edittask.EditTaskSystem

data class EditTask(
    val task: Task,
    val categories: List<Category>,
    val editTaskSystem: EditTaskSystem
)