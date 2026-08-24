package com.example.home.component.bottom_sheet

import android.text.format.DateFormat
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.model.Category
import com.example.model.PriorityType
import com.example.model.ReminderTimeType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@Composable
internal fun TaskActionRow(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    taskCategory: Long,
    taskDate: LocalDate,
    taskTime: LocalTime,
    taskPriorityType: PriorityType,
    taskReminder: Boolean,
    reminderTimeType: ReminderTimeType,
    locale: Locale,
    isShowCategoryMenu: Boolean,
    isShowPriorityMenu: Boolean,
    isShowReminderMenu: Boolean,
    onCategoryMenuStateChanged: (Boolean) -> Unit,
    onCategoryClick: (Long) -> Unit,
    onCreateNewCategoryClick: () -> Unit,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit,
    onPriorityMenuStateChanged: (Boolean) -> Unit,
    onPriorityTypeClick: (PriorityType) -> Unit,
    onReminderMenuStateChanged: (Boolean) -> Unit,
    onReminderTimeTypeClick: (ReminderTimeType) -> Unit,
    onReminderOffClick: () -> Unit,
    onAddTaskClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(weight = 1f)
                .horizontalScroll(state = scrollState),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 10.dp)
        ) {
            if (categories.isNotEmpty()) {
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
                    }
                )
            }

            val dateFormat = DateTimeFormatter.ofPattern(
                DateFormat.getBestDateTimePattern(locale, "MMMMd"),
                locale
            )
            TaskActionItem(
                iconResId = DesignSystemR.drawable.svg_calendar,
                title = taskDate.format(dateFormat),
                onClick = onDateClick
            )

            val timeFormat =
                DateTimeFormatter.ofPattern("hh : mm a", locale)
            TaskActionItem(
                iconResId = DesignSystemR.drawable.svg_clock,
                title = taskTime.format(timeFormat),
                onClick = onTimeClick
            )

            TaskActionPriorityItem(
                priorityType = taskPriorityType,
                isShowPriorityMenu = isShowPriorityMenu,
                onOpenClick = { onPriorityMenuStateChanged(true) },
                onCloseClick = { onPriorityMenuStateChanged(false) },
                onPriorityTypeClick = { type ->
                    onPriorityTypeClick(type)
                    onPriorityMenuStateChanged(false)
                }
            )

            TaskActionReminderItem(
                taskReminder = taskReminder,
                reminderTimeType = reminderTimeType,
                isShowReminderMenu = isShowReminderMenu,
                onOpenClick = { onReminderMenuStateChanged(true) },
                onCloseClick = { onReminderMenuStateChanged(false) },
                onReminderTimeTypeClick = { type ->
                    onReminderTimeTypeClick(type)
                    onReminderMenuStateChanged(false)
                },
                onReminderOffClick = {
                    onReminderOffClick()
                    onReminderMenuStateChanged(false)
                },
            )
        }

        IconButton(onClick = onAddTaskClick) {
            Icon(
                modifier = Modifier.size(size = 20.dp),
                imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_paper_plane),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TaskActionRowPreview() {
    TodoTheme {
        TaskActionRow(
            categories = persistentListOf(),
            taskCategory = 1L,
            taskDate = LocalDate.now(),
            taskTime = LocalTime.now(),
            taskPriorityType = PriorityType.LOW,
            taskReminder = false,
            reminderTimeType = ReminderTimeType.ON_TIME,
            locale = Locale.KOREA,
            isShowCategoryMenu = false,
            isShowPriorityMenu = false,
            isShowReminderMenu = false,
            onCategoryMenuStateChanged = {},
            onCategoryClick = {},
            onCreateNewCategoryClick = {},
            onDateClick = {},
            onTimeClick = {},
            onPriorityMenuStateChanged = {},
            onPriorityTypeClick = {},
            onReminderMenuStateChanged = {},
            onReminderTimeTypeClick = {},
            onReminderOffClick = {},
            onAddTaskClick = {}
        )
    }
}