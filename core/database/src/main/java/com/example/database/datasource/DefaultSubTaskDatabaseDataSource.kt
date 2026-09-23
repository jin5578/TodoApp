package com.example.database.datasource

import com.example.database.task.SubTaskEntity
import com.example.database.task.TaskDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultSubTaskDatabaseDataSource
@Inject
constructor(
    private val taskDatabase: TaskDatabase,
) : SubTaskDatabaseDataSource {
    override fun getSubTasksByParentId(parentId: Long): Flow<List<SubTaskEntity>> = taskDatabase.subTaskDao().getSubTasksByParentId(parentId = parentId)

    override suspend fun insertSubTask(entity: SubTaskEntity) = taskDatabase.subTaskDao().insertSubTask(entity = entity)

    override suspend fun updateSubTask(entity: SubTaskEntity) = taskDatabase.subTaskDao().updateSubTask(entity = entity)

    override suspend fun updateSubTasks(entities: List<SubTaskEntity>) = taskDatabase.subTaskDao().updateSubTasks(entities = entities)

    override suspend fun updateSubTaskTitle(
        id: Long,
        title: String,
    ) = taskDatabase.subTaskDao().updateSubTaskTitle(id = id, title = title)

    override suspend fun updateSubTaskCompleted(
        id: Long,
        isCompleted: Boolean,
    ) = taskDatabase
        .subTaskDao()
        .updateSubTaskCompleted(id = id, isCompleted = isCompleted)

    override suspend fun deleteSubTaskById(id: Long) = taskDatabase.subTaskDao().deleteSubTaskById(id = id)

    override suspend fun deleteSubTaskByEntity(entity: SubTaskEntity) = taskDatabase.subTaskDao().deleteSubTaskByEntity(entity = entity)

    override suspend fun syncSubTasks(
        parentId: Long,
        entities: List<SubTaskEntity>,
    ) = taskDatabase
        .subTaskDao()
        .syncSubTasks(parentId = parentId, entities = entities)
}
