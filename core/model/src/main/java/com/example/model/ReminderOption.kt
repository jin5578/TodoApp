package com.example.model

import androidx.annotation.StringRes

data class ReminderOption(
    @param:StringRes val title: Int,
    val isRemind: Boolean,
    val onClick: () -> Unit,
)