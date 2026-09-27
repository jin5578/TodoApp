package com.example.domain

import com.example.dataApi.repository.SubTaskRepository
import com.example.model.SubTask
import javax.inject.Inject

class SyncSubTasksUseCase
@Inject
constructor(
    private val subTaskRepository: SubTaskRepository,
) {
    suspend operator fun invoke(
        parentId: Long,
        subTasks: List<SubTask>,
    ) = subTaskRepository.syncSubTasks(parentId = parentId, subTasks = subTasks)
}
