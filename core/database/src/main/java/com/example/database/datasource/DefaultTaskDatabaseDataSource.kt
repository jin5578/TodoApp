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
    override fun getTasksByDate(date: LocalDate): Flow<List<TaskEntity>> =
        taskDatabase.taskDao().getTasksByDate(date = date.toString())

    override fun getTaskCountByDate(date: LocalDate): Flow<Int> =
        taskDatabase.taskDao().getTasksByDate(date = date.toString())
            .map { it.count() }

    override fun getTasksByDateRange(
        fromDate: LocalDate,
        toDate: LocalDate
    ): Flow<List<TaskEntity>> =
        taskDatabase.taskDao().getTasksByEpochDayRange(
            fromDate = fromDate.toEpochDay(),
            toDate = toDate.toEpochDay()
        )

    override fun getTasksByState(isCompleted: Boolean): Flow<List<TaskEntity>> =
        taskDatabase.taskDao().getTasksByState(isCompleted = isCompleted)

    override fun getFlowTaskById(id: Long): Flow<TaskEntity> =
        taskDatabase.taskDao().getFlowTaskById(id = id)

    override suspend fun getTaskById(id: Long): TaskEntity =
        taskDatabase.taskDao().getTaskById(id = id)

    override suspend fun insertTask(entity: TaskEntity) =
        taskDatabase.taskDao().insertTask(entity = entity)

    override suspend fun updateTask(entity: TaskEntity) =
        taskDatabase.taskDao().updateTask(entity = entity)

    override suspend fun deleteTask(entity: TaskEntity) =
        taskDatabase.taskDao().deleteTask(entity = entity)
}