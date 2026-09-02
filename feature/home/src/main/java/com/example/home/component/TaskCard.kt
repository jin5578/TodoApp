package com.example.home.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.home.model.TaskSymbolType
import com.example.model.ReminderTimeType
import com.example.model.Task
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@Composable
internal fun TaskCard(
    modifier: Modifier = Modifier,
    task: Task,
    locale: Locale,
    onTaskToggleClick: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskEditClick: (Long) -> Unit,
    onDeleteSymbolClick: (taskId: Long) -> Unit,
    onSymbolClick: (taskId: Long, symbolId: Int) -> Unit,
) {
    var isShowFlagMenu by remember { mutableStateOf(value = false) }

    Box(
        modifier = modifier.fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(size = 8.dp)
                ).clickable {
                    onTaskEditClick(task.id)
                }.padding(all = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            IconButton(
                modifier = Modifier.size(size = 20.dp),
                onClick = {
                    onTaskToggleClick(task.id, !task.isCompleted)
                }
            ) {
                if (task.isCompleted) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = DesignSystemR.drawable.svg_check_circle),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                } else {
                    Box(
                        modifier = Modifier.size(size = 20.dp)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
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
                    style = TodoTheme.typography.bold_14,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )

                if (task.memo.isNotEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth().basicMarquee(),
                        text = task.memo,
                        style = TodoTheme.typography.medium_12,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val dateFormat =
                        DateTimeFormatter.ofPattern("MM-dd", locale)
                    Text(
                        text = task.date.format(dateFormat),
                        style = TodoTheme.typography.medium_10,
                        color = MaterialTheme.colorScheme.primary,
                    )

                    if (task.isRemind) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(space = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val timeFormat =
                                DateTimeFormatter.ofPattern("hh:mm a", locale)
                            Text(
                                text = /*(task.time ?: LocalTime.now()).format(
                                    timeFormat
                                )*/"",
                                style = TodoTheme.typography.regular_10,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Icon(
                                modifier = Modifier.size(size = 10.dp),
                                imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_clock),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            Box {
                IconButton(
                    modifier = Modifier.size(size = 24.dp),
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
                        val iconResId = selectedSymbol?.symbolIcon?.iconResId
                            ?: DesignSystemR.drawable.svg_flag_stroke
                        val iconTint = selectedSymbol?.symbolIcon?.iconColor
                            ?: MaterialTheme.colorScheme.onPrimaryContainer

                        Icon(
                            modifier = Modifier.size(20.dp),
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
                    containerColor = MaterialTheme.colorScheme.surface,
                    expanded = isShowFlagMenu,
                    onDismissRequest = { isShowFlagMenu = false }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = DesignSystemR.string.marked_with_a_symbol),
                            style = TodoTheme.typography.medium_12,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            modifier = Modifier.clickable {
                                onDeleteSymbolClick(
                                    task.id
                                )
                            },
                            text = stringResource(id = DesignSystemR.string.delete),
                            style = TodoTheme.typography.medium_12,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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
            text = stringResource(id = taskSymbolType.title),
            style = TodoTheme.typography.medium_10,
            color = MaterialTheme.colorScheme.onSurfaceVariant
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
        val task = Task(
            id = 8578,
            uuid = "corrumpit",
            title = "inceptos",
            isCompleted = false,
            isRemind = true,
            time = LocalDateTime.now(),
            date = LocalDate.now(),
            reminderTime = /*ReminderTimeType.ON_TIME.ordinal*/LocalDateTime.now(),
            memo = "memo",
            priority = 2,
            categoryId = -1L,
        )
        TaskCard(
            task = task,
            locale = Locale.KOREA,
            onTaskToggleClick = { _, _ -> },
            onTaskEditClick = {},
            onDeleteSymbolClick = {},
            onSymbolClick = { _, _ -> }
        )
    }
}