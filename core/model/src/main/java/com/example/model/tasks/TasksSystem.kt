package com.example.model.tasks

import com.example.model.SortByType
import com.example.model.TimePickerType
import java.util.Locale

data class TasksSystem(
    val sortByType: SortByType,
    val locale: Locale,
    val timePickerType: TimePickerType,
)