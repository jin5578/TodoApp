package com.example.database.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query(value = "SELECT * FROM category")
    fun getAllCategory(): Flow<List<CategoryEntity>>

    @Query(value = "SELECT * FROM category WHERE id=:id")
    suspend fun getCategoryById(id: Long): CategoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(entity: CategoryEntity)
}