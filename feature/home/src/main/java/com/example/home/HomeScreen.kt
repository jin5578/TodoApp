package com.example.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toColorLong
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.component.EmptyContent
import com.example.design_system.theme.TodoTheme
import com.example.home.component.HomeFloatingActionButton
import com.example.home.component.HomeTopAppBar
import com.example.home.component.bottom_sheet.AddTaskBottomSheetContent
import com.example.home.component.taskStateGroupContent
import com.example.home.model.BottomSheetType
import com.example.home.model.TaskStateGroup
import com.example.home.utils.toggled
import com.example.model.Category
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
    navigateManageCategories: () -> Unit,
    onSettingClick: () -> Unit,
    onCategoryClick: (Long) -> Unit,
    onTaskToggleClick: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskEditClick: (Long) -> Unit,
    onDeleteSymbolClick: (taskId: Long) -> Unit,
    onSymbolClick: (taskId: Long, symbolId: Int) -> Unit,
    onAddTaskClick: (Task) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val scrollState = rememberScrollState()

    var collapsedTaskStates by remember { mutableStateOf(value = persistentSetOf<String>()) }

    val bottomSheetState = rememberModalBottomSheetState()
    var showAddTaskBottomSheet by remember { mutableStateOf(value = BottomSheetType.IDLE) }

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

        if (taskStateGroups.isEmpty())
            EmptyContent(
                modifier = modifier.padding(paddingValues = paddingValues),
                title = stringResource(id = DesignSystemR.string.no_tasks)
            )
        else
            LazyColumn(
                modifier = modifier.fillMaxSize()
                    .padding(paddingValues = paddingValues),
            ) {
                taskStateGroups.forEach { taskStateGroup ->
                    val taskState = taskStateGroup.taskState
                    taskStateGroupContent(
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
                    )
                }
            }
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
            navigateManageCategories = {},
            onSettingClick = {},
            onCategoryClick = {},
            onTaskToggleClick = { _, _ -> },
            onTaskEditClick = {},
            onDeleteSymbolClick = {},
            onSymbolClick = { _, _ -> },
            onAddTaskClick = {},
            onShowMessageSnackbar = {}
        )
    }
}