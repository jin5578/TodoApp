package com.example.model

import androidx.annotation.StringRes
import java.time.LocalDateTime

data class TimeOption(
    @param:StringRes val resId: Int,
    val time: LocalDateTime?,
    val onClick: ((LocalDateTime?) -> Unit)? = null,
)