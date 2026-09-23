package com.example.model.edit_task

import com.example.model.TimePickerType
import java.util.Locale

data class EditTaskSystem(
    val locale: Locale,
    val timePickerType: TimePickerType,
)
