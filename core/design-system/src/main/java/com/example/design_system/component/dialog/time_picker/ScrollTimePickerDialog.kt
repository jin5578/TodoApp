package com.example.design_system.component.dialog.time_picker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun ScrollTimePickerDialog(
    taskDate: LocalDate,
    taskTime: LocalDateTime?,
    onCloseClick: () -> Unit,
    onConfirmClick: (LocalDateTime?) -> Unit,
) {
    val timeOptions = rememberTimeOptions(taskDate = taskDate)

    var selectedTaskTime: LocalDateTime? by remember {
        mutableStateOf(value = taskTime)
    }

    var pickerSyncTime: LocalDateTime by remember {
        mutableStateOf(value = taskTime ?: LocalDateTime.now())
    }

    Dialog(
        onDismissRequest = { onCloseClick() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(size = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(
                    horizontal = 2.dp,
                    vertical = 16.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.set_time),
                    style = TodoTheme.typography.bold_20,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                )

                key(pickerSyncTime) {
                    ScrollTimePicker(
                        initTime = pickerSyncTime.toLocalTime(),
                        onSelect = { time ->
                            selectedTaskTime = LocalDateTime.of(taskDate, time)
                        },
                    )
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                ) {
                    timeOptions.forEach { timeOption ->
                        TimeOptionItem(
                            timeOption = timeOption,
                            isClicked = timeOption.time == selectedTaskTime,
                            onClick = { dateTime ->
                                selectedTaskTime = dateTime
                                if (dateTime != null) {
                                    pickerSyncTime = dateTime
                                }
                            },
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            onCloseClick()
                        }.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp,
                        ),
                        text = stringResource(id = R.string.cancel),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.inversePrimary,
                    )

                    Text(
                        modifier = Modifier.clickable {
                            onConfirmClick(selectedTaskTime)
                        }.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp,
                        ),
                        text = stringResource(id = R.string.confirm),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Composable
private fun ScrollTimePicker(
    initTime: LocalTime,
    onSelect: (LocalTime) -> Unit,
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(animationSpec = tween(durationMillis = 0)),
    ) {
        WheelTimePicker(
            timeFormat = TimeFormat.AM_PM,
            startTime = initTime,
            textColor = MaterialTheme.colorScheme.onBackground,
            textStyle = TodoTheme.typography.bold_16,
            onSnappedTime = onSelect,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScrollTimePickerDialogPreview() {
    TodoTheme {
        ScrollTimePickerDialog(
            taskDate = LocalDate.now(),
            taskTime = LocalDateTime.now(),
            onCloseClick = {},
            onConfirmClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScrollTimePickerPreview() {
    TodoTheme {
        ScrollTimePicker(
            initTime = LocalTime.now(),
            onSelect = {},
        )
    }
}
