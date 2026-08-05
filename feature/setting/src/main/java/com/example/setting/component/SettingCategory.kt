package com.example.setting.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.setting.model.CategoryItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import com.example.design_system.R as DesignSystemR

@Composable
internal fun SettingCategory(
    modifier: Modifier = Modifier,
    @StringRes titleResId: Int,
    category: ImmutableList<CategoryItemUiState>
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(id = titleResId),
            style = TodoTheme.typography.infoDescTextStyle,
            color = MaterialTheme.colorScheme.onSurface
        )

        Column {
            category.forEachIndexed { index, state ->
                SettingCategoryItem(
                    titleResId = state.titleResId,
                    iconResId = state.iconResId,
                    onClick = state.onClick
                )
                if (index != category.lastIndex)
                    Spacer(modifier = Modifier.height(height = 4.dp))
            }
        }
    }
}

@Composable
private fun SettingCategoryItem(
    modifier: Modifier = Modifier,
    @StringRes titleResId: Int,
    @DrawableRes iconResId: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clickable { onClick() }
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp)
    ) {
        Icon(
            modifier = Modifier.size(size = 18.dp),
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        Text(
            modifier = Modifier.weight(weight = 1f),
            text = stringResource(id = titleResId),
            style = TodoTheme.typography.infoTextStyle,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        Icon(
            modifier = Modifier.size(size = 18.dp),
            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_right_twin),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingCategoryPreview() {
    val categoryItemUiStates = persistentListOf(
        CategoryItemUiState(
            titleResId = DesignSystemR.string.about,
            iconResId = DesignSystemR.drawable.svg_information,
            onClick = {}
        ),
        CategoryItemUiState(
            titleResId = DesignSystemR.string.github,
            iconResId = DesignSystemR.drawable.svg_github,
            onClick = {}
        )
    )
    TodoTheme {
        SettingCategory(
            titleResId = DesignSystemR.string.info,
            category = categoryItemUiStates
        )
    }
}