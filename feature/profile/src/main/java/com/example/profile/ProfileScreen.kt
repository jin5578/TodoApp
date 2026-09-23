package com.example.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.model.HeatmapEntry
import com.example.design_system.theme.TodoTheme
import com.example.profile.component.ProfileCategorySummary
import com.example.profile.component.ProfileDailySummary
import com.example.profile.component.ProfileHeatmapSummary
import com.example.profile.component.ProfileTaskSummary
import com.example.profile.model.ProfileCategoryEntry
import com.example.profile.model.ProfileTaskDuration
import com.example.profile.model.ProfileTaskState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import java.time.LocalDate
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileScreen(
    modifier: Modifier = Modifier,
    completedTasksCount: Int,
    incompletedTasksCount: Int,
    heatmapEntries: ImmutableList<HeatmapEntry>,
    githubHeatmapEntries: ImmutableList<HeatmapEntry>,
    categoryEntries: ImmutableList<ProfileCategoryEntry>,
    categoryTaskState: ProfileTaskState,
    categoryTaskDuration: ProfileTaskDuration,
    dailyEntries: ImmutableList<Float>,
    dailyFromDate: LocalDate,
    dailyToDate: LocalDate,
    locale: Locale,
    onTaskStateChanged: (ProfileTaskState) -> Unit,
    onTaskDurationChanged: (ProfileTaskDuration) -> Unit,
    onDailyDateRangeChanged: (fromDate: LocalDate, toDate: LocalDate) -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold() { paddingValues ->
        Column(
            modifier = modifier.fillMaxSize()
                .verticalScroll(state = scrollState)
                .padding(paddingValues = paddingValues)
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            ProfileTaskSummary(
                completedTasksCount = completedTasksCount,
                incompletedTasksCount = incompletedTasksCount
            )

            ProfileHeatmapSummary(
                heatmapEntries = heatmapEntries,
                locale = locale,
                titleResId = DesignSystemR.string.heatmap,
                descriptionResId = DesignSystemR.string.heatmap_description,
            )

            if (githubHeatmapEntries.isNotEmpty()) {
                ProfileHeatmapSummary(
                    heatmapEntries = githubHeatmapEntries,
                    locale = locale,
                    titleResId = DesignSystemR.string.github_heatmap,
                    descriptionResId = DesignSystemR.string.github_heatmap_description
                )
            }

            ProfileCategorySummary(
                categoryEntries = categoryEntries,
                taskState = categoryTaskState,
                taskDuration = categoryTaskDuration,
                onTaskStateChanged = onTaskStateChanged,
                onTaskDurationChanged = onTaskDurationChanged
            )

            ProfileDailySummary(
                dailyEntries = dailyEntries,
                fromDate = dailyFromDate,
                toDate = dailyToDate,
                locale = locale,
                onDailyDateRangeChanged = onDailyDateRangeChanged
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    val today = LocalDate.now()
    val heatmapEntries = (0..365).mapNotNull { offset ->
        val level = offset % 5
        if (level == 0) null else HeatmapEntry(
            date = today.minusDays(offset.toLong()),
            level = level
        )
    }.toPersistentList()

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

    val dailyEntries =
        persistentListOf(3f, 7f, 5f, 12f, 8f, 2f, 6f)

    TodoTheme {
        ProfileScreen(
            completedTasksCount = 3,
            incompletedTasksCount = 1,
            heatmapEntries = heatmapEntries,
            githubHeatmapEntries = persistentListOf(),
            categoryEntries = profileCategoryEntries,
            categoryTaskState = ProfileTaskState.COMPLETED,
            categoryTaskDuration = ProfileTaskDuration.ALL,
            dailyEntries = dailyEntries,
            dailyFromDate = today.minusDays(6),
            dailyToDate = today,
            locale = LocalConfiguration.current.locales[0],
            onTaskStateChanged = {},
            onTaskDurationChanged = {},
            onDailyDateRangeChanged = { _, _ -> }
        )
    }
}