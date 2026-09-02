package com.example.home.component.bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.dialog.date_picker.DatePickerDialog
import com.example.design_system.theme.TodoTheme
import com.example.model.Category
import com.example.model.PriorityType
import com.example.model.Task
import com.example.model.TimePickerType
import com.example.utils.checkValidTask
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddTaskBottomSheetContent(
    modifier: Modifier = Modifier,
    locale: Locale,
    categories: ImmutableList<Category>,
    timePickerType: TimePickerType,
    navigateManageCategories: () -> Unit,
    onAddTaskClick: (Task) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) {
    var isShowDatePickerDialog by remember { mutableStateOf(value = false) }
    var isShowTimePickerDialog by remember { mutableStateOf(value = false) }

    var isShowCategoryMenu by remember { mutableStateOf(value = false) }
    var isShowPriorityMenu by remember { mutableStateOf(value = false) }
    var isShowReminderMenu by remember { mutableStateOf(value = false) }

    var taskTitle by remember { mutableStateOf(value = "") }
    var taskCategory by remember { mutableLongStateOf(value = -1L) }
    var taskDate by remember { mutableStateOf(value = LocalDate.now()) }
    var taskTime: LocalDateTime? by remember { mutableStateOf(value = null) }
    var taskReminderTime: LocalDateTime? by remember { mutableStateOf(value = null) }
    var taskPriorityType by remember { mutableStateOf(value = PriorityType.LOW) }

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp)
    ) {
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
            /*if (timePickerType == TimePickerType.SCROLL_TIME_PICKER) {
                ScrollTimePickerDialog(
                    initTime = *//*taskTime*//*LocalDateTime.now(),
                    onClose = {
                        *//*taskTime = it*//*
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
            }*/
        }

        TaskTitleTextField(
            taskTitle = taskTitle,
            onValueChange = { taskTitle = it }
        )

        TaskActionRow(
            categories = categories,
            taskCategory = taskCategory,
            taskDate = taskDate,
            taskTime = taskTime,
            taskReminderTime = taskReminderTime,
            taskPriorityType = taskPriorityType,
            locale = locale,
            isShowCategoryMenu = isShowCategoryMenu,
            isShowPriorityMenu = isShowPriorityMenu,
            isShowReminderMenu = isShowReminderMenu,
            onCategoryMenuStateChanged = { state ->
                isShowCategoryMenu = state
            },
            onCategoryClick = { id ->
                taskCategory = id
            },
            onCreateNewCategoryClick = {
                navigateManageCategories()
            },
            onPriorityMenuStateChanged = { state ->
                isShowPriorityMenu = state
            },
            onPriorityTypeClick = { type ->
                taskPriorityType = type
            },
            onReminderMenuStateChanged = { state ->
                isShowReminderMenu = state
            },
            onReminderTimeTypeClick = { type ->
                /*taskReminder = true*/
                /*
                                reminderTimeType = type
                */
            },
            onReminderOffClick = { /*taskReminder = false*/ },
            onDateClick = { isShowDatePickerDialog = true },
            onTimeClick = { isShowTimePickerDialog = true },
            onAddTaskClick = {
                val task = Task(
                    uuid = UUID.randomUUID().toString(),
                    title = taskTitle.trim(),
                    isCompleted = false,
                    isRemind = /*taskReminder*/true,
                    time = /*taskTime*/LocalDateTime.now(),
                    date = taskDate,
                    memoTitle = "",
                    memoContent = "",
                    memoUpdatedAt = null,
                    priority = taskPriorityType.ordinal,
                    categoryId = taskCategory,
                    reminderTime = /*reminderTimeType.ordinal*/LocalDateTime.now(),
                )

                val (isValid, errorMessage) = checkValidTask(task = task)
                if (isValid)
                    onAddTaskClick(task)
                else
                    onShowMessageSnackbar(errorMessage)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun AddTaskBottomSheetContentPreview() {
    TodoTheme {
        val categories = persistentListOf(
            Category(
                id = 3690, title = "solet", colorValue = 3145
            )
        )
        AddTaskBottomSheetContent(
            locale = Locale.KOREA,
            categories = categories,
            timePickerType = TimePickerType.SCROLL_TIME_PICKER,
            navigateManageCategories = {},
            onAddTaskClick = {},
            onShowMessageSnackbar = {}
        )
    }
}