package com.example.manage_categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.model.CategoryColorType
import com.example.design_system.R as DesignSystemR

@Composable
internal fun CategoryColorItem(
    modifier: Modifier = Modifier,
    type: CategoryColorType,
    isSelected: Boolean,
    onSelect: (CategoryColorType) -> Unit,
) {
    Box(
        modifier = modifier.size(size = 40.dp)
            .clip(shape = CircleShape)
            .background(color = Color(color = type.colorValue))
            .clickable { onSelect(type) },
        contentAlignment = Alignment.Center,
        content = {
            if (isSelected) {
                val colorName = type.colorName
                val tint =
                    if (colorName == "Blue" || colorName == "Indigo" || colorName == "Violet") {
                        Color.White
                    } else {
                        Color.Black
                    }
                Icon(
                    modifier = Modifier.size(size = 14.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_check),
                    contentDescription = null,
                    tint = tint
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CategoryColorItemPreview() {
    TodoTheme {
        CategoryColorItem(
            type = CategoryColorType.RED,
            isSelected = true,
            onSelect = {}
        )
    }
}