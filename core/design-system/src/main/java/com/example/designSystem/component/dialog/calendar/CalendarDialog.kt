package com.example.designSystem.component.dialog.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.designSystem.R
import com.example.designSystem.component.DaysOfWeek
import com.example.designSystem.component.MonthDay
import com.example.designSystem.theme.TodoTheme
import com.example.model.DateOption
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

@Composable
fun CalendarDialog(
    taskDate: LocalDate = LocalDate.now(),
    taskTime: LocalDateTime?,
    reminderTime: LocalDateTime?,
    locale: Locale,
    onTimeClick: () -> Unit,
    onReminderClick: () -> Unit,
    onCloseClick: () -> Unit,
    onConfirmClick: (taskDate: LocalDate, taskTime: LocalDateTime?, reminderTime: LocalDateTime?) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    val dateOptions = persistentListOf(
        DateOption(
            resId = R.string.today,
            date = LocalDate.now(),
        ),
        DateOption(
            resId = R.string.tomorrow,
            date = LocalDate.now().plusDays(1),
        ),
        DateOption(
            resId = R.string.three_days_later,
            date = LocalDate.now().plusDays(3),
        ),
        DateOption(
            resId = R.string.this_saturday,
            date = LocalDate.now()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)),
        ),
        DateOption(
            resId = R.string.this_sunday,
            date = LocalDate.now()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)),
        ),
    )

    var selectedTaskDate by remember(key1 = taskDate) {
        mutableStateOf(value = taskDate)
    }
    var selectedTaskTime by remember(key1 = taskTime) {
        mutableStateOf(value = taskTime)
    }
    var selectedReminderTime by remember(key1 = reminderTime) {
        mutableStateOf(
            value = reminderTime,
        )
    }

    val onDateChange: (LocalDate) -> Unit = { date ->
        selectedTaskDate = date
        selectedTaskTime =
            selectedTaskTime?.let { date.atTime(it.toLocalTime()) }
        selectedReminderTime =
            selectedReminderTime?.let { date.atTime(it.toLocalTime()) }
    }

    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val currentMonth = remember { YearMonth.now() }

    val calendarState = rememberCalendarState(
        startMonth = currentMonth,
        endMonth = currentMonth.plusMonths(12),
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,
    )

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
                modifier = Modifier.padding(
                    horizontal = 2.dp,
                    vertical = 16.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.due_date),
                    style = TodoTheme.typography.bold_20,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .verticalScroll(state = verticalScrollState),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        CalendarArrowIcon(
                            iconResId = R.drawable.svg_calendar_arrow_left,
                            onClick = {
                                coroutineScope.launch {
                                    calendarState.animateScrollToMonth(
                                        month = calendarState.firstVisibleMonth.yearMonth.minusMonths(
                                            1,
                                        ),
                                    )
                                }
                            },
                        )

                        Spacer(modifier = Modifier.width(width = 16.dp))

                        val visibleMonth =
                            calendarState.firstVisibleMonth.yearMonth
                        Text(
                            text = remember(visibleMonth, locale) {
                                visibleMonth.format(
                                    DateTimeFormatter.ofPattern(
                                        "MMMM yyyy",
                                        locale,
                                    ),
                                )
                            },
                            style = TodoTheme.typography.bold_16,
                            color = MaterialTheme.colorScheme.onBackground,
                        )

                        Spacer(modifier = Modifier.width(width = 16.dp))

                        CalendarArrowIcon(
                            iconResId = R.drawable.svg_calendar_arrow_right,
                            onClick = {
                                coroutineScope.launch {
                                    calendarState.animateScrollToMonth(
                                        month = calendarState.firstVisibleMonth.yearMonth.plusMonths(
                                            1,
                                        ),
                                    )
                                }
                            },
                        )
                    }

                    Spacer(modifier = Modifier.height(height = 16.dp))

                    HorizontalCalendar(
                        state = calendarState,
                        monthHeader = { month ->
                            val daysOfWeek = month.weekDays.first().map {
                                it.date.dayOfWeek
                            }
                            DaysOfWeek(
                                daysOfWeek = daysOfWeek,
                                locale = locale,
                            )
                        },
                        dayContent = { day ->
                            MonthDay(
                                day = day,
                                isSelected = selectedTaskDate == day.date,
                                isVisibleIndicator = false,
                                onClick = onDateChange,
                            )
                        },
                    )

                    Spacer(modifier = Modifier.height(height = 16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .horizontalScroll(state = horizontalScrollState)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                    ) {
                        dateOptions.forEach { dateOption ->
                            DateOptionItem(
                                dateOption = dateOption,
                                isClicked = dateOption.date == selectedTaskDate,
                                onClick = onDateChange,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(height = 16.dp))

                    val timeFormat =
                        DateTimeFormatter.ofPattern("hh:mm a", locale)
                    val timeContent = selectedTaskTime?.format(timeFormat)
                        ?: stringResource(id = R.string.no)
                    CalendarActionRow(
                        iconResId = R.drawable.svg_clock,
                        iconColor = MaterialTheme.colorScheme.onBackground,
                        titleResId = R.string.time,
                        titleColor = MaterialTheme.colorScheme.onBackground,
                        content = timeContent,
                        enabled = true,
                        onClick = onTimeClick,
                    )

                    val reminderContent =
                        selectedReminderTime?.format(timeFormat)
                            ?: stringResource(id = R.string.no)
                    CalendarActionRow(
                        iconResId = R.drawable.svg_reminder,
                        iconColor = MaterialTheme.colorScheme.onBackground,
                        titleResId = R.string.reminder,
                        titleColor = MaterialTheme.colorScheme.onBackground,
                        content = reminderContent,
                        enabled = selectedTaskTime != null,
                        onClick = onReminderClick,
                    )

                    Spacer(modifier = Modifier.height(height = 16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(end = 16.dp),
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
                                onConfirmClick(
                                    selectedTaskDate,
                                    selectedTaskTime,
                                    selectedReminderTime,
                                )
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
}

@Composable
private fun CalendarArrowIcon(
    modifier: Modifier = Modifier,
    iconResId: Int,
    onClick: () -> Unit,
) {
    Icon(
        modifier = modifier.size(size = 28.dp)
            .clip(shape = CircleShape)
            .clickable { onClick() }
            .padding(all = 4.dp),
        imageVector = ImageVector.vectorResource(id = iconResId),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onBackground,
    )
}

@Composable
private fun DateOptionItem(
    modifier: Modifier = Modifier,
    dateOption: DateOption,
    isClicked: Boolean,
    onClick: (LocalDate) -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(
                color =
                if (isClicked) {
                    MaterialTheme.colorScheme.secondaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceContainer
                },
            ).clickable {
                onClick(dateOption.date)
            }.padding(
                all = 8.dp,
            ),
    ) {
        Text(
            text = stringResource(id = dateOption.resId),
            style = TodoTheme.typography.medium_12,
            color =
            if (isClicked) {
                MaterialTheme.colorScheme.onSecondaryContainer
            } else {
                MaterialTheme.colorScheme.onBackground
            },
        )
    }
}

@Composable
private fun CalendarActionRow(
    modifier: Modifier = Modifier,
    iconResId: Int,
    iconColor: Color,
    titleResId: Int,
    titleColor: Color,
    content: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clickable(enabled = enabled) { onClick() }
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
    ) {
        Icon(
            modifier = Modifier.size(size = 16.dp),
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            tint = iconColor,
        )

        Text(
            modifier = Modifier.weight(weight = 1f),
            text = stringResource(id = titleResId),
            style = TodoTheme.typography.medium_16,
            color = titleColor,
        )

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 8.dp))
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(size = 8.dp),
                )
                .padding(horizontal = 8.dp, vertical = 8.dp),
        ) {
            Text(
                text = content,
                style = TodoTheme.typography.medium_16,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarDialogPreview() {
    TodoTheme {
        CalendarDialog(
            taskDate = LocalDate.now(),
            taskTime = null,
            reminderTime = LocalDateTime.now(),
            locale = Locale.KOREA,
            onTimeClick = {},
            onReminderClick = {},
            onCloseClick = {},
            onConfirmClick = { _, _, _ -> },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarArrowIconPreview() {
    TodoTheme {
        CalendarArrowIcon(
            iconResId = -1,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DateOptionItemPreview() {
    TodoTheme {
        val dateOption = DateOption(
            resId = R.string.today,
            date = LocalDate.now(),
        )
        DateOptionItem(
            dateOption = dateOption,
            isClicked = false,
            onClick = {},

        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarActionRowPreview() {
    TodoTheme {
        CalendarActionRow(
            iconResId = -1,
            iconColor = Color.Black,
            titleResId = -1,
            titleColor = Color.Black,
            content = "",
            enabled = true,
            onClick = {},
        )
    }
}
