package com.example.database.datasource

import com.example.database.task.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskDatabaseDataSource {
    fun getTaskCountByDate(date: LocalDate): Flow<Int>
    fun getFlowTaskById(id: Long): Flow<TaskEntity>
    suspend fun insertTask(entity: TaskEntity)
    suspend fun updateTask(entity: TaskEntity)
}