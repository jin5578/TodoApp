package com.example.database.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SubTaskDao {
    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubTask(entity: SubTaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubTasks(entities: List<SubTaskEntity>)

    // Select
    @Query("SELECT * FROM sub_task WHERE parentId = :parentId ORDER BY sortOrder ASC")
    fun getSubTasksByParentId(parentId: Long): Flow<List<SubTaskEntity>>

    // Update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSubTask(entity: SubTaskEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSubTasks(entities: List<SubTaskEntity>)

    @Query("UPDATE sub_task SET title = :title WHERE id = :id")
    suspend fun updateSubTaskTitle(
        id: Long,
        title: String,
    )

    @Query("UPDATE sub_task SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateSubTaskCompleted(
        id: Long,
        isCompleted: Boolean,
    )

    // Delete
    @Query("DELETE FROM sub_task WHERE id = :id")
    suspend fun deleteSubTaskById(id: Long)

    @Delete
    suspend fun deleteSubTaskByEntity(entity: SubTaskEntity)

    @Query("DELETE FROM sub_task WHERE parentId = :parentId AND id NOT IN (:ids)")
    suspend fun deleteSubTasksNotIn(
        parentId: Long,
        ids: List<Long>,
    )

    // Sync
    @Transaction
    suspend fun syncSubTasks(
        parentId: Long,
        entities: List<SubTaskEntity>,
    ) {
        deleteSubTasksNotIn(parentId = parentId, ids = entities.map { it.id })
        insertSubTasks(
            entities =
            entities.mapIndexed { index, entity ->
                val normalizedEntity =
                    if (entity.id > 0L) entity else entity.copy(id = 0L)
                normalizedEntity.copy(sortOrder = index)
            },
        )
    }
}
