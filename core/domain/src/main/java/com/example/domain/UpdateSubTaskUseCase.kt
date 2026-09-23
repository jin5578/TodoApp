package com.example.domain

import com.example.data_api.repository.SubTaskRepository
import com.example.model.SubTask
import javax.inject.Inject

class UpdateSubTaskUseCase @Inject constructor(
    private val subTaskRepository: SubTaskRepository
) {
    suspend operator fun invoke(subTask: SubTask) =
        subTaskRepository.updateSubTask(subTask = subTask)
}