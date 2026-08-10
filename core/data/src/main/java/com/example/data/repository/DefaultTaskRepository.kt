package com.example.data.repository

import com.example.data_api.repository.TaskRepository
import com.example.database.datasource.TaskDatabaseDataSource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

internal class DefaultTaskRepository @Inject constructor(
    private val taskDataSource: TaskDatabaseDataSource
) : TaskRepository {
    override fun getTaskCountByDate(date: LocalDate): Flow<Int> =
        taskDataSource.getTaskCountByDate(date = date)
}