package com.example.design_system.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun CategoryDropdownMenu(
    categories: ImmutableList<Category>,
    isShowCategoryMenu: Boolean,
    onCloseClick: () -> Unit,
    onCategoryClick: (Long) -> Unit,
    onCreateNewCategoryClick: () -> Unit,
) {
    DropdownMenu(
        containerColor = MaterialTheme.colorScheme.background,
        expanded = isShowCategoryMenu,
        onDismissRequest = onCloseClick
    ) {
        categories.forEach { category ->
            BasicDropdownMenuItem(
                title = category.title,
                onClick = { onCategoryClick(category.id) }
            )
        }
        DropdownMenuItem(
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(size = 12.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.svg_plus_small),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = stringResource(id = R.string.create_new),
                        style = TodoTheme.typography.medium_12,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            onClick = onCreateNewCategoryClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryDropdownMenuPreview() {
    TodoTheme {
        CategoryDropdownMenu(
            categories = persistentListOf(),
            isShowCategoryMenu = false,
            onCloseClick = {},
            onCategoryClick = {},
            onCreateNewCategoryClick = {}
        )
    }
}
