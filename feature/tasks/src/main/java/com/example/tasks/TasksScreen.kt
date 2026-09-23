package com.example.tasks

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toColorLong
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.AddTaskBottomSheetContent
import com.example.design_system.component.BasicDropdownMenuItem
import com.example.design_system.component.CustomFloatingActionButton
import com.example.design_system.component.EmptyContent
import com.example.design_system.component.dialog.category.CategoryDialog
import com.example.design_system.component.dialog.sort_by.SortByDialog
import com.example.design_system.theme.TodoTheme
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.model.Category
import com.example.model.CategoryColorType
import com.example.model.SortByType
import com.example.model.Task
import com.example.model.TimePickerType
import com.example.model.open_weather.WeatherInfo
import com.example.tasks.component.taskStateGroup
import com.example.tasks.model.TaskStateGroup
import com.example.tasks.utils.toggled
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import java.util.Locale
import kotlin.math.roundToInt
import com.example.design_system.R as DesignSystemR

private const val WEATHER_HEADER_ANCHOR_KEY = "weather_header_anchor"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TasksScreen(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    taskStateGroups: ImmutableList<TaskStateGroup>,
    weatherInfo: WeatherInfo?,
    locale: Locale,
    timePickerType: TimePickerType,
    sortByType: SortByType,
    isVisibleCompletedTask: Boolean,
    onCategoryClick: (Long) -> Unit,
    onTaskToggleClick: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskEditClick: (Long) -> Unit,
    onDeleteSymbolClick: (Long) -> Unit,
    onAddTaskClick: (Task) -> Unit,
    onSymbolClick: (taskId: Long, symbolId: Int) -> Unit,
    onSubTaskToggleClick: (subTaskId: Long, isCompleted: Boolean) -> Unit,
    onAddCategoryClick: (categoryTitle: String, categoryColorType: CategoryColorType) -> Unit,
    onSortByTypeChanged: (SortByType) -> Unit,
    onCompletedTasksClick: () -> Unit,
    onSearchClick: () -> Unit,
    onManageCategoriesClick: () -> Unit,
    onSettingClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val taskListState = rememberLazyListState()

    var collapsedTaskStates by remember { mutableStateOf(value = persistentSetOf<String>()) }

    val bottomSheetState = rememberModalBottomSheetState()
    var isShowAddTaskBottomSheet by remember { mutableStateOf(value = false) }

    var isShowSortByDialog by remember { mutableStateOf(value = false) }
    var isShowAddCategoryDialog by remember { mutableStateOf(value = false) }

    Scaffold(
        topBar = {
            TasksTopAppBar(
                scrollState = scrollState,
                categories = categories,
                onCategoryClick = onCategoryClick,
                onSortByClick = {
                    isShowSortByDialog = true
                },
                onSearchClick = onSearchClick,
                onManageCategoriesClick = onManageCategoriesClick,
                onSettingClick = onSettingClick,
            )
        },
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = {
                    isShowAddTaskBottomSheet = true
                }
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

        if (isShowSortByDialog) {
            SortByDialog(
                sortByType = sortByType,
                onCloseClick = {
                    isShowSortByDialog = false
                },
                onSelectClick = { sortByType ->
                    onSortByTypeChanged(sortByType)
                    isShowSortByDialog = false
                }
            )
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

        val density = LocalDensity.current
        var weatherHeaderHeightPx by remember { mutableIntStateOf(value = 0) }
        val weatherHeaderHeightDp =
            with(density) { weatherHeaderHeightPx.toDp() }
        val weatherHeaderAlpha = remember {
            derivedStateOf {
                if (weatherHeaderHeightPx <= 0) {
                    1f
                } else {
                    val anchorOffset = taskListState.layoutInfo.visibleItemsInfo
                        .firstOrNull { it.key == WEATHER_HEADER_ANCHOR_KEY }
                        ?.offset
                        ?: -weatherHeaderHeightPx
                    ((weatherHeaderHeightPx + anchorOffset).toFloat() / weatherHeaderHeightPx)
                        .coerceIn(0f, 1f)
                }
            }
        }

        Box(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            if (weatherInfo != null) {
                TasksWeatherHeader(
                    modifier = Modifier
                        .align(alignment = Alignment.TopCenter)
                        .onSizeChanged { size ->
                            weatherHeaderHeightPx = size.height
                        }
                        .graphicsLayer { alpha = weatherHeaderAlpha.value },
                    temp = weatherInfo.main.temp,
                    name = weatherInfo.name,
                    description = weatherInfo.weather?.description.orEmpty(),
                    iconResId = weatherInfo.weather?.icon.orEmpty()
                        .toIconResId(),
                )
            }

            if (taskStateGroups.isEmpty())
                EmptyContent(
                    modifier = Modifier.padding(top = weatherHeaderHeightDp),
                    title = stringResource(id = DesignSystemR.string.no_tasks)
                )
            else
                LazyColumn(
                    state = taskListState,
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item(key = WEATHER_HEADER_ANCHOR_KEY) {
                        Spacer(modifier = Modifier.height(height = weatherHeaderHeightDp))
                    }

                    taskStateGroups.forEach { taskStateGroup ->
                        taskStateGroup(
                            taskStateGroup = taskStateGroup,
                            locale = locale,
                            collapsedTaskStates = collapsedTaskStates,
                            onTaskToggleClick = onTaskToggleClick,
                            onTaskEditClick = onTaskEditClick,
                            onDeleteSymbolClick = onDeleteSymbolClick,
                            onSymbolClick = onSymbolClick,
                            onTaskStateGroupHeaderClick = { key ->
                                collapsedTaskStates =
                                    collapsedTaskStates.toggled(element = key)
                            },
                            onSubTaskToggleClick = onSubTaskToggleClick
                        )
                    }

                    if (isVisibleCompletedTask) {
                        item {
                            Text(
                                modifier = Modifier
                                    .clickable { onCompletedTasksClick() }
                                    .padding(all = 16.dp),
                                text = stringResource(id = DesignSystemR.string.check_all_completed_tasks),
                                textAlign = TextAlign.Center,
                                style = TodoTheme.typography.medium_12,
                                textDecoration = TextDecoration.Underline,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TasksTopAppBar(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    categories: ImmutableList<Category>,
    onCategoryClick: (Long) -> Unit,
    onSearchClick: () -> Unit,
    onSortByClick: () -> Unit,
    onManageCategoriesClick: () -> Unit,
    onSettingClick: () -> Unit,
) {
    var isShowDropdownMenu by remember { mutableStateOf(value = false) }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            if (categories.isEmpty()) {
                Text(
                    text = stringResource(id = DesignSystemR.string.app_name),
                    style = TodoTheme.typography.bold_20,
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                Row(
                    modifier = modifier.fillMaxWidth()
                        .padding(end = 4.dp)
                        .horizontalScroll(state = scrollState),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp)
                ) {
                    CategoryItem(
                        id = -1L,
                        title = stringResource(id = DesignSystemR.string.entire),
                        onClick = onCategoryClick
                    )
                    categories.forEach { category ->
                        CategoryItem(
                            id = category.id,
                            title = category.title,
                            onClick = onCategoryClick
                        )
                    }
                }
            }
        },
        actions = {
            IconButton(
                onClick = {
                    isShowDropdownMenu = true
                }
            ) {
                Icon(
                    modifier = modifier.size(size = 18.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_menu_dots),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )

                DropdownMenu(
                    containerColor = MaterialTheme.colorScheme.background,
                    expanded = isShowDropdownMenu,
                    onDismissRequest = { isShowDropdownMenu = false }
                ) {
                    BasicDropdownMenuItem(
                        title = stringResource(id = DesignSystemR.string.search),
                        onClick = {
                            onSearchClick()
                            isShowDropdownMenu = false
                        }
                    )

                    BasicDropdownMenuItem(
                        title = stringResource(id = DesignSystemR.string.sort_by),
                        onClick = {
                            onSortByClick()
                            isShowDropdownMenu = false
                        }
                    )

                    BasicDropdownMenuItem(
                        title = stringResource(id = DesignSystemR.string.manage_categories),
                        onClick = {
                            onManageCategoriesClick()
                            isShowDropdownMenu = false
                        }
                    )

                    BasicDropdownMenuItem(
                        title = stringResource(id = DesignSystemR.string.settings),
                        onClick = {
                            onSettingClick()
                            isShowDropdownMenu = false
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun CategoryItem(
    id: Long,
    title: String,
    onClick: (Long) -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(
                color = MaterialTheme.colorScheme.primary,
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
            .clickable { onClick(id) },
    ) {
        Text(
            text = title,
            style = TodoTheme.typography.bold_16,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun TasksWeatherHeader(
    modifier: Modifier = Modifier,
    temp: Double,
    name: String,
    description: String,
    iconResId: Int,
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(space = 2.dp)
            ) {
                Text(
                    text = "${temp.roundToInt()} °",
                    style = TodoTheme.typography.bold_32,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 2.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(size = 8.dp),
                        imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_marker),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = name,
                        style = TodoTheme.typography.medium_12,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = 2.dp)
            ) {
                Icon(
                    modifier = Modifier.size(size = 36.dp),
                    imageVector = ImageVector.vectorResource(id = iconResId),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )

                Text(
                    text = description,
                    style = TodoTheme.typography.medium_12,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

private fun String.toIconResId(): Int =
    when (this) {
        "01d" -> DesignSystemR.drawable.svg_weather_01
        "01n" -> DesignSystemR.drawable.svg_weather_02
        "02d" -> DesignSystemR.drawable.svg_weather_03
        "02n" -> DesignSystemR.drawable.svg_weather_04
        "03d", "03n" -> DesignSystemR.drawable.svg_weather_05
        "04d", "04n" -> DesignSystemR.drawable.svg_weather_06
        "09d", "09n" -> DesignSystemR.drawable.svg_weather_07
        "10d", "10n" -> DesignSystemR.drawable.svg_weather_08
        "11d", "11n" -> DesignSystemR.drawable.svg_weather_09
        "13d", "13n" -> DesignSystemR.drawable.svg_weather_10
        else -> DesignSystemR.drawable.svg_weather_11
    }

@Preview(showBackground = true)
@Composable
private fun TasksScreenPreview() {
    TodoTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val categories = persistentListOf(
            Category(
                id = 1,
                title = "운동",
                colorValue = Color.Red.toColorLong()
            )
        )
        CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
            TasksScreen(
                categories = categories,
                taskStateGroups = persistentListOf(),
                weatherInfo = null,
                locale = Locale.KOREA,
                timePickerType = TimePickerType.CLOCK_TIME_PICKER,
                sortByType = SortByType.DUE_DATE_AND_TIME,
                isVisibleCompletedTask = true,
                onCategoryClick = {},
                onTaskToggleClick = { _, _ -> },
                onTaskEditClick = {},
                onDeleteSymbolClick = {},
                onAddTaskClick = {},
                onSymbolClick = { _, _ -> },
                onSubTaskToggleClick = { _, _ -> },
                onAddCategoryClick = { _, _ -> },
                onSortByTypeChanged = {},
                onCompletedTasksClick = {},
                onSearchClick = {},
                onManageCategoriesClick = {},
                onSettingClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TasksTopAppBarPreview() {
    TodoTheme {
        TasksTopAppBar(
            scrollState = rememberScrollState(),
            categories = persistentListOf(),
            onCategoryClick = {},
            onSearchClick = {},
            onSortByClick = {},
            onManageCategoriesClick = {},
            onSettingClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryItemPreview() {
    TodoTheme {
        CategoryItem(
            id = -1L,
            title = "",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TasksWeatherHeaderPreview() {
    TodoTheme {
        TasksWeatherHeader(
            temp = 24.0,
            name = "Bucheon-si",
            description = "맑음",
            iconResId = DesignSystemR.drawable.svg_weather_01
        )
    }
}