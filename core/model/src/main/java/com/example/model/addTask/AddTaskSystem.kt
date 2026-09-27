package com.example.model.addTask

import com.example.model.TimePickerType
import java.util.Locale

data class AddTaskSystem(
    val locale: Locale,
    val timePickerType: TimePickerType,
)
