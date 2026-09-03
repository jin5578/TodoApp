package com.example.memo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.memo.model.MemoUiState
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

@Composable
internal fun MemoRoute(
    viewModel: MemoViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    taskId: Long,
    onShowErrorSnackbar: (Throwable?) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = taskId) {
        viewModel.fetchMemo(taskId = taskId)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    MemoContent(
        popBackStack = popBackStack,
        taskId = taskId,
        uiState = uiState,
        titleUpdate = viewModel::updateTitle,
        contentUpdate = viewModel::updateContent,
    )
}

@Composable
private fun MemoContent(
    popBackStack: () -> Unit,
    taskId: Long,
    uiState: MemoUiState,
    titleUpdate: (taskId: Long, memoTitle: String) -> Unit,
    contentUpdate: (taskId: Long, memoContent: String) -> Unit,
) {
    when (uiState) {
        is MemoUiState.Loading ->
            Loading()

        is MemoUiState.Screen ->
            MemoScreen(
                popBackStack = popBackStack,
                taskId = taskId,
                title = uiState.memoTitle,
                content = uiState.memoContent,
                updatedAt = uiState.memoUpdatedAt,
                locale = Locale.KOREA,
                onTitleValueChanged = titleUpdate,
                onContentValueChanged = contentUpdate,
            )
    }

}