package com.example.design_system.component.dialog.time_picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClockTimePickerDialog(
    initTime: LocalTime = LocalTime.now(),
    onClose: (LocalTime) -> Unit,
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initTime.hour,
        initialMinute = initTime.minute,
        is24Hour = false,
    )

    Dialog(
        onDismissRequest = { onClose(initTime) }
    ) {
        Card(
            modifier = Modifier.wrapContentWidth(),
            shape = RoundedCornerShape(size = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            )
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 20.dp
                ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(
                    state = timePickerState
                )
                Text(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                        .align(alignment = Alignment.End)
                        .clickable {
                            val selectedTime = LocalTime.of(
                                timePickerState.hour,
                                timePickerState.minute
                            )
                            onClose(selectedTime)
                        },
                    text = stringResource(id = R.string.done),
                    style = TodoTheme.typography.bold_14,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ClockTimePickerDialogPreview() {
    TodoTheme {
        ClockTimePickerDialog(
            initTime = LocalTime.now(),
            onClose = {}
        )
    }
}