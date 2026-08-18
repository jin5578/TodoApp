package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme

@Composable
fun CircleIndicator(
    modifier: Modifier = Modifier,
    backgroundColor: Color
) {
    Box(
        modifier = modifier.size(size = 14.dp)
            .clip(shape = CircleShape)
            .background(color = backgroundColor)
    )
}

@Preview(showBackground = true)
@Composable
private fun CircleIndicatorPreview() {
    TodoTheme {
        CircleIndicator(
            backgroundColor = Color.LightGray
        )
    }
}