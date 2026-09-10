package com.example.model.home

import com.example.model.SortByType
import com.example.model.TimePickerType
import java.util.Locale

data class HomeSystem(
    val sortByType: SortByType,
    val locale: Locale,
    val timePickerType: TimePickerType,
)