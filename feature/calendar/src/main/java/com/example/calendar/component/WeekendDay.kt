package com.example.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.Green
import com.example.design_system.theme.Red
import com.example.design_system.theme.TodoTheme
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.WeekDayPosition
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
internal fun WeekendDay(
    modifier: Modifier = Modifier,
    day: WeekDay,
    locale: Locale,
    isSelected: Boolean,
    isVisibleIndicator: Boolean,
    onClick: (LocalDate) -> Unit,
) {
    val density = LocalDensity.current
    val screenWidth =
        with(density) { LocalWindowInfo.current.containerSize.width.toDp() }

    val dateFormat = DateTimeFormatter.ofPattern("dd")

    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        if (day.date == LocalDate.now()) {
            Green
        } else {
            MaterialTheme.colorScheme.onBackground
        }
    }

    Box(
        modifier = modifier.width(screenWidth / 7)
            .padding(all = 4.dp)
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                },
            )
            .clickable { onClick(day.date) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(bottom = 10.dp, top = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = dateFormat.format(day.date),
                style = TodoTheme.typography.bold_12,
                color = textColor,
            )

            Text(
                text = day.date.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    locale,
                ),
                style = TodoTheme.typography.bold_12,
                color = textColor,
            )

            if (isVisibleIndicator) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier.size(4.dp)
                        .background(
                            color = Red,
                            shape = CircleShape,
                        ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeekendDayPreview() {
    TodoTheme {
        WeekendDay(
            day = WeekDay(LocalDate.now(), WeekDayPosition.InDate),
            locale = Locale.KOREA,
            isSelected = true,
            isVisibleIndicator = true,
            onClick = {},
        )
    }
}
