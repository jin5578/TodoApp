package com.example.database.utils

import androidx.room.TypeConverter
import java.time.LocalTime

object LocalTimeConverter {
    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): LocalTime? =
        value?.let { LocalTime.parse(it) }

    @TypeConverter
    @JvmStatic
    fun toString(localTime: LocalTime?): String? =
        localTime?.toString()
}