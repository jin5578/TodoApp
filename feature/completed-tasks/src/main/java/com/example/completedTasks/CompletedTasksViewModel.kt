package com.example.completedTasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.completedTasks.model.CompletedTasksUiState
import com.example.completedTasks.model.TaskDateGroup
import com.example.domain.DeleteTasksByStateUseCase
import com.example.domain.GetCompletedTasksDataUseCase
import com.example.domain.UpdateSubTaskCompletedUseCase
import com.example.domain.UpdateTaskCompletedUseCase
import com.example.domain.UpdateTaskSymbolUseCase
import com.example.model.Task
import com.example.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedTasksViewModel @Inject constructor(
    private val getCompletedTasksDataUseCase: GetCompletedTasksDataUseCase,
    private val updateTaskCompletedUseCase: UpdateTaskCompletedUseCase,
    private val updateTaskSymbolUseCase: UpdateTaskSymbolUseCase,
    private val updateSubTaskCompletedUseCase: UpdateSubTaskCompletedUseCase,
    private val deleteTasksByStateUseCase: DeleteTasksByStateUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<CompletedTasksUiState> =
        MutableStateFlow(value = CompletedTasksUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchCompletedTasksUiState()
    }

    private fun fetchCompletedTasksUiState() = viewModelScope.launch {
        getCompletedTasksDataUseCase().map { completedTasks ->
            val taskDateGroups = completedTasks.tasks.toTaskDateGroups()
            val completedTasksSystem = completedTasks.completedTasksSystem

            CompletedTasksUiState.Screen(
                taskDateGroups = taskDateGroups,
                locale = completedTasksSystem.locale,
            )
        }.catch { throwable ->
            _errorFlow.emit(value = throwable)
        }.collect {
            _uiState.value = it
        }
    }

    fun updateTaskCompletion(id: Long, isCompleted: Boolean) = viewModelScope.launch {
        updateTaskCompletedUseCase(
            id = id,
            isCompleted = isCompleted,
        )
    }

    fun deleteAllCompletedTasks() = viewModelScope.launch {
        deleteTasksByStateUseCase(isCompleted = true)
    }

    fun updateTaskSymbol(taskId: Long, symbolId: Int) = viewModelScope.launch {
        updateTaskSymbolUseCase(
            taskId = taskId,
            symbolId = symbolId,
        )
    }

    fun toggleSubTaskCompletion(subTaskId: Long, isCompleted: Boolean) = viewModelScope.launch {
        updateSubTaskCompletedUseCase(
            id = subTaskId,
            isCompleted = isCompleted,
        )
    }

    private fun List<Task>.toTaskDateGroups(): ImmutableList<TaskDateGroup> = filter { task -> task.completedAt != null }
        .groupBy { task -> task.completedAt!!.toLocalDate() }
        .toSortedMap()
        .map { (date, tasks) ->
            TaskDateGroup(
                taskDate = date,
                tasks = tasks.map { task -> task.toUiModel() }
                    .toPersistentList(),
            )
        }.toPersistentList()
}
