package com.example.model.add_task

import com.example.model.Category
import java.time.LocalDate

data class AddTask(
    val date: LocalDate,
    val taskCount: Int,
    val categories: List<Category>,
    val addTaskSystem: AddTaskSystem,
)
