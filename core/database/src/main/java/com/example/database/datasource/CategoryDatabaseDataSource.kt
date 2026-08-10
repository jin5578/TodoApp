package com.example.database.datasource

import com.example.database.category.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryDatabaseDataSource {
    fun getAllCategory(): Flow<List<CategoryEntity>>
    suspend fun insertCategory(entity: CategoryEntity)
}