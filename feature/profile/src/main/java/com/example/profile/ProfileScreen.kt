package com.example.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.Heatmap
import com.example.design_system.model.HeatmapEntry
import com.example.design_system.theme.TodoTheme
import com.example.profile.component.CustomTooltipBox
import kotlinx.collections.immutable.ImmutableList
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
    locale: Locale,
) {
    Scaffold() { paddingValues ->
        Column(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            ProfileTaskState(
                completedTasksCount = completedTasksCount,
                incompletedTasksCount = incompletedTasksCount
            )

            ProfileHeatmap(
                heatmapEntries = heatmapEntries,
                locale = locale
            )
        }
    }
}

@Composable
private fun ProfileTaskState(
    completedTasksCount: Int,
    incompletedTasksCount: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileTaskStateItem(
            modifier = Modifier.weight(weight = 1f),
            isVisibleIcon = false,
            titleResId = DesignSystemR.string.completed_tasks,
            content = completedTasksCount.toString(),
        )

        ProfileTaskStateItem(
            modifier = Modifier.weight(weight = 1f),
            isVisibleIcon = true,
            titleResId = DesignSystemR.string.pending_tasks,
            content = incompletedTasksCount.toString(),
        )
    }
}

@Composable
private fun ProfileTaskStateItem(
    modifier: Modifier = Modifier,
    isVisibleIcon: Boolean,
    titleResId: Int,
    content: String,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(size = 8.dp)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    start = 8.dp,
                    top = if (isVisibleIcon) 8.dp else 28.dp,
                    end = 8.dp,
                    bottom = 24.dp
                ),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            if (isVisibleIcon) {
                CustomTooltipBox(
                    descriptionResId = DesignSystemR.string.pending_tasks_description,
                    iconSize = 12.dp,
                )
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = titleResId),
                style = TodoTheme.typography.medium_12,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = content,
                style = TodoTheme.typography.bold_20,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ProfileHeatmap(
    heatmapEntries: ImmutableList<HeatmapEntry>,
    locale: Locale,
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        CustomTooltipBox(
            descriptionResId = DesignSystemR.string.heatmap_description,
            iconSize = 16.dp
        ) {
            Text(
                text = stringResource(id = DesignSystemR.string.heatmap),
                style = TodoTheme.typography.medium_16,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Heatmap(
            entries = heatmapEntries,
            locale = locale,
        )
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

    TodoTheme {
        ProfileScreen(
            completedTasksCount = 3,
            incompletedTasksCount = 1,
            heatmapEntries = heatmapEntries,
            locale = Locale.getDefault(),
        )
    }
}