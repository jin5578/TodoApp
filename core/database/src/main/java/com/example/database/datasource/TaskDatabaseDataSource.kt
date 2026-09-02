package com.example.database.datasource

import com.example.database.task.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

interface TaskDatabaseDataSource {
    fun getAllTask(): Flow<List<TaskEntity>>
    fun getTasksByDate(date: LocalDate): Flow<List<TaskEntity>>
    fun getTaskCountByDate(date: LocalDate): Flow<Int>
    fun getTasksByDateRange(
        fromDate: LocalDate,
        toDate: LocalDate
    ): Flow<List<TaskEntity>>

    fun getTasksByState(isCompleted: Boolean): Flow<List<TaskEntity>>
    fun getTasksByCategory(categoryId: Long): Flow<List<TaskEntity>>
    fun getFlowTaskById(id: Long): Flow<TaskEntity>
    suspend fun getTaskById(id: Long): TaskEntity
    suspend fun insertTask(entity: TaskEntity)
    suspend fun updateTask(entity: TaskEntity)
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

    suspend fun deleteTask(entity: TaskEntity)
    suspend fun deleteAllTask()
}