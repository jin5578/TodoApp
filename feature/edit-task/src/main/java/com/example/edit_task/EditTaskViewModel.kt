package com.example.edit_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteTaskByIdUseCase
import com.example.domain.GetEditTaskDataUseCase
import com.example.domain.SyncSubTasksUseCase
import com.example.domain.UpdateSubTaskCompletedUseCase
import com.example.domain.UpdateTaskCategoryUseCase
import com.example.domain.UpdateTaskCompletedUseCase
import com.example.domain.UpdateTaskDateTimeUseCase
import com.example.domain.UpdateTaskTitleUseCase
import com.example.edit_task.model.EditTaskUiEffect
import com.example.edit_task.model.EditTaskUiState
import com.example.model.SubTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val getEditTaskDataUseCase: GetEditTaskDataUseCase,
    private val updateTaskCategoryUseCase: UpdateTaskCategoryUseCase,
    private val updateTaskTitleUseCase: UpdateTaskTitleUseCase,
    private val updateTaskDateTimeUseCase: UpdateTaskDateTimeUseCase,
    private val updateTaskCompletedUseCase: UpdateTaskCompletedUseCase,
    private val deleteTaskByIdUseCase: DeleteTaskByIdUseCase,
    private val syncSubTasksUseCase: SyncSubTasksUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<EditTaskUiState> =
        MutableStateFlow(value = EditTaskUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<EditTaskUiEffect> =
        MutableSharedFlow()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _titleChanges: MutableSharedFlow<Pair<Long, String>> =
        MutableSharedFlow(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    private var fetchEditTaskJob: Job? = null

    init {
        _titleChanges
            .debounce(timeoutMillis = 1000)
            .onEach { (taskId: Long, title: String) ->
                updateTaskTitleUseCase(id = taskId, title = title)
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.launchIn(scope = viewModelScope)
    }

    fun fetchEditTask(taskId: Long) {
        fetchEditTaskJob = viewModelScope.launch {
            getEditTaskDataUseCase(id = taskId).map { editTask ->
                val editTaskSystem = editTask.editTaskSystem
                EditTaskUiState.Screen(
                    task = editTask.task,
                    subTasks = editTask.subTasks.toPersistentList(),
                    locale = editTaskSystem.locale,
                    timePickerType = editTaskSystem.timePickerType,
                    categories = editTask.categories.toPersistentList()
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun updateCategory(taskId: Long, categoryId: Long) =
        viewModelScope.launch {
            updateTaskCategoryUseCase(taskId = taskId, categoryId = categoryId)
        }

    fun updateTitle(taskId: Long, title: String) =
        _titleChanges.tryEmit(value = taskId to title)

    fun updateDateTime(
        taskId: Long,
        date: LocalDate,
        time: LocalDateTime?,
        reminderTime: LocalDateTime?
    ) = viewModelScope.launch {
        updateTaskDateTimeUseCase(
            id = taskId,
            date = date,
            time = time,
            reminderTime = reminderTime
        )
    }

    fun updateCompleted(taskId: Long, isCompleted: Boolean) =
        viewModelScope.launch {
            updateTaskCompletedUseCase(
                id = taskId,
                isCompleted = isCompleted
            )
        }

    fun deleteTask(taskId: Long, uuid: String) =
        viewModelScope.launch {
            fetchEditTaskJob?.cancelAndJoin()
            deleteTaskByIdUseCase(id = taskId, uuid = uuid)
            _uiEffect.emit(
                value = EditTaskUiEffect.SuccessDeleteTask
            )
        }

    fun syncSubTasks(parentId: Long, subTasks: List<SubTask>) =
        viewModelScope.launch {
            syncSubTasksUseCase(parentId = parentId, subTasks = subTasks)
        }
}