package com.example.model

enum class SortByType(
    val key: String,
) {
    DUE_DATE_AND_TIME(
        key = "dueDateAndTime",
    ),
    TASK_CREATION_TIME_ASC(
        key = "taskCreationTimeASC",
    ),
    TASK_CREATION_TIME_DESC(
        key = "taskCreationTimeDESC",
    ),
    /*MANUAL(
        key = "manual",
    )*/
}
