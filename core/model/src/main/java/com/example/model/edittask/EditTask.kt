package com.example.model.edittask

import com.example.model.Category
import com.example.model.SubTask
import com.example.model.Task

data class EditTask(
    val task: Task,
    val subTasks: List<SubTask>,
    val categories: List<Category>,
    val editTaskSystem: EditTaskSystem
)