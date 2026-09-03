package com.example.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.model.CalendarUiState
import com.example.domain.DeleteTaskByTaskUseCase
import com.example.domain.GetCalendarDataUseCase
import com.example.domain.GetTaskByIdUseCase
import com.example.domain.UpdateTaskUseCase
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
class CalendarViewModel @Inject constructor(
    private val getCalendarDataUseCase: GetCalendarDataUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskByTaskUseCase: DeleteTaskByTaskUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<CalendarUiState> =
        MutableStateFlow(value = CalendarUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchCalendar()
    }

    private fun fetchCalendar() =
        viewModelScope.launch {
            getCalendarDataUseCase().map { calendar ->
                val calendarSystem = calendar.calendarSystem
                CalendarUiState.Screen(
                    tasks = calendar.tasks.toPersistentList(),
                    categories = calendar.categories.toPersistentList(),
                    sortTaskType = calendarSystem.sortTaskType,
                    locale = calendarSystem.locale
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
        }

    fun toggleTaskCompletion(taskId: Long, isCompleted: Boolean) =
        viewModelScope.launch {
            val task =
                getTaskByIdUseCase(id = taskId).copy(isCompleted = isCompleted)
            updateTaskUseCase(task = task)
        }

    fun deleteTask(taskId: Long) =
        viewModelScope.launch {
            val task = getTaskByIdUseCase(id = taskId)
            deleteTaskByTaskUseCase(task = task)
        }
}
