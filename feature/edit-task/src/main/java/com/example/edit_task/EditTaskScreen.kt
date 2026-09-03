package com.example.edit_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.dialog.calendar.CalendarDialog
import com.example.design_system.component.dialog.reminder.ReminderDialog
import com.example.design_system.component.dialog.time_picker.TimePickerDialog
import com.example.design_system.theme.TodoTheme
import com.example.edit_task.component.EditTaskCategoryChip
import com.example.edit_task.component.EditTaskDateRow
import com.example.edit_task.component.EditTaskMemoRow
import com.example.edit_task.component.EditTaskTimeRow
import com.example.edit_task.component.EditTaskTitleTextField
import com.example.edit_task.component.EditTaskTopAppBar
import com.example.model.Category
import com.example.model.PriorityType
import com.example.model.Task
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditTaskScreen(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
    navigateManageCategories: () -> Unit,
    navigateMemo: (Long) -> Unit,
    categories: ImmutableList<Category>,
    task: Task,
    locale: Locale,
    onCategoryClick: (taskId: Long, categoryId: Long) -> Unit,
    onTitleValueChanged: (taskId: Long, title: String) -> Unit,
    onDateTimeChanged: (taskId: Long, date: LocalDate, time: LocalDateTime?, reminderTime: LocalDateTime?) -> Unit,
    onCompletedChanged: (taskId: Long, isCompleted: Boolean) -> Unit,
    onDeleteClick: (id: Long, uuid: String) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    var isShowCalendarDialog by remember { mutableStateOf(value = false) }
    var isShowTimePickerDialog by remember { mutableStateOf(value = false) }
    var isShowReminderDialog by remember { mutableStateOf(value = false) }

    var isShowCategoryMenu by remember { mutableStateOf(value = false) }

    var taskTitle by remember { mutableStateOf(value = task.title) }

    Scaffold(
        topBar = {
            EditTaskTopAppBar(
                popBackStack = popBackStack,
                isCompleted = task.isCompleted,
                onCompletedChanged = { isCompleted ->
                    onCompletedChanged(
                        task.id,
                        isCompleted
                    )
                },
                onDeleteClick = { onDeleteClick(task.id, task.uuid) }
            )
        }
    ) { paddingValues ->
        if (isShowCalendarDialog) {
            CalendarDialog(
                taskDate = task.date,
                taskTime = task.time,
                reminderTime = task.reminderTime,
                locale = locale,
                onTimeClick = { isShowTimePickerDialog = true },
                onReminderClick = { isShowReminderDialog = true },
                onCloseClick = {
                    isShowCalendarDialog = false
                },
                onConfirmClick = { date, time, reminderTime ->
                    onDateTimeChanged(task.id, date, time, reminderTime)
                    isShowCalendarDialog = false
                },
                onShowMessageSnackbar = onShowMessageSnackbar
            )
        }

        if (isShowTimePickerDialog) {
            TimePickerDialog(
                taskDate = task.date,
                taskTime = task.time,
                onCloseClick = {
                    isShowTimePickerDialog = false
                },
                onConfirmClick = { dateTime ->
                    onDateTimeChanged(task.id, task.date, dateTime, null)
                    isShowTimePickerDialog = false
                }
            )
        }

        if (isShowReminderDialog) {
            val tempTime = task.time ?: return@Scaffold
            ReminderDialog(
                taskTime = tempTime,
                reminderTime = task.reminderTime,
                onCloseClick = {
                    isShowReminderDialog = false
                },
                onConfirmClick = { dateTime ->
                    onDateTimeChanged(task.id, task.date, task.time, dateTime)
                    isShowReminderDialog = false
                }
            )
        }

        Column(modifier = modifier.padding(paddingValues = paddingValues)) {
            EditTaskCategoryChip(
                categories = categories,
                taskCategoryId = task.categoryId,
                isShowCategoryMenu = isShowCategoryMenu,
                onOpenClick = { isShowCategoryMenu = true },
                onCloseClick = { isShowCategoryMenu = false },
                onCategoryClick = { categoryId ->
                    onCategoryClick(task.id, categoryId)
                    isShowCategoryMenu = false
                },
                onCreateNewCategoryClick = {
                    isShowCategoryMenu = false
                    navigateManageCategories()
                },
            )

            EditTaskTitleTextField(
                title = taskTitle,
                onValueChange = { title ->
                    taskTitle = title
                    onTitleValueChanged(task.id, title)
                }
            )

            EditTaskDateRow(
                date = task.date,
                locale = locale,
                onClick = { isShowCalendarDialog = true }
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.surfaceDim
            )

            EditTaskTimeRow(
                locale = locale,
                time = task.time,
                reminderTime = task.reminderTime,
                onTimeClick = {
                    isShowTimePickerDialog = true
                },
                onReminderTimeClick = {
                    isShowReminderDialog = true
                }
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.surfaceDim
            )

            EditTaskMemoRow(
                id = task.id,
                memoTitle = task.memoTitle,
                memoContent = task.memoContent,
                navigateMemo = navigateMemo
            )
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
            date = LocalDate.now(),
            time = LocalDateTime.now(),
            reminderTime = LocalDateTime.now(),
            memoTitle = "",
            memoContent = "",
            memoUpdatedAt = LocalDateTime.now(),
            priority = PriorityType.LOW.ordinal,
            categoryId = 0,
        )

        EditTaskScreen(
            popBackStack = {},
            categories = persistentListOf(),
            task = task,
            locale = Locale.KOREA,
            navigateManageCategories = {},
            navigateMemo = {},
            onCategoryClick = { _, _ -> },
            onTitleValueChanged = { _, _ -> },
            onDateTimeChanged = { _, _, _, _ -> },
            onCompletedChanged = { _, _ -> },
            onDeleteClick = { _, _ -> },
            onShowMessageSnackbar = {}
        )
    }
}