package com.example.utils

import com.example.model.Task

fun checkValidTask(
    task: Task,
): Pair<Boolean, String> {
    if (task.title.trim().isEmpty()) {
        return Pair(false, "Title can't be empty")
    }

    return Pair(true, "Valid task")
}