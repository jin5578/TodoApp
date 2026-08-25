package com.example.design_system.component.input

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.design_system.theme.priorityColors
import com.example.model.PriorityType

@Composable
fun InputTaskPriority(
    modifier: Modifier = Modifier,
    priorityType: PriorityType,
    onSelect: (PriorityType) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(space = 20.dp),
    ) {
        Text(
            text = stringResource(id = R.string.priority),
            style = TodoTheme.typography.bold_18,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 10.dp)
        ) {
            PriorityType.entries.forEach { type ->
                val titleResId = type.getTitleResId()
                InputTaskPriorityItem(
                    modifier = Modifier.weight(weight = 1f),
                    title = stringResource(id = titleResId),
                    backgroundColor = priorityColors[type.ordinal],
                    isSelected = type == priorityType,
                    onClick = { onSelect(type) }
                )
            }
        }
    }
}

@Composable
private fun InputTaskPriorityItem(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var bgColor = MaterialTheme.colorScheme.surfaceDim
        var textColor = MaterialTheme.colorScheme.onSurface

        if (isSelected) {
            bgColor = backgroundColor
            textColor = MaterialTheme.colorScheme.onSurface
        }

        Box(
            modifier = Modifier.fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 8.dp))
                .background(color = bgColor)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 4.dp,
                    vertical = 16.dp
                ),
                text = title,
                style = TodoTheme.typography.medium_14,
                color = textColor,
            )
        }

        if (isSelected) {
            val animValue = remember { Animatable(initialValue = 0f) }
            LaunchedEffect(key1 = Unit) {
                animValue.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 300)
                )
            }
            Box(
                modifier = Modifier.width(width = 40.dp * animValue.value)
                    .height(height = 4.dp)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
            )
        }
    }
}

private fun PriorityType.getTitleResId(): Int =
    when (this) {
        PriorityType.LOW -> R.string.low
        PriorityType.MEDIUM -> R.string.medium
        PriorityType.HIGH -> R.string.high
    }

@Preview(showBackground = true)
@Composable
private fun InputTaskPriorityPreview() {
    TodoTheme {
        InputTaskPriority(
            priorityType = PriorityType.HIGH,
            onSelect = {}
        )
    }
}