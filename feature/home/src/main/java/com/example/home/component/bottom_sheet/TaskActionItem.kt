package com.example.home.component.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.home.utils.getTitleResId
import com.example.model.Category
import com.example.model.PriorityType
import com.example.model.ReminderTimeType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import com.example.design_system.R as DesignSystemR

@Composable
internal fun TaskActionCategoryItem(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    taskCategory: Long,
    isShowCategoryMenu: Boolean,
    onOpenClick: () -> Unit,
    onCloseClick: () -> Unit,
    onCategoryClick: (Long) -> Unit,
    onCreateNewCategoryClick: () -> Unit,
) {
    Box {
        Box(
            modifier = modifier.clip(
                shape = RoundedCornerShape(
                    size = 16.dp
                )
            )
                .background(color = MaterialTheme.colorScheme.surfaceDim)
                .padding(all = 8.dp)
                .clickable { onOpenClick() }
        ) {
            Text(
                text = categories.firstOrNull { it.id == taskCategory }?.title
                    ?: stringResource(id = DesignSystemR.string.no_category),
                style = TodoTheme.typography.medium_10,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        CategoryDropdownMenu(
            categories = categories,
            isShowCategoryMenu = isShowCategoryMenu,
            onCloseClick = onCloseClick,
            onCategoryClick = onCategoryClick,
            onCreateNewCategoryClick = onCreateNewCategoryClick
        )
    }
}

@Composable
internal fun TaskActionItem(
    modifier: Modifier = Modifier,
    iconResId: Int,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(space = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(size = 20.dp),
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = title,
            style = TodoTheme.typography.medium_10,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
internal fun TaskActionPriorityItem(
    modifier: Modifier = Modifier,
    priorityType: PriorityType,
    isShowPriorityMenu: Boolean,
    onOpenClick: () -> Unit,
    onCloseClick: () -> Unit,
    onPriorityTypeClick: (PriorityType) -> Unit,
) {
    Box {
        val titleResId = priorityType.getTitleResId()
        TaskActionItem(
            iconResId = DesignSystemR.drawable.svg_priority,
            title = stringResource(id = titleResId),
            onClick = onOpenClick
        )

        PriorityDropdownMenu(
            isShowPriorityMenu = isShowPriorityMenu,
            onCloseClick = onCloseClick,
            onPriorityTypeClick = onPriorityTypeClick,
        )
    }
}

@Composable
internal fun TaskActionReminderItem(
    modifier: Modifier = Modifier,
    taskReminder: Boolean,
    reminderTimeType: ReminderTimeType,
    isShowReminderMenu: Boolean,
    onOpenClick: () -> Unit,
    onCloseClick: () -> Unit,
    onReminderTimeTypeClick: (ReminderTimeType) -> Unit,
    onReminderOffClick: () -> Unit,
) {
    Box {
        val titleResId =
            if (taskReminder) reminderTimeType.getTitleResId() else DesignSystemR.string.off

        TaskActionItem(
            iconResId = DesignSystemR.drawable.svg_reminder,
            title = stringResource(id = titleResId),
            onClick = onOpenClick
        )

        ReminderDropdownMenu(
            isShowReminderMenu = isShowReminderMenu,
            onCloseClick = onCloseClick,
            onReminderTimeTypeClick = onReminderTimeTypeClick,
            onReminderOffClick = onReminderOffClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskActionCategoryItemPreview() {
    TodoTheme {
        TaskActionCategoryItem(
            categories = persistentListOf(),
            taskCategory = 1L,
            isShowCategoryMenu = false,
            onOpenClick = {},
            onCloseClick = {},
            onCategoryClick = {},
            onCreateNewCategoryClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskActionItemPreview() {
    TodoTheme {
        TaskActionItem(
            iconResId = DesignSystemR.drawable.svg_calendar,
            title = "Title",
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskActionPriorityItemPreview() {
    TodoTheme {
        TaskActionPriorityItem(
            priorityType = PriorityType.LOW,
            isShowPriorityMenu = false,
            onOpenClick = {},
            onCloseClick = {},
            onPriorityTypeClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskActionReminderItemPreview() {
    TodoTheme {
        TaskActionReminderItem(
            taskReminder = true,
            reminderTimeType = ReminderTimeType.ON_TIME,
            isShowReminderMenu = false,
            onOpenClick = {},
            onCloseClick = {},
            onReminderTimeTypeClick = {},
            onReminderOffClick = {}
        )
    }
}