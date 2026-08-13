package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteTaskUseCase
import com.example.domain.GetHomeDataUseCase
import com.example.domain.GetTaskByIdUseCase
import com.example.domain.UpdateSortTaskTypeUseCase
import com.example.domain.UpdateTaskUseCase
import com.example.home.model.HomeUiState
import com.example.model.SortTaskType
import com.example.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.copy

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val updateSortTaskTypeUseCase: UpdateSortTaskTypeUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(value = HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchHome()
    }

    private fun fetchHome() =
        viewModelScope.launch {
            getHomeDataUseCase().map { home ->
                val tasks = home.tasks
                val completedTasks = tasks.filter { task ->
                    task.isCompleted
                }.toPersistentList()
                val incompleteTasks = tasks.filter { task ->
                    !task.isCompleted
                }.toPersistentList()

                val homeSystem = home.homeSystem

                HomeUiState.Success(
                    completedTasks = completedTasks,
                    incompleteTasks = incompleteTasks,
                    categories = home.categories.toPersistentList(),
                    sleepTime = homeSystem.sleepTime,
                    sortTaskType = homeSystem.sortTaskType,
                    themeType = homeSystem.themeType,
                    buildVersion = homeSystem.buildVersion,
                    locale = homeSystem.locale
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
        }

    fun updateSortTaskType(sortTaskType: SortTaskType) {
        val state = _uiState.value
        if (state !is HomeUiState.Success) return

        _uiState.value = state.copy(
            sortTaskType = sortTaskType
        )

        viewModelScope.launch {
            updateSortTaskTypeUseCase(sortTaskType = sortTaskType)
        }
    }

    fun deleteTask(id: Long) =
        viewModelScope.launch {
            val task = getTaskByIdUseCase(id = id)
            deleteTaskUseCase(task = task)
        }

    fun toggleTaskCompletion(id: Long, isCompleted: Boolean) =
        viewModelScope.launch {
            val task = getTaskByIdUseCase(id = id).copy(
                isCompleted = isCompleted
            )
            updateTaskUseCase(task = task)
        }
}