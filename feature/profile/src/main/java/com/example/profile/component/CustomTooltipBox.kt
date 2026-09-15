package com.example.profile.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CustomTooltipBox(
    modifier: Modifier = Modifier,
    descriptionResId: Int,
    content: @Composable RowScope.() -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val tooltipState = rememberTooltipState(isPersistent = true)

    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = TooltipAnchorPosition.Below
        ),
        tooltip = {
            PlainTooltip(
                containerColor = MaterialTheme.colorScheme.background,
                shadowElevation = 4.dp
            ) {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = stringResource(id = descriptionResId),
                    style = TodoTheme.typography.medium_12,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        state = tooltipState
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()

            Icon(
                modifier = Modifier.size(size = 12.dp)
                    .clip(shape = CircleShape)
                    .clickable { coroutineScope.launch { tooltipState.show() } },
                imageVector = ImageVector.vectorResource(id = R.drawable.svg_information),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomTooltipBoxPreview() {
    TodoTheme {
        CustomTooltipBox(
            descriptionResId = R.string.pending_tasks_description,
        ) {
            Text(
                text = "Label",
                style = TodoTheme.typography.medium_16,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}