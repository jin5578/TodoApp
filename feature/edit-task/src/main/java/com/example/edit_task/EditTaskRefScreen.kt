package com.example.edit_task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.BasicDropdownMenuItem
import com.example.design_system.component.dialog.calendar.CalendarDialog
import com.example.design_system.component.dialog.reminder.ReminderDialog
import com.example.design_system.component.dialog.time_picker.TimePickerDialog
import com.example.design_system.theme.TodoTheme
import com.example.edit_task.component.EditTaskCategoryChip
import com.example.edit_task.component.EditTaskDateRow
import com.example.edit_task.component.EditTaskMemoRow
import com.example.edit_task.component.EditTaskTimeRow
import com.example.edit_task.component.EditTaskTitleTextField
import com.example.model.Category
import com.example.model.PriorityType
import com.example.model.Task
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditTaskRefScreen(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    task: Task,
    locale: Locale,
    navigateManageCategories: () -> Unit,
    navigateMemo: (Long) -> Unit,
    popBackStack: () -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    var isShowCalendarDialog by remember { mutableStateOf(value = false) }
    var isShowTimePickerDialog by remember { mutableStateOf(value = false) }
    var isShowReminderDialog by remember { mutableStateOf(value = false) }

    var isShowCategoryMenu by remember { mutableStateOf(value = false) }

    var taskCategoryId by remember { mutableLongStateOf(value = task.categoryId) }
    var taskTitle by remember { mutableStateOf(value = task.title) }
    var taskDate by remember { mutableStateOf(value = task.date) }
    var taskTime by remember { mutableStateOf(value = task.time) }
    var taskReminderTime by remember { mutableStateOf(value = task.reminderTime) }
    var taskMemoTitle by remember { mutableStateOf(value = task.memoTitle) }
    var taskMemoContent by remember { mutableStateOf(value = task.memoContent) }

    Scaffold(
        topBar = {
            EditTaskTopAppBar(popBackStack = popBackStack)
        }
    ) { paddingValues ->
        if (isShowCalendarDialog) {
            CalendarDialog(
                taskDate = taskDate,
                taskTime = taskTime,
                reminderTime = taskReminderTime,
                locale = locale,
                onTimeClick = { isShowTimePickerDialog = true },
                onReminderClick = { isShowReminderDialog = true },
                onCloseClick = {
                    isShowCalendarDialog = false
                },
                onConfirmClick = { date, time, reminderTime ->
                    taskDate = date
                    taskTime = time
                    taskReminderTime = reminderTime
                    isShowCalendarDialog = false
                },
                onShowMessageSnackbar = onShowMessageSnackbar
            )
        }

        if (isShowTimePickerDialog) {
            TimePickerDialog(
                taskDate = taskDate,
                taskTime = taskTime,
                onCloseClick = {
                    isShowTimePickerDialog = false
                },
                onConfirmClick = { dateTime ->
                    taskTime = dateTime
                    taskReminderTime = null
                    isShowTimePickerDialog = false
                }
            )
        }

        if (isShowReminderDialog) {
            val tempTime = taskTime ?: return@Scaffold
            ReminderDialog(
                taskTime = tempTime,
                reminderTime = taskReminderTime,
                onCloseClick = {
                    isShowReminderDialog = false
                },
                onConfirmClick = { dateTime ->
                    taskReminderTime = dateTime
                    isShowReminderDialog = false
                }
            )
        }

        Column(modifier = modifier.padding(paddingValues = paddingValues)) {
            EditTaskCategoryChip(
                categories = categories,
                taskCategoryId = taskCategoryId,
                isShowCategoryMenu = isShowCategoryMenu,
                onOpenClick = { isShowCategoryMenu = true },
                onCloseClick = { isShowCategoryMenu = false },
                onCategoryClick = { id ->
                    taskCategoryId = id
                    isShowCategoryMenu = false
                },
                onCreateNewCategoryClick = {
                    isShowCategoryMenu = false
                    navigateManageCategories()
                },
            )

            EditTaskTitleTextField(
                title = taskTitle,
                onValueChange = { title -> taskTitle = title }
            )

            EditTaskDateRow(
                date = taskDate,
                locale = locale,
                onClick = { isShowCalendarDialog = true }
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.surfaceDim
            )

            EditTaskTimeRow(
                locale = locale,
                time = taskTime,
                reminderTime = taskReminderTime,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditTaskTopAppBar(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
) {
    var isShowDropdownMenu by remember { mutableStateOf(value = false) }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {

        },
        navigationIcon = {
            IconButton(onClick = popBackStack) {
                Icon(
                    modifier = modifier.size(size = 24.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_left),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    isShowDropdownMenu = true
                }
            ) {
                Icon(
                    modifier = Modifier.size(size = 20.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_menu_dots),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )

                DropdownMenu(
                    containerColor = MaterialTheme.colorScheme.surface,
                    expanded = isShowDropdownMenu,
                    onDismissRequest = { isShowDropdownMenu = false }
                ) {
                    BasicDropdownMenuItem(
                        title = "하하",
                        onClick = {}
                    )
                }
            }
        }
    )
}

/*@Composable
private fun EditTaskCategoryChip(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    taskCategoryId: Long,
    isShowCategoryMenu: Boolean,
    onOpenClick: () -> Unit,
    onCloseClick: () -> Unit,
    onCategoryClick: (Long) -> Unit,
    onCreateNewCategoryClick: () -> Unit,
) {
    Box(
        modifier = modifier.wrapContentSize()
            .padding(start = 16.dp)
            .clickable {
                onOpenClick()
            }
            .background(
                color = MaterialTheme.colorScheme.surfaceDim,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp,
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
        ) {
            Text(
                text = categories.firstOrNull { it.id == taskCategoryId }?.title
                    ?: stringResource(id = DesignSystemR.string.no_category),
                style = TodoTheme.typography.medium_10,
                color = MaterialTheme.colorScheme.onSurface
            )
            Icon(
                modifier = Modifier.size(size = 8.dp),
                imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_down),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    CategoryDropdownMenu(
        categories = categories,
        isShowCategoryMenu = isShowCategoryMenu,
        onCloseClick = onCloseClick,
        onCategoryClick = onCategoryClick,
        onCreateNewCategoryClick = onCreateNewCategoryClick
    )
}*/

@Composable
private fun EditTaskActionRow(
    modifier: Modifier = Modifier,
    iconResId: Int,
    titleResId: Int,
    content: String,
    contentBgColor: Color?,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clickable { onClick() }
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(size = 16.dp),
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
        )

        Text(
            modifier = Modifier.weight(weight = 1f),
            text = stringResource(id = titleResId),
            style = TodoTheme.typography.medium_14,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Box(
            modifier = Modifier.background(
                color = contentBgColor ?: Color.Transparent,
                shape = RoundedCornerShape(size = 8.dp)
            ).padding(
                horizontal = if (contentBgColor != null) 12.dp else 0.dp,
                vertical = 8.dp
            )
        ) {
            Text(
                text = content,
                style = TodoTheme.typography.medium_14,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}


@Composable
private fun EditTaskActionChildElementRow(
    modifier: Modifier = Modifier,
    titleResId: Int,
    content: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth().clickable { onClick() }
            .padding(start = 40.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(weight = 1f),
            text = stringResource(id = titleResId),
            style = TodoTheme.typography.medium_14,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Box(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(size = 8.dp)
            ).padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
        ) {
            Text(
                text = content,
                style = TodoTheme.typography.medium_14,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun EditTaskRefScreenPreview() {
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

        EditTaskRefScreen(
            categories = persistentListOf(),
            task = task,
            locale = Locale.KOREA,
            navigateManageCategories = {},
            navigateMemo = {},
            popBackStack = {},
            onShowMessageSnackbar = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTaskTopAppBarPreview() {
    TodoTheme {
        EditTaskTopAppBar(
            popBackStack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTaskCategoryChipPreview() {
    TodoTheme {
        EditTaskCategoryChip(
            categories = persistentListOf(),
            taskCategoryId = -1L,
            isShowCategoryMenu = false,
            onOpenClick = {},
            onCloseClick = { },
            onCategoryClick = {},
            onCreateNewCategoryClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTaskActionRowPreview() {
    TodoTheme {
        EditTaskActionRow(
            iconResId = -1,
            titleResId = -1,
            content = "",
            contentBgColor = Color.Red,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTaskActionChildElementRowPreview() {
    TodoTheme {
        EditTaskActionChildElementRow(
            titleResId = DesignSystemR.string.reminder_at,
            content = "",
            onClick = {}
        )
    }
}