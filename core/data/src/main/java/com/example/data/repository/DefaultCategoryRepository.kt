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

    private fun CategoryEntity.toCategory() = Category(
        id = this.id,
        title = this.title,
        colorValue = this.colorValue
    )
}