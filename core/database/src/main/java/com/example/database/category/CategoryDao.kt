package com.example.database.category

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query(value = "SELECT * FROM category")
    fun getAllCategory(): Flow<List<CategoryEntity>>
}