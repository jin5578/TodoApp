package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.design_system.theme.priorityColors
import com.example.model.Category
import com.example.model.PriorityType
import com.example.model.ReminderTimeType
import com.example.model.Task
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val OFF = "OFF"

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: Task,
    category: Category? = null,
    locale: Locale,
    isAvailableSwipe: Boolean,
    onTaskEdit: ((Long) -> Unit)? = null,
    onTaskToggleCompletion: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskDelete: (id: Long) -> Unit,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier.fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(size = 8.dp)
            ).clickable(enabled = !isAvailableSwipe) {
                onTaskEdit?.invoke(task.id)
            }.padding(
                horizontal = 8.dp,
                vertical = 10.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            IconButton(
                modifier = Modifier.size(size = 32.dp).weight(weight = 0.1f),
                onClick = {
                    onTaskToggleCompletion(task.id, !task.isCompleted)
                }
            ) {
                if (task.isCompleted) {
                    Icon(
                        modifier = Modifier.size(21.dp),
                        painter = painterResource(id = R.drawable.svg_check_circle),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Box(
                        modifier = Modifier.size(size = 20.dp)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center,
                        content = {}
                    )
                }
            }

            Column(
                modifier = Modifier.weight(weight = 0.8f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth().basicMarquee(),
                    text = task.title,
                    style = TodoTheme.typography.bold_14,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(height = 8.dp))

                if (task.memo.isNotEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(end = 10.dp)
                            .basicMarquee(),
                        text = task.memo,
                        style = TodoTheme.typography.medium_14,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(height = 8.dp))
                }

                Row(
                    modifier = Modifier.horizontalScroll(state = scrollState),
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ExtraInfo(
                        painter = painterResource(id = R.drawable.svg_flag),
                        title = PriorityType.entries[task.priority].title,
                        textColor = priorityColors[task.priority],
                        tintColor = priorityColors[task.priority],
                    )

                    if (category != null) {
                        CategoryInfo(
                            color = androidx.compose.ui.graphics.Color(category.colorValue),
                            title = category.title
                        )
                    }

                    val timeFormat =
                        DateTimeFormatter.ofPattern("hh:mm a", locale)
                    ExtraInfo(
                        painter = painterResource(id = R.drawable.svg_clock),
                        title = task.time.format(timeFormat),
                        textColor = MaterialTheme.colorScheme.onSurface,
                        tintColor = MaterialTheme.colorScheme.onSurface,
                    )

                    ExtraInfo(
                        painter = painterResource(id = R.drawable.svg_calendar),
                        title = task.date.toString(),
                        textColor = MaterialTheme.colorScheme.onSurface,
                        tintColor = MaterialTheme.colorScheme.onSurface,
                    )

                    val notificationIcon: Int
                    val notificationText: String
                    if (task.isRemind) {
                        notificationIcon =
                            R.drawable.svg_notification
                        notificationText =
                            ReminderTimeType.entries[task.reminderTime].subtitle
                    } else {
                        notificationIcon =
                            R.drawable.svg_notification_slash
                        notificationText = OFF
                    }
                    ExtraInfo(
                        painter = painterResource(id = notificationIcon),
                        title = notificationText,
                        textColor = MaterialTheme.colorScheme.onSurface,
                        tintColor = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

            if (!isAvailableSwipe) {
                IconButton(
                    modifier = Modifier.weight(weight = 0.1f),
                    onClick = { onTaskDelete(task.id) }
                ) {
                    Icon(
                        modifier = Modifier.size(size = 18.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.svg_trash),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun ExtraInfo(
    painter: Painter,
    title: String,
    textColor: Color,
    tintColor: Color
) {
    Row(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.surfaceDim,
            shape = RoundedCornerShape(size = 8.dp),
        ).padding(all = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(size = 14.dp),
            painter = painter,
            contentDescription = null,
            tint = tintColor
        )

        Text(
            text = title,
            style = TodoTheme.typography.regular_12,
            color = textColor,
        )
    }
}

@Composable
private fun CategoryInfo(
    color: Color,
    title: String
) {
    Row(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.surfaceDim,
            shape = RoundedCornerShape(size = 8.dp),
        ).padding(all = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(size = 14.dp)
                .background(
                    color = color,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center,
            content = {}
        )

        Text(
            text = title,
            style = TodoTheme.typography.regular_12,
            color = MaterialTheme.colorScheme.onSurface
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
            time = LocalTime.now(),
            date = LocalDate.now(),
            memo = "memo",
            priority = 2,
            categoryId = -1L,
            reminderTime = ReminderTimeType.ON_TIME.ordinal,
        )
        TaskCard(
            task = task,
            locale = Locale.KOREA,
            isAvailableSwipe = false,
            onTaskEdit = {},
            onTaskToggleCompletion = { _, _ -> },
            onTaskDelete = { _ -> }
        )
    }
}