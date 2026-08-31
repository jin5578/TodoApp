package com.example.model.home

import com.example.model.SortTaskType
import com.example.model.ThemeType
import com.example.model.TimePickerType
import java.time.LocalTime
import java.util.Locale

data class HomeSystem(
    val sortTaskType: SortTaskType,
    val locale: Locale,
    val timePickerType: TimePickerType,
)