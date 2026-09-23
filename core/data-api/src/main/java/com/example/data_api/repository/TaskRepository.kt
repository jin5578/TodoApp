package com.example.data_api.repository

import com.example.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

interface TaskRepository {
    suspend fun insertTask(task: Task)

    fun getTasks(): Flow<List<Task>>
    fun getTasksByDate(date: LocalDate): Flow<List<Task>>
    fun getTaskCountByDate(date: LocalDate): Flow<Int>
    fun getTasksByDateRange(
        fromDate: LocalDate,
        toDate: LocalDate
    ): Flow<List<Task>>

    fun getTasksByState(isCompleted: Boolean): Flow<List<Task>>
    fun getTasksByCategory(categoryId: Long): Flow<List<Task>>
    fun getTasksByKeyword(keyword: String): Flow<List<Task>>
    fun getTasksByCategoryAndDate(
        categoryId: Long,
        date: LocalDate
    ): Flow<List<Task>>

    fun getTaskById(id: Long): Flow<Task>
    suspend fun updateTask(task: Task)
    suspend fun updateTaskSymbol(taskId: Long, symbolId: Int)
    suspend fun updateTaskMemoTitle(id: Long, memoTitle: String)
    suspend fun updateTaskMemoContent(id: Long, memoContent: String)
    suspend fun updateTaskCategory(taskId: Long, categoryId: Long)
    suspend fun updateTaskTitle(id: Long, title: String)
    suspend fun updateTaskDate(id: Long, date: LocalDate)
    suspend fun updateTaskTime(id: Long, time: LocalDateTime)
    suspend fun updateTaskReminderTime(id: Long, reminderTime: LocalDateTime)
    suspend fun updateTaskDateTime(
        id: Long,
        date: LocalDate,
        time: LocalDateTime?,
        reminderTime: LocalDateTime?
    )

    suspend fun updateTaskCompleted(id: Long, isCompleted: Boolean)

    suspend fun deleteAllTask()
    suspend fun deleteTaskById(id: Long)
    suspend fun deleteTaskByTask(task: Task)
    suspend fun deleteTasksByState(isCompleted: Boolean)
}