package com.example.completed_tasks.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.completed_tasks.model.TaskDateGroup
import com.example.design_system.component.TaskCard
import com.example.design_system.theme.TodoTheme
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

internal fun LazyListScope.taskDateGroup(
    index: Int,
    groupSize: Int,
    taskDateGroup: TaskDateGroup,
    locale: Locale,
    onTaskToggleClick: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskEditClick: (Long) -> Unit,
    onDeleteSymbolClick: (taskId: Long) -> Unit,
    onSymbolClick: (taskId: Long, symbolId: Int) -> Unit,
    onSubTaskToggleClick: (subTaskId: Long, isCompleted: Boolean) -> Unit,
) {
    stickyHeader(key = taskDateGroup.taskDate) {
        val dateFormat = DateTimeFormatter.ofPattern(
            "yyyy/MM/dd", locale
        )
        val dateContent = taskDateGroup.taskDate.format(dateFormat)
        TaskDateGroupHeader(
            index = index,
            title = dateContent
        )
    }

    itemsIndexed(
        items = taskDateGroup.tasks,
        key = { _, task -> task.id }
    ) { itemIndex, task ->
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(horizontal = 16.dp)
        ) {
            TimelineLineIndicator()

            val bottomPadding =
                if (itemIndex != taskDateGroup.tasks.size - 1) 8.dp
                else 0.dp
            TaskCard(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 14.dp, bottom = bottomPadding),
                task = task,
                locale = locale,
                onTaskToggleClick = onTaskToggleClick,
                onTaskEditClick = onTaskEditClick,
                onDeleteSymbolClick = onDeleteSymbolClick,
                onSymbolClick = onSymbolClick,
                onSubTaskToggleClick = onSubTaskToggleClick
            )
        }

        if (index == groupSize - 1 && itemIndex == taskDateGroup.tasks.size - 1) {
            Spacer(
                modifier = Modifier.height(height = 8.dp)
            )
        }
    }
}

@Composable
private fun TaskDateGroupHeader(
    modifier: Modifier = Modifier,
    index: Int,
    title: String,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 14.dp)
    ) {
        when (index) {
            0 -> {
                TimelineStartIndicator()
            }

            else -> {
                TimelineMiddleIndicator()
            }
        }

        Text(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            text = title,
            style = TodoTheme.typography.bold_12,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun TimelineStartIndicator(
    modifier: Modifier = Modifier,
    outerCircleSize: Dp = 16.dp,
    strokeWidth: Dp = 1.5.dp,
    gap: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Canvas(
        modifier = modifier.width(outerCircleSize).fillMaxHeight()
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        val gapPx = gap.toPx()
        val outerRadius = outerCircleSize.toPx() / 2f
        val center = Offset(x = size.width / 2f, y = outerRadius + 6.dp.toPx())

        drawCircle(
            color = color,
            radius = outerRadius - strokeWidthPx / 2f,
            center = center,
            style = Stroke(width = strokeWidthPx)
        )

        val innerRadius = outerRadius - strokeWidthPx - gapPx
        drawCircle(
            color = color,
            radius = innerRadius,
            center = center
        )

        drawLine(
            color = color,
            start = Offset(x = center.x, y = center.y + innerRadius),
            end = Offset(x = center.x, y = size.height),
            strokeWidth = strokeWidthPx
        )
    }
}

/*@Composable
private fun TimelineEndIndicator(
    modifier: Modifier = Modifier,
    outerCircleSize: Dp = 16.dp,
    strokeWidth: Dp = 1.5.dp,
    gap: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Canvas(
        modifier = modifier.width(outerCircleSize).fillMaxHeight()
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        val gapPx = gap.toPx()
        val outerRadius = outerCircleSize.toPx() / 2f
        val center = Offset(x = size.width / 2f, y = size.height - outerRadius)

        drawCircle(
            color = color,
            radius = outerRadius - strokeWidthPx / 2f,
            center = center,
            style = Stroke(width = strokeWidthPx)
        )

        val innerRadius = outerRadius - strokeWidthPx - gapPx
        drawCircle(
            color = color,
            radius = innerRadius,
            center = center
        )

        drawLine(
            color = color,
            start = Offset(x = center.x, y = 0f),
            end = Offset(x = center.x, y = center.y - innerRadius),
            strokeWidth = strokeWidthPx
        )
    }
}*/

@Composable
private fun TimelineMiddleIndicator(
    modifier: Modifier = Modifier,
    outerCircleSize: Dp = 16.dp,
    strokeWidth: Dp = 1.5.dp,
    gap: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Canvas(
        modifier = modifier.width(outerCircleSize).fillMaxHeight()
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        val gapPx = gap.toPx()
        val outerRadius = outerCircleSize.toPx() / 2f
        val center = Offset(x = size.width / 2f, y = size.height / 2f)

        drawCircle(
            color = color,
            radius = outerRadius - strokeWidthPx / 2f,
            center = center,
            style = Stroke(width = strokeWidthPx)
        )

        val innerRadius = outerRadius - strokeWidthPx - gapPx
        drawCircle(
            color = color,
            radius = innerRadius,
            center = center
        )

        drawLine(
            color = color,
            start = Offset(x = center.x, y = 0f),
            end = Offset(x = center.x, y = center.y - innerRadius),
            strokeWidth = strokeWidthPx
        )

        drawLine(
            color = color,
            start = Offset(x = center.x, y = center.y + innerRadius),
            end = Offset(x = center.x, y = size.height),
            strokeWidth = strokeWidthPx
        )
    }
}

@Composable
private fun TimelineLineIndicator(
    modifier: Modifier = Modifier,
    outerCircleSize: Dp = 16.dp,
    strokeWidth: Dp = 1.5.dp,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Canvas(
        modifier = modifier.width(outerCircleSize).fillMaxHeight()
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        val centerX = size.width / 2f

        drawLine(
            color = color,
            start = Offset(x = centerX, y = 0f),
            end = Offset(x = centerX, y = size.height),
            strokeWidth = strokeWidthPx
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskDateGroupPreview() {
    TodoTheme {
        TaskDateGroup(
            taskDate = LocalDate.now(),
            tasks = persistentListOf()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimelineStartIndicatorPreview() {
    TodoTheme {
        TimelineStartIndicator()
    }
}

/*@Preview(showBackground = true)
@Composable
private fun TimelineEndIndicatorPreview() {
    TodoTheme {
        TimelineEndIndicator()
    }
}*/

@Preview(showBackground = true)
@Composable
private fun TimelineMiddleIndicatorPreview() {
    TodoTheme {
        TimelineMiddleIndicator()
    }
}

@Preview(showBackground = true)
@Composable
private fun TimelineLineIndicatorPreview() {
    TodoTheme {
        TimelineLineIndicator()
    }
}