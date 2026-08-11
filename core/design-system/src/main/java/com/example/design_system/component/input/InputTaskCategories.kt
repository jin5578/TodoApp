package com.example.design_system.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.Red
import com.example.design_system.theme.TodoTheme
import com.example.model.Category
import com.example.model.CategoryColorType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun InputTaskCategories(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    categoryId: Long,
    onSelectClick: (Long) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = 20.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = stringResource(id = R.string.category),
            style = TodoTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Row(
            modifier = Modifier.horizontalScroll(state = scrollState)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
        ) {
            categories.forEachIndexed { _, category ->
                val categoryColorType =
                    CategoryColorType.entries.filter { it.colorValue == category.colorValue }
                        .getOrNull(index = 0) ?: CategoryColorType.RED
                CategoryItem(
                    title = category.title,
                    isSelected = category.id == categoryId,
                    categoryColorType = categoryColorType,
                    onSelectClick = { onSelectClick(category.id) }
                )
            }
        }
    }
}

@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    categoryColorType: CategoryColorType,
    onSelectClick: () -> Unit,
) {
    val bgColor =
        if (isSelected)
            MaterialTheme.colorScheme.secondaryContainer
        else
            MaterialTheme.colorScheme.surface

    Row(
        modifier = modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(size = 8.dp),
            )
            .background(color = bgColor)
            .clickable { onSelectClick() }
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        Box(
            modifier = Modifier.size(size = 10.dp)
                .background(
                    color = androidx.compose.ui.graphics.Color(color = categoryColorType.colorValue),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center,
            content = {}
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style =
                if (isSelected)
                    TodoTheme.typography.infoDescTextStyle.copy(fontWeight = FontWeight.Bold)
                else
                    TodoTheme.typography.infoDescTextStyle
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputTaskCategoriesPreview() {
    TodoTheme {
        val categories = persistentListOf(
            Category(
                title = "solum",
                colorValue = Red.value.toLong()
            )
        )
        InputTaskCategories(
            categories = categories,
            categoryId = -1L,
            onSelectClick = {},
        )
    }
}