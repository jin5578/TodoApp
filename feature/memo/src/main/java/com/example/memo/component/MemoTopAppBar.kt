package com.example.memo.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MemoTopAppBar(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {},
        navigationIcon = {
            IconButton(
                onClick = popBackStack
            ) {
                Icon(
                    modifier = modifier.size(size = 24.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_left),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun MemoTopAppBarPreview() {
    TodoTheme {
        MemoTopAppBar(
            popBackStack = {}
        )
    }
}