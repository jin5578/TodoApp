package com.example.edit_task.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.design_system.theme.TodoTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@Composable
internal fun EditTaskTimeRow(
    modifier: Modifier = Modifier,
    locale: Locale,
    time: LocalDateTime?,
    reminderTime: LocalDateTime?,
    onTimeClick: () -> Unit,
    onReminderTimeClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        val timeFormat =
            DateTimeFormatter.ofPattern("hh:mm a", locale)
        val timeContent = time?.format(timeFormat)
            ?: stringResource(id = DesignSystemR.string.no)
        Row(
            modifier = modifier.fillMaxWidth()
                .clickable { onTimeClick() }
                .padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(size = 16.dp),
                imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_clock),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                modifier = Modifier.weight(weight = 1f),
                text = stringResource(id = DesignSystemR.string.time_and_reminder),
                style = TodoTheme.typography.medium_14,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Box(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(size = 8.dp)
                ).padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
            ) {
                Text(
                    text = timeContent,
                    style = TodoTheme.typography.medium_14,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        if (time != null) {
            val reminderContent = reminderTime?.format(timeFormat)
                ?: stringResource(id = DesignSystemR.string.no)
            Row(
                modifier = modifier.fillMaxWidth()
                    .clickable { onReminderTimeClick() }
                    .padding(start = 40.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(weight = 1f),
                    text = stringResource(id = DesignSystemR.string.reminder_at),
                    style = TodoTheme.typography.medium_14,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(size = 8.dp)
                    ).padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                ) {
                    Text(
                        text = reminderContent,
                        style = TodoTheme.typography.medium_14,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

            Spacer(modifier = Modifier.height(height = 16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTaskTimeRowPreview() {
    TodoTheme {
        EditTaskTimeRow(
            locale = Locale.KOREA,
            time = LocalDateTime.now(),
            reminderTime = LocalDateTime.now(),
            onTimeClick = {},
            onReminderTimeClick = {}
        )
    }
}