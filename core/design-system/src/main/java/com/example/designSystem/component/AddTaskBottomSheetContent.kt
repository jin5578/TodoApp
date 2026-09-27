package com.example.designSystem.component

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designSystem.R
import com.example.designSystem.component.dialog.calendar.CalendarDialog
import com.example.designSystem.component.dialog.reminder.ReminderDialog
import com.example.designSystem.component.dialog.timePicker.ClockTimePickerDialog
import com.example.designSystem.component.dialog.timePicker.ScrollTimePickerDialog
import com.example.designSystem.theme.TodoTheme
import com.example.designSystem.utils.LocalSnackbarHostState
import com.example.designSystem.utils.LocalSnackbarScope
import com.example.designSystem.utils.checkValidTask
import com.example.designSystem.utils.getTitleResId
import com.example.model.Category
import com.example.model.PriorityType
import com.example.model.Task
import com.example.model.TimePickerType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheetContent(
    modifier: Modifier = Modifier,
    locale: Locale,
    categories: ImmutableList<Category>,
    timePickerType: TimePickerType,
    onAddTaskClick: (Task) -> Unit,
    onCreateNewCategoryClick: () -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val snackbarScope = LocalSnackbarScope.current
    val onShowMessageSnackbar: (String) -> Unit = { message ->
        snackbarScope.launch { snackbarHostState.showSnackbar(message = message) }
    }

    var isShowCalendarDialog by remember { mutableStateOf(value = false) }
    var isShowTimePickerDialog by remember { mutableStateOf(value = false) }
    var isShowReminderDialog by remember { mutableStateOf(value = false) }

    var isShowCategoryMenu by remember { mutableStateOf(value = false) }
    var isShowPriorityMenu by remember { mutableStateOf(value = false) }

    var taskTitle by remember { mutableStateOf(value = "") }
    var taskCategory by remember { mutableLongStateOf(value = -1L) }
    var taskDate by remember { mutableStateOf(value = LocalDate.now()) }
    var taskTime: LocalDateTime? by remember { mutableStateOf(value = null) }
    var taskReminderTime: LocalDateTime? by remember { mutableStateOf(value = null) }
    var taskPriorityType by remember { mutableStateOf(value = PriorityType.LOW) }

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
    ) {
        if (isShowCalendarDialog) {
            CalendarDialog(
                taskDate = taskDate,
                taskTime = taskTime,
                reminderTime = taskReminderTime,
                locale = locale,
                onTimeClick = { isShowTimePickerDialog = true },
                onReminderClick = { isShowReminderDialog = true },
                onCloseClick = { isShowCalendarDialog = false },
                onConfirmClick = { date, time, reminderTime ->
                    taskDate = date
                    taskTime = time
                    taskReminderTime = reminderTime
                    isShowCalendarDialog = false
                },
            )
        }

        if (isShowTimePickerDialog) {
            if (timePickerType == TimePickerType.CLOCK_TIME_PICKER) {
                ClockTimePickerDialog(
                    taskDate = taskDate,
                    taskTime = taskTime,
                    onCloseClick = { isShowTimePickerDialog = false },
                    onConfirmClick = { dateTime ->
                        taskTime = dateTime
                        isShowTimePickerDialog = false
                    },
                )
            } else {
                ScrollTimePickerDialog(
                    taskDate = taskDate,
                    taskTime = taskTime,
                    onCloseClick = { isShowTimePickerDialog = false },
                    onConfirmClick = { dateTime ->
                        taskTime = dateTime
                        isShowTimePickerDialog = false
                    },
                )
            }
        }

        if (isShowReminderDialog) {
            val tempTime = taskTime ?: return@Column
            ReminderDialog(
                taskTime = tempTime,
                reminderTime = taskReminderTime,
                onCloseClick = {
                    isShowReminderDialog = false
                },
                onConfirmClick = { dateTime ->
                    taskReminderTime = dateTime
                    isShowReminderDialog = false
                },
            )
        }

        TaskTitleTextField(
            taskTitle = taskTitle,
            onValueChange = { taskTitle = it },
        )

        TaskActionRow(
            categories = categories,
            taskCategory = taskCategory,
            taskDate = taskDate,
            taskPriorityType = taskPriorityType,
            locale = locale,
            isShowCategoryMenu = isShowCategoryMenu,
            isShowPriorityMenu = isShowPriorityMenu,
            onCategoryMenuStateChanged = { state ->
                isShowCategoryMenu = state
            },
            onCategoryClick = { id ->
                taskCategory = id
            },
            onCreateNewCategoryClick = onCreateNewCategoryClick,
            onPriorityMenuStateChanged = { state ->
                isShowPriorityMenu = state
            },
            onPriorityTypeClick = { type ->
                taskPriorityType = type
            },
            onDateClick = { isShowCalendarDialog = true },
            onAddTaskClick = {
                val task = Task(
                    uuid = UUID.randomUUID().toString(),
                    title = taskTitle.trim(),
                    date = taskDate,
                    time = taskTime,
                    reminderTime = taskReminderTime,
                    priority = taskPriorityType.ordinal,
                    categoryId = taskCategory,
                    createdAt = LocalDateTime.now(),
                )

                val (isValid, errorMessage) = checkValidTask(task = task)
                if (isValid) {
                    onAddTaskClick(task)
                } else {
                    onShowMessageSnackbar(errorMessage)
                }
            },
        )
    }
}

@Composable
private fun TaskTitleTextField(
    modifier: Modifier = Modifier,
    taskTitle: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 8.dp)),
        value = taskTitle,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            cursorColor = MaterialTheme.colorScheme.onBackground,
        ),
        textStyle = TodoTheme.typography.medium_16,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = stringResource(id = R.string.please_enter_what_you_need_to_do),
                color = MaterialTheme.colorScheme.onBackground,
                style = TodoTheme.typography.medium_16,
            )
        },
        shape = RoundedCornerShape(size = 8.dp),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done,
        ),
    )
}

@Composable
private fun TaskActionRow(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    taskCategory: Long,
    taskDate: LocalDate,
    taskPriorityType: PriorityType,
    locale: Locale,
    isShowCategoryMenu: Boolean,
    isShowPriorityMenu: Boolean,
    onCategoryMenuStateChanged: (Boolean) -> Unit,
    onCategoryClick: (Long) -> Unit,
    onCreateNewCategoryClick: () -> Unit,
    onDateClick: () -> Unit,
    onPriorityMenuStateChanged: (Boolean) -> Unit,
    onPriorityTypeClick: (PriorityType) -> Unit,
    onAddTaskClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.weight(weight = 1f)
                .horizontalScroll(state = scrollState),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
        ) {
            TaskActionCategoryItem(
                categories = categories,
                taskCategory = taskCategory,
                isShowCategoryMenu = isShowCategoryMenu,
                onOpenClick = { onCategoryMenuStateChanged(true) },
                onCloseClick = { onCategoryMenuStateChanged(false) },
                onCategoryClick = { id ->
                    onCategoryClick(id)
                    onCategoryMenuStateChanged(false)
                },
                onCreateNewCategoryClick = {
                    onCreateNewCategoryClick()
                    onCategoryMenuStateChanged(false)
                },
            )

            val dateFormat = DateTimeFormatter.ofPattern(
                DateFormat.getBestDateTimePattern(locale, "MMMMd"),
                locale,
            )
            TaskActionItem(
                iconResId = R.drawable.svg_calendar,
                title = taskDate.format(dateFormat),
                onClick = onDateClick,
            )

            TaskActionPriorityItem(
                priorityType = taskPriorityType,
                isShowPriorityMenu = isShowPriorityMenu,
                onOpenClick = { onPriorityMenuStateChanged(true) },
                onCloseClick = { onPriorityMenuStateChanged(false) },
                onPriorityTypeClick = { type ->
                    onPriorityTypeClick(type)
                    onPriorityMenuStateChanged(false)
                },
            )
        }

        IconButton(onClick = onAddTaskClick) {
            Icon(
                modifier = Modifier.size(size = 20.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.svg_paper_plane),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
private fun TaskActionCategoryItem(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    taskCategory: Long,
    isShowCategoryMenu: Boolean,
    onOpenClick: () -> Unit,
    onCloseClick: () -> Unit,
    onCategoryClick: (Long) -> Unit,
    onCreateNewCategoryClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 16.dp))
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .padding(all = 8.dp)
            .clickable { onOpenClick() },
    ) {
        Text(
            text = categories.firstOrNull { it.id == taskCategory }?.title
                ?: stringResource(id = R.string.no_category),
            style = TodoTheme.typography.medium_12,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }

    CategoryDropdownMenu(
        categories = categories,
        isShowCategoryMenu = isShowCategoryMenu,
        onCloseClick = onCloseClick,
        onCategoryClick = onCategoryClick,
        onCreateNewCategoryClick = onCreateNewCategoryClick,
    )
}

@Composable
private fun TaskActionPriorityItem(
    priorityType: PriorityType,
    isShowPriorityMenu: Boolean,
    onOpenClick: () -> Unit,
    onCloseClick: () -> Unit,
    onPriorityTypeClick: (PriorityType) -> Unit,
) {
    Box {
        val titleResId = priorityType.getTitleResId()
        TaskActionItem(
            iconResId = R.drawable.svg_priority,
            title = stringResource(id = titleResId),
            onClick = onOpenClick,
        )

        PriorityDropdownMenu(
            isShowPriorityMenu = isShowPriorityMenu,
            onCloseClick = onCloseClick,
            onPriorityTypeClick = onPriorityTypeClick,
        )
    }
}

@Composable
private fun TaskActionItem(
    modifier: Modifier = Modifier,
    iconResId: Int,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(space = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(size = 20.dp),
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
        )

        Text(
            text = title,
            style = TodoTheme.typography.medium_12,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun PriorityDropdownMenu(
    isShowPriorityMenu: Boolean,
    onCloseClick: () -> Unit,
    onPriorityTypeClick: (PriorityType) -> Unit,
) {
    DropdownMenu(
        containerColor = MaterialTheme.colorScheme.background,
        expanded = isShowPriorityMenu,
        onDismissRequest = onCloseClick,
    ) {
        PriorityType.entries.forEach { priority ->
            val titleResId = priority.getTitleResId()
            BasicDropdownMenuItem(
                title = stringResource(id = titleResId),
                onClick = { onPriorityTypeClick(priority) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun AddTaskBottomSheetContentPreview() {
    TodoTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val snackbarScope = rememberCoroutineScope()
        val categories = persistentListOf(
            Category(
                id = 3690,
                title = "solet",
                colorValue = 3145,
            ),
        )
        CompositionLocalProvider(
            LocalSnackbarHostState provides snackbarHostState,
            LocalSnackbarScope provides snackbarScope,
        ) {
            AddTaskBottomSheetContent(
                locale = Locale.KOREA,
                categories = categories,
                timePickerType = TimePickerType.SCROLL_TIME_PICKER,
                onAddTaskClick = {},
                onCreateNewCategoryClick = {},
            )
        }
    }
}
