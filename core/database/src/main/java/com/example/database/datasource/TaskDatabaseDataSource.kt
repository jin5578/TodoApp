package com.example.database.datasource

import com.example.database.task.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

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
    suspend fun updateTaskMemo(taskId: Long, memoTitle: String, memoContent: String)
    suspend fun deleteTask(entity: TaskEntity)
    suspend fun deleteAllTask()
}