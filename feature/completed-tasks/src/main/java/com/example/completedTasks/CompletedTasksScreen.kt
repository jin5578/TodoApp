package com.example.completedTasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.completedTasks.component.taskDateGroup
import com.example.completedTasks.model.TaskDateGroup
import com.example.designSystem.component.EmptyContent
import com.example.designSystem.theme.TodoTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.util.Locale
import com.example.designSystem.R as DesignSystemR

@Composable
internal fun CompletedTasksScreen(
    modifier: Modifier = Modifier,
    taskDateGroups: ImmutableList<TaskDateGroup>,
    locale: Locale,
    onDeleteClick: () -> Unit,
    onTaskToggleClick: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskEditClick: (Long) -> Unit,
    onDeleteSymbolClick: (taskId: Long) -> Unit,
    onSymbolClick: (taskId: Long, symbolId: Int) -> Unit,
    onSubTaskToggleClick: (subTaskId: Long, isCompleted: Boolean) -> Unit,
    popBackStack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CompletedTasksTopAppBar(
                onDeleteClick = onDeleteClick,
                popBackStack = popBackStack,
            )
        },
    ) { paddingValues ->
        if (taskDateGroups.isEmpty()) {
            EmptyContent(
                modifier = modifier.padding(paddingValues = paddingValues),
                title = stringResource(id = DesignSystemR.string.no_completed_tasks),
            )
        } else {
            Column(
                modifier = modifier.fillMaxSize()
                    .padding(paddingValues = paddingValues),
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    ),
                    text = stringResource(id = DesignSystemR.string.completed_time),
                    style = TodoTheme.typography.bold_16,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                LazyColumn {
                    taskDateGroups.forEachIndexed { index, taskDateGroup ->
                        taskDateGroup(
                            index = index,
                            groupSize = taskDateGroups.size,
                            taskDateGroup = taskDateGroup,
                            locale = locale,
                            onTaskToggleClick = onTaskToggleClick,
                            onTaskEditClick = onTaskEditClick,
                            onDeleteSymbolClick = onDeleteSymbolClick,
                            onSymbolClick = onSymbolClick,
                            onSubTaskToggleClick = onSubTaskToggleClick,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompletedTasksTopAppBar(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    popBackStack: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        title = {},
        navigationIcon = {
            IconButton(onClick = popBackStack) {
                Icon(
                    modifier = modifier.size(size = 24.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_left),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        actions = {
            IconButton(
                onClick = onDeleteClick,
            ) {
                Icon(
                    modifier = modifier.size(size = 18.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_trash),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun CompletedTasksScreenPreview() {
    TodoTheme {
        CompletedTasksScreen(
            taskDateGroups = persistentListOf(),
            locale = Locale.KOREA,
            onDeleteClick = {},
            onTaskToggleClick = { _, _ -> },
            onTaskEditClick = {},
            onDeleteSymbolClick = {},
            onSymbolClick = { _, _ -> },
            onSubTaskToggleClick = { _, _ -> },
            popBackStack = {},
        )
    }
}
