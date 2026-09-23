package com.example.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import java.time.LocalDate
import java.time.LocalDateTime

data class Task(
    val id: Long = 0L,
    val uuid: String,
    val title: String,
    val isCompleted: Boolean = false,
    val date: LocalDate,
    val time: LocalDateTime?,
    val reminderTime: LocalDateTime?,
    val memoTitle: String = "",
    val memoContent: String = "",
    val memoUpdatedAt: LocalDateTime? = null,
    val completedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime,
    val priority: Int = 0,
    val categoryId: Long = -1L,
    val symbol: Int = -1,
    val subTasks: List<SubTask> = emptyList(),
)

fun List<Task>.toUiModels(): ImmutableList<TaskUiModel> = map { task -> task.toUiModel() }.toPersistentList()

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
