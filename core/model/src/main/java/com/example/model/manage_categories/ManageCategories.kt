package com.example.model.manage_categories

import com.example.model.Category
import com.example.model.Task

data class ManageCategories(
    val categories: List<Category>,
    val tasks: List<Task>,
)