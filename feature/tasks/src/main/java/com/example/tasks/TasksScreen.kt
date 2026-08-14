package com.example.tasks

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.EmptyContent
import com.example.design_system.component.Loading
import com.example.design_system.component.TaskCard
import com.example.design_system.theme.TodoTheme
import com.example.model.Category
import com.example.model.Task
import com.example.model.TasksType
import com.example.tasks.model.TasksUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@Composable
internal fun TasksRoute(
    viewModel: TasksViewModel = hiltViewModel(),
    type: TasksType,
    popBackStack: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = type) {
        viewModel.fetchTasks(type = type)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    TasksContent(
        uiState = uiState,
        type = type,
        popBackStack = popBackStack,
        navigateEditTask = navigateEditTask,
        onTaskToggleCompletion = { taskId, isCompleted ->
            viewModel.toggleTaskCompletion(
                taskId = taskId,
                isCompleted = isCompleted
            )
        },
        onTaskDelete = viewModel::deleteTask,
        onShowMessageSnackbar = onShowMessageSnackbar
    )
}

@Composable
private fun TasksContent(
    uiState: TasksUiState,
    type: TasksType,
    popBackStack: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    onTaskToggleCompletion: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) {
    when (uiState) {
        is TasksUiState.Loading ->
            Loading()

        is TasksUiState.Success ->
            TasksScreen(
                type = type,
                tasks = uiState.tasks,
                categories = uiState.categories,
                locale = uiState.locale,
                popBackStack = popBackStack,
                navigateEditTask = navigateEditTask,
                onTaskToggleCompletion = onTaskToggleCompletion,
                onTaskDelete = onTaskDelete,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TasksScreen(
    modifier: Modifier = Modifier,
    type: TasksType,
    tasks: ImmutableList<Task>,
    categories: ImmutableList<Category>,
    locale: Locale,
    popBackStack: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    onTaskToggleCompletion: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = type.title,
                        style = TodoTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = modifier.size(size = 24.dp),
                            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (tasks.isEmpty()) {
            EmptyContent(
                modifier = modifier.fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .background(color = MaterialTheme.colorScheme.surface),
                title = stringResource(id = DesignSystemR.string.no_tasks)
            )
        } else {
            LazyColumn(
                modifier = modifier.fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
            ) {
                itemsIndexed(
                    items = tasks,
                    key = { _, task -> task.id }
                ) { _, task ->
                    Box(
                        modifier = Modifier.animateItem(
                            fadeInSpec = tween(
                                durationMillis = 500
                            )
                        )
                    ) {
                        TaskCard(
                            task = task,
                            category = categories.filter { it.id == task.categoryId }
                                .getOrNull(index = 0),
                            locale = locale,
                            isAvailableSwipe = false,
                            onTaskEdit = { taskId -> navigateEditTask(taskId) },
                            onTaskToggleCompletion = onTaskToggleCompletion,
                            onTaskDelete = { taskId -> onTaskDelete(taskId) }
                        )
                    }
                    Spacer(modifier = Modifier.height(height = 10.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TasksScreenPreview() {
    TodoTheme {
        TasksScreen(
            type = TasksType.COMPLETED,
            tasks = persistentListOf(),
            categories = persistentListOf(),
            locale = Locale.KOREA,
            popBackStack = {},
            navigateEditTask = {},
            onTaskToggleCompletion = { _, _ -> },
            onTaskDelete = { _ -> },
            onShowMessageSnackbar = {}
        )
    }
}