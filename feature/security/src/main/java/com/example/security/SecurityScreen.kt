package com.example.security

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SecurityScreen(
    modifier: Modifier = Modifier,
    hasExistingPassword: Boolean,
    hasBiometricEnabled: Boolean,
    popBackStack: () -> Unit,
    onBiometricEnabledChanged: (Boolean) -> Unit,
    onPasswordSettingClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {},
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = modifier.size(size = 24.dp),
                            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(vertical = 20.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                text = stringResource(
                    id = DesignSystemR.string.security
                ),
                style = TodoTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(height = 20.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable { onPasswordSettingClick() }
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(
                        id = DesignSystemR.string.password_setting
                    ),
                    style = TodoTheme.typography.infoDescTextStyle,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                val lockIconResId =
                    if (hasExistingPassword) DesignSystemR.drawable.svg_lock
                    else DesignSystemR.drawable.svg_unlock

                Icon(
                    modifier = Modifier.size(size = 18.dp),
                    imageVector = ImageVector.vectorResource(id = lockIconResId),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }

            if (hasExistingPassword) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .clickable { onBiometricEnabledChanged(!hasBiometricEnabled) }
                        .padding(all = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = DesignSystemR.string.biometric_authentication_setting),
                        style = TodoTheme.typography.infoDescTextStyle,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    val fingerPrintIconColor =
                        if (hasBiometricEnabled) MaterialTheme.colorScheme.onSecondaryContainer
                        else MaterialTheme.colorScheme.error
                    Icon(
                        modifier = Modifier.size(size = 18.dp),
                        imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_fingerprint),
                        contentDescription = null,
                        tint = fingerPrintIconColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SecurityScreenPreview() {
    TodoTheme {
        SecurityScreen(
            hasExistingPassword = true,
            hasBiometricEnabled = true,
            popBackStack = {},
            onBiometricEnabledChanged = { _ -> },
            onPasswordSettingClick = {}
        )
    }
}