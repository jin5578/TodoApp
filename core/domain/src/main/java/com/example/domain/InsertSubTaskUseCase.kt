package com.example.domain

import com.example.data_api.repository.SubTaskRepository
import com.example.model.SubTask
import javax.inject.Inject

class InsertSubTaskUseCase
@Inject
constructor(
    private val subTaskRepository: SubTaskRepository,
) {
    suspend operator fun invoke(subTask: SubTask) = subTaskRepository.insertSubTask(subTask = subTask)
}
