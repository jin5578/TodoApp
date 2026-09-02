package com.example.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetMemoDataUseCase
import com.example.domain.UpdateTaskMemoUseCase
import com.example.memo.model.MemoUiEffect
import com.example.memo.model.MemoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val getMemoDataUseCase: GetMemoDataUseCase,
    private val updateTaskMemoUseCase: UpdateTaskMemoUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<MemoUiState> =
        MutableStateFlow(value = MemoUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<MemoUiEffect> =
        MutableSharedFlow()
    val uiEffect = _uiEffect.asSharedFlow()

    fun fetchMemo(taskId: Long) =
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

    fun updateTaskMemo(taskId: Long, memoTitle: String, memoContent: String) =
        viewModelScope.launch {
            updateTaskMemoUseCase(
                taskId = taskId,
                memoTitle = memoTitle,
                memoContent = memoContent
            ).onSuccess {
                _uiEffect.emit(value = MemoUiEffect.SuccessUpdateMemo)
            }.onFailure { throwable ->
                _errorFlow.emit(value = throwable)
            }
        }
}