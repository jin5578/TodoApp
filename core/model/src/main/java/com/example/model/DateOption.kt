package com.example.model

import androidx.annotation.StringRes
import java.time.LocalDate

data class DateOption(
    @param:StringRes val resId: Int,
    val date: LocalDate,
    val onClick: ((LocalDate) -> Unit)? = null
)