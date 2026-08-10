package com.example.database.utils

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object LocalDateConverter {
    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    @JvmStatic
    fun toString(value: LocalDate?): String? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
        return value?.format(formatter)
    }
}