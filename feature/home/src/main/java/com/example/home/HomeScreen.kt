package com.example.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.EmptyContent
import com.example.design_system.component.TaskCard
import com.example.design_system.component.dialog.sort_task.SortTaskDialog
import com.example.design_system.theme.TodoTheme
import com.example.home.component.SwipeActionBox
import com.example.home.component.TaskInfoCard
import com.example.home.component.bottom_sheet.AddTaskBottomSheetContent
import com.example.home.model.BottomSheetType
import com.example.model.Category
import com.example.model.SortTaskType
import com.example.model.Task
import com.example.model.TasksType
import com.example.model.TimePickerType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import java.util.Locale
import com.example.design_system.R as DesignSystemR

private const val SLIDE_DISTANCE = 600f
private const val SLIDE_DURATION_MILLIS = 500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    completedTasks: ImmutableList<Task>,
    incompleteTasks: ImmutableList<Task>,
    categories: ImmutableList<Category>,
    sortTaskType: SortTaskType,
    locale: Locale,
    timePickerType: TimePickerType,
    navigateCalendar: () -> Unit,
    navigateSetting: () -> Unit,
    navigateCompletedTask: (String) -> Unit,
    navigateIncompleteTask: (String) -> Unit,
    navigateThisWeekTask: (String) -> Unit,
    navigateAllTask: (String) -> Unit,
    navigateEditTask: (Long) -> Unit,
    navigateManageCategories: () -> Unit,
    onSortTaskTypeChanged: (SortTaskType) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onTaskToggleCompletion: (id: Long, isCompleted: Boolean) -> Unit,
    onAddTaskClick: (Task) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val leftTranslate = remember { Animatable(initialValue = -SLIDE_DISTANCE) }
    val rightTranslate = remember { Animatable(initialValue = SLIDE_DISTANCE) }

    LaunchedEffect(key1 = Unit) {
        launch {
            leftTranslate.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = SLIDE_DURATION_MILLIS)
            )
        }
        launch {
            rightTranslate.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = SLIDE_DURATION_MILLIS)
            )
        }
    }

    var isShowSortTaskDialog by remember { mutableStateOf(value = false) }

    val bottomSheetState = rememberModalBottomSheetState()
    var showAddTaskBottomSheet by remember { mutableStateOf(value = BottomSheetType.IDLE) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = stringResource(id = DesignSystemR.string.app_name),
                        style = TodoTheme.typography.bold_20,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                actions = {
                    IconButton(
                        onClick = navigateCalendar
                    ) {
                        Icon(
                            modifier = modifier.size(size = 21.dp),
                            imageVector = ImageVector.vectorResource(
                                id = DesignSystemR.drawable.svg_calendar
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }

                    IconButton(
                        onClick = navigateSetting
                    ) {
                        Icon(
                            modifier = modifier.size(size = 24.dp),
                            imageVector = ImageVector.vectorResource(
                                id = DesignSystemR.drawable.svg_setting
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    showAddTaskBottomSheet = BottomSheetType.ADD_TASK
                }
            ) {
                Icon(
                    modifier = modifier.size(size = 32.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_plus_small),
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        if (isShowSortTaskDialog) {
            SortTaskDialog(
                sortTaskType = sortTaskType,
                onCloseClick = { isShowSortTaskDialog = false },
                onSelectClick = { sortTask ->
                    onSortTaskTypeChanged(sortTask)
                    isShowSortTaskDialog = false
                }
            )
        }

        if (showAddTaskBottomSheet == BottomSheetType.ADD_TASK) {
            ModalBottomSheet(
                onDismissRequest = {
                    showAddTaskBottomSheet = BottomSheetType.IDLE
                },
                sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                AddTaskBottomSheetContent(
                    locale = locale,
                    categories = categories,
                    timePickerType = timePickerType,
                    navigateManageCategories = {
                        showAddTaskBottomSheet = BottomSheetType.IDLE
                        navigateManageCategories()
                    },
                    onAddTaskClick = { task ->
                        onAddTaskClick(task)
                        showAddTaskBottomSheet = BottomSheetType.IDLE
                    },
                    onShowMessageSnackbar = onShowMessageSnackbar
                )
            }
        }

        Column(
            modifier = modifier.padding(paddingValues = paddingValues)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TaskInfoCard(
                    modifier = Modifier.weight(weight = 1f).graphicsLayer {
                        translationX = leftTranslate.value
                    },
                    tasksType = TasksType.COMPLETED,
                    icon = DesignSystemR.drawable.svg_completed,
                    content = stringResource(
                        id = DesignSystemR.string.today_completed_tasks_count,
                        completedTasks.size
                    ),
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = navigateCompletedTask
                )

                TaskInfoCard(
                    modifier = Modifier.weight(weight = 1f)
                        .graphicsLayer { translationX = rightTranslate.value },
                    tasksType = TasksType.INCOMPLETE,
                    icon = DesignSystemR.drawable.svg_incomplete,
                    content = stringResource(
                        id = DesignSystemR.string.today_incomplete_tasks_count,
                        incompleteTasks.size
                    ),
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = navigateIncompleteTask
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    ),
                horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TaskInfoCard(
                    modifier = Modifier.weight(weight = 1f)
                        .graphicsLayer { translationX = leftTranslate.value },
                    tasksType = TasksType.THIS_WEEK,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = navigateThisWeekTask,
                )

                TaskInfoCard(
                    modifier = Modifier.weight(weight = 1f)
                        .graphicsLayer { translationX = rightTranslate.value },
                    tasksType = TasksType.ALL,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = navigateAllTask,
                )
            }

            if (incompleteTasks.isEmpty()) {
                EmptyContent(title = stringResource(id = DesignSystemR.string.no_tasks))
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.padding(all = 16.dp),
                        text = stringResource(id = DesignSystemR.string.today_tasks),
                        style = TodoTheme.typography.bold_18,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                    IconButton(onClick = { isShowSortTaskDialog = true }) {
                        Icon(
                            modifier = Modifier.size(size = 18.dp),
                            imageVector = ImageVector.vectorResource(
                                id = DesignSystemR.drawable.svg_sort
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                val sortedTasks: List<Task> =
                    remember(key1 = incompleteTasks, key2 = sortTaskType) {
                        incompleteTasks.sortedWith(
                            comparator = compareBy { task ->
                                when (sortTaskType) {
                                    SortTaskType.BY_CREATE_TIME_ASCENDING -> {
                                        task.id
                                    }

                                    SortTaskType.BY_CREATE_TIME_DESCENDING -> {
                                        -task.id
                                    }

                                    SortTaskType.BY_PRIORITY_ASCENDING -> {
                                        task.priority
                                    }

                                    SortTaskType.BY_PRIORITY_DESCENDING -> {
                                        -task.priority
                                    }

                                    SortTaskType.BY_TIME_ASCENDING -> {
                                        task.time.toSecondOfDay()
                                    }

                                    SortTaskType.BY_TIME_DESCENDING -> {
                                        -task.time.toSecondOfDay()
                                    }
                                }
                            }
                        )
                    }

                val taskDeletedMessage =
                    stringResource(id = DesignSystemR.string.task_deleted)

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 10.dp,
                        )
                ) {
                    itemsIndexed(
                        items = sortedTasks,
                        key = { _, task ->
                            task.id
                        }
                    ) { _, task ->
                        Box(
                            modifier = Modifier.animateItem(
                                fadeInSpec = tween(
                                    durationMillis = 500
                                )
                            )
                        ) {
                            SwipeActionBox(
                                item = task,
                                onEditAction = { task ->
                                    navigateEditTask(task.id)
                                },
                                onDeleteAction = { task ->
                                    onTaskDelete(task.id)
                                    onShowMessageSnackbar(taskDeletedMessage)
                                }
                            ) { task ->
                                TaskCard(
                                    task = task,
                                    category = categories.filter { category ->
                                        category.id == task.categoryId
                                    }.getOrNull(index = 0),
                                    locale = locale,
                                    isAvailableSwipe = true,
                                    onTaskToggleCompletion = { id, isCompleted ->
                                        onTaskToggleCompletion(
                                            id,
                                            isCompleted
                                        )
                                    },
                                    onTaskDelete = { id ->
                                        onTaskDelete(id)
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(height = 10.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    TodoTheme {
        HomeScreen(
            completedTasks = persistentListOf(),
            incompleteTasks = persistentListOf(),
            categories = persistentListOf(),
            sortTaskType = SortTaskType.BY_CREATE_TIME_ASCENDING,
            locale = Locale.KOREA,
            timePickerType = TimePickerType.SCROLL_TIME_PICKER,
            navigateCalendar = {},
            navigateSetting = {},
            navigateCompletedTask = {},
            navigateIncompleteTask = {},
            navigateThisWeekTask = {},
            navigateAllTask = {},
            navigateEditTask = { _ -> },
            navigateManageCategories = {},
            onSortTaskTypeChanged = {},
            onTaskDelete = { _ -> },
            onTaskToggleCompletion = { _, _ -> },
            onAddTaskClick = {},
            onShowMessageSnackbar = {}
        )
    }
}
