package com.example.edit_task.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.model.SubTask
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import com.example.design_system.R as DesignSystemR

@Composable
internal fun EditTaskSubTask(
    modifier: Modifier = Modifier,
    subTasks: ImmutableList<SubTask>,
    parentId: Long,
    onSubTasksSync: (parentId: Long, subTasks: List<SubTask>) -> Unit,
) {
    var draftSubTasks by remember { mutableStateOf(value = subTasks) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        draftSubTasks.forEachIndexed { index, subTask ->
            EditTaskSubTaskItem(
                subTask = subTask,
                onUpdate = { updatedSubTask ->
                    draftSubTasks =
                        draftSubTasks.mapIndexed { draftIndex, draftSubTask ->
                            if (draftIndex == index) updatedSubTask else draftSubTask
                        }.toImmutableList()
                    onSubTasksSync(parentId, draftSubTasks.toList())
                },
                onDelete = {
                    draftSubTasks =
                        draftSubTasks.minus(subTask).toImmutableList()
                    onSubTasksSync(parentId, draftSubTasks.toList())
                },
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    val subTask = SubTask(
                        id = -1L,
                        parentId = parentId,
                        title = "",
                        isCompleted = false
                    )
                    draftSubTasks =
                        draftSubTasks.plus(subTask).toImmutableList()
                }
                .padding(horizontal = 26.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 17.dp)
        ) {
            Icon(
                modifier = Modifier.size(size = 16.dp),
                imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_plus),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(id = DesignSystemR.string.add_subtask),
                style = TodoTheme.typography.medium_16,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
private fun EditTaskSubTaskItem(
    modifier: Modifier = Modifier,
    subTask: SubTask,
    onUpdate: (SubTask) -> Unit,
    onDelete: (SubTask) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(value = false) }

    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier.size(size = 18.dp),
            onClick = {
                val updatedSubTask =
                    subTask.copy(isCompleted = !subTask.isCompleted)
                onUpdate(updatedSubTask)
            }
        ) {
            if (subTask.isCompleted) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.svg_check_circle),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            } else {
                Box(
                    modifier = Modifier.size(size = 17.dp)
                        .border(
                            width = 1.8.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center,
                    content = {}
                )
            }
        }

        TextField(
            modifier = modifier
                .weight(weight = 1f)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            value = subTask.title,
            singleLine = false,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.onBackground,
            ),
            textStyle = TodoTheme.typography.medium_16,
            onValueChange = {
                val updatedSubTask = subTask.copy(title = it)
                onUpdate(updatedSubTask)
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.input_the_subtask),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = TodoTheme.typography.medium_16
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
        )

        if (isFocused) {
            IconButton(
                modifier = Modifier.size(size = 32.dp),
                onClick = { onDelete(subTask) }
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.svg_cross_small),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTaskSubTaskPreview() {
    TodoTheme {
        EditTaskSubTask(
            subTasks = persistentListOf(),
            parentId = -1L,
            onSubTasksSync = { _, _ -> },
        )
    }
}