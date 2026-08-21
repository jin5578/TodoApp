package com.example.design_system.component.dialog.time_picker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.example.design_system.theme.TodoTheme
import java.time.LocalTime

@Composable
internal fun ScrollTimePicker(
    initTime: LocalTime,
    onSelect: (LocalTime) -> Unit,
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(animationSpec = tween(durationMillis = 0))
    ) {
        WheelTimePicker(
            timeFormat = TimeFormat.AM_PM,
            startTime = initTime,
            textColor = MaterialTheme.colorScheme.onSecondaryContainer,
            textStyle = TodoTheme.typography.medium_16,
            onSnappedTime = onSelect
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScrollTimePickerPreview() {
    TodoTheme {
        ScrollTimePicker(
            initTime = LocalTime.now(),
            onSelect = {}
        )
    }
}