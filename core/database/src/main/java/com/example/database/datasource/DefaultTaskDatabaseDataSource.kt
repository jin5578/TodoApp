package com.example.database.datasource

import com.example.database.task.TaskDatabase
import com.example.database.task.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class DefaultTaskDatabaseDataSource @Inject constructor(
    private val taskDatabase: TaskDatabase
) : TaskDatabaseDataSource {
    override fun getAllTask(): Flow<List<TaskEntity>> =
        taskDatabase.taskDao().getAllTask()

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

    override fun getTasksByCategory(categoryId: Long): Flow<List<TaskEntity>> =
        taskDatabase.taskDao().getTasksByCategory(categoryId)

    override fun getFlowTaskById(id: Long): Flow<TaskEntity> =
        taskDatabase.taskDao().getFlowTaskById(id = id)

    override suspend fun getTaskById(id: Long): TaskEntity =
        taskDatabase.taskDao().getTaskById(id = id)

    override suspend fun insertTask(entity: TaskEntity) =
        taskDatabase.taskDao().insertTask(entity = entity)

    override suspend fun updateTask(entity: TaskEntity) =
        taskDatabase.taskDao().updateTask(entity = entity)

    override suspend fun updateTaskSymbol(taskId: Long, symbolId: Int) =
        taskDatabase.taskDao().updateTaskSymbol(
            taskId = taskId,
            symbolId = symbolId
        )

    override suspend fun updateTaskMemoTitle(
        id: Long,
        memoTitle: String,
    ) = taskDatabase.taskDao().updateTaskMemoTitle(
        id = id,
        memoTitle = memoTitle,
        memoUpdatedAt = LocalDateTime.now()
    )

    override suspend fun updateTaskMemoContent(
        id: Long,
        memoContent: String
    ) = taskDatabase.taskDao().updateTaskMemoContent(
        id = id,
        memoContent = memoContent,
        memoUpdatedAt = LocalDateTime.now()
    )

    override suspend fun updateTaskCategory(taskId: Long, categoryId: Long) =
        taskDatabase.taskDao()
            .updateTaskCategory(taskId = taskId, categoryId = categoryId)

    override suspend fun updateTaskTitle(id: Long, title: String) =
        taskDatabase.taskDao()
            .updateTaskTitle(id = id, title = title)

    override suspend fun updateTaskDate(id: Long, date: LocalDate) =
        taskDatabase.taskDao()
            .updateTaskDate(id = id, date = date)

    override suspend fun updateTaskTime(id: Long, time: LocalDateTime) =
        taskDatabase.taskDao()
            .updateTaskTime(id = id, time = time)

    override suspend fun updateTaskReminderTime(
        id: Long,
        reminderTime: LocalDateTime
    ) = taskDatabase.taskDao().updateTaskReminderTime(
        id = id,
        reminderTime = reminderTime
    )

    override suspend fun updateTaskDateTime(
        id: Long,
        date: LocalDate,
        time: LocalDateTime?,
        reminderTime: LocalDateTime?
    ) = taskDatabase.taskDao().updateTaskDateTime(
        id = id,
        date = date,
        time = time,
        reminderTime = reminderTime
    )

    override suspend fun updateTaskCompleted(id: Long, isCompleted: Boolean) =
        taskDatabase.taskDao().updateTaskCompleted(
            id = id,
            isCompleted = isCompleted
        )

    override suspend fun deleteTask(entity: TaskEntity) =
        taskDatabase.taskDao().deleteTask(entity = entity)

    override suspend fun deleteAllTask() =
        taskDatabase.taskDao().deleteAllTask()
}