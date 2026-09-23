package com.example.edit_task.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.design_system.component.CategoryDropdownMenu
import com.example.design_system.theme.TodoTheme
import com.example.model.Category
import kotlinx.collections.immutable.ImmutableList
import com.example.design_system.R as DesignSystemR

@Composable
internal fun EditTaskCategoryChip(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    taskCategoryId: Long,
    isShowCategoryMenu: Boolean,
    onOpenClick: () -> Unit,
    onCloseClick: () -> Unit,
    onCategoryClick: (Long) -> Unit,
    onCreateNewCategoryClick: () -> Unit,
) {
    Box {
        Box(
            modifier = modifier.wrapContentSize()
                .padding(start = 16.dp)
                .clip(shape = RoundedCornerShape(size = 16.dp))
                .clickable {
                    onOpenClick()
                }
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                )
                .padding(all = 8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
            ) {
                Text(
                    text = categories.firstOrNull { it.id == taskCategoryId }?.title
                        ?: stringResource(id = DesignSystemR.string.no_category),
                    style = TodoTheme.typography.medium_12,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Icon(
                    modifier = Modifier.size(size = 8.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_down),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        CategoryDropdownMenu(
            categories = categories,
            isShowCategoryMenu = isShowCategoryMenu,
            onCloseClick = onCloseClick,
            onCategoryClick = onCategoryClick,
            onCreateNewCategoryClick = onCreateNewCategoryClick,
        )
    }
}
