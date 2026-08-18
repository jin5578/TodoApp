package com.example.add_task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.add_task.model.AddTaskUiEffect
import com.example.add_task.model.AddTaskUiState
import com.example.design_system.component.Loading
import com.example.model.Task
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@Composable
internal fun AddTaskRoute(
    viewModel: AddTaskViewModel = hiltViewModel(),
    date: LocalDate,
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(key1 = date) {
        viewModel.fetchAddTaskUiState(date)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEffect.collectLatest { uiEffect ->
            if (uiEffect is AddTaskUiEffect.SuccessInsertTask) {
                val message = uiEffect.message
                onShowMessageSnackbar(message)
                popBackStack()
            }
        }
    }

    AddTaskContent(
        uiState = uiState,
        popBackStack = popBackStack,
        onAddTaskClick = viewModel::insertTask,
        onShowMessageSnackbar = onShowMessageSnackbar
    )
}

@Composable
private fun AddTaskContent(
    uiState: AddTaskUiState,
    popBackStack: () -> Unit,
    onAddTaskClick: (Task) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    when (uiState) {
        is AddTaskUiState.Loading ->
            Loading()

        is AddTaskUiState.Screen ->
            AddTaskScreen(
                date = uiState.date,
                locale = uiState.locale,
                timePickerType = uiState.timePickerType,
                categories = uiState.categories,
                popBackStack = popBackStack,
                onAddTaskClick = onAddTaskClick,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
    }
}