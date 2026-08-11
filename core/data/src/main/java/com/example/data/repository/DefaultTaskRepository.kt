package com.example.data.repository

import com.example.data_api.repository.TaskRepository
import com.example.database.datasource.TaskDatabaseDataSource
import com.example.database.task.TaskEntity
import com.example.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

internal class DefaultTaskRepository @Inject constructor(
    private val taskDataSource: TaskDatabaseDataSource
) : TaskRepository {
    override fun getTaskCountByDate(date: LocalDate): Flow<Int> =
        taskDataSource.getTaskCountByDate(date = date)

    override suspend fun insertTask(task: Task) =
        taskDataSource.insertTask(entity = task.toTaskEntity())

    private fun Task.toTaskEntity() = TaskEntity(
        id = this.id,
        uuid = this.uuid,
        title = this.title,
        isCompleted = this.isCompleted,
        isRemind = this.isRemind,
        time = this.time,
        date = this.date,
        epochDay = this.date.toEpochDay(),
        memo = this.memo,
        priority = this.priority,
        categoryId = this.categoryId,
        reminderTime = this.reminderTime,
    )
}