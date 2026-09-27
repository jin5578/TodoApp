package com.example.domain

import com.example.dataApi.repository.SubTaskRepository
import javax.inject.Inject

class DeleteSubTaskUseCase
@Inject
constructor(
    private val subTaskRepository: SubTaskRepository,
) {
    suspend operator fun invoke(id: Long) = subTaskRepository.deleteSubTaskById(id = id)
}
