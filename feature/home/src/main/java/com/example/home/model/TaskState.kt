package com.example.home.model

enum class TaskState(val key: String) {
    PREVIOUS(
        key = "previous",
    ),
    COMPLETED_TODAY(
        key = "completedToday"
    )
}