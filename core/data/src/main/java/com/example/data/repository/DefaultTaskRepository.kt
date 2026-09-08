package com.example.data.repository

import com.example.data_api.repository.TaskRepository
import com.example.database.datasource.TaskDatabaseDataSource
import com.example.database.task.SubTaskEntity
import com.example.database.task.TaskEntity
import com.example.database.task.TaskWithSubTasksEntity
import com.example.model.SubTask
import com.example.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

internal class DefaultTaskRepository @Inject constructor(
    private val taskDataSource: TaskDatabaseDataSource,
) : TaskRepository {
    override suspend fun insertTask(task: Task) =
        taskDataSource.insertTask(entity = task.toTaskEntity())

    override fun getTasks(): Flow<List<Task>> =
        taskDataSource.getTasks().map { entities ->
            entities.map { entity ->
                entity.toTask()
            }
        }

    override fun getTasksByDate(date: LocalDate): Flow<List<Task>> =
        taskDataSource.getTasksByDate(date = date).map { entities ->
            entities.map { entity ->
                entity.toTask()
            }
        }

    override fun getTaskCountByDate(date: LocalDate): Flow<Int> =
        taskDataSource.getTaskCountByDate(date = date)

    override fun getTasksByDateRange(
        fromDate: LocalDate,
        toDate: LocalDate
    ): Flow<List<Task>> =
        taskDataSource.getTasksByDateRange(fromDate = fromDate, toDate = toDate)
            .map { entities ->
                entities.map { entity ->
                    entity.toTask()
                }
            }

    override fun getTasksByState(isCompleted: Boolean): Flow<List<Task>> =
        taskDataSource.getTasksByState(isCompleted = isCompleted)
            .map { entities ->
                entities.map { entity ->
                    entity.toTask()
                }
            }

    override fun getTasksByCategory(categoryId: Long): Flow<List<Task>> =
        taskDataSource.getTasksByCategory(categoryId = categoryId)
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

    override suspend fun updateTask(task: Task) =
        taskDataSource.updateTask(entity = task.toTaskEntity())

    override suspend fun updateTaskSymbol(taskId: Long, symbolId: Int) =
        taskDataSource.updateTaskSymbol(taskId = taskId, symbolId = symbolId)

    override suspend fun updateTaskMemoTitle(
        id: Long,
        memoTitle: String,
    ) = taskDataSource.updateTaskMemoTitle(
        id = id,
        memoTitle = memoTitle,
    )

    override suspend fun updateTaskMemoContent(
        id: Long,
        memoContent: String
    ) = taskDataSource.updateTaskMemoContent(
        id = id,
        memoContent = memoContent
    )

    override suspend fun updateTaskCategory(taskId: Long, categoryId: Long) =
        taskDataSource.updateTaskCategory(
            taskId = taskId,
            categoryId = categoryId
        )

    override suspend fun updateTaskTitle(id: Long, title: String) =
        taskDataSource.updateTaskTitle(id = id, title = title)

    override suspend fun updateTaskDate(id: Long, date: LocalDate) =
        taskDataSource.updateTaskDate(id = id, date = date)

    override suspend fun updateTaskTime(id: Long, time: LocalDateTime) =
        taskDataSource.updateTaskTime(id = id, time = time)

    override suspend fun updateTaskReminderTime(
        id: Long,
        reminderTime: LocalDateTime
    ) = taskDataSource.updateTaskReminderTime(
        id = id,
        reminderTime = reminderTime
    )

    override suspend fun updateTaskDateTime(
        id: Long,
        date: LocalDate,
        time: LocalDateTime?,
        reminderTime: LocalDateTime?
    ) = taskDataSource.updateTaskDateTime(
        id = id,
        date = date,
        time = time,
        reminderTime = reminderTime
    )

    override suspend fun updateTaskCompleted(id: Long, isCompleted: Boolean) =
        taskDataSource.updateTaskCompleted(
            id = id,
            isCompleted = isCompleted
        )

    override suspend fun deleteAllTask() =
        taskDataSource.deleteAllTask()

    override suspend fun deleteTaskById(id: Long) =
        taskDataSource.deleteTaskById(id = id)

    override suspend fun deleteTaskByTask(task: Task) =
        taskDataSource.deleteTaskByEntity(entity = task.toTaskEntity())

    override suspend fun deleteTasksByState(isCompleted: Boolean) =
        taskDataSource.deleteTasksByState(isCompleted = isCompleted)

    private fun TaskWithSubTasksEntity.toTask(): Task {
        val taskEntity = this.taskEntity
        val subTasks =
            this.subTaskEntities.map { subTaskEntity -> subTaskEntity.toSubTask() }
        return Task(
            id = taskEntity.id,
            uuid = taskEntity.uuid,
            title = taskEntity.title,
            isCompleted = taskEntity.isCompleted,
            date = taskEntity.date,
            time = taskEntity.time,
            reminderTime = taskEntity.reminderTime,
            memoTitle = taskEntity.memoTitle,
            memoContent = taskEntity.memoContent,
            memoUpdatedAt = taskEntity.memoUpdatedAt,
            completedAt = taskEntity.completedAt,
            priority = taskEntity.priority,
            categoryId = taskEntity.categoryId,
            symbol = taskEntity.symbol,
            subTasks = subTasks,
        )
    }

    private fun Task.toTaskEntity() = TaskEntity(
        id = this.id,
        uuid = this.uuid,
        title = this.title,
        isCompleted = this.isCompleted,
        date = this.date,
        time = this.time,
        reminderTime = this.reminderTime,
        epochDay = this.date.toEpochDay(),
        memoTitle = this.memoTitle,
        memoContent = this.memoContent,
        memoUpdatedAt = this.memoUpdatedAt,
        completedAt = this.completedAt,
        priority = this.priority,
        categoryId = this.categoryId,
        symbol = this.symbol
    )

    private fun SubTaskEntity.toSubTask() = SubTask(
        id = this.id,
        parentId = this.parentId,
        title = this.title,
        isCompleted = this.isCompleted,
    )
}