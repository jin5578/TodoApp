package com.example.database.task

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task WHERE date = :date")
    fun getTasksByDate(date: String): Flow<List<TaskEntity>>
}