package com.example.data_api.repository

import com.example.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskRepository {
    fun getTaskCountByDate(date: LocalDate): Flow<Int>
    fun getFlowTaskById(id: Long): Flow<Task>
    suspend fun getTaskById(id: Long): Task
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}