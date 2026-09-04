package com.example.design_system.component.input

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.model.ReminderrOption
import com.example.model.ReminderTimeType
import kotlinx.collections.immutable.persistentListOf

@Composable
fun InputTaskReminder(
    modifier: Modifier = Modifier,
    isRemind: Boolean,
    reminderTimeType: ReminderTimeType,
    onReminderChanged: (Boolean) -> Unit,
    onReminderTimeTypeChanged: (ReminderTimeType) -> Unit,
) {
    val options = persistentListOf(
        ReminderrOption(
            title = R.string.on,
            isRemind = true,
            onClick = { onReminderChanged(true) }
        ),
        ReminderrOption(
            title = R.string.off,
            isRemind = false,
            onClick = { onReminderChanged(false) }
        )
    )

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 20.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.reminder),
            style = TodoTheme.typography.bold_16,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(height = 20.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(size = 8.dp)
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEachIndexed { index, option ->
                val shape = when (index) {
                    0 -> RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                    else -> RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                }

                val isSelected = option.isRemind == isRemind

                InputTaskReminderItem(
                    modifier = Modifier.weight(weight = 1f),
                    title = stringResource(id = option.title),
                    textStyle = TodoTheme.typography.medium_16,
                    isSelected = isSelected,
                    shape = shape,
                    onReminderChanged = { onReminderChanged(option.isRemind) }
                )
            }
        }

        Spacer(modifier = Modifier.height(height = 10.dp))

        AnimatedVisibility(
            visible = isRemind,
            enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(size = 8.dp),
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ReminderTimeType.entries.forEachIndexed { index, type ->
                    val shape = when (index) {
                        0 -> RoundedCornerShape(
                            topStart = 8.dp,
                            bottomStart = 8.dp
                        )

                        ReminderTimeType.entries.lastIndex -> RoundedCornerShape(
                            topEnd = 8.dp,
                            bottomEnd = 8.dp
                        )

                        else -> RoundedCornerShape(size = 0.dp)
                    }

                    val titleResId = type.getTitleResId()

                    InputTaskReminderItem(
                        modifier = Modifier.weight(weight = 1f),
                        title = stringResource(id = titleResId),
                        textStyle = TodoTheme.typography.medium_12,
                        isSelected = reminderTimeType == type,
                        shape = shape,
                        onReminderChanged = { onReminderTimeTypeChanged(type) }
                    )
                }
            }
        }
    }
}

@Composable
fun InputTaskReminderItem(
    modifier: Modifier = Modifier,
    title: String,
    textStyle: TextStyle,
    isSelected: Boolean,
    shape: Shape,
    onReminderChanged: () -> Unit
) {
    val bgColor = if (isSelected)
        MaterialTheme.colorScheme.surfaceDim
    else
        MaterialTheme.colorScheme.background

    Box(
        modifier = modifier.fillMaxWidth()
            .background(
                color = bgColor,
                shape = shape,
            )
            .clickable { onReminderChanged() }
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = if (isSelected)
                textStyle.copy(fontWeight = FontWeight.Bold)
            else
                textStyle
        )
    }
}

private fun ReminderTimeType.getTitleResId(): Int =
    when (this) {
        ReminderTimeType.ON_TIME -> R.string.on_time
        ReminderTimeType.TEN_MINUTES_BEFORE -> R.string.ten_minutes_before
        ReminderTimeType.THIRTY_MINUTES_BEFORE -> R.string.thirty_minutes_before
        ReminderTimeType.ONE_HOUR_BEFORE -> R.string.one_hour_before
    }

@Preview(showBackground = true)
@Composable
private fun InputTaskReminderPreview() {
    TodoTheme {
        InputTaskReminder(
            isRemind = true,
            reminderTimeType = ReminderTimeType.ON_TIME,
            onReminderChanged = {},
            onReminderTimeTypeChanged = {}
        )
    }
}