package com.example.design_system.component.dialog.time_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
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
import com.example.design_system.theme.TodoTheme
import com.example.model.TimeOption
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    taskDate: LocalDate,
    taskTime: LocalDateTime?,
    onCloseClick: () -> Unit,
    onConfirmClick: (LocalDateTime?) -> Unit,
) {
    val timeOptions = persistentListOf(
        TimeOption(
            resId = DesignSystemR.string.no_time,
            time = null,
        ),
        TimeOption(
            resId = DesignSystemR.string.time_00,
            time = taskDate.atTime(0, 0),
        ),
        TimeOption(
            resId = DesignSystemR.string.time_02,
            time = taskDate.atTime(2, 0),
        ),
        TimeOption(
            resId = DesignSystemR.string.time_04,
            time = taskDate.atTime(4, 0),
        ),
        TimeOption(
            resId = DesignSystemR.string.time_06,
            time = taskDate.atTime(6, 0),
        ),
        TimeOption(
            resId = DesignSystemR.string.time_08,
            time = taskDate.atTime(8, 0),
        ),
        TimeOption(
            resId = DesignSystemR.string.time_10,
            time = taskDate.atTime(10, 0),
        ),
        TimeOption(
            resId = DesignSystemR.string.time_12,
            time = taskDate.atTime(12, 0),
        ),
        TimeOption(
            resId = DesignSystemR.string.time_14,
            time = taskDate.atTime(14, 0),
        ),
        TimeOption(
            resId = DesignSystemR.string.time_16,
            time = taskDate.atTime(16, 0),
        ),
        TimeOption(
            resId = DesignSystemR.string.time_18,
            time = taskDate.atTime(18, 0),
        ),
        TimeOption(
            resId = DesignSystemR.string.time_20,
            time = taskDate.atTime(20, 0),
        ),
        TimeOption(
            resId = DesignSystemR.string.time_22,
            time = taskDate.atTime(22, 0),
        ),
    )

    var selectedTaskTime: LocalDateTime? by remember {
        mutableStateOf(value = taskTime)
    }

    val timePickerState = rememberTimePickerState(
        initialHour = taskTime?.hour ?: LocalDateTime.now().hour,
        initialMinute = taskTime?.minute ?: LocalDateTime.now().minute,
        is24Hour = false,
    )

    Dialog(
        onDismissRequest = { onCloseClick() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(size = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
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
                    text = stringResource(id = DesignSystemR.string.set_time),
                    style = TodoTheme.typography.bold_20,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start
                )

                key(timePickerState.hour, timePickerState.minute) {
                    TimePicker(
                        state = timePickerState
                    )
                }
                FlowRow(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp)
                ) {
                    timeOptions.forEach { timeOption ->
                        TimeOptionItem(
                            timeOption = timeOption,
                            isClicked = timeOption.time == selectedTaskTime,
                            onClick = { dateTime ->
                                selectedTaskTime = dateTime
                                if (dateTime != null) {
                                    timePickerState.hour = dateTime.hour
                                    timePickerState.minute = dateTime.minute
                                }
                            }
                        )
                    }
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
                            if (selectedTaskTime == null) {
                                onConfirmClick(null)
                            } else {
                                val localTime = LocalTime.of(
                                    timePickerState.hour,
                                    timePickerState.minute
                                )
                                val localDateTime = LocalDateTime.of(
                                    taskDate, localTime
                                )
                                onConfirmClick(localDateTime)
                            }
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
private fun TimeOptionItem(
    modifier: Modifier = Modifier,
    timeOption: TimeOption,
    isClicked: Boolean,
    onClick: (LocalDateTime?) -> Unit,
) {
    Box(
        modifier = modifier.background(
            color = if (isClicked) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer,
            shape = RoundedCornerShape(size = 8.dp)
        ).clickable {
            onClick(timeOption.time)
        }.padding(
            all = 8.dp
        )
    ) {
        Text(
            text = stringResource(id = timeOption.resId),
            style = TodoTheme.typography.medium_12,
            color = if (isClicked) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimePickerDialogPreview() {
    TodoTheme {
        TimePickerDialog(
            taskDate = LocalDate.now(),
            taskTime = LocalDateTime.now(),
            onCloseClick = {},
            onConfirmClick = {}
        )
    }
}