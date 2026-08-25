package com.example.setting.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.model.LanguageType
import com.example.design_system.R as DesignSystemR

@Composable
internal fun SettingLanguageContent(
    modifier: Modifier = Modifier,
    languageType: LanguageType,
    onSelect: (LanguageType) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 30.dp)
    ) {
        Text(
            text = stringResource(id = DesignSystemR.string.choose_language),
            style = TodoTheme.typography.bold_18,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            LanguageType.entries.forEachIndexed { index, type ->
                SettingLanguageItem(
                    modifier = Modifier.weight(weight = 1f),
                    languageType = type,
                    isSelected = languageType == type,
                    onClick = { onSelect(type) }
                )
                if (index == 0)
                    Spacer(modifier = Modifier.width(width = 10.dp))
            }
        }
    }
}

@Composable
private fun SettingLanguageItem(
    modifier: Modifier = Modifier,
    languageType: LanguageType,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 8.dp))
                .background(
                    color =
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surface
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 16.dp,
                ),
                text = languageType.title,
                style = TodoTheme.typography.medium_14,
                color =
                    if (isSelected) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurface
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
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(size = 8.dp),
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingLanguageContentPreview() {
    TodoTheme {
        SettingLanguageContent(
            languageType = LanguageType.KOREAN,
            onSelect = {}
        )
    }
}