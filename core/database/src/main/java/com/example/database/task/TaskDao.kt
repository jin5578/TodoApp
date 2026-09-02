package com.example.database.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(entity: TaskEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(entity: TaskEntity)

    @Query("UPDATE task SET symbol = :symbolId WHERE id = :taskId")
    suspend fun updateTaskSymbol(taskId: Long, symbolId: Int)

    @Query("UPDATE task SET memoTitle = :memoTitle, memoContent = :memoContent, memoUpdatedAt = :memoUpdatedAt WHERE id = :taskId")
    suspend fun updateTaskMemo(
        taskId: Long,
        memoTitle: String,
        memoContent: String,
        memoUpdatedAt: LocalDateTime?
    )

    @Delete
    suspend fun deleteTask(entity: TaskEntity)

    @Query(value = "SELECT * FROM task ORDER BY date ASC")
    fun getAllTask(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE date = :date")
    fun getTasksByDate(date: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE epochDay BETWEEN :fromDate AND :toDate ORDER BY date ASC")
    fun getTasksByEpochDayRange(
        fromDate: Long,
        toDate: Long
    ): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE isCompleted = :isCompleted ORDER BY date ASC")
    fun getTasksByState(isCompleted: Boolean): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE categoryId = :categoryId ORDER BY date ASC")
    fun getTasksByCategory(categoryId: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE id=:id")
    fun getFlowTaskById(id: Long): Flow<TaskEntity>

    @Query("SELECT * FROM task WHERE id=:id")
    suspend fun getTaskById(id: Long): TaskEntity

    @Query(value = "DELETE FROM task")
    suspend fun deleteAllTask()
}