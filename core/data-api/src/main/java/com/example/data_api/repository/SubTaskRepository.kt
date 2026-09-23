package com.example.data_api.repository

import com.example.model.SubTask
import kotlinx.coroutines.flow.Flow

interface SubTaskRepository {
    fun getSubTasksByParentId(parentId: Long): Flow<List<SubTask>>
    suspend fun insertSubTask(subTask: SubTask)
    suspend fun updateSubTask(subTask: SubTask)
    suspend fun updateSubTasks(subTasks: List<SubTask>)
    suspend fun updateSubTaskTitle(id: Long, title: String)
    suspend fun updateSubTaskCompleted(id: Long, isCompleted: Boolean)
    suspend fun deleteSubTaskById(id: Long)
    suspend fun deleteSubTaskBySubTask(subTask: SubTask)
    suspend fun syncSubTasks(parentId: Long, subTasks: List<SubTask>)
}
