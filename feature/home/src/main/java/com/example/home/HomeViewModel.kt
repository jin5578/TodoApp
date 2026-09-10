package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.CheckPasswordUseCase
import com.example.domain.DeleteAllDataUseCase
import com.example.domain.GetHasBiometricEnabledUseCase
import com.example.domain.GetHasExistingPasswordUseCase
import com.example.domain.GetHomeDataUseCase
import com.example.domain.InsertCategoryUseCase
import com.example.domain.InsertTaskUseCase
import com.example.domain.UpdateSubTaskCompletedUseCase
import com.example.domain.UpdateTaskCompletedUseCase
import com.example.domain.UpdateTaskSymbolUseCase
import com.example.home.model.HomeUiState
import com.example.home.model.TaskState
import com.example.home.model.TaskStateGroup
import com.example.model.Category
import com.example.model.HomePasswordProcessType
import com.example.model.Task
import com.example.model.TaskUiModel
import com.example.model.toUiModel
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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHasBiometricEnabledUseCase: GetHasBiometricEnabledUseCase,
    private val getHasExistingPasswordUseCase: GetHasExistingPasswordUseCase,
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val updateTaskCompletedUseCase: UpdateTaskCompletedUseCase,
    private val checkPasswordUseCase: CheckPasswordUseCase,
    private val deleteAllDataUseCase: DeleteAllDataUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val updateTaskSymbolUseCase: UpdateTaskSymbolUseCase,
    private val updateSubTaskCompletedUseCase: UpdateSubTaskCompletedUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(value = HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val selectedCategoryId: MutableStateFlow<Long> =
        MutableStateFlow(value = -1L)
    private var isHomeObserved = false

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

    fun fetchHome(categoryId: Long = -1L) {
        selectedCategoryId.value = categoryId
        startObservingHome()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun startObservingHome() {
        if (isHomeObserved) return
        isHomeObserved = true

        viewModelScope.launch {
            selectedCategoryId.flatMapLatest { id ->
                getHomeDataUseCase(categoryId = id)
            }.map { home ->
                val taskStateGroups = home.tasks.toTaskStateGroups()
                val homeSystem = home.homeSystem

                val isVisibleCompletedTask =
                    home.tasks.any { task ->
                        task.isCompleted && task.completedAt?.toLocalDate() != LocalDate.now()
                    }

                HomeUiState.Screen(
                    taskStateGroups = taskStateGroups,
                    categories = home.categories.toPersistentList(),
                    sortTaskType = homeSystem.sortTaskType,
                    locale = homeSystem.locale,
                    timePickerType = homeSystem.timePickerType,
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
        }

    fun executePasswordAuth() {
        _uiState.value = HomeUiState.Password(
            homePasswordProcessType = HomePasswordProcessType.ENTER_EXISTING_PASSWORD
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

    private fun List<Task>.toTaskStateGroups(): ImmutableList<TaskStateGroup> {
        val (completedTasks, previousTasks) = partition { task -> task.isCompleted }
        val completedTodayTasks = completedTasks.filter { task ->
            task.completedAt?.toLocalDate() == LocalDate.now()
        }

        return listOfNotNull(
            previousTasks.takeIf { it.isNotEmpty() }?.let { tasks ->
                TaskStateGroup(
                    taskState = TaskState.PREVIOUS,
                    tasks = tasks.toUiModels()
                )
            },
            completedTodayTasks.takeIf { it.isNotEmpty() }?.let { tasks ->
                TaskStateGroup(
                    taskState = TaskState.COMPLETED_TODAY,
                    tasks = tasks.toUiModels()
                )
            }
        ).toPersistentList()
    }

    private fun List<Task>.toUiModels(): ImmutableList<TaskUiModel> =
        map { task -> task.toUiModel() }.toPersistentList()
}

