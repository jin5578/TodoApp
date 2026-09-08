package com.example.home.utils

import com.example.home.model.TaskState
import com.example.model.PriorityType
import com.example.model.ReminderTimeType
import com.example.design_system.R as DesignSystemR

internal fun PriorityType.getTitleResId(): Int =
    when (this) {
        PriorityType.LOW -> DesignSystemR.string.low
        PriorityType.MEDIUM -> DesignSystemR.string.medium
        PriorityType.HIGH -> DesignSystemR.string.high
    }

internal fun ReminderTimeType.getTitleResId(): Int =
    when (this) {
        ReminderTimeType.ON_TIME -> DesignSystemR.string.on_time
        ReminderTimeType.TEN_MINUTES_BEFORE -> DesignSystemR.string.ten_minutes_before
        ReminderTimeType.THIRTY_MINUTES_BEFORE -> DesignSystemR.string.thirty_minutes_before
        ReminderTimeType.ONE_HOUR_BEFORE -> DesignSystemR.string.one_hour_before
    }

internal fun TaskState.getTitleResId(): Int =
    when (this) {
        TaskState.PREVIOUS -> DesignSystemR.string.previous
        TaskState.COMPLETED_TODAY -> DesignSystemR.string.completed_today
    }