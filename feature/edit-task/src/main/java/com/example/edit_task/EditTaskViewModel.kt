package com.example.edit_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteTaskUseCase
import com.example.domain.GetEditTaskDataUseCase
import com.example.domain.GetTaskByIdUseCase
import com.example.domain.UpdateTaskUseCase
import com.example.edit_task.model.EditTaskUiEffect
import com.example.edit_task.model.EditTaskUiState
import com.example.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
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
class EditTaskViewModel @Inject constructor(
    private val getEditTaskDataUseCase: GetEditTaskDataUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<EditTaskUiState> =
        MutableStateFlow(value = EditTaskUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<EditTaskUiEffect> =
        MutableSharedFlow()
    val uiEffect = _uiEffect.asSharedFlow()

    fun fetchEditTask(taskId: Long) =
        viewModelScope.launch {
            getEditTaskDataUseCase(id = taskId).map { editTask ->
                val editTaskSystem = editTask.editTaskSystem
                EditTaskUiState.Screen(
                    task = editTask.task,
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

    fun updateTask(task: Task) =
        viewModelScope.launch {
            updateTaskUseCase(task = task)
            _uiEffect.emit(
                value = EditTaskUiEffect.SuccessEditTask
            )
        }

    fun deleteTask(taskId: Long, uuid: String) =
        viewModelScope.launch {
            val task = getTaskByIdUseCase(id = taskId)
            deleteTaskUseCase(task = task)
            _uiEffect.emit(
                value = EditTaskUiEffect.SuccessDeleteTask
            )
        }
}