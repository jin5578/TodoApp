package com.example.database.datasource

import com.example.database.category.CategoryDatabase
import com.example.database.category.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultCategoryDatabaseDataSource @Inject constructor(
    private val categoryDatabase: CategoryDatabase
) : CategoryDatabaseDataSource {
    override fun getAllCategory(): Flow<List<CategoryEntity>> =
        categoryDatabase.categoryDao().getAllCategory()
}