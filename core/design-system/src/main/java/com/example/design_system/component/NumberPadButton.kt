package com.example.design_system.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme

@Composable
fun NumberPadButton(
    modifier: Modifier = Modifier,
    title: String,
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(size = 72.dp)
            .clip(shape = CircleShape)
            .then(
                other = when {
                    title.toIntOrNull() != null -> Modifier.clickable {
                        onNumberClick(title)
                    }

                    title.isNotEmpty() -> Modifier.clickable {
                        onDeleteClick()
                    }

                    else -> Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                style = TodoTheme.typography.bold_20,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NumberPadButtonPreview() {
    TodoTheme {
        NumberPadButton(
            title = "1",
            onNumberClick = {},
            onDeleteClick = {}
        )
    }
}