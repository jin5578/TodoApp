package com.example.domain

import com.example.data_api.repository.SubTaskRepository
import com.example.model.SubTask
import javax.inject.Inject

class UpdateSubTasksUseCase @Inject constructor(
    private val subTaskRepository: SubTaskRepository
) {
    suspend operator fun invoke(subTasks: List<SubTask>) =
        subTaskRepository.updateSubTasks(subTasks = subTasks)
}