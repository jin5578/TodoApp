package com.example.domain

import com.example.data_api.repository.CategoryRepository
import com.example.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoryUseCase
@Inject
constructor(
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke(): Flow<List<Category>> = categoryRepository.getAllCategory()
}
