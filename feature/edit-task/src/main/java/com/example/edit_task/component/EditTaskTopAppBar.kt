package com.example.edit_task.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.BasicDropdownMenuItem
import com.example.design_system.theme.TodoTheme
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditTaskTopAppBar(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
    isCompleted: Boolean,
    onCompletedChanged: (isCompleted: Boolean) -> Unit,
    onDeleteClick: () -> Unit,
) {
    var isShowDropdownMenu by remember { mutableStateOf(value = false) }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {},
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
                    modifier = modifier.size(size = 20.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_menu_dots),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )

                DropdownMenu(
                    containerColor = MaterialTheme.colorScheme.background,
                    expanded = isShowDropdownMenu,
                    onDismissRequest = { isShowDropdownMenu = false }
                ) {
                    BasicDropdownMenuItem(
                        title =
                            if (isCompleted) stringResource(id = DesignSystemR.string.mark_as_undone)
                            else stringResource(id = DesignSystemR.string.mark_as_done),
                        onClick = {
                            onCompletedChanged(!isCompleted)
                            isShowDropdownMenu = false
                        }
                    )

                    BasicDropdownMenuItem(
                        title = stringResource(id = DesignSystemR.string.delete),
                        onClick = {
                            onDeleteClick()
                            isShowDropdownMenu = false
                        }
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun EditTaskTopAppBarPreview() {
    TodoTheme {
        EditTaskTopAppBar(
            popBackStack = {},
            isCompleted = false,
            onCompletedChanged = {},
            onDeleteClick = {}
        )
    }
}