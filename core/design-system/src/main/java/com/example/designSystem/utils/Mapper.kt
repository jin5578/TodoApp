package com.example.designSystem.utils

import com.example.designSystem.R
import com.example.model.PriorityType
import com.example.model.SortByType

internal fun PriorityType.getTitleResId(): Int = when (this) {
    PriorityType.LOW -> R.string.low
    PriorityType.MEDIUM -> R.string.medium
    PriorityType.HIGH -> R.string.high
}

internal fun SortByType.getTitleResId(): Int = when (this) {
    SortByType.DUE_DATE_AND_TIME -> R.string.due_date_and_time
    SortByType.TASK_CREATION_TIME_ASC -> R.string.task_creation_time_asc
    SortByType.TASK_CREATION_TIME_DESC -> R.string.task_creation_time_desc
    /*SortByType.MANUAL -> R.string.manual*/
}
