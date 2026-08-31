package com.example.home.component.bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.BasicDropdownMenuItem
import com.example.design_system.theme.TodoTheme
import com.example.home.utils.getTitleResId
import com.example.model.Category
import com.example.model.PriorityType
import com.example.model.ReminderTimeType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import com.example.design_system.R as DesignSystemR


@Composable
internal fun CategoryDropdownMenu(
    categories: ImmutableList<Category>,
    isShowCategoryMenu: Boolean,
    onCloseClick: () -> Unit,
    onCategoryClick: (Long) -> Unit,
    onCreateNewCategoryClick: () -> Unit,
) {
    DropdownMenu(
        containerColor = MaterialTheme.colorScheme.surface,
        expanded = isShowCategoryMenu,
        onDismissRequest = onCloseClick
    ) {
        categories.forEach { category ->
            BasicDropdownMenuItem(
                title = category.title,
                onClick = { onCategoryClick(category.id) }
            )
        }
        DropdownMenuItem(
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(size = 12.dp),
                        imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_plus_small),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = stringResource(id = DesignSystemR.string.create_new),
                        style = TodoTheme.typography.medium_10,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            onClick = onCreateNewCategoryClick
        )
    }
}

@Composable
internal fun PriorityDropdownMenu(
    isShowPriorityMenu: Boolean,
    onCloseClick: () -> Unit,
    onPriorityTypeClick: (PriorityType) -> Unit,
) {
    DropdownMenu(
        containerColor = MaterialTheme.colorScheme.surface,
        expanded = isShowPriorityMenu,
        onDismissRequest = onCloseClick
    ) {
        PriorityType.entries.forEach { priority ->
            val titleResId = priority.getTitleResId()
            BasicDropdownMenuItem(
                title = stringResource(id = titleResId),
                onClick = { onPriorityTypeClick(priority) }
            )
        }
    }
}

@Composable
internal fun ReminderDropdownMenu(
    isShowReminderMenu: Boolean,
    onCloseClick: () -> Unit,
    onReminderTimeTypeClick: (ReminderTimeType) -> Unit,
    onReminderOffClick: () -> Unit,
) {
    DropdownMenu(
        containerColor = MaterialTheme.colorScheme.surface,
        expanded = isShowReminderMenu,
        onDismissRequest = onCloseClick
    ) {
        ReminderTimeType.entries.forEach { type ->
            val titleResId = type.getTitleResId()
            BasicDropdownMenuItem(
                title = stringResource(id = titleResId),
                onClick = { onReminderTimeTypeClick(type) }
            )
        }

        BasicDropdownMenuItem(
            title = stringResource(id = DesignSystemR.string.off),
            onClick = onReminderOffClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryDropdownMenuPreview() {
    TodoTheme {
        CategoryDropdownMenu(
            categories = persistentListOf(),
            isShowCategoryMenu = false,
            onCloseClick = {},
            onCategoryClick = {},
            onCreateNewCategoryClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PriorityDropdownMenuPreview() {
    TodoTheme {
        PriorityDropdownMenu(
            isShowPriorityMenu = false,
            onCloseClick = {},
            onPriorityTypeClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReminderDropdownMenuPreview() {
    TodoTheme {
        ReminderDropdownMenu(
            isShowReminderMenu = false,
            onCloseClick = {},
            onReminderTimeTypeClick = {},
            onReminderOffClick = {}
        )
    }
}