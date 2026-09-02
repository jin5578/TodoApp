package com.example.design_system.component.dialog.reminder

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.design_system.theme.TodoTheme
import com.example.model.ReminderOption
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDateTime
import com.example.design_system.R as DesignSystemR

@Composable
fun ReminderDialog(
    taskTime: LocalDateTime,
    reminderTime: LocalDateTime?,
    onCloseClick: () -> Unit,
    onConfirmClick: (LocalDateTime?) -> Unit,
) {
    val reminderOptions = persistentListOf(
        ReminderOption(
            resId = DesignSystemR.string.reminder_off,
            time = null,
        ),
        ReminderOption(
            resId = DesignSystemR.string.reminder_same_as_due_date,
            time = taskTime,
        ),
        ReminderOption(
            resId = DesignSystemR.string.reminder_5_minutes_before,
            time = taskTime.minusMinutes(5)
        ),
        ReminderOption(
            resId = DesignSystemR.string.reminder_15_minutes_before,
            time = taskTime.minusMinutes(15)
        ),
        ReminderOption(
            resId = DesignSystemR.string.reminder_30_minutes_before,
            time = taskTime.minusMinutes(30)
        ),
        ReminderOption(
            resId = DesignSystemR.string.reminder_1_day_before,
            time = taskTime.minusDays(1)
        ),
    )

    var selectedReminderTime: LocalDateTime? by remember {
        mutableStateOf(value = reminderTime)
    }

    Dialog(
        onDismissRequest = { onCloseClick() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(size = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(
                    horizontal = 2.dp,
                    vertical = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = stringResource(id = DesignSystemR.string.reminder),
                    style = TodoTheme.typography.bold_20,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Start
                )

                reminderOptions.forEach { reminderOption ->
                    ReminderOptionItem(
                        reminderOption = reminderOption,
                        isClicked = reminderOption.time == selectedReminderTime,
                        onClick = { dateTime ->
                            selectedReminderTime = dateTime
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            onCloseClick()
                        }.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                        text = stringResource(id = DesignSystemR.string.cancel),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.inversePrimary
                    )

                    Text(
                        modifier = Modifier.clickable {
                            onConfirmClick(selectedReminderTime)
                        }.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                        text = stringResource(id = DesignSystemR.string.confirm),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun ReminderOptionItem(
    modifier: Modifier = Modifier,
    reminderOption: ReminderOption,
    isClicked: Boolean,
    onClick: (LocalDateTime?) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth().clickable {
            onClick(reminderOption.time)
        }.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        Box(
            modifier = Modifier.size(size = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isClicked) {
                Icon(
                    modifier = Modifier.size(size = 21.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_check_circle),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            } else {
                Box(
                    modifier = Modifier.size(size = 20.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = CircleShape
                        )
                )
            }
        }

        Text(
            text = stringResource(id = reminderOption.resId),
            style = TodoTheme.typography.medium_14,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReminderDialogPreview() {
    TodoTheme {
        ReminderDialog(
            taskTime = LocalDateTime.now(),
            reminderTime = LocalDateTime.now(),
            onCloseClick = {},
            onConfirmClick = {}
        )
    }
}