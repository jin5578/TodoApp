package com.example.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.BasicDropdownMenuItem
import com.example.design_system.theme.TodoTheme
import com.example.profile.model.ProfileCategoryEntry
import com.example.profile.model.ProfileCategoryOption
import com.example.profile.model.ProfileTaskDuration
import com.example.profile.model.ProfileTaskState
import com.patrykandpatrick.vico.compose.common.Fill
import com.patrykandpatrick.vico.compose.pie.PieChart
import com.patrykandpatrick.vico.compose.pie.PieChartHost
import com.patrykandpatrick.vico.compose.pie.PieSize
import com.patrykandpatrick.vico.compose.pie.data.PieChartModel
import com.patrykandpatrick.vico.compose.pie.rememberPieChart
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import com.example.design_system.R as DesignSystemR

@Composable
internal fun ProfileCategorySummary(
    modifier: Modifier = Modifier,
    categoryEntries: ImmutableList<ProfileCategoryEntry>,
    taskState: ProfileTaskState,
    taskDuration: ProfileTaskDuration,
    onTaskStateChanged: (ProfileTaskState) -> Unit,
    onTaskDurationChanged: (ProfileTaskDuration) -> Unit,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ProfileCategoryOption(
                selected = taskState,
                options = ProfileTaskState.entries,
                onClick = { option -> onTaskStateChanged(option) }
            )

            ProfileCategoryOption(
                selected = taskDuration,
                options = ProfileTaskDuration.entries,
                onClick = { option -> onTaskDurationChanged(option) }
            )
        }

        ProfileCategoryChart(
            categoryEntries = categoryEntries,
            isCompleted = taskState.isCompleted
        )
    }
}

@Composable
private fun <T : ProfileCategoryOption> ProfileCategoryOption(
    selected: T,
    options: List<T>,
    onClick: (T) -> Unit,
) {
    var isShowDropdownMenu by remember { mutableStateOf(value = false) }

    Box {
        Row(
            modifier = Modifier.clickable {
                isShowDropdownMenu = true
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 4.dp)
        ) {
            Text(
                text = stringResource(id = selected.titleResId),
                style = TodoTheme.typography.medium_12,
                color = MaterialTheme.colorScheme.onBackground
            )

            Icon(
                modifier = Modifier.size(size = 12.dp),
                imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_down),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        DropdownMenu(
            containerColor = MaterialTheme.colorScheme.background,
            expanded = isShowDropdownMenu,
            onDismissRequest = { isShowDropdownMenu = false }
        ) {
            options.forEach { option ->
                BasicDropdownMenuItem(
                    title = stringResource(id = option.titleResId),
                    onClick = {
                        onClick(option)
                        isShowDropdownMenu = false
                    }
                )
            }
        }
    }
}

@Composable
private fun ProfileCategoryChart(
    modifier: Modifier = Modifier,
    categoryEntries: List<ProfileCategoryEntry>,
    isCompleted: Boolean,
) {
    val isEmpty = categoryEntries.isEmpty()

    val total = remember(key1 = categoryEntries) {
        categoryEntries.sumOf { entry -> entry.value.toDouble() }.toFloat()
    }

    val pieChart = rememberPieChart(
        sliceProvider = PieChart.SliceProvider.series(
            slices = if (isEmpty) {
                listOf(
                    PieChart.Slice(
                        fill = Fill(
                            color = MaterialTheme.colorScheme.surfaceContainerHighest
                        ),
                    )
                )
            } else {
                categoryEntries.map { entry ->
                    PieChart.Slice(
                        fill = Fill(
                            color = entry.color
                        ),
                    )
                }
            }
        ),
        outerSize = PieSize.Outer.Fill,
        innerSize = PieSize.Inner.fixed(maxDiameter = 50.dp),
    )

    val model = remember(key1 = categoryEntries) {
        val values =
            if (isEmpty) listOf(1f)
            else categoryEntries.map { entry -> entry.value }
        PieChartModel.build(values = values.toTypedArray())
    }

    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PieChartHost(
            modifier = Modifier.size(size = 120.dp),
            chart = pieChart,
            model = model,
        )

        if (isEmpty) {
            Text(
                text = stringResource(
                    id = if (isCompleted) {
                        DesignSystemR.string.no_completed_category_tasks
                    } else {
                        DesignSystemR.string.no_pending_category_tasks
                    }
                ),
                style = TodoTheme.typography.medium_12,
                color = MaterialTheme.colorScheme.onBackground
            )
        } else {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(space = 8.dp)
            ) {
                categoryEntries.forEach { entry ->
                    ProfileCategoryProportion(
                        entry = entry,
                        total = total
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileCategoryProportion(
    modifier: Modifier = Modifier,
    entry: ProfileCategoryEntry,
    total: Float,
) {
    val percent = if (total > 0f) entry.value / total * 100f else 0f
    val locale = LocalConfiguration.current.locales[0]

    Row(
        modifier = modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(size = 10.dp)
                .background(color = entry.color, shape = CircleShape)
        )

        Text(
            text = entry.name,
            style = TodoTheme.typography.medium_12,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = String.format(locale, "%.0f%%", percent),
            style = TodoTheme.typography.medium_12,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileCategorySummaryPreview() {
    val profileCategoryEntries = listOf(
        ProfileCategoryEntry(
            name = "Work",
            value = 40f,
            color = Color(color = 0xFF4C6FFF)
        ),
        ProfileCategoryEntry(
            name = "Study",
            value = 25f,
            color = Color(color = 0xFF00C2A8)
        ),
        ProfileCategoryEntry(
            name = "Exercise",
            value = 20f,
            color = Color(color = 0xFFFFB020)
        ),
        ProfileCategoryEntry(
            name = "Etc",
            value = 15f,
            color = Color(color = 0xFFF6416C)
        ),
    ).toPersistentList()

    TodoTheme {
        ProfileCategorySummary(
            categoryEntries = profileCategoryEntries,
            taskState = ProfileTaskState.COMPLETED,
            taskDuration = ProfileTaskDuration.ALL,
            onTaskStateChanged = {},
            onTaskDurationChanged = {}
        )
    }
}