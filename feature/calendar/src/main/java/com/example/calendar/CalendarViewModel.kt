package com.example.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.model.CalendarUiState
import com.example.domain.GetCalendarDataUseCase
import com.example.domain.InsertCategoryUseCase
import com.example.domain.InsertTaskUseCase
import com.example.domain.UpdateSubTaskCompletedUseCase
import com.example.domain.UpdateTaskCompletedUseCase
import com.example.domain.UpdateTaskSymbolUseCase
import com.example.model.Category
import com.example.model.Task
import com.example.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendarDataUseCase: GetCalendarDataUseCase,
    private val updateTaskCompletedUseCase: UpdateTaskCompletedUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val updateTaskSymbolUseCase: UpdateTaskSymbolUseCase,
    private val updateSubTaskCompletedUseCase: UpdateSubTaskCompletedUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<CalendarUiState> =
        MutableStateFlow(value = CalendarUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _selectedCategoryId: MutableStateFlow<Long> =
        MutableStateFlow(value = -1L)
    private val _selectedDate: MutableStateFlow<LocalDate> =
        MutableStateFlow(value = LocalDate.now())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val calendarDataFlow = _selectedCategoryId.flatMapLatest { categoryId ->
        getCalendarDataUseCase(categoryId = categoryId)
    }

    init {
        observeCalendarUiState()
    }

    fun onCategorySelected(categoryId: Long) {
        _selectedCategoryId.value = categoryId
    }

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
    }

    private fun observeCalendarUiState() = viewModelScope.launch {
        combine(
            flow = calendarDataFlow,
            flow2 = _selectedCategoryId,
            flow3 = _selectedDate,
        ) { calendar, categoryId, date ->
            val calendarSystem = calendar.calendarSystem
            val calendarTasks =
                calendar.tasks.map { task -> task.toUiModel() }
                    .toPersistentList()
            val tasks =
                calendarTasks.filter { task -> task.date == date }
                    .toPersistentList()
            CalendarUiState.Screen(
                calendarTasks = calendarTasks,
                tasks = tasks,
                categories = calendar.categories.toPersistentList(),
                sortByType = calendarSystem.sortByType,
                locale = calendarSystem.locale,
                timePickerType = calendarSystem.timePickerType,
                selectedDate = date,
                selectedCategoryId = categoryId,
            )
        }.catch { throwable ->
            _errorFlow.emit(value = throwable)
        }.collect {
            _uiState.value = it
        }
    }

    fun insertCategory(title: String, colorValue: Long) =
        viewModelScope.launch {
            val category = Category(
                title = title,
                colorValue = colorValue
            )
            insertCategoryUseCase(category = category)
        }

    fun insertTask(task: Task) =
        viewModelScope.launch {
            insertTaskUseCase(task)
        }

    fun updateTaskSymbol(taskId: Long, symbolId: Int) =
        viewModelScope.launch {
            updateTaskSymbolUseCase(
                taskId = taskId,
                symbolId = symbolId
            )
        }

    fun updateTaskCompleted(taskId: Long, isCompleted: Boolean) =
        viewModelScope.launch {
            updateTaskCompletedUseCase(id = taskId, isCompleted = isCompleted)
        }

    fun updateSubTaskCompleted(subTaskId: Long, isCompleted: Boolean) =
        viewModelScope.launch {
            updateSubTaskCompletedUseCase(
                id = subTaskId,
                isCompleted = isCompleted
            )
        }
}
