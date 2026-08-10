package com.example.data.repository

import com.example.data_api.repository.CategoryRepository
import com.example.database.category.CategoryEntity
import com.example.database.datasource.CategoryDatabaseDataSource
import com.example.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultCategoryRepository @Inject constructor(
    private val categoryDataSource: CategoryDatabaseDataSource,
) : CategoryRepository {
    override fun getAllCategory(): Flow<List<Category>> =
        categoryDataSource.getAllCategory().map { entities ->
            entities.map { entity ->
                entity.toCategory()
            }
        }

    override suspend fun getCategoryById(id: Long): Category =
        categoryDataSource.getCategoryById(id = id).toCategory()

    override suspend fun insertCategory(category: Category) =
        categoryDataSource.insertCategory(entity = category.toCategoryEntity())

    override suspend fun updateCategory(category: Category) =
        categoryDataSource.updateCategory(entity = category.toCategoryEntity())

    override suspend fun deleteCategory(category: Category) =
        categoryDataSource.deleteCategory(entity = category.toCategoryEntity())

    private fun CategoryEntity.toCategory() =
        Category(
            id = this.id,
            title = this.title,
            colorValue = this.colorValue
        )

    private fun Category.toCategoryEntity() =
        CategoryEntity(
            id = this.id,
            title = this.title,
            colorValue = this.colorValue
        )
}