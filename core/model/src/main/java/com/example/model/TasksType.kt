package com.example.model

enum class TasksType(
    val title: String,
) {
    COMPLETED(title = "Completed"),
    INCOMPLETE(title = "Incompleted"),
    THIS_WEEK(title = "This week"),
    ALL(title = "All"),
}

fun String.toTasksType() = when (this) {
    "Completed" -> TasksType.COMPLETED
    "Incompleted" -> TasksType.INCOMPLETE
    "This week" -> TasksType.THIS_WEEK
    else -> TasksType.ALL
}
