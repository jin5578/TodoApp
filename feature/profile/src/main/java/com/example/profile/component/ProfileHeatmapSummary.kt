package com.example.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designSystem.component.Heatmap
import com.example.designSystem.model.HeatmapEntry
import com.example.designSystem.theme.TodoTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import java.time.LocalDate
import java.util.Locale
import com.example.designSystem.R as DesignSystemR

@Composable
internal fun ProfileHeatmapSummary(
    modifier: Modifier = Modifier,
    heatmapEntries: ImmutableList<HeatmapEntry>,
    locale: Locale,
    titleResId: Int,
    descriptionResId: Int,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(size = 8.dp),
            )
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
    ) {
        CustomTooltipBox(
            descriptionResId = descriptionResId,
        ) {
            Text(
                text = stringResource(id = titleResId),
                style = TodoTheme.typography.medium_16,
                color = MaterialTheme.colorScheme.onBackground,
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
private fun ProfileHeatmapSummaryPreview() {
    TodoTheme {
        val today = LocalDate.now()
        val heatmapEntries = (0..365).mapNotNull { offset ->
            val level = offset % 5
            if (level == 0) {
                null
            } else {
                HeatmapEntry(
                    date = today.minusDays(offset.toLong()),
                    level = level,
                )
            }
        }.toPersistentList()

        ProfileHeatmapSummary(
            heatmapEntries = heatmapEntries,
            locale = Locale.KOREA,
            titleResId = DesignSystemR.string.heatmap,
            descriptionResId = DesignSystemR.string.heatmap_description,
        )
    }
}
