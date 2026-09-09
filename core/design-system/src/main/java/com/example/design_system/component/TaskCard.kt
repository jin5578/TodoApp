package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.model.TaskSymbolType
import com.example.design_system.theme.TodoTheme
import com.example.model.SubTask
import com.example.model.TaskUiModel
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: TaskUiModel,
    locale: Locale,
    onTaskToggleClick: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskEditClick: (Long) -> Unit,
    onDeleteSymbolClick: (Long) -> Unit,
    onSymbolClick: (taskId: Long, symbolId: Int) -> Unit,
    onSubTaskToggleClick: (subTaskId: Long, isCompleted: Boolean) -> Unit,
) {
    var isShowFlagMenu by remember { mutableStateOf(value = false) }

    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(size = 8.dp)
                ).clickable {
                    onTaskEditClick(task.id)
                }.padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
            ) {
                IconButton(
                    modifier = Modifier.size(size = 18.dp),
                    onClick = {
                        onTaskToggleClick(task.id, !task.isCompleted)
                    }
                ) {
                    if (task.isCompleted) {
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

                Column(
                    modifier = Modifier.weight(weight = 0.8f),
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth().basicMarquee(),
                        text = task.title,
                        style = TodoTheme.typography.bold_16,
                        color = MaterialTheme.colorScheme.onBackground,
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val dateFormat =
                            DateTimeFormatter.ofPattern("MM-dd", locale)
                        Text(
                            text = task.date.format(dateFormat),
                            style = TodoTheme.typography.medium_12,
                            color = MaterialTheme.colorScheme.error,
                        )

                        val timeFormat =
                            DateTimeFormatter.ofPattern("hh:mm a", locale)
                        task.time?.let { time ->
                            Text(
                                text = time.format(timeFormat),
                                style = TodoTheme.typography.medium_12,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        task.reminderTime?.let { reminderTime ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = 4.dp
                                )
                            ) {
                                Icon(
                                    modifier = Modifier.size(size = 12.dp),
                                    imageVector = ImageVector.vectorResource(id = R.drawable.svg_reminder),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )

                                Text(
                                    text = reminderTime.format(timeFormat),
                                    style = TodoTheme.typography.medium_12,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                        if (task.memoTitle.isNotEmpty() || task.memoContent.isNotEmpty()) {
                            Icon(
                                modifier = Modifier.size(size = 12.dp),
                                imageVector = ImageVector.vectorResource(id = R.drawable.svg_note),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        if (task.subTasks.isNotEmpty()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = 4.dp
                                )
                            ) {
                                Icon(
                                    modifier = Modifier.size(size = 12.dp),
                                    imageVector = ImageVector.vectorResource(id = R.drawable.svg_subtask),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )

                                val completedCount =
                                    task.subTasks.count { it.isCompleted }
                                Text(
                                    text = "$completedCount/${task.subTasks.size}",
                                    style = TodoTheme.typography.medium_12,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }

                Box {
                    IconButton(
                        modifier = Modifier.size(size = 20.dp),
                        onClick = {
                            isShowFlagMenu = true
                        }
                    ) {
                        val selectedSymbol = TaskSymbolType.entries
                            .flatMap { it.taskSymbols }
                            .firstOrNull { it.id == task.symbol }

                        if (selectedSymbol?.emojiIcon != null) {
                            Text(
                                text = selectedSymbol.emojiIcon,
                                style = TodoTheme.typography.medium_20,
                            )
                        } else {
                            val iconResId =
                                selectedSymbol?.symbolIcon?.iconResId
                                    ?: R.drawable.svg_flag_stroke
                            val iconTint = selectedSymbol?.symbolIcon?.iconColor
                                ?: MaterialTheme.colorScheme.onBackground

                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = ImageVector.vectorResource(id = iconResId),
                                contentDescription = null,
                                tint = iconTint
                            )
                        }
                    }

                    DropdownMenu(
                        modifier = Modifier.wrapContentSize().padding(
                            horizontal = 10.dp,
                            vertical = 2.dp,
                        ),
                        containerColor = MaterialTheme.colorScheme.background,
                        expanded = isShowFlagMenu,
                        onDismissRequest = { isShowFlagMenu = false }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.marked_with_a_symbol),
                                style = TodoTheme.typography.medium_12,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Text(
                                modifier = Modifier.clickable {
                                    onDeleteSymbolClick(
                                        task.id
                                    )
                                },
                                text = stringResource(id = R.string.delete),
                                style = TodoTheme.typography.medium_12,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        Spacer(modifier = Modifier.height(height = 10.dp))

                        TaskSymbolType.entries.forEachIndexed { index, type ->
                            DropdownMenuElement(
                                taskSymbolType = type,
                                onSymbolClick = { symbolId ->
                                    onSymbolClick(task.id, symbolId)
                                    isShowFlagMenu = false
                                },
                            )

                            if (index != TaskSymbolType.entries.size - 1)
                                Spacer(modifier = Modifier.height(height = 10.dp))
                        }
                    }
                }
            }

            if (task.subTasks.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 12.dp)
                ) {
                    task.subTasks.forEachIndexed { index, subTask ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 34.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
                        ) {
                            IconButton(
                                modifier = Modifier.size(size = 16.dp),
                                onClick = {
                                    onSubTaskToggleClick(
                                        subTask.id,
                                        !subTask.isCompleted
                                    )
                                }
                            ) {
                                if (subTask.isCompleted) {
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        painter = painterResource(id = R.drawable.svg_check_circle),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier.size(size = 15.dp)
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

                            Text(
                                text = subTask.title,
                                style = TodoTheme.typography.medium_16,
                                color = MaterialTheme.colorScheme.onBackground,
                                textDecoration = if (subTask.isCompleted) TextDecoration.LineThrough else null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DropdownMenuElement(
    modifier: Modifier = Modifier,
    taskSymbolType: TaskSymbolType,
    onSymbolClick: (Int) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(space = 10.dp)
    ) {
        Text(
            text = stringResource(id = taskSymbolType.titleResId),
            style = TodoTheme.typography.medium_08,
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            taskSymbolType.taskSymbols.forEach { symbol ->
                if (symbol.symbolIcon != null) {
                    TaskSymbolIconButton(
                        id = symbol.id,
                        iconResId = symbol.symbolIcon.iconResId,
                        iconColor = symbol.symbolIcon.iconColor,
                        onClick = onSymbolClick
                    )
                    return@forEach
                }

                if (symbol.emojiIcon != null) {
                    TaskEmojiIconButton(
                        id = symbol.id,
                        emojiIcon = symbol.emojiIcon,
                        onClick = onSymbolClick
                    )
                    return@forEach
                }
            }
        }
    }
}

@Composable
private fun TaskSymbolIconButton(
    modifier: Modifier = Modifier,
    id: Int,
    iconResId: Int,
    iconColor: Color,
    onClick: (Int) -> Unit,
) {
    IconButton(
        modifier = modifier.size(size = 24.dp),
        onClick = { onClick(id) }
    ) {
        Icon(
            modifier = Modifier.size(size = 20.dp),
            imageVector = ImageVector.vectorResource(
                id = iconResId
            ),
            contentDescription = null,
            tint = iconColor
        )
    }
}

@Composable
private fun TaskEmojiIconButton(
    modifier: Modifier = Modifier,
    id: Int,
    emojiIcon: String,
    onClick: (Int) -> Unit,
) {
    TextButton(
        modifier = modifier.size(size = 24.dp),
        contentPadding = PaddingValues(all = 0.dp),
        onClick = { onClick(id) }
    ) {
        Text(
            text = emojiIcon,
            style = TodoTheme.typography.medium_20,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCardPreview() {
    TodoTheme {

        val task = TaskUiModel(
            id = 8578,
            uuid = "corrumpit",
            title = "inceptos",
            isCompleted = false,
            time = LocalDateTime.now(),
            date = LocalDate.now(),
            reminderTime = LocalDateTime.now(),
            memoTitle = "memo",
            memoContent = "",
            memoUpdatedAt = LocalDateTime.now(),
            priority = 2,
            categoryId = -1L,
            symbol = -1,
            subTasks = persistentListOf(
                SubTask(
                    id = 1L,
                    parentId = 8578,
                    title = "sub 1",
                    isCompleted = true,
                    sortOrder = 0,
                ),
                SubTask(
                    id = 2L,
                    parentId = 8578,
                    title = "sub 2",
                    isCompleted = false,
                    sortOrder = 1,
                ),
            ),
        )
        TaskCard(
            task = task,
            locale = Locale.KOREA,
            onTaskToggleClick = { _, _ -> },
            onTaskEditClick = {},
            onDeleteSymbolClick = {},
            onSymbolClick = { _, _ -> },
            onSubTaskToggleClick = { _, _ -> }
        )
    }
}