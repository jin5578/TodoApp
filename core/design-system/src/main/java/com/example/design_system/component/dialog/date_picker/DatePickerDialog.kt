package com.example.design_system.component.dialog.date_picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DatePickerDialog(
    initDay: LocalDate = LocalDate.now(),
    locale: Locale,
    onClose: (LocalDate) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    var selectedDay by remember { mutableStateOf(value = initDay) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val currentMonth = remember { YearMonth.now() }

    val calendarState = rememberCalendarState(
        startMonth = currentMonth,
        endMonth = currentMonth.plusMonths(12),
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    val dateFormat = DateTimeFormatter.ofPattern("d MMMM, yyyy", locale)

    Dialog(
        onDismissRequest = { onClose(initDay) }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            shape = RoundedCornerShape(size = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(all = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.calendar),
                        style = TodoTheme.typography.bold_20,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                    Text(
                        text = selectedDay.format(dateFormat),
                        style = TodoTheme.typography.bold_16,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                Column(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 16.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp)
                ) {
                    val title =
                        calendarState.lastVisibleMonth.yearMonth.month.getDisplayName(
                            TextStyle.FULL,
                            locale
                        )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Icon(
                            modifier = Modifier.size(size = 32.dp)
                                .clip(shape = CircleShape)
                                .clickable {
                                    scope.launch {
                                        calendarState.animateScrollToMonth(
                                            month = calendarState.firstVisibleMonth.yearMonth.minusMonths(
                                                1
                                            )
                                        )
                                    }
                                }
                                .padding(all = 8.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.svg_calendar_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )

                        Icon(
                            modifier = Modifier.size(size = 32.dp)
                                .clip(shape = CircleShape)
                                .clickable {
                                    scope.launch {
                                        calendarState.animateScrollToMonth(
                                            month = calendarState.firstVisibleMonth.yearMonth.plusMonths(
                                                1
                                            )
                                        )
                                    }
                                }.padding(all = 8.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.svg_calendar_arrow_right),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    HorizontalCalendar(
                        state = calendarState,
                        dayContent = { day ->
                            MonthDay(
                                day = day,
                                isSelected = selectedDay == day.date,
                                onClick = { selectedDay = it },
                                onShowMessageSnackbar = onShowMessageSnackbar,
                            )
                        },
                        monthHeader = { month ->
                            val daysOfWeek = month.weekDays.first().map {
                                it.date.dayOfWeek
                            }
                            DaysOfWeek(daysOfWeek = daysOfWeek, locale = locale)
                        }
                    )

                    Text(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                            .align(alignment = Alignment.End)
                            .clickable {
                                onClose(selectedDay)
                            },
                        text = stringResource(id = R.string.done),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DatePickerDialogPreview() {
    TodoTheme {
        DatePickerDialog(
            initDay = LocalDate.now(),
            locale = Locale.KOREA,
            onClose = {},
            onShowMessageSnackbar = {}
        )
    }
}