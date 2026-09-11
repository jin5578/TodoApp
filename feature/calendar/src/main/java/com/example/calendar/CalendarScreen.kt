package com.example.calendar

import android.text.format.DateFormat
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.AddTaskBottomSheetContent
import com.example.design_system.component.BasicDropdownMenuItem
import com.example.design_system.component.CustomFloatingActionButton
import com.example.design_system.component.DaysOfWeek
import com.example.design_system.component.EmptyContent
import com.example.design_system.component.MonthDay
import com.example.design_system.component.TaskCard
import com.example.design_system.component.dialog.category.CategoryDialog
import com.example.design_system.theme.TodoTheme
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.model.Category
import com.example.model.CategoryColorType
import com.example.model.Task
import com.example.model.TaskUiModel
import com.example.model.TimePickerType
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarScreen(
    modifier: Modifier = Modifier,
    calendarTasks: ImmutableList<TaskUiModel>,
    tasks: ImmutableList<TaskUiModel>,
    categories: ImmutableList<Category>,
    locale: Locale,
    timePickerType: TimePickerType,
    onFetchCalendarTasks: (categoryId: Long, date: LocalDate) -> Unit,
    onFetchTasks: (LocalDate) -> Unit,
    onAddTaskClick: (Task) -> Unit,
    onAddCategoryClick: (categoryTitle: String, categoryColorType: CategoryColorType) -> Unit,
    onTaskToggleCompletion: (id: Long, isCompleted: Boolean) -> Unit,
    onDeleteSymbolClick: (Long) -> Unit,
    onSymbolClick: (taskId: Long, symbolId: Int) -> Unit,
    onSubTaskToggleClick: (subTaskId: Long, isCompleted: Boolean) -> Unit,
    navigateEditTask: (Long) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val bottomSheetState = rememberModalBottomSheetState()

    var isShowAddTaskBottomSheet by remember { mutableStateOf(value = false) }
    var isShowAddCategoryDialog by remember { mutableStateOf(value = false) }

    val currentMonth = remember { YearMonth.now() }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val calendarState = rememberCalendarState(
        startMonth = currentMonth.minusMonths(60),
        endMonth = currentMonth.plusMonths(60),
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    var selectedCategoryId by remember { mutableLongStateOf(value = -1L) }
    var selectedTaskDate by remember { mutableStateOf(value = LocalDate.now()) }

    Scaffold(
        topBar = {
            CalendarTopAppBar(
                categories = categories,
                visibleDate = calendarState.firstVisibleMonth.yearMonth,
                locale = locale,
                isCheckedFilter = selectedCategoryId != -1L,
                onPreviousClick = {
                    coroutineScope.launch {
                        calendarState.animateScrollToMonth(
                            month = calendarState.firstVisibleMonth.yearMonth.minusMonths(
                                1
                            )
                        )
                    }
                },
                onNextClick = {
                    coroutineScope.launch {
                        calendarState.animateScrollToMonth(
                            month = calendarState.firstVisibleMonth.yearMonth.plusMonths(
                                1
                            )
                        )
                    }
                },
                onTodayClick = {
                    coroutineScope.launch {
                        val today = LocalDate.now()
                        selectedTaskDate = today
                        onFetchTasks(today)
                        calendarState.animateScrollToMonth(month = YearMonth.now())
                    }
                },
                onCategoryClick = { id ->
                    selectedCategoryId = id
                    onFetchCalendarTasks(selectedCategoryId, selectedTaskDate)
                },
                onCreateNewCategoryClick = {
                    isShowAddCategoryDialog = true
                }
            )
        },
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = { isShowAddTaskBottomSheet = true }
            )
        }
    ) { paddingValues ->
        if (isShowAddTaskBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    isShowAddTaskBottomSheet = false
                },
                sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AddTaskBottomSheetContent(
                        locale = locale,
                        categories = categories,
                        timePickerType = timePickerType,
                        onAddTaskClick = { task ->
                            onAddTaskClick(task)
                            isShowAddTaskBottomSheet = false
                        },
                        onCreateNewCategoryClick = {
                            isShowAddCategoryDialog = true
                        },
                    )

                    SnackbarHost(
                        modifier = Modifier.align(alignment = Alignment.BottomCenter),
                        hostState = LocalSnackbarHostState.current,
                    )
                }
            }
        }

        if (isShowAddCategoryDialog) {
            CategoryDialog(
                titleResId = DesignSystemR.string.create_new_category,
                onCloseClick = { isShowAddCategoryDialog = false },
                onSaveClick = { categoryTitle, categoryColorType ->
                    onAddCategoryClick(categoryTitle, categoryColorType)
                    isShowAddCategoryDialog = false
                }
            )
        }

        Column(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            HorizontalCalendar(
                state = calendarState,
                monthHeader = { month ->
                    val daysOfWeek = month.weekDays.first().map {
                        it.date.dayOfWeek
                    }
                    DaysOfWeek(
                        daysOfWeek = daysOfWeek,
                        locale = locale
                    )
                },
                dayContent = { day ->
                    MonthDay(
                        day = day,
                        isSelected = selectedTaskDate == day.date,
                        isVisibleIndicator = calendarTasks.any { it.date == day.date },
                        onClick = { date ->
                            selectedTaskDate = date
                            onFetchTasks(date)
                        },
                    )
                }
            )

            if (tasks.isEmpty()) {
                EmptyContent(
                    title = stringResource(id = DesignSystemR.string.no_tasks)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(
                        items = tasks,
                        key = { index, task -> task.id }
                    ) { index, task ->
                        TaskCard(
                            modifier = Modifier.fillMaxWidth()
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 8.dp
                                ),
                            task = task,
                            locale = locale,
                            onTaskToggleClick = onTaskToggleCompletion,
                            onTaskEditClick = navigateEditTask,
                            onDeleteSymbolClick = onDeleteSymbolClick,
                            onSymbolClick = onSymbolClick,
                            onSubTaskToggleClick = onSubTaskToggleClick
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarTopAppBar(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    visibleDate: YearMonth,
    locale: Locale,
    isCheckedFilter: Boolean,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onCategoryClick: (Long) -> Unit,
    onTodayClick: () -> Unit,
    onCreateNewCategoryClick: () -> Unit,
) {
    var isShowDropdownMenu by remember { mutableStateOf(value = false) }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {},
        navigationIcon = {
            Row(
                modifier = modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(size = 20.dp)
                        .clickable { onPreviousClick() },
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_calendar_arrow_left),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )

                val dateFormat = DateTimeFormatter.ofPattern(
                    DateFormat.getBestDateTimePattern(locale, "yMMMM"), locale
                )
                Text(
                    text = visibleDate.format(dateFormat),
                    style = TodoTheme.typography.medium_16,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Icon(
                    modifier = Modifier.size(size = 20.dp)
                        .clickable { onNextClick() },
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_calendar_arrow_right),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    isShowDropdownMenu = true
                }
            ) {
                Icon(
                    modifier = Modifier.size(size = 18.dp),
                    imageVector =
                        if (isCheckedFilter)
                            ImageVector.vectorResource(id = DesignSystemR.drawable.svg_filter_check)
                        else
                            ImageVector.vectorResource(id = DesignSystemR.drawable.svg_filter),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )

                DropdownMenu(
                    containerColor = MaterialTheme.colorScheme.background,
                    expanded = isShowDropdownMenu,
                    onDismissRequest = { isShowDropdownMenu = false }
                ) {
                    BasicDropdownMenuItem(
                        title = stringResource(id = DesignSystemR.string.entire),
                        onClick = {
                            onCategoryClick(-1L)
                            isShowDropdownMenu = false
                        }
                    )

                    categories.forEach { category ->
                        BasicDropdownMenuItem(
                            title = category.title,
                            onClick = {
                                onCategoryClick(category.id)
                                isShowDropdownMenu = false
                            }
                        )
                    }

                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = 10.dp
                                )
                            ) {
                                Icon(
                                    modifier = Modifier.size(size = 12.dp),
                                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_plus_small),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )

                                Text(
                                    text = stringResource(id = DesignSystemR.string.create_new),
                                    style = TodoTheme.typography.medium_12,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        },
                        onClick = {
                            onCreateNewCategoryClick()
                            isShowDropdownMenu = false
                        }
                    )
                }
            }

            Box(
                modifier = Modifier.padding(end = 14.dp).size(size = 20.dp)
                    .clip(shape = RoundedCornerShape(size = 4.dp))
                    .border(
                        width = 1.5.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(size = 4.dp)
                    ).clickable { onTodayClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "18",
                    style = TodoTheme.typography.medium_12,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CalendarScreenPreview() {
    TodoTheme {
        CalendarScreen(
            calendarTasks = persistentListOf(),
            tasks = persistentListOf(),
            categories = persistentListOf(),
            locale = Locale.KOREA,
            timePickerType = TimePickerType.CLOCK_TIME_PICKER,
            onFetchCalendarTasks = { _, _ -> },
            onFetchTasks = {},
            onAddTaskClick = {},
            onAddCategoryClick = { _, _ -> },
            onTaskToggleCompletion = { _, _ -> },
            onDeleteSymbolClick = { },
            onSymbolClick = { _, _ -> },
            onSubTaskToggleClick = { _, _ -> },
            navigateEditTask = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarTopAppBarPreview() {
    TodoTheme {
        CalendarTopAppBar(
            categories = persistentListOf(),
            visibleDate = YearMonth.now(),
            locale = Locale.KOREA,
            isCheckedFilter = true,
            onPreviousClick = {},
            onNextClick = {},
            onCategoryClick = {},
            onTodayClick = {},
            onCreateNewCategoryClick = {}
        )
    }
}