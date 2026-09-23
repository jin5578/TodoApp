package com.example.database.utils

import androidx.room.TypeConverter
import java.time.LocalDateTime

object LocalDateTimeConverter {
    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): LocalDateTime? =
        value?.let { LocalDateTime.parse(it) }

    @TypeConverter
    @JvmStatic
    fun toString(localDateTime: LocalDateTime?): String? =
        localDateTime?.toString()
}