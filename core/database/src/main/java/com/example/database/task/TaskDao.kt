package com.example.database.task

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(entity: TaskEntity)

    @Query("SELECT * FROM task WHERE date = :date")
    fun getTasksByDate(date: String): Flow<List<TaskEntity>>
}