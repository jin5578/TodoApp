package com.example.database.datasource

import com.example.database.task.TaskDatabase
import com.example.database.task.TaskEntity
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

    override fun getFlowTaskById(id: Long): Flow<TaskEntity> =
        taskDatabase.taskDao().getFlowTaskById(id = id)

    override suspend fun insertTask(entity: TaskEntity) =
        taskDatabase.taskDao().insertTask(entity = entity)

    override suspend fun updateTask(entity: TaskEntity) =
        taskDatabase.taskDao().updateTask(entity = entity)
}