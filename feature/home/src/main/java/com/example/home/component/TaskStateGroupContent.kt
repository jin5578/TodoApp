package com.example.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.home.model.TaskStateGroup
import com.example.home.utils.getTitleResId
import kotlinx.collections.immutable.PersistentSet
import java.util.Locale
import com.example.design_system.R as DesignSystemR

internal fun LazyListScope.taskStateGroupContent(
    taskStateGroup: TaskStateGroup,
    locale: Locale,
    collapsedTaskStates: PersistentSet<String>,
    onTaskToggleClick: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskEditClick: (Long) -> Unit,
    onDeleteSymbolClick: (taskId: Long) -> Unit,
    onSymbolClick: (taskId: Long, symbolId: Int) -> Unit,
    onTaskStateGroupHeaderClick: (String) -> Unit,
) {
    val taskState = taskStateGroup.taskState
    val taskStateKey = taskState.key
    val isExpanded = taskStateKey !in collapsedTaskStates

    stickyHeader(key = taskStateKey) {
        TaskStateGroupHeader(
            title = stringResource(id = taskState.getTitleResId()),
            isExpanded = isExpanded,
            onClick = { onTaskStateGroupHeaderClick(taskStateKey) }
        )
    }

    if (isExpanded) {
        items(
            items = taskStateGroup.tasks,
            key = { task -> task.id }
        ) { task ->
            TaskCard(
                task = task,
                locale = locale,
                onTaskToggleClick = onTaskToggleClick,
                onTaskEditClick = onTaskEditClick,
                onDeleteSymbolClick = onDeleteSymbolClick,
                onSymbolClick = onSymbolClick,
            )
        }
    }
}

@Composable
private fun TaskStateGroupHeader(
    modifier: Modifier = Modifier,
    title: String,
    isExpanded: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(weight = 1f),
            text = title,
            style = TodoTheme.typography.bold_20,
            color = MaterialTheme.colorScheme.onBackground
        )

        val iconResId =
            if (isExpanded) DesignSystemR.drawable.svg_arrow_up
            else DesignSystemR.drawable.svg_arrow_down
        Icon(
            modifier = Modifier.size(size = 20.dp),
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}