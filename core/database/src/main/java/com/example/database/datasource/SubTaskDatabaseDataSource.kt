package com.example.database.datasource

import com.example.database.task.SubTaskEntity
import kotlinx.coroutines.flow.Flow

interface SubTaskDatabaseDataSource {
    fun getSubTasksByParentId(parentId: Long): Flow<List<SubTaskEntity>>
    suspend fun insertSubTask(entity: SubTaskEntity)
    suspend fun updateSubTask(entity: SubTaskEntity)
    suspend fun updateSubTasks(entities: List<SubTaskEntity>)
    suspend fun updateSubTaskTitle(id: Long, title: String)
    suspend fun updateSubTaskCompleted(id: Long, isCompleted: Boolean)
    suspend fun deleteSubTaskById(id: Long)
    suspend fun deleteSubTaskByEntity(entity: SubTaskEntity)
    suspend fun syncSubTasks(parentId: Long, entities: List<SubTaskEntity>)
}