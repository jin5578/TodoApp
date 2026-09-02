package com.example.design_system.component.dialog.date_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.LocalDate

@Composable
fun MonthDay(
    modifier: Modifier = Modifier,
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (LocalDate) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primaryContainer
        else Color.Transparent
    val borderWidth = if (isSelected) 1.dp else (-1).dp
    val textColor =
        if (day.date == LocalDate.now()) MaterialTheme.colorScheme.error
        else if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
        else MaterialTheme.colorScheme.onSurface
    val fontStyle =
        if (isSelected) TodoTheme.typography.bold_14
        else TodoTheme.typography.medium_14

    val message = stringResource(R.string.cannot_be_selected_of_day)

    Box(
        modifier = modifier.aspectRatio(ratio = 1f)
            .padding(all = 6.dp)
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(backgroundColor)
            .border(
                width = borderWidth,
                color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .clickable {
                if (day.date < LocalDate.now()) {
                    onShowMessageSnackbar(message)
                } else {
                    onClick(day.date)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = fontStyle,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MonthDayPreview() {
    TodoTheme {
        MonthDay(
            day = CalendarDay(
                date = LocalDate.now(),
                position = DayPosition.MonthDate
            ),
            isSelected = false,
            onClick = {},
            onShowMessageSnackbar = {}
        )
    }
}