package com.example.model.calendar

import com.example.model.Category
import com.example.model.Task

data class Calendar(
    val tasks: List<Task>,
    val categories: List<Category>,
    val calendarSystem: CalendarSystem,
)