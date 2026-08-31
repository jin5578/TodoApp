package com.example.home.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.design_system.R as DesignSystemR

@Composable
internal fun HomeFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        onClick = onClick
    ) {
        Icon(
            modifier = modifier.size(size = 32.dp),
            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_plus_small),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeFloatingActionButtonPreview() {
    TodoTheme {
        HomeFloatingActionButton(
            onClick = {}
        )
    }
}