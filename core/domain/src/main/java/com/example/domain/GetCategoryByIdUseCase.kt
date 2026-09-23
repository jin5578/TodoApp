package com.example.domain

import com.example.data_api.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryByIdUseCase
@Inject
constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(id: Long) = categoryRepository.getCategoryById(id = id)
}
