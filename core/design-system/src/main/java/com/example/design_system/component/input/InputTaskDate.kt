package com.example.design_system.component.input

import android.text.format.DateFormat
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.model.DateOption
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun InputTaskDate(
    modifier: Modifier = Modifier,
    date: LocalDate,
    locale: java.util.Locale,
    onDateChange: (LocalDate) -> Unit,
    onShowDatePickerDialog: () -> Unit,
) {
    val options = persistentListOf(
        DateOption(
            resId = R.string.today,
            date = LocalDate.now(),
            onClick = { onDateChange(it) }
        ),
        DateOption(
            resId = R.string.tomorrow,
            date = LocalDate.now().plusDays(1),
            onClick = { onDateChange(it) }
        ),
        DateOption(
            resId = R.string.next_week,
            date = LocalDate.now().plusWeeks(1),
            onClick = { onDateChange(it) }
        )
    )

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
                text = stringResource(id = R.string.date),
                style = TodoTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Row(
                modifier = Modifier.clickable { onShowDatePickerDialog() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            ) {
                Icon(
                    modifier = Modifier.size(size = 14.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.svg_calendar),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )

                val dateFormat = DateTimeFormatter.ofPattern(
                    DateFormat.getBestDateTimePattern(locale, "MMMMd"),
                    locale
                )
                Text(
                    text = date.format(dateFormat),
                    style = TodoTheme.typography.infoDescTextStyle,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    shape = RoundedCornerShape(size = 8.dp)
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            options.forEachIndexed { index, option ->
                val shape = when (index) {
                    0 ->
                        RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)

                    options.size - 1 ->
                        RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)

                    else ->
                        RectangleShape
                }
                InputTaskDateItem(
                    modifier = Modifier.weight(weight = 1f),
                    resId = option.resId,
                    isSelected = option.date == date,
                    shape = shape,
                    onDateChange = { onDateChange(option.date) }
                )
            }
        }
    }
}

@Composable
private fun InputTaskDateItem(
    modifier: Modifier = Modifier,
    @StringRes resId: Int,
    isSelected: Boolean,
    shape: Shape,
    onDateChange: () -> Unit
) {
    val bgColor = if (isSelected)
        MaterialTheme.colorScheme.secondaryContainer
    else
        MaterialTheme.colorScheme.surface

    Box(
        modifier = modifier.fillMaxWidth()
            .background(
                color = bgColor,
                shape = shape
            )
            .clickable { onDateChange() }
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = resId),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = if (isSelected)
                TodoTheme.typography.infoDescTextStyle.copy(fontWeight = FontWeight.Bold)
            else
                TodoTheme.typography.infoDescTextStyle
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputTaskDatePreview() {
    TodoTheme {
        InputTaskDate(
            date = LocalDate.now(),
            locale = Locale.KOREA,
            onDateChange = {},
            onShowDatePickerDialog = {}
        )
    }
}