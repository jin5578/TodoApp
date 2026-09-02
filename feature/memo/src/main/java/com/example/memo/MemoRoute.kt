package com.example.memo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.R
import com.example.design_system.component.Loading
import com.example.memo.model.MemoUiEffect
import com.example.memo.model.MemoUiState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@Composable
internal fun MemoRoute(
    viewModel: MemoViewModel = hiltViewModel(),
    taskId: Long,
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val updateMemoSuccessMessage =
        stringResource(id = DesignSystemR.string.successfully_updated_the_memo)

    LaunchedEffect(key1 = taskId) {
        viewModel.fetchMemo(taskId = taskId)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEffect.collectLatest { uiEffect ->
            if (uiEffect is MemoUiEffect.SuccessUpdateMemo) {
                onShowMessageSnackbar(updateMemoSuccessMessage)
                popBackStack()
            }
        }
    }

    MemoContent(
        taskId = taskId,
        uiState = uiState,
        popBackStack = viewModel::updateTaskMemo
    )
}

@Composable
private fun MemoContent(
    taskId: Long,
    uiState: MemoUiState,
    popBackStack: (taskId: Long, memoTitle: String, memoContent: String) -> Unit,
) {
    when (uiState) {
        is MemoUiState.Loading ->
            Loading()

        is MemoUiState.Screen ->
            MemoScreen(
                taskId = taskId,
                title = uiState.memoTitle,
                content = uiState.memoContent,
                updatedAt = uiState.memoUpdatedAt,
                locale = Locale.KOREA,
                popBackStack = popBackStack,
            )
    }

}