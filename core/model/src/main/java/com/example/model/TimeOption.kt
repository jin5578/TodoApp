package com.example.model

import androidx.annotation.StringRes
import java.time.LocalTime

data class TimeOption(
    @param:StringRes val resId: Int,
    val time: LocalTime,
    val onClick: (LocalTime) -> Unit,
)