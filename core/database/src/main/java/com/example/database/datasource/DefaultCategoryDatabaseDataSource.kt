package com.example.database.datasource

import com.example.database.category.CategoryDatabase
import com.example.database.category.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultCategoryDatabaseDataSource
@Inject
constructor(
    private val categoryDatabase: CategoryDatabase,
) : CategoryDatabaseDataSource {
    override fun getAllCategory(): Flow<List<CategoryEntity>> = categoryDatabase.categoryDao().getAllCategory()

    override suspend fun getCategoryById(id: Long): CategoryEntity = categoryDatabase.categoryDao().getCategoryById(id = id)

    override suspend fun insertCategory(entity: CategoryEntity) = categoryDatabase.categoryDao().insertCategory(entity = entity)

    override suspend fun updateCategory(entity: CategoryEntity) = categoryDatabase.categoryDao().updateCategory(entity = entity)

    override suspend fun deleteCategory(entity: CategoryEntity) = categoryDatabase.categoryDao().deleteCategory(entity = entity)

    override suspend fun deleteAllCategory() = categoryDatabase.categoryDao().deleteAllCategory()
}
