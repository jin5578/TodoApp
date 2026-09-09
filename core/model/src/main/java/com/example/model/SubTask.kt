package com.example.model

data class SubTask(
    val id: Long,
    val parentId: Long,
    val title: String,
    val isCompleted: Boolean,
    val sortOrder: Int,
)