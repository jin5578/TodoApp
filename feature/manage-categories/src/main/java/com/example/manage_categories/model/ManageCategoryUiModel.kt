package com.example.manage_categories.model

import com.example.model.Category

data class ManageCategoryUiModel(
    val category: Category,
    val taskCount: Int,
)
