package com.example.search_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetSearchTaskDataUseCase
import com.example.domain.UpdateSubTaskCompletedUseCase
import com.example.domain.UpdateTaskCompletedUseCase
import com.example.domain.UpdateTaskSymbolUseCase
import com.example.model.toUiModels
import com.example.search_task.model.SearchTaskUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchTaskViewModel @Inject constructor(
    private val getSearchTaskDataUseCase: GetSearchTaskDataUseCase,
    private val updateTaskCompletedUseCase: UpdateTaskCompletedUseCase,
    private val updateTaskSymbolUseCase: UpdateTaskSymbolUseCase,
    private val updateSubTaskCompletedUseCase: UpdateSubTaskCompletedUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<SearchTaskUiState> =
        MutableStateFlow(value = SearchTaskUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val searchKeyword: MutableStateFlow<String> =
        MutableStateFlow(value = "")
    private var isSearchTaskObserved = false

    init {
        startObservingSearchTask()
    }

    fun fetchSearchTaskUiState(keyword: String) {
        searchKeyword.value = keyword
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun startObservingSearchTask() {
        if (isSearchTaskObserved) return
        isSearchTaskObserved = true

        viewModelScope.launch {
            searchKeyword.flatMapLatest { keyword ->
                getSearchTaskDataUseCase(keyword = keyword)
            }.map { searchTask ->
                val searchTaskSystem = searchTask.searchTaskSystem
                SearchTaskUiState.Screen(
                    tasks = searchTask.tasks.toUiModels(),
                    locale = searchTaskSystem.locale,
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun updateTaskCompletion(id: Long, isCompleted: Boolean) = viewModelScope.launch {
        updateTaskCompletedUseCase(
            id = id,
            isCompleted = isCompleted,
        )
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
}
