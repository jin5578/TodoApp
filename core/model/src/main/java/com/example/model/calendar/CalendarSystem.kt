package com.example.model.calendar

import com.example.model.SortByType
import com.example.model.TimePickerType
import java.util.Locale

data class CalendarSystem(
    val locale: Locale,
    val sortByType: SortByType,
    val timePickerType: TimePickerType
)