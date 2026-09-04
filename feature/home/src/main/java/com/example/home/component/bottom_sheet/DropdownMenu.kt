package com.example.home.component.bottom_sheet

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.component.BasicDropdownMenuItem
import com.example.design_system.theme.TodoTheme
import com.example.home.utils.getTitleResId
import com.example.model.PriorityType
import com.example.model.ReminderTimeType
import com.example.design_system.R as DesignSystemR

@Composable
internal fun PriorityDropdownMenu(
    isShowPriorityMenu: Boolean,
    onCloseClick: () -> Unit,
    onPriorityTypeClick: (PriorityType) -> Unit,
) {
    DropdownMenu(
        containerColor = MaterialTheme.colorScheme.background,
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
        containerColor = MaterialTheme.colorScheme.background,
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