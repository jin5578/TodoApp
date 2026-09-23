package com.example.model.add_task

import com.example.model.TimePickerType
import java.util.Locale

data class AddTaskSystem(
    val locale: Locale,
    val timePickerType: TimePickerType,
)
