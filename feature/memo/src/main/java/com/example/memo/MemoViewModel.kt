package com.example.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetMemoDataUseCase
import com.example.domain.UpdateTaskMemoContentUseCase
import com.example.domain.UpdateTaskMemoTitleUseCase
import com.example.memo.model.MemoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
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
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MemoViewModel @Inject constructor(
    private val getMemoDataUseCase: GetMemoDataUseCase,
    private val updateTaskMemoTitleUseCase: UpdateTaskMemoTitleUseCase,
    private val updateTaskMemoContentUseCase: UpdateTaskMemoContentUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<MemoUiState> =
        MutableStateFlow(value = MemoUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _titleChanges: MutableSharedFlow<Pair<Long, String>> =
        MutableSharedFlow(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    private val _contentChanges: MutableSharedFlow<Pair<Long, String>> =
        MutableSharedFlow(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    init {
        _titleChanges
            .debounce(timeoutMillis = 1000)
            .onEach { (taskId: Long, title: String) ->
                updateTaskMemoTitleUseCase(
                    id = taskId,
                    memoTitle = title
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.launchIn(scope = viewModelScope)

        _contentChanges
            .debounce(timeoutMillis = 1000)
            .onEach { (taskId: Long, content: String) ->
                updateTaskMemoContentUseCase(
                    id = taskId,
                    memoContent = content
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.launchIn(scope = viewModelScope)
    }

    fun fetchMemoUiState(taskId: Long) =
        viewModelScope.launch {
            getMemoDataUseCase(id = taskId).map { memo ->
                val memoSystem = memo.memoSystem
                MemoUiState.Screen(
                    memoTitle = memo.memoTitle,
                    memoContent = memo.memoContent,
                    memoUpdatedAt = memo.memoUpdatedAt,
                    locale = memoSystem.locale
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
        }

    fun updateTitle(taskId: Long, memoTitle: String) =
        _titleChanges.tryEmit(value = taskId to memoTitle)

    fun updateContent(taskId: Long, memoContent: String) =
        _contentChanges.tryEmit(value = taskId to memoContent)
}