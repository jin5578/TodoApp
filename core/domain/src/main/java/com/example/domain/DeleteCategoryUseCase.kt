package com.example.domain

import com.example.data_api.repository.CategoryRepository
import com.example.model.Category
import javax.inject.Inject

class DeleteCategoryUseCase
@Inject
constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(category: Category) = categoryRepository.deleteCategory(category = category)
}
