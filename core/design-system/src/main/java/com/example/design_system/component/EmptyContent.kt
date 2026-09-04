package com.example.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier,
    title: String,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 24.dp)
        ) {
            Image(
                modifier = Modifier.size(size = 120.dp),
                painter = painterResource(id = R.drawable.svg_no_tasks),
                contentDescription = null,
            )
            Text(
                text = title,
                style = TodoTheme.typography.bold_20,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyContentPreview() {
    TodoTheme {
        EmptyContent(
            title = stringResource(id = R.string.no_tasks)
        )
    }
}