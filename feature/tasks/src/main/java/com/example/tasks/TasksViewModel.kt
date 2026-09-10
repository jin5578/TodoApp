package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteTaskByIdUseCase
import com.example.domain.GetTasksByDateRangeUseCase
import com.example.domain.GetTasksByStateUseCase
import com.example.domain.GetTasksUseCase
import com.example.domain.UpdateTaskCompletedUseCase
import com.example.model.TasksType
import com.example.model.toUiModel
import com.example.tasks.model.TasksUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksByStateUseCase: GetTasksByStateUseCase,
    private val getTasksByDateRangeUseCase: GetTasksByDateRangeUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val updateTaskCompletedUseCase: UpdateTaskCompletedUseCase,
    private val deleteTaskByIdUseCase: DeleteTaskByIdUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<TasksUiState> =
        MutableStateFlow(value = TasksUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchTasksUiState(type: TasksType) =
        when (type) {
            TasksType.COMPLETED -> fetchTasksByState(isCompleted = true)
            TasksType.INCOMPLETE -> fetchTasksByState(isCompleted = false)
            TasksType.THIS_WEEK -> {
                val currentDate = LocalDate.now()
                val fromDate = currentDate.with(DayOfWeek.MONDAY)
                val toDate = currentDate.with(DayOfWeek.SUNDAY)
                fetchTasksByDateRange(
                    fromDate = fromDate,
                    toDate = toDate
                )
            }

            else -> fetchAllTasks()
        }

    private fun fetchTasksByState(isCompleted: Boolean) =
        viewModelScope.launch {
            getTasksByStateUseCase(isCompleted = isCompleted).map {
                TasksUiState.Screen(
                    tasks = it.tasks.map { task -> task.toUiModel() }
                        .toPersistentList(),
                    categories = it.categories.toPersistentList(),
                    locale = it.tasksSystem.locale
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
        }

    private fun fetchTasksByDateRange(fromDate: LocalDate, toDate: LocalDate) =
        viewModelScope.launch {
            getTasksByDateRangeUseCase(
                fromDate = fromDate,
                toDate = toDate
            ).map {
                TasksUiState.Screen(
                    tasks = it.tasks.map { task -> task.toUiModel() }
                        .toPersistentList(),
                    categories = it.categories.toPersistentList(),
                    locale = it.tasksSystem.locale,
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
        }

    private fun fetchAllTasks() =
        viewModelScope.launch {
            getTasksUseCase().map {
                TasksUiState.Screen(
                    tasks = it.tasks.map { task -> task.toUiModel() }
                        .toPersistentList(),
                    categories = it.categories.toPersistentList(),
                    locale = it.tasksSystem.locale
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
        }

    fun toggleTaskCompletion(taskId: Long, isCompleted: Boolean) =
        viewModelScope.launch {
            updateTaskCompletedUseCase(id = taskId, isCompleted = isCompleted)
        }

    fun deleteTask(taskId: Long, uuid: String) =
        viewModelScope.launch {
            deleteTaskByIdUseCase(id = taskId, uuid = uuid)
        }
}