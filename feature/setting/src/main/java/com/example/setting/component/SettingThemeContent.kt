package com.example.setting.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.model.ThemeType
import com.example.setting.model.ThemeColor
import com.example.design_system.R as DesignSystemR

@Composable
internal fun SettingThemeContent(
    modifier: Modifier = Modifier,
    themeType: ThemeType,
    onSelect: (ThemeType) -> Unit,
) {
    val scrollState = rememberScrollState()
    val isSystemInDarkTheme = isSystemInDarkTheme()

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 30.dp),
    ) {
        Text(
            text = stringResource(id = DesignSystemR.string.choose_theme_style),
            style = TodoTheme.typography.bold_18,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Row(
            modifier = Modifier.horizontalScroll(state = scrollState)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
        ) {
            ThemeType.entries.forEachIndexed { index, type ->
                if (index == ThemeType.entries.size - 1) return

                var themeColor = ThemeColor.entries[index]
                if (index == 0 && isSystemInDarkTheme)
                    themeColor = ThemeColor.DARK_SYSTEM_THEME
                SettingThemeItem(
                    title = type.title,
                    themeColor = themeColor,
                    isSelected = type == themeType,
                    onClick = { onSelect(type) }
                )
            }
        }
    }
}

@Composable
private fun SettingThemeItem(
    modifier: Modifier = Modifier,
    title: String,
    themeColor: ThemeColor,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 8.dp))
                .background(color = themeColor.backgroundColor)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 16.dp,
                ),
                text = title,
                style = TodoTheme.typography.medium_14,
                color = themeColor.textColor
            )
        }

        if (isSelected) {
            val animValue = remember {
                Animatable(initialValue = 0f)
            }

            LaunchedEffect(key1 = Unit) {
                animValue.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 300)
                )
            }

            Box(
                modifier = Modifier.width(width = 40.dp * animValue.value)
                    .height(height = 4.dp)
                    .background(
                        color = themeColor.dividerColor,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingThemeContentPreview() {
    TodoTheme {
        SettingThemeContent(
            themeType = ThemeType.SUN_RISE,
            onSelect = {}
        )
    }
}