package com.example.database.datasource

import com.example.database.task.TaskDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class DefaultTaskDatabaseDataSource @Inject constructor(
    private val taskDatabase: TaskDatabase
) : TaskDatabaseDataSource {
    override fun getTaskCountByDate(date: LocalDate): Flow<Int> =
        taskDatabase.taskDao().getTasksByDate(date = date.toString())
            .map { it.count() }
}