package com.example.memo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.design_system.utils.toErrorMessage
import com.example.memo.model.MemoUiState
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

@Composable
internal fun MemoRoute(
    viewModel: MemoViewModel = hiltViewModel(),
    taskId: Long,
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = LocalSnackbarHostState.current
    val contextResources = LocalResources.current

    LaunchedEffect(key1 = taskId) {
        viewModel.fetchMemoUiState(taskId = taskId)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackbarHostState.showSnackbar(
                message = throwable.toErrorMessage(resources = contextResources)
            )
        }
    }

    MemoContent(
        taskId = taskId,
        uiState = uiState,
        titleUpdate = viewModel::updateTitle,
        contentUpdate = viewModel::updateContent,
        popBackStack = popBackStack,
    )
}

@Composable
private fun MemoContent(
    taskId: Long,
    uiState: MemoUiState,
    titleUpdate: (taskId: Long, memoTitle: String) -> Unit,
    contentUpdate: (taskId: Long, memoContent: String) -> Unit,
    popBackStack: () -> Unit,
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