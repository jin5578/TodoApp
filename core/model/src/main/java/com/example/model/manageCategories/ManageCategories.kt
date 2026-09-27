package com.example.model.manageCategories

import com.example.model.Category
import com.example.model.Task

data class ManageCategories(
    val categories: List<Category>,
    val tasks: List<Task>,
)
