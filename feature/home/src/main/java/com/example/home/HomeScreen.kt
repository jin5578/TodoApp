package com.example.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toColorLong
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.EmptyContent
import com.example.design_system.component.dialog.category.CategoryDialog
import com.example.design_system.theme.TodoTheme
import com.example.home.component.bottom_sheet.AddTaskBottomSheetContent
import com.example.home.component.taskStateGroup
import com.example.home.model.BottomSheetType
import com.example.home.model.TaskStateGroup
import com.example.home.utils.toggled
import com.example.model.Category
import com.example.model.CategoryColorType
import com.example.model.Task
import com.example.model.TimePickerType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    taskStateGroups: ImmutableList<TaskStateGroup>,
    locale: Locale,
    timePickerType: TimePickerType,
    onSettingClick: () -> Unit,
    onCategoryClick: (Long) -> Unit,
    onTaskToggleClick: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskEditClick: (Long) -> Unit,
    onDeleteSymbolClick: (taskId: Long) -> Unit,
    onAddTaskClick: (Task) -> Unit,
    onSymbolClick: (taskId: Long, symbolId: Int) -> Unit,
    onSubTaskToggleClick: (subTaskId: Long, isCompleted: Boolean) -> Unit,
    onAddCategoryClick: (categoryTitle: String, categoryColorType: CategoryColorType) -> Unit,
    onCompletedTasksClick: () -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val scrollState = rememberScrollState()

    var collapsedTaskStates by remember { mutableStateOf(value = persistentSetOf<String>()) }

    val bottomSheetState = rememberModalBottomSheetState()
    var showAddTaskBottomSheet by remember { mutableStateOf(value = BottomSheetType.IDLE) }

    var isShowAddCategoryDialog by remember { mutableStateOf(value = false) }

    Scaffold(
        topBar = {
            HomeTopAppBar(
                scrollState = scrollState,
                categories = categories,
                navigateSetting = onSettingClick,
                onCategoryClick = onCategoryClick
            )
        },
        floatingActionButton = {
            HomeFloatingActionButton(
                onClick = {
                    showAddTaskBottomSheet = BottomSheetType.ADD_TASK
                }
            )
        }
    ) { paddingValues ->
        if (showAddTaskBottomSheet == BottomSheetType.ADD_TASK) {
            ModalBottomSheet(
                onDismissRequest = {
                    showAddTaskBottomSheet = BottomSheetType.IDLE
                },
                sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                AddTaskBottomSheetContent(
                    locale = locale,
                    categories = categories,
                    timePickerType = timePickerType,
                    onAddTaskClick = { task ->
                        onAddTaskClick(task)
                        showAddTaskBottomSheet = BottomSheetType.IDLE
                    },
                    onCreateNewCategoryClick = {
                        isShowAddCategoryDialog = true
                    },
                    onShowMessageSnackbar = onShowMessageSnackbar
                )
            }
        }

        if (isShowAddCategoryDialog) {
            CategoryDialog(
                titleResId = DesignSystemR.string.create_new_category,
                onCloseClick = { isShowAddCategoryDialog = false },
                onSaveClick = { categoryTitle, categoryColorType ->
                    onAddCategoryClick(categoryTitle, categoryColorType)
                    isShowAddCategoryDialog = false
                }
            )
        }

        if (taskStateGroups.isEmpty())
            EmptyContent(
                modifier = modifier.padding(paddingValues = paddingValues),
                title = stringResource(id = DesignSystemR.string.no_tasks)
            )
        else
            LazyColumn(
                modifier = modifier.fillMaxSize()
                    .padding(paddingValues = paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                taskStateGroups.forEach { taskStateGroup ->
                    taskStateGroup(
                        taskStateGroup = taskStateGroup,
                        locale = locale,
                        collapsedTaskStates = collapsedTaskStates,
                        onTaskToggleClick = onTaskToggleClick,
                        onTaskEditClick = onTaskEditClick,
                        onDeleteSymbolClick = onDeleteSymbolClick,
                        onSymbolClick = onSymbolClick,
                        onTaskStateGroupHeaderClick = { key ->
                            collapsedTaskStates =
                                collapsedTaskStates.toggled(element = key)
                        },
                        onSubTaskToggleClick = onSubTaskToggleClick
                    )
                }

                item {
                    Text(
                        modifier = Modifier
                            .clickable { onCompletedTasksClick() }
                            .padding(all = 16.dp),
                        text = stringResource(id = DesignSystemR.string.check_all_completed_tasks),
                        textAlign = TextAlign.Center,
                        style = TodoTheme.typography.medium_12,
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    categories: ImmutableList<Category>,
    navigateSetting: () -> Unit,
    onCategoryClick: (Long) -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            if (categories.isEmpty()) {
                Text(
                    text = stringResource(id = DesignSystemR.string.app_name),
                    style = TodoTheme.typography.bold_20,
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                Row(
                    modifier = modifier.fillMaxWidth()
                        .padding(end = 4.dp)
                        .horizontalScroll(state = scrollState),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp)
                ) {
                    CategoryItem(
                        id = -1L,
                        title = stringResource(id = DesignSystemR.string.entire),
                        onClick = onCategoryClick
                    )
                    categories.forEach { category ->
                        CategoryItem(
                            id = category.id,
                            title = category.title,
                            onClick = onCategoryClick
                        )
                    }
                }
            }
        },
        actions = {
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
}

@Composable
private fun CategoryItem(
    id: Long,
    title: String,
    onClick: (Long) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
            .clickable { onClick(id) },
    ) {
        Text(
            text = title,
            style = TodoTheme.typography.bold_16,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun HomeFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primaryContainer,
        onClick = onClick
    ) {
        Icon(
            modifier = modifier.size(size = 32.dp),
            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_plus_small),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    TodoTheme {
        val categories = persistentListOf(
            Category(
                id = 1,
                title = "운동",
                colorValue = Color.Red.toColorLong()
            )
        )
        HomeScreen(
            categories = categories,
            taskStateGroups = persistentListOf(),
            locale = Locale.KOREA,
            timePickerType = TimePickerType.CLOCK_TIME_PICKER,
            onSettingClick = {},
            onCategoryClick = {},
            onTaskToggleClick = { _, _ -> },
            onTaskEditClick = {},
            onDeleteSymbolClick = {},
            onAddTaskClick = {},
            onSymbolClick = { _, _ -> },
            onSubTaskToggleClick = { _, _ -> },
            onAddCategoryClick = { _, _ -> },
            onCompletedTasksClick = {},
            onShowMessageSnackbar = {}
        )
    }
}