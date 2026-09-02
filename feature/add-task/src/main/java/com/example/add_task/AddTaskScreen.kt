package com.example.add_task

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.example.model.Category
import com.example.model.PriorityType
import com.example.model.ReminderTimeType
import com.example.model.Task
import com.example.model.TimePickerType
import com.example.utils.checkValidTask
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale
import java.util.UUID
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddTaskScreen(
    modifier: Modifier = Modifier,
    date: LocalDate,
    locale: Locale,
    timePickerType: TimePickerType,
    categories: ImmutableList<Category>,
    popBackStack: () -> Unit,
    onAddTaskClick: (Task) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) {
    val titleFocusRequester = remember { FocusRequester() }
    val memoFocusRequester = remember { FocusRequester() }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var isShowDatePickerDialog by remember { mutableStateOf(value = false) }
    var isShowTimePickerDialog by remember { mutableStateOf(value = false) }

    var taskTitle by remember { mutableStateOf(value = "") }
    var taskCategory by remember { mutableLongStateOf(value = -1L) }
    var taskDate by remember { mutableStateOf(value = date) }
    var taskTime by remember { mutableStateOf(value = LocalDateTime.now()) }
    var taskPriorityType by remember { mutableStateOf(value = PriorityType.LOW) }
    var taskMemo by remember { mutableStateOf(value = "") }
    var taskReminder by remember { mutableStateOf(value = true) }
    var reminderTimeType by remember { mutableStateOf(value = ReminderTimeType.ON_TIME) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = stringResource(id = DesignSystemR.string.add_task),
                        style = TodoTheme.typography.bold_20,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = modifier.size(size = 24.dp),
                            imageVector = ImageVector.vectorResource(
                                id = DesignSystemR.drawable.svg_arrow_left
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
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
                    initTime = LocalDateTime.of(
                        taskDate,
                        taskTime.toLocalTime(),
                    ),
                    onClose = {
                        taskTime = it
                        isShowTimePickerDialog = false
                    }
                )
            } else {
                ClockTimePickerDialog(
                    initTime = taskTime.toLocalTime(),
                    onClose = {
                        taskTime = LocalDateTime.of(
                            taskDate,
                            it
                        )
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
                .background(color = MaterialTheme.colorScheme.background),
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
                        categoryId = taskCategory,
                        onSelectClick = { taskCategory = it }
                    )
                }

                InputTaskDate(
                    date = taskDate,
                    locale = locale,
                    onDateChange = { taskDate = it },
                    onShowDatePickerDialog = { isShowDatePickerDialog = true }
                )

                InputTaskTime(
                    time = taskTime.toLocalTime(),
                    locale = locale,
                    onTimeChange = {
                        taskTime =
                            LocalDateTime.of(taskDate, /*it*/LocalTime.now())
                    },
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
                modifier = Modifier.fillMaxWidth().padding(
                    start = 20.dp,
                    top = 30.dp,
                    end = 20.dp,
                    bottom = 20.dp
                ),
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = RoundedCornerShape(size = 16.dp),
                    onClick = {
                        val task = Task(
                            uuid = UUID.randomUUID().toString(),
                            title = taskTitle.trim(),
                            isCompleted = false,
                            isRemind = taskReminder,
                            time = taskTime,
                            date = taskDate,
                            reminderTime = LocalDateTime.now(),
                            memoTitle = taskMemo,
                            memoContent = "",
                            memoUpdatedAt = LocalDateTime.now(),
                            priority = taskPriorityType.ordinal,
                            categoryId = taskCategory,
                        )

                        val (isValid, errorMessage) = checkValidTask(task = task)
                        if (isValid)
                            onAddTaskClick(task)
                        else
                            onShowMessageSnackbar(errorMessage)
                    },
                ) {
                    Text(
                        modifier = Modifier.padding(all = 8.dp),
                        text = stringResource(id = DesignSystemR.string.add_task),
                        style = TodoTheme.typography.bold_14
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddTaskScreenPreview() {
    TodoTheme {
        AddTaskScreen(
            date = LocalDate.now(),
            locale = Locale.KOREA,
            timePickerType = TimePickerType.SCROLL_TIME_PICKER,
            categories = persistentListOf(),
            popBackStack = {},
            onAddTaskClick = {},
            onShowMessageSnackbar = {}
        )
    }
}