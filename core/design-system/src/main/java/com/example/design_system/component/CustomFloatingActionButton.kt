package com.example.design_system.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme

@Composable
fun CustomFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "fab_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2500,
                easing = LinearOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "fab_pulse_scale",
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2500,
                easing = LinearOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "fab_pulse_alpha",
    )

    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .scale(scale = pulseScale)
                .alpha(alpha = pulseAlpha)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape,
                ),
        )

        FloatingActionButton(
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primaryContainer,
            onClick = onClick,
        ) {
            Icon(
                modifier = modifier.size(size = 32.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.svg_plus_small),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomFloatingActionButtonPreview() {
    TodoTheme {
        CustomFloatingActionButton(
            onClick = {},
        )
    }
}
