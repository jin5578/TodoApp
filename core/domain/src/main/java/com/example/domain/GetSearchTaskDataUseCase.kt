package com.example.domain

import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.model.search_task.SearchTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSearchTaskDataUseCase
@Inject
constructor(
    private val systemRepository: SystemRepository,
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(keyword: String): Flow<SearchTask> = combine(
        flow =
        if (keyword.isBlank()) {
            taskRepository.getTasks()
        } else {
            taskRepository.getTasksByKeyword(keyword = keyword)
        },
        flow2 = systemRepository.getSearchTaskSystem(),
    ) { tasks, searchTaskSystem ->
        SearchTask(
            tasks = tasks,
            searchTaskSystem = searchTaskSystem,
        )
    }
}
