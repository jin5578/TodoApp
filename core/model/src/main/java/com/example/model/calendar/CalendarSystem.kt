package com.example.model.calendar

import com.example.model.SortTaskType
import java.util.Locale

data class CalendarSystem(
    val locale: Locale,
    val sortTaskType: SortTaskType,
)