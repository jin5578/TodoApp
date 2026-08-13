package com.example.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.Indigo
import com.example.design_system.theme.Red
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.design_system.R as DesignSystemR

@Composable
internal fun <T> SwipeActionBox(
    item: T,
    animationDuration: Int = 300,
    onEditAction: (T) -> Unit,
    onDeleteAction: (T) -> Unit,
    content: @Composable (T) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val state = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled
    )

    AnimatedVisibility(
        visible = true,
        exit = fadeOut(
            animationSpec = tween(durationMillis = animationDuration)
        )
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                ActionBackground(
                    editBackgroundColor = Indigo,
                    editIcon = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_pencil),
                    deleteBackgroundColor = Red,
                    deleteIcon = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_trash),
                    iconTint = MaterialTheme.colorScheme.onError,
                    swipeToDismissBoxState = state
                )
            },
            content = { content(item) },
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = true,
            onDismiss = { value ->
                if (value == SwipeToDismissBoxValue.StartToEnd) {
                    coroutineScope.launch {
                        onEditAction(item)
                        delay(timeMillis = animationDuration.toLong())
                        state.snapTo(targetValue = SwipeToDismissBoxValue.Settled)
                    }
                } else if (value == SwipeToDismissBoxValue.EndToStart) {
                    coroutineScope.launch {
                        onDeleteAction(item)
                    }
                }
            }
        )
    }
}

@Composable
private fun ActionBackground(
    modifier: Modifier = Modifier,
    editBackgroundColor: Color,
    editIcon: ImageVector,
    deleteBackgroundColor: Color,
    deleteIcon: ImageVector,
    iconTint: Color,
    swipeToDismissBoxState: SwipeToDismissBoxState,
) {
    var editIconAlphaValue = 0f
    var deleteIconAlphaValue = 0f
    var backgroundColor = Color.Transparent

    if (swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
        backgroundColor = editBackgroundColor
        editIconAlphaValue = 1f
        deleteIconAlphaValue = 0f
    } else if (swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        backgroundColor = deleteBackgroundColor
        editIconAlphaValue = 0f
        deleteIconAlphaValue = 1f
    }

    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier.weight(weight = 1f).fillMaxHeight()
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        bottomStart = 8.dp
                    )
                ).graphicsLayer {
                    alpha = editIconAlphaValue
                }
                .padding(all = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                modifier = Modifier.size(size = 18.dp),
                imageVector = editIcon,
                contentDescription = null,
                tint = iconTint
            )
        }

        Box(
            modifier = Modifier.weight(weight = 1f).fillMaxHeight()
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                )
                .graphicsLayer {
                    alpha = deleteIconAlphaValue
                }
                .padding(all = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                modifier = Modifier.size(size = 18.dp),
                imageVector = deleteIcon,
                contentDescription = null,
                tint = iconTint
            )
        }
    }
}