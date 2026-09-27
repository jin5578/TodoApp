package com.example.dataApi.repository

import com.example.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategory(): Flow<List<Category>>
    suspend fun getCategoryById(id: Long): Category
    suspend fun insertCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    suspend fun deleteAllCategory()
}
