package com.example.tasks.utils

import com.example.tasks.model.TaskState
import com.example.tasks.model.TasksPasswordProcessType
import com.example.design_system.R as DesignSystemR

internal fun TaskState.getTitleResId(): Int =
    when (this) {
        TaskState.PREVIOUS -> DesignSystemR.string.previous
        TaskState.COMPLETED_TODAY -> DesignSystemR.string.completed_today
    }

internal fun TasksPasswordProcessType.getTitleResId(): Int =
    when (this) {
        TasksPasswordProcessType.ENTER_EXISTING_PASSWORD -> DesignSystemR.string.enter_your_password_to_open_the_app
        TasksPasswordProcessType.EXISTING_PASSWORD_MISMATCHED -> DesignSystemR.string.password_doesnt_match
    }