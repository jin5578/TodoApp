package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.CheckPasswordUseCase
import com.example.domain.DeleteAllDataUseCase
import com.example.domain.GetHasBiometricEnabledUseCase
import com.example.domain.GetHasExistingPasswordUseCase
import com.example.domain.GetTasksDataUseCase
import com.example.domain.InsertCategoryUseCase
import com.example.domain.InsertTaskUseCase
import com.example.domain.UpdateSortByTypeUseCase
import com.example.domain.UpdateSubTaskCompletedUseCase
import com.example.domain.UpdateTaskCompletedUseCase
import com.example.domain.UpdateTaskSymbolUseCase
import com.example.model.Category
import com.example.model.SortByType
import com.example.model.Task
import com.example.model.toUiModels
import com.example.tasks.model.TaskState
import com.example.tasks.model.TaskStateGroup
import com.example.tasks.model.TasksPasswordProcessType
import com.example.tasks.model.TasksUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getHasBiometricEnabledUseCase: GetHasBiometricEnabledUseCase,
    private val getHasExistingPasswordUseCase: GetHasExistingPasswordUseCase,
    private val getTasksDataUseCase: GetTasksDataUseCase,
    private val updateTaskCompletedUseCase: UpdateTaskCompletedUseCase,
    private val checkPasswordUseCase: CheckPasswordUseCase,
    private val deleteAllDataUseCase: DeleteAllDataUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val updateTaskSymbolUseCase: UpdateTaskSymbolUseCase,
    private val updateSubTaskCompletedUseCase: UpdateSubTaskCompletedUseCase,
    private val updateSortByTypeUseCase: UpdateSortByTypeUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<TasksUiState> =
        MutableStateFlow(value = TasksUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val selectedCategoryId: MutableStateFlow<Long> =
        MutableStateFlow(value = -1L)
    private var isTasksObserved = false

    init {
        executeLockProcess()
    }

    private fun executeLockProcess() =
        viewModelScope.launch {
            val hasBiometricEnabled = getHasBiometricEnabledUseCase().first()
            if (hasBiometricEnabled) {
                _uiState.value = TasksUiState.Biometric
                return@launch
            }

            val hasExistingPassword = getHasExistingPasswordUseCase().first()
            if (hasExistingPassword) {
                _uiState.value = TasksUiState.Password(
                    tasksPasswordProcessType = TasksPasswordProcessType.ENTER_EXISTING_PASSWORD
                )
                return@launch
            }

            fetchTasks()
        }

    fun fetchTasks(categoryId: Long = -1L) {
        selectedCategoryId.value = categoryId
        startObservingTasks()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun startObservingTasks() {
        if (isTasksObserved) return
        isTasksObserved = true

        viewModelScope.launch {
            selectedCategoryId.flatMapLatest { id ->
                getTasksDataUseCase(categoryId = id)
            }.map { tasks ->
                val tasksSystem = tasks.tasksSystem
                val taskStateGroups = tasks.tasks.toTaskStateGroups(
                    sortByType = tasksSystem.sortByType
                )
                val isVisibleCompletedTask =
                    tasks.tasks.any { task ->
                        task.isCompleted && task.completedAt?.toLocalDate() != LocalDate.now()
                    }

                TasksUiState.Screen(
                    taskStateGroups = taskStateGroups,
                    categories = tasks.categories.toPersistentList(),
                    sortByType = tasksSystem.sortByType,
                    locale = tasksSystem.locale,
                    timePickerType = tasksSystem.timePickerType,
                    isVisibleCompletedTask = isVisibleCompletedTask
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun updateTaskCompletion(id: Long, isCompleted: Boolean) =
        viewModelScope.launch {
            updateTaskCompletedUseCase(
                id = id,
                isCompleted = isCompleted
            )
        }

    fun checkPassword(password: String) =
        viewModelScope.launch {
            val isPasswordMatched =
                checkPasswordUseCase(password = password).first()
            if (isPasswordMatched) {
                fetchTasks()
            } else {
                val state = _uiState.value
                if (state !is TasksUiState.Password) return@launch

                _uiState.value = state.copy(
                    tasksPasswordProcessType = TasksPasswordProcessType.EXISTING_PASSWORD_MISMATCHED
                )
            }
        }

    fun deleteAllData() =
        viewModelScope.launch {
            deleteAllDataUseCase()
        }

    fun executePasswordAuth() {
        _uiState.value = TasksUiState.Password(
            tasksPasswordProcessType = TasksPasswordProcessType.ENTER_EXISTING_PASSWORD
        )
    }

    fun insertTask(task: Task) =
        viewModelScope.launch {
            insertTaskUseCase(task)
        }

    fun insertCategory(title: String, colorValue: Long) =
        viewModelScope.launch {
            val category = Category(
                title = title,
                colorValue = colorValue
            )
            insertCategoryUseCase(category = category)
        }

    fun updateTaskSymbol(taskId: Long, symbolId: Int) =
        viewModelScope.launch {
            updateTaskSymbolUseCase(
                taskId = taskId,
                symbolId = symbolId
            )
        }

    fun toggleSubTaskCompletion(subTaskId: Long, isCompleted: Boolean) =
        viewModelScope.launch {
            updateSubTaskCompletedUseCase(
                id = subTaskId,
                isCompleted = isCompleted
            )
        }

    fun updateSortByType(sortByType: SortByType) =
        viewModelScope.launch {
            updateSortByTypeUseCase(sortByType = sortByType)
        }

    private fun List<Task>.toTaskStateGroups(sortByType: SortByType): ImmutableList<TaskStateGroup> {
        val (completedTasks, previousTasks) = partition { task -> task.isCompleted }
        val completedTodayTasks = completedTasks.filter { task ->
            task.completedAt?.toLocalDate() == LocalDate.now()
        }

        return listOfNotNull(
            previousTasks.takeIf { it.isNotEmpty() }?.let { tasks ->
                TaskStateGroup(
                    taskState = TaskState.PREVIOUS,
                    tasks = tasks.sortedBy(sortByType).toUiModels()
                )
            },
            completedTodayTasks.takeIf { it.isNotEmpty() }?.let { tasks ->
                TaskStateGroup(
                    taskState = TaskState.COMPLETED_TODAY,
                    tasks = tasks.sortedBy(sortByType).toUiModels()
                )
            }
        ).toPersistentList()
    }

    private fun List<Task>.sortedBy(sortByType: SortByType): List<Task> =
        when (sortByType) {
            SortByType.DUE_DATE_AND_TIME ->
                sortedWith(
                    compareBy(
                        { it.date },
                        { it.time ?: LocalDateTime.MAX })
                )

            SortByType.TASK_CREATION_TIME_ASC -> sortedBy { it.createdAt }
            SortByType.TASK_CREATION_TIME_DESC -> sortedByDescending { it.createdAt }
        }
}