package com.example.model.addtask

import com.example.model.TimePickerType
import java.util.Locale

data class AddTaskSystem(
    val locale: Locale,
    val timePickerType: TimePickerType
)