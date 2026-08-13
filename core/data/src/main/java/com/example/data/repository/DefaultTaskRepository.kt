package com.example.data.repository

import com.example.data_api.repository.TaskRepository
import com.example.database.datasource.TaskDatabaseDataSource
import com.example.database.task.TaskEntity
import com.example.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

internal class DefaultTaskRepository @Inject constructor(
    private val taskDataSource: TaskDatabaseDataSource
) : TaskRepository {
    override fun getTasksByDate(date: LocalDate): Flow<List<Task>> =
        taskDataSource.getTasksByDate(date = date).map { entities ->
            entities.map { entity ->
                entity.toTask()
            }
        }

    override fun getTaskCountByDate(date: LocalDate): Flow<Int> =
        taskDataSource.getTaskCountByDate(date = date)

    override fun getTasksByState(isCompleted: Boolean): Flow<List<Task>> =
        taskDataSource.getTasksByState(isCompleted = isCompleted)
            .map { entities ->
                entities.map { entity ->
                    entity.toTask()
                }
            }

    override fun getFlowTaskById(id: Long): Flow<Task> =
        taskDataSource.getFlowTaskById(id = id).map { entity ->
            entity.toTask()
        }

    override suspend fun getTaskById(id: Long): Task =
        taskDataSource.getTaskById(id = id).toTask()

    override suspend fun insertTask(task: Task) =
        taskDataSource.insertTask(entity = task.toTaskEntity())

    override suspend fun updateTask(task: Task) =
        taskDataSource.updateTask(entity = task.toTaskEntity())

    override suspend fun deleteTask(task: Task) =
        taskDataSource.deleteTask(entity = task.toTaskEntity())

    private fun TaskEntity.toTask() = Task(
        id = this.id,
        uuid = this.uuid,
        title = this.title,
        isCompleted = this.isCompleted,
        isRemind = this.isRemind,
        time = this.time,
        date = this.date,
        memo = this.memo,
        priority = this.priority,
        categoryId = this.categoryId,
        reminderTime = this.reminderTime
    )

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