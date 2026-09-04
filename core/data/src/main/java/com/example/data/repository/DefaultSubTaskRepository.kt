package com.example.data.repository

import com.example.data_api.repository.SubTaskRepository
import com.example.database.datasource.SubTaskDatabaseDataSource
import com.example.database.task.SubTaskEntity
import com.example.model.SubTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultSubTaskRepository @Inject constructor(
    private val subTaskDataSource: SubTaskDatabaseDataSource
) : SubTaskRepository {
    override fun getSubTasksByParentId(parentId: Long): Flow<List<SubTask>> =
        subTaskDataSource.getSubTasksByParentId(parentId = parentId)
            .map { entities ->
                entities.map { entity ->
                    entity.toSubTask()
                }
            }

    override suspend fun insertSubTask(subTask: SubTask) =
        subTaskDataSource.insertSubTask(entity = subTask.toSubTaskEntity())


    override suspend fun updateSubTask(subTask: SubTask) =
        subTaskDataSource.updateSubTask(entity = subTask.toSubTaskEntity())

    override suspend fun updateSubTasks(subTasks: List<SubTask>) =
        subTaskDataSource.updateSubTasks(
            entities =
                subTasks.map { subTask -> subTask.toSubTaskEntity() }
        )

    override suspend fun updateSubTaskTitle(
        id: Long,
        title: String
    ) = subTaskDataSource.updateSubTaskTitle(id = id, title = title)

    override suspend fun updateSubTaskCompleted(
        id: Long,
        isCompleted: Boolean
    ) = subTaskDataSource.updateSubTaskCompleted(
        id = id,
        isCompleted = isCompleted
    )

    override suspend fun deleteSubTaskById(id: Long) =
        subTaskDataSource.deleteSubTaskById(id = id)

    override suspend fun deleteSubTaskBySubTask(subTask: SubTask) =
        subTaskDataSource.deleteSubTaskByEntity(entity = subTask.toSubTaskEntity())

    override suspend fun syncSubTasks(
        parentId: Long,
        subTasks: List<SubTask>
    ) = subTaskDataSource.syncSubTasks(
        parentId = parentId,
        entities = subTasks.map { subTask -> subTask.toSubTaskEntity() }
    )

    private fun SubTaskEntity.toSubTask() = SubTask(
        id = this.id,
        parentId = this.parentId,
        title = this.title,
        isCompleted = this.isCompleted,
    )

    private fun SubTask.toSubTaskEntity() = SubTaskEntity(
        id = id,
        parentId = parentId,
        title = title,
        isCompleted = isCompleted,
    )
}