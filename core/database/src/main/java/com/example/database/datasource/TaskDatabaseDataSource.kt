package com.example.database.datasource

import com.example.database.task.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskDatabaseDataSource {
    fun getTaskCountByDate(date: LocalDate): Flow<Int>
}