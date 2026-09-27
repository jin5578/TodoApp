package com.example.domain

import com.example.dataApi.repository.SystemRepository
import com.example.dataApi.repository.TaskRepository
import com.example.model.searchTask.SearchTask
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
