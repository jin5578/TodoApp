package com.example.database.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

@Dao
interface TaskDao {
    /* INSERT */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(entity: TaskEntity)

    /* SELECT */
    @Transaction
    @Query(value = "SELECT * FROM task ORDER BY date ASC")
    fun getTasks(): Flow<List<TaskWithSubTasksEntity>>

    @Transaction
    @Query("SELECT * FROM task WHERE date = :date")
    fun getTasksByDate(date: String): Flow<List<TaskWithSubTasksEntity>>

    @Transaction
    @Query("SELECT * FROM task WHERE epochDay BETWEEN :fromDate AND :toDate ORDER BY date ASC")
    fun getTasksByEpochDayRange(
        fromDate: Long,
        toDate: Long
    ): Flow<List<TaskWithSubTasksEntity>>

    @Transaction
    @Query("SELECT * FROM task WHERE isCompleted = :isCompleted ORDER BY date ASC")
    fun getTasksByState(isCompleted: Boolean): Flow<List<TaskWithSubTasksEntity>>

    @Transaction
    @Query("SELECT * FROM task WHERE categoryId = :categoryId ORDER BY date ASC")
    fun getTasksByCategory(categoryId: Long): Flow<List<TaskWithSubTasksEntity>>

    @Transaction
    @Query(
        "SELECT * FROM task WHERE " +
                "title LIKE '%' || :keyword || '%' OR " +
                "memoTitle LIKE '%' || :keyword || '%' OR " +
                "memoContent LIKE '%' || :keyword || '%'"
    )
    fun getTasksByKeyword(keyword: String): Flow<List<TaskWithSubTasksEntity>>

    @Transaction
    @Query("SELECT * FROM task WHERE categoryId = :categoryId AND date = :date ORDER BY date ASC")
    fun getTasksByCategoryAndDate(
        categoryId: Long,
        date: String
    ): Flow<List<TaskWithSubTasksEntity>>


    @Transaction
    @Query("SELECT * FROM task WHERE id=:id")
    fun getTaskById(id: Long): Flow<TaskWithSubTasksEntity>

    /* Update */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(entity: TaskEntity)

    @Query("UPDATE task SET symbol = :symbolId WHERE id = :taskId")
    suspend fun updateTaskSymbol(taskId: Long, symbolId: Int)

    @Query("UPDATE task SET memoTitle = :memoTitle, memoUpdatedAt = :memoUpdatedAt WHERE id = :id")
    suspend fun updateTaskMemoTitle(
        id: Long,
        memoTitle: String,
        memoUpdatedAt: LocalDateTime?
    )

    @Query("UPDATE task SET memoContent = :memoContent, memoUpdatedAt = :memoUpdatedAt WHERE id = :id")
    suspend fun updateTaskMemoContent(
        id: Long,
        memoContent: String,
        memoUpdatedAt: LocalDateTime?
    )

    @Query("UPDATE task SET categoryId = :categoryId WHERE id = :taskId")
    suspend fun updateTaskCategory(taskId: Long, categoryId: Long)

    @Query("UPDATE task SET title = :title WHERE id = :id")
    suspend fun updateTaskTitle(id: Long, title: String)

    @Query("UPDATE task SET date = :date WHERE id = :id")
    suspend fun updateTaskDate(id: Long, date: LocalDate)

    @Query("UPDATE task SET time = :time WHERE id = :id")
    suspend fun updateTaskTime(id: Long, time: LocalDateTime)

    @Query("UPDATE task SET reminderTime = :reminderTime WHERE id = :id")
    suspend fun updateTaskReminderTime(id: Long, reminderTime: LocalDateTime)

    @Query("UPDATE task SET date = :date, time = :time, reminderTime = :reminderTime WHERE id = :id")
    suspend fun updateTaskDateTime(
        id: Long,
        date: LocalDate,
        time: LocalDateTime?,
        reminderTime: LocalDateTime?
    )

    @Query("UPDATE task SET isCompleted = :isCompleted, completedAt = :completedAt WHERE id = :id")
    suspend fun updateTaskCompleted(
        id: Long,
        isCompleted: Boolean,
        completedAt: LocalDateTime?
    )

    /* Delete */
    @Query(value = "DELETE FROM task")
    suspend fun deleteAllTask()

    @Query("DELETE FROM task WHERE id = :id")
    suspend fun deleteTaskById(id: Long)

    @Delete
    suspend fun deleteTaskByEntity(entity: TaskEntity)

    @Query("DELETE FROM task WHERE isCompleted = :isCompleted")
    suspend fun deleteTasksByState(isCompleted: Boolean)
}