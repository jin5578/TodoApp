package com.example.home.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    categories: ImmutableList<Category>,
    navigateSetting: () -> Unit,
    onCategoryClick: (Long) -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            if (categories.isEmpty()) {
                Text(
                    text = stringResource(id = DesignSystemR.string.app_name),
                    style = TodoTheme.typography.bold_20,
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                Row(
                    modifier = modifier.fillMaxWidth()
                        .padding(end = 4.dp)
                        .horizontalScroll(state = scrollState),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp)
                ) {
                    CategoryItem(
                        id = -1L,
                        title = stringResource(id = DesignSystemR.string.entire),
                        onClick = onCategoryClick
                    )
                    categories.forEach { category ->
                        CategoryItem(
                            id = category.id,
                            title = category.title,
                            onClick = onCategoryClick
                        )
                    }
                }
            }
        },
        actions = {
            IconButton(
                onClick = navigateSetting
            ) {
                Icon(
                    modifier = modifier.size(size = 24.dp),
                    imageVector = ImageVector.vectorResource(
                        id = DesignSystemR.drawable.svg_setting
                    ),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    )
}

@Composable
private fun CategoryItem(
    id: Long,
    title: String,
    onClick: (Long) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
            .clickable { onClick(id) },
    ) {
        Text(
            text = title,
            style = TodoTheme.typography.bold_16,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTopAppBarPreview() {
    TodoTheme {
        HomeTopAppBar(
            scrollState = rememberScrollState(),
            categories = persistentListOf(),
            navigateSetting = {},
            onCategoryClick = {}
        )
    }
}