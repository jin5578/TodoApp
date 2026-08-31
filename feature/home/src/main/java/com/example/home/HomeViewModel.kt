package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.CheckPasswordUseCase
import com.example.domain.DeleteAllDataUseCase
import com.example.domain.DeleteTaskUseCase
import com.example.domain.GetHasBiometricEnabledUseCase
import com.example.domain.GetHasExistingPasswordUseCase
import com.example.domain.GetHomeDataUseCase
import com.example.domain.GetTaskByIdUseCase
import com.example.domain.InsertTaskUseCase
import com.example.domain.UpdateSortTaskTypeUseCase
import com.example.domain.UpdateTaskSymbolUseCase
import com.example.domain.UpdateTaskUseCase
import com.example.home.model.HomeUiState
import com.example.home.model.TaskState
import com.example.home.model.TaskStateGroup
import com.example.model.HomePasswordProcessType
import com.example.model.SortTaskType
import com.example.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHasBiometricEnabledUseCase: GetHasBiometricEnabledUseCase,
    private val getHasExistingPasswordUseCase: GetHasExistingPasswordUseCase,
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val updateSortTaskTypeUseCase: UpdateSortTaskTypeUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val checkPasswordUseCase: CheckPasswordUseCase,
    private val deleteAllDataUseCase: DeleteAllDataUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val updateTaskSymbolUseCase: UpdateTaskSymbolUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(value = HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        executeLockProcess()
    }

    private fun executeLockProcess() =
        viewModelScope.launch {
            val hasBiometricEnabled = getHasBiometricEnabledUseCase().first()
            if (hasBiometricEnabled) {
                _uiState.value = HomeUiState.Biometric
                return@launch
            }

            val hasExistingPassword = getHasExistingPasswordUseCase().first()
            if (hasExistingPassword) {
                _uiState.value = HomeUiState.Password(
                    homePasswordProcessType = HomePasswordProcessType.ENTER_EXISTING_PASSWORD
                )
                return@launch
            }

            fetchHome()
        }

    fun fetchHome(categoryId: Long = -1L) =
        viewModelScope.launch {
            getHomeDataUseCase(categoryId = categoryId).map { home ->
                val tasks = home.tasks
                val completedTasks = tasks.filter { task ->
                    task.isCompleted
                }.toPersistentList()
                val incompleteTasks = tasks.filter { task ->
                    !task.isCompleted
                }.toPersistentList()

                val taskStateGroups = tasks.toTaskStateGroups()

                val homeSystem = home.homeSystem

                HomeUiState.Screen(
                    taskStateGroups = taskStateGroups,
                    categories = home.categories.toPersistentList(),
                    sortTaskType = homeSystem.sortTaskType,
                    locale = homeSystem.locale,
                    timePickerType = homeSystem.timePickerType
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
        }

    fun updateSortTaskType(sortTaskType: SortTaskType) {
        val state = _uiState.value
        if (state !is HomeUiState.Screen) return

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

    fun checkPassword(password: String) =
        viewModelScope.launch {
            val isPasswordMatched =
                checkPasswordUseCase(password = password).first()
            if (isPasswordMatched) {
                fetchHome()
            } else {
                val state = _uiState.value
                if (state !is HomeUiState.Password) return@launch

                _uiState.value = state.copy(
                    homePasswordProcessType = HomePasswordProcessType.EXISTING_PASSWORD_MISMATCHED
                )
            }
        }

    fun deleteAllData() =
        viewModelScope.launch {
            deleteAllDataUseCase()
            fetchHome()
        }

    fun executePasswordAuth() {
        _uiState.value = HomeUiState.Password(
            homePasswordProcessType = HomePasswordProcessType.ENTER_EXISTING_PASSWORD
        )
    }

    fun insertTask(task: Task) =
        viewModelScope.launch {
            insertTaskUseCase(task)
            fetchHome()
        }

    fun updateTaskSymbol(taskId: Long, symbolId: Int) =
        viewModelScope.launch {
            updateTaskSymbolUseCase(
                taskId = taskId,
                symbolId = symbolId
            )
            fetchHome()
        }

    private fun List<Task>.toTaskStateGroups(): ImmutableList<TaskStateGroup> =
        groupBy { task -> task.isCompleted }
            .toSortedMap()
            .map { (isCompleted, tasks) ->
                val taskState =
                    if (isCompleted) TaskState.COMPLETED else TaskState.INCOMPLETE
                TaskStateGroup(
                    taskState = taskState,
                    tasks = tasks.toPersistentList()
                )
            }.toPersistentList()
}

