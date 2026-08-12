package com.example.edit_task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.design_system.component.dialog.date_picker.DatePickerDialog
import com.example.design_system.component.dialog.time_picker.ClockTimePickerDialog
import com.example.design_system.component.dialog.time_picker.ScrollTimePickerDialog
import com.example.design_system.component.input.InputTaskCategories
import com.example.design_system.component.input.InputTaskDate
import com.example.design_system.component.input.InputTaskMemo
import com.example.design_system.component.input.InputTaskPriority
import com.example.design_system.component.input.InputTaskReminder
import com.example.design_system.component.input.InputTaskTime
import com.example.design_system.component.input.InputTaskTitle
import com.example.design_system.theme.TodoTheme
import com.example.design_system.theme.priorityColors
import com.example.edit_task.model.EditTaskUiEffect
import com.example.edit_task.model.EditTaskUiState
import com.example.model.Category
import com.example.model.PriorityType
import com.example.model.ReminderTimeType
import com.example.model.Task
import com.example.model.TimePickerType
import com.example.utils.checkValidTask
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@Composable
internal fun EditTaskRoute(
    viewModel: EditTaskViewModel = hiltViewModel(),
    taskId: Long,
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(key1 = taskId) {
        viewModel.fetchEditTask(taskId = taskId)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEffect.collectLatest { uiEffect ->
            if (uiEffect is EditTaskUiEffect.SuccessEditTask) {
                val message = uiEffect.message
                onShowMessageSnackbar(message)
                popBackStack()
            }
        }
    }

    EditTaskContent(
        uiState = uiState,
        popBackStack = popBackStack,
        onUpdateTaskClick = viewModel::updateTask,
        onTaskDelete = viewModel::deleteTask,
        onShowMessageSnackbar = onShowMessageSnackbar
    )
}

@Composable
private fun EditTaskContent(
    uiState: EditTaskUiState,
    popBackStack: () -> Unit,
    onUpdateTaskClick: (Task) -> Unit,
    onTaskDelete: (id: Long, uuid: String) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) {
    when (uiState) {
        is EditTaskUiState.Loading ->
            Loading()

        is EditTaskUiState.Success ->
            EditTaskScreen(
                task = uiState.task,
                locale = uiState.locale,
                timePickerType = uiState.timePickerType,
                categories = uiState.categories,
                popBackStack = popBackStack,
                onUpdateTaskClick = onUpdateTaskClick,
                onTaskDelete = onTaskDelete,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditTaskScreen(
    modifier: Modifier = Modifier,
    task: Task,
    locale: Locale,
    timePickerType: TimePickerType,
    categories: ImmutableList<Category>,
    popBackStack: () -> Unit,
    onUpdateTaskClick: (Task) -> Unit,
    onTaskDelete: (id: Long, uuid: String) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) {
    val titleFocusRequester = remember { FocusRequester() }
    val memoFocusRequester = remember { FocusRequester() }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var isShowDatePickerDialog by remember { mutableStateOf(value = false) }
    var isShowTimePickerDialog by remember { mutableStateOf(value = false) }

    var taskTitle by remember { mutableStateOf(value = task.title) }
    var taskCategoryId by remember { mutableLongStateOf(value = task.categoryId) }
    var taskDate by remember { mutableStateOf(value = task.date) }
    var taskTime by remember { mutableStateOf(value = task.time) }
    var taskPriorityType by remember {
        mutableStateOf(
            value = PriorityType.entries.getOrNull(index = task.priority)
                ?: PriorityType.LOW
        )
    }
    var taskMemo by remember { mutableStateOf(value = task.memo) }
    var taskReminder by remember { mutableStateOf(value = task.isRemind) }
    var reminderTimeType by remember {
        mutableStateOf(
            value = ReminderTimeType.entries.getOrNull(
                index = task.reminderTime
            ) ?: ReminderTimeType.ON_TIME
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = stringResource(id = DesignSystemR.string.edit_task),
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
                },
                actions = {
                    IconButton(
                        onClick = { onTaskDelete(task.id, task.uuid) }
                    ) {
                        Icon(
                            modifier = modifier.size(size = 21.dp),
                            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_trash),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isShowDatePickerDialog) {
            DatePickerDialog(
                initDay = taskDate,
                locale = locale,
                onClose = { day ->
                    taskDate = day
                    isShowDatePickerDialog = false
                },
                onShowMessageSnackbar = onShowMessageSnackbar
            )
        }

        if (isShowTimePickerDialog) {
            if (timePickerType == TimePickerType.SCROLL_TIME_PICKER) {
                ScrollTimePickerDialog(
                    initTime = taskTime,
                    onClose = {
                        taskTime = it
                        isShowTimePickerDialog = false
                    }
                )
            } else {
                ClockTimePickerDialog(
                    initTime = taskTime,
                    onClose = {
                        taskTime = it
                        isShowTimePickerDialog = false
                    }
                )
            }
        }

        LaunchedEffect(key1 = Unit) {
            titleFocusRequester.requestFocus()
        }

        Column(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues)
                .verticalScroll(state = scrollState)
                .background(color = MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = 30.dp),
            ) {
                InputTaskTitle(
                    focusRequester = titleFocusRequester,
                    backgroundColor = priorityColors[taskPriorityType.ordinal],
                    title = taskTitle,
                    onValueChange = { taskTitle = it }
                )

                if (categories.isNotEmpty()) {
                    InputTaskCategories(
                        categories = categories,
                        categoryId = taskCategoryId,
                        onSelectClick = { taskCategoryId = it }
                    )
                }

                InputTaskDate(
                    date = taskDate,
                    locale = locale,
                    onDateChange = { taskDate = it },
                    onShowDatePickerDialog = { isShowDatePickerDialog = true }
                )

                InputTaskTime(
                    time = taskTime,
                    locale = locale,
                    onTimeChange = { taskTime = it },
                    onShowTimePickerDialog = { isShowTimePickerDialog = true }
                )

                InputTaskPriority(
                    priorityType = taskPriorityType,
                    onSelect = { taskPriorityType = it }
                )

                InputTaskMemo(
                    focusRequester = memoFocusRequester,
                    memo = taskMemo,
                    onValueChange = { taskMemo = it }
                )

                InputTaskReminder(
                    isRemind = taskReminder,
                    reminderTimeType = reminderTimeType,
                    onReminderChanged = {
                        coroutineScope.launch {
                            delay(timeMillis = 300)
                            if (it) {
                                scrollState.animateScrollTo(value = scrollState.maxValue)
                            } else {
                                reminderTimeType = ReminderTimeType.ON_TIME
                            }
                        }
                        taskReminder = it
                    },
                    onReminderTimeTypeChanged = { reminderTimeType = it }
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(all = 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val updateTask = Task(
                            id = task.id,
                            uuid = task.uuid,
                            title = taskTitle.trim(),
                            isCompleted = task.isCompleted,
                            isRemind = task.isRemind,
                            time = taskTime,
                            date = taskDate,
                            memo = taskMemo,
                            priority = taskPriorityType.ordinal,
                            categoryId = taskCategoryId,
                            reminderTime = reminderTimeType.ordinal,
                        )

                        val (isValid, errorMessage) = checkValidTask(
                            updateTask
                        )

                        if (isValid)
                            onUpdateTaskClick(updateTask)
                        else
                            onShowMessageSnackbar(errorMessage)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    shape = RoundedCornerShape(size = 16.dp),
                ) {
                    Text(
                        modifier = Modifier.padding(all = 8.dp),
                        text = stringResource(id = DesignSystemR.string.edit_task),
                        style = TodoTheme.typography.headlineSmall,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTaskScreenPreview() {
    TodoTheme {
        val task = Task(
            id = 0,
            uuid = "",
            title = "",
            isCompleted = true,
            isRemind = true,
            time = LocalTime.now(),
            date = LocalDate.now(),
            memo = "",
            priority = PriorityType.LOW.ordinal,
            categoryId = 0,
            reminderTime = ReminderTimeType.ON_TIME.ordinal
        )
        EditTaskScreen(
            task = task,
            locale = Locale.KOREA,
            timePickerType = TimePickerType.SCROLL_TIME_PICKER,
            categories = persistentListOf(),
            popBackStack = {},
            onUpdateTaskClick = {},
            onTaskDelete = { _, _ -> },
            onShowMessageSnackbar = {}
        )
    }
}