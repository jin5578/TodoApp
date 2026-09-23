package com.example.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.design_system.R as DesignSystemR

@Composable
internal fun ProfileTaskSummary(
    modifier: Modifier = Modifier,
    completedTasksCount: Int,
    incompletedTasksCount: Int,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileTaskSummaryItem(
            modifier = Modifier.weight(weight = 1f),
            isVisibleIcon = false,
            titleResId = DesignSystemR.string.completed_tasks,
            content = completedTasksCount.toString(),
        )

        ProfileTaskSummaryItem(
            modifier = Modifier.weight(weight = 1f),
            isVisibleIcon = true,
            titleResId = DesignSystemR.string.pending_tasks,
            content = incompletedTasksCount.toString(),
        )
    }
}

@Composable
private fun ProfileTaskSummaryItem(
    modifier: Modifier = Modifier,
    isVisibleIcon: Boolean,
    titleResId: Int,
    content: String,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(size = 8.dp),
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    start = 8.dp,
                    top = if (isVisibleIcon) 8.dp else 28.dp,
                    end = 8.dp,
                    bottom = 24.dp,
                ),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            if (isVisibleIcon) {
                CustomTooltipBox(
                    descriptionResId = DesignSystemR.string.pending_tasks_description,
                )
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = titleResId),
                style = TodoTheme.typography.medium_12,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = content,
                style = TodoTheme.typography.bold_20,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileTaskSummaryPreview() {
    TodoTheme {
        ProfileTaskSummary(
            completedTasksCount = 7,
            incompletedTasksCount = 7,
        )
    }
}
