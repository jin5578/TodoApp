package com.example.manage_categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.theme.TodoTheme
import com.example.model.CategoryColorType
import com.example.design_system.R as DesignSystemR

@Composable
internal fun CategoryCard(
    modifier: Modifier = Modifier,
    id: Long,
    title: String,
    type: CategoryColorType,
    onEditClick: (id: Long, title: String, type: CategoryColorType) -> Unit,
    onDeleteClick: (id: Long) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clickable { onEditClick(id, title, type) }
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp)
    ) {
        Box(
            modifier = Modifier.size(size = 20.dp)
                .background(
                    color = Color(color = type.colorValue),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center,
            content = {}
        )

        Text(
            modifier = Modifier.weight(weight = 1f),
            text = title,
            style = TodoTheme.typography.infoTextStyle.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onSurface
        )

        IconButton(
            modifier = Modifier.size(size = 20.dp),
            onClick = { onDeleteClick(id) },
        ) {
            Icon(
                modifier = Modifier.size(size = 21.dp),
                imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_cancel_small),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryCardPreview() {
    TodoTheme {
        CategoryCard(
            id = 0L,
            title = "Red",
            type = CategoryColorType.RED,
            onEditClick = { _, _, _ -> },
            onDeleteClick = {}
        )
    }
}