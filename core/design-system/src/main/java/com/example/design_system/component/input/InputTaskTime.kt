package com.example.design_system.component.input

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.model.TimeOption
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun InputTaskTime(
    modifier: Modifier = Modifier,
    time: LocalTime,
    locale: Locale,
    onTimeChange: (LocalTime) -> Unit,
    onShowTimePickerDialog: () -> Unit,
) {
    val options = persistentListOf(
        TimeOption(
            resId = R.string.now,
            time = LocalTime.now(),
            onClick = { onTimeChange(it) }
        ),
        TimeOption(
            resId = R.string.in_1_hour,
            time = LocalTime.now().plusHours(1),
            onClick = { onTimeChange(it) }
        ),
        TimeOption(
            resId = R.string.in_2_hours,
            time = LocalTime.now().plusHours(2),
            onClick = { onTimeChange(it) }
        )
    )

    val timeFormat = DateTimeFormatter.ofPattern("hh : mm a", locale)

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(space = 20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.time),
                style = TodoTheme.typography.bold_18,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                modifier = Modifier.clickable { onShowTimePickerDialog() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            ) {
                Icon(
                    modifier = Modifier.size(size = 14.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.svg_clock),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = time.format(timeFormat),
                    style = TodoTheme.typography.medium_16,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

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
                    0 -> RoundedCornerShape(
                        topStart = 8.dp,
                        bottomStart = 8.dp
                    )

                    options.size - 1 -> RoundedCornerShape(
                        topEnd = 8.dp,
                        bottomEnd = 8.dp
                    )

                    else -> RectangleShape
                }

                val isSelected =
                    time.format(timeFormat) == option.time.format(timeFormat)

                InputTaskTimeItem(
                    modifier = Modifier.weight(weight = 1f),
                    resId = option.resId,
                    isSelected = isSelected,
                    shape = shape,
                    onTimeChange = { onTimeChange(option.time) }
                )
            }
        }
    }
}

@Composable
private fun InputTaskTimeItem(
    modifier: Modifier = Modifier,
    @StringRes resId: Int,
    isSelected: Boolean,
    shape: Shape,
    onTimeChange: () -> Unit
) {
    val bgColor = if (isSelected)
        MaterialTheme.colorScheme.surfaceDim
    else
        MaterialTheme.colorScheme.background

    Box(
        modifier = modifier.fillMaxWidth()
            .background(
                color = bgColor,
                shape = shape
            )
            .clickable { onTimeChange() }
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = resId),
            color = MaterialTheme.colorScheme.onBackground,
            style = if (isSelected)
                TodoTheme.typography.bold_14
            else
                TodoTheme.typography.medium_14,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputTaskTimePreview() {
    TodoTheme {
        InputTaskTime(
            time = LocalTime.now(),
            locale = Locale.KOREA,
            onTimeChange = {},
            onShowTimePickerDialog = {}
        )
    }
}