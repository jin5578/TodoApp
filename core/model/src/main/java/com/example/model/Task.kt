package com.example.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Task(
    val id: Long = 0L,
    val uuid: String,
    val title: String,
    val isCompleted: Boolean,
    val isRemind: Boolean,
    val date: LocalDate,
    val time: LocalDateTime?,
    val reminderTime: LocalDateTime?,
    val memoTitle: String,
    val memoContent: String,
    val memoUpdatedAt: LocalDateTime?,
    val priority: Int = 0,
    val categoryId: Long = -1L,
    val symbol: Int = -1,
)