package com.example.model

import androidx.annotation.StringRes
import java.time.LocalDate
import java.time.LocalDateTime

data class ReminderOption(
    @param:StringRes val resId: Int,
    val time: LocalDateTime?,
)