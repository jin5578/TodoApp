package com.example.designSystem.utils

import com.example.model.Task

internal fun checkValidTask(
    task: Task,
): Pair<Boolean, String> {
    if (task.title.trim().isEmpty()) {
        return Pair(false, "Title can't be empty")
    }

    return Pair(true, "Valid task")
}
