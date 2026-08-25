package com.example.calendar

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calendar.component.WeekendDay
import com.example.design_system.component.EmptyContent
import com.example.design_system.component.TaskCard
import com.example.design_system.theme.TodoTheme
import com.example.model.Category
import com.example.model.Task
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarScreen(
    modifier: Modifier = Modifier,
    tasks: ImmutableList<Task>,
    categories: ImmutableList<Category>,
    locale: Locale,
    navigateAddTask: (LocalDate) -> Unit,
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
    onTaskToggleCompletion: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val currentMonth = remember { YearMonth.now() }
    var currentMonthTitle by remember { mutableStateOf(value = currentMonth.month) }
    val currentDate = remember { LocalDate.now() }
    var selectedDay by remember { mutableStateOf(value = currentDate) }

    val weekCalendarState = rememberWeekCalendarState(
        startDate = currentDate.minusDays(100),
        endDate = currentDate.plusDays(100),
        firstDayOfWeek = firstDayOfWeekFromLocale()
    )

    val dateFormat = DateTimeFormatter.ofPattern("dd")

    currentMonthTitle = weekCalendarState.firstVisibleWeek.days[0].date.month

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = currentMonthTitle.getDisplayName(
                            TextStyle.FULL,
                            locale
                        ),
                        style = TodoTheme.typography.bold_20,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(size = 24.dp),
                            imageVector = ImageVector.vectorResource(
                                id = DesignSystemR.drawable.svg_arrow_left
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                },
                actions = {
                    Row {
                        Box(
                            modifier = Modifier.clickable {
                                selectedDay = currentDate
                                coroutineScope.launch {
                                    weekCalendarState.animateScrollToWeek(
                                        date = currentDate
                                    )
                                }
                            }
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = 6.dp,
                                    vertical = 4.dp
                                ),
                                text = currentDate.format(dateFormat),
                                style = TodoTheme.typography.bold_16
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(width = 10.dp))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = { navigateAddTask(selectedDay) },
            ) {
                Icon(
                    modifier = Modifier.size(size = 32.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_plus_small),
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            WeekCalendar(
                modifier = Modifier.padding(horizontal = 10.dp),
                state = weekCalendarState,
                dayContent = { day ->
                    WeekendDay(
                        day = day,
                        locale = locale,
                        isSelected = selectedDay == day.date,
                        isVisibleIndicator = tasks.any { it.date == day.date },
                        onClick = { selectedDay = it }
                    )
                }
            )
            val selectedDayTasks =
                tasks.filter { it.date == selectedDay }.toPersistentList()
            if (selectedDayTasks.isEmpty()) {
                EmptyContent(title = stringResource(id = DesignSystemR.string.no_tasks))
            } else {
                Spacer(modifier = Modifier.height(height = 10.dp))
                LazyColumn(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 0.dp,
                    )
                ) {
                    itemsIndexed(
                        items = selectedDayTasks,
                        key = { _, task ->
                            task.id
                        }
                    ) { _, task ->
                        TaskCard(
                            task = task,
                            category = categories.filter { it.id == task.categoryId }
                                .getOrNull(index = 0),
                            locale = locale,
                            isAvailableSwipe = true,
                            onTaskEdit = navigateEditTask,
                            onTaskToggleCompletion = onTaskToggleCompletion,
                            onTaskDelete = onTaskDelete
                        )
                        Spacer(modifier = Modifier.height(height = 10.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarScreenPreview() {
    TodoTheme {
        CalendarScreen(
            tasks = persistentListOf(),
            categories = persistentListOf(),
            locale = Locale.KOREA,
            navigateAddTask = {},
            navigateEditTask = {},
            popBackStack = {},
            onTaskToggleCompletion = { _, _ -> },
            onTaskDelete = { _ -> }
        )
    }
}