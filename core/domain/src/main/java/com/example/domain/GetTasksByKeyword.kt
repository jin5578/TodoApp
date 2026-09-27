package com.example.domain

import com.example.dataApi.repository.TaskRepository
import javax.inject.Inject

class GetTasksByKeyword
@Inject
constructor(
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(keyword: String) = taskRepository.getTasksByKeyword(keyword = keyword)
}
