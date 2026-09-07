package com.example.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate
import java.time.LocalDateTime

data class TaskUiModel(
    val id: Long,
    val uuid: String,
    val title: String,
    val isCompleted: Boolean,
    val date: LocalDate,
    val time: LocalDateTime?,
    val reminderTime: LocalDateTime?,
    val memoTitle: String,
    val memoContent: String,
    val memoUpdatedAt: LocalDateTime?,
    val priority: Int,
    val categoryId: Long,
    val symbol: Int,
    val subTasks: ImmutableList<SubTask>,
)

fun Task.toUiModel(): TaskUiModel = TaskUiModel(
    id = id,
    uuid = uuid,
    title = title,
    isCompleted = isCompleted,
    date = date,
    time = time,
    reminderTime = reminderTime,
    memoTitle = memoTitle,
    memoContent = memoContent,
    memoUpdatedAt = memoUpdatedAt,
    priority = priority,
    categoryId = categoryId,
    symbol = symbol,
    subTasks = subTasks.toImmutableList(),
)