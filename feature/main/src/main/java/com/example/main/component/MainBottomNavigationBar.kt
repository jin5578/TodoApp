package com.example.main.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.main.model.MainTab

@Composable
internal fun MainBottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedTab: MainTab,
    onTabClick: (MainTab) -> Unit,
) {
    NavigationBar(
        modifier = modifier
            .navigationBarsPadding()
            .shadow(elevation = 10.dp)
            .height(height = 64.dp),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
        windowInsets = WindowInsets(left = 0, top = 0, right = 0, bottom = 0),
    ) {
        MainTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = tab == selectedTab,
                onClick = { onTabClick(tab) },
                icon = {
                    Icon(
                        modifier = Modifier.size(size = 18.dp),
                        imageVector = ImageVector.vectorResource(id = tab.iconResId),
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = tab.titleResId),
                        style = TodoTheme.typography.medium_12,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                    unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainBottomNavigationBarPreview() {
    TodoTheme {
        MainBottomNavigationBar(
            selectedTab = MainTab.TASKS,
            onTabClick = {},
        )
    }
}