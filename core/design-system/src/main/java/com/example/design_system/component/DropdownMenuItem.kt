package com.example.design_system.component

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.TodoTheme

@Composable
fun BasicDropdownMenuItem(
    title: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Text(
                text = title,
                style = TodoTheme.typography.medium_12,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
private fun BasicDropdownMenuItemPreview() {
    TodoTheme {
        BasicDropdownMenuItem(
            title = "Title",
            onClick = {}
        )
    }
}