package com.example.database.datasource

import com.example.database.task.TaskEntity
import com.example.database.task.TaskWithSubTasksEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

interface TaskDatabaseDataSource {
    suspend fun insertTask(entity: TaskEntity)

    fun getTasks(): Flow<List<TaskWithSubTasksEntity>>
    fun getTasksByDate(date: LocalDate): Flow<List<TaskWithSubTasksEntity>>
    fun getTaskCountByDate(date: LocalDate): Flow<Int>
    fun getTasksByDateRange(
        fromDate: LocalDate,
        toDate: LocalDate
    ): Flow<List<TaskWithSubTasksEntity>>

    fun getTasksByState(isCompleted: Boolean): Flow<List<TaskWithSubTasksEntity>>
    fun getTasksByCategory(categoryId: Long): Flow<List<TaskWithSubTasksEntity>>
    fun getFlowTaskById(id: Long): Flow<TaskWithSubTasksEntity>
    suspend fun getTaskById(id: Long): TaskWithSubTasksEntity

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

    suspend fun deleteAllTask()
    suspend fun deleteTaskById(id: Long)
    suspend fun deleteTaskByEntity(entity: TaskEntity)
}