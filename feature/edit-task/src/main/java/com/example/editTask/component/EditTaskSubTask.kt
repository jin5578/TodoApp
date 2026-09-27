package com.example.editTask.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.designSystem.theme.TodoTheme
import com.example.model.SubTask
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import com.example.designSystem.R as DesignSystemR

private data class DraftSubTaskItem(
    val key: Long,
    val subTask: SubTask,
)

@Composable
internal fun EditTaskSubTask(
    modifier: Modifier = Modifier,
    subTasks: ImmutableList<SubTask>,
    parentId: Long,
    onSubTasksSync: (parentId: Long, subTasks: List<SubTask>) -> Unit,
) {
    var draftItems by remember {
        mutableStateOf(
            value = subTasks.mapIndexed { index, subTask ->
                DraftSubTaskItem(key = index.toLong(), subTask = subTask)
            },
        )
    }

    var nextKey by remember { mutableLongStateOf(value = draftItems.size.toLong()) }

    var focusRequestKey by remember { mutableStateOf<Long?>(value = null) }

    var draggedKey by remember { mutableStateOf<Long?>(value = null) }
    var dragOffsetY by remember { mutableFloatStateOf(value = 0f) }

    val itemHeightsPx = remember { mutableStateMapOf<Long, Int>() }

    fun syncSubTasks(items: List<DraftSubTaskItem>) {
        draftItems = items
        onSubTasksSync(parentId, items.map { item -> item.subTask })
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        draftItems.forEach { item ->
            key(item.key) {
                EditTaskSubTaskItem(
                    modifier = Modifier
                        .onSizeChanged { size ->
                            itemHeightsPx[item.key] = size.height
                        }
                        .graphicsLayer {
                            translationY =
                                if (item.key == draggedKey) dragOffsetY else 0f
                        }
                        .zIndex(
                            zIndex = if (item.key == draggedKey) 1f else 0f,
                        ),
                    subTask = item.subTask,
                    requestFocus = item.key == focusRequestKey,
                    onFocusRequested = { focusRequestKey = null },
                    onUpdate = { updatedSubTask ->
                        syncSubTasks(
                            items = draftItems.map { draftItem ->
                                if (draftItem.key == item.key) {
                                    draftItem.copy(
                                        subTask = updatedSubTask,
                                    )
                                } else {
                                    draftItem
                                }
                            },
                        )
                    },
                    onDelete = {
                        itemHeightsPx.remove(item.key)
                        syncSubTasks(
                            items = draftItems.filterNot { draftItem ->
                                draftItem.key == item.key
                            },
                        )
                    },
                    onDragStart = {
                        draggedKey = item.key
                        dragOffsetY = 0f
                    },
                    onDrag = { dragAmountY ->
                        dragOffsetY += dragAmountY
                        var currentIndex =
                            draftItems.indexOfFirst { draftItem -> draftItem.key == item.key }

                        while (dragOffsetY > 0f && currentIndex < draftItems.lastIndex) {
                            val nextHeight =
                                itemHeightsPx[draftItems[currentIndex + 1].key]
                                    ?: break
                            if (dragOffsetY <= nextHeight / 2f) break

                            draftItems =
                                draftItems.toMutableList().apply {
                                    val temp = this[currentIndex]
                                    this[currentIndex] = this[currentIndex + 1]
                                    this[currentIndex + 1] = temp
                                }
                            dragOffsetY -= nextHeight
                            currentIndex += 1
                        }

                        while (dragOffsetY < 0f && currentIndex > 0) {
                            val prevHeight =
                                itemHeightsPx[draftItems[currentIndex - 1].key]
                                    ?: break
                            if (-dragOffsetY <= prevHeight / 2f) break

                            draftItems =
                                draftItems.toMutableList().apply {
                                    val temp = this[currentIndex]
                                    this[currentIndex] = this[currentIndex - 1]
                                    this[currentIndex - 1] = temp
                                }
                            dragOffsetY += prevHeight
                            currentIndex -= 1
                        }
                    },
                    onDragEnd = {
                        draggedKey = null
                        dragOffsetY = 0f
                        syncSubTasks(items = draftItems)
                    },
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    val subTask = SubTask(
                        id = -1L,
                        parentId = parentId,
                        title = "",
                        isCompleted = false,
                        sortOrder = 0,
                    )
                    val newItem =
                        DraftSubTaskItem(key = nextKey, subTask = subTask)
                    nextKey += 1
                    draftItems = draftItems + newItem
                    focusRequestKey = newItem.key
                }
                .padding(horizontal = 26.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 17.dp),
        ) {
            Icon(
                modifier = Modifier.size(size = 16.dp),
                imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_plus),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
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
    requestFocus: Boolean,
    onFocusRequested: () -> Unit,
    onUpdate: (SubTask) -> Unit,
    onDelete: (SubTask) -> Unit,
    onDragStart: () -> Unit,
    onDrag: (dragAmountY: Float) -> Unit,
    onDragEnd: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(value = false) }

    LaunchedEffect(key1 = requestFocus) {
        if (requestFocus) {
            focusRequester.requestFocus()
            onFocusRequested()
        }
    }

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
            },
        ) {
            if (subTask.isCompleted) {
                Icon(
                    modifier = Modifier.size(size = 18.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_check_circle),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            } else {
                Box(
                    modifier = Modifier.size(size = 17.dp)
                        .border(
                            width = 1.8.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = CircleShape,
                        ),
                    contentAlignment = Alignment.Center,
                    content = {},
                )
            }
        }

        TextField(
            modifier = Modifier
                .weight(weight = 1f)
                .focusRequester(focusRequester = focusRequester)
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
            textStyle = TodoTheme.typography.medium_16.copy(
                textDecoration = if (subTask.isCompleted) TextDecoration.LineThrough else null,
            ),
            onValueChange = {
                val updatedSubTask = subTask.copy(title = it)
                onUpdate(updatedSubTask)
            },
            placeholder = {
                Text(
                    text = stringResource(id = DesignSystemR.string.input_the_subtask),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = TodoTheme.typography.medium_16,
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() },
            ),
        )

        IconButton(
            modifier = Modifier.size(size = 20.dp),
            onClick = {
                val updatedSubTask =
                    subTask.copy(isCompleted = !subTask.isCompleted)
                onUpdate(updatedSubTask)
            },
        ) {
            if (isFocused) {
                Icon(
                    modifier = Modifier.size(20.dp)
                        .clickable { onDelete(subTask) },
                    painter = painterResource(id = DesignSystemR.drawable.svg_cross_small),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            } else {
                Icon(
                    modifier = Modifier.size(size = 16.dp)
                        .pointerInput(Unit) {
                            detectDragGesturesAfterLongPress(
                                onDragStart = {
                                    onDragStart()
                                    focusManager.clearFocus()
                                },
                                onDragEnd = { onDragEnd() },
                                onDragCancel = { onDragEnd() },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    onDrag(dragAmount.y)
                                },
                            )
                        },
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_menu),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
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
