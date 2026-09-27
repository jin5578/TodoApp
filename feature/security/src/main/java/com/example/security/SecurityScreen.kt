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
import com.example.designSystem.theme.TodoTheme
import com.example.designSystem.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SecurityScreen(
    modifier: Modifier = Modifier,
    hasExistingPassword: Boolean,
    hasBiometricEnabled: Boolean,
    onBiometricEnabledChanged: (Boolean) -> Unit,
    onPasswordSettingClick: () -> Unit,
    popBackStack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                title = {},
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = modifier.size(size = 24.dp),
                            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(top = 40.dp, bottom = 20.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                text = stringResource(
                    id = DesignSystemR.string.security,
                ),
                style = TodoTheme.typography.bold_20,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(height = 40.dp))

            val lockIconResId =
                if (hasExistingPassword) {
                    DesignSystemR.drawable.svg_lock
                } else {
                    DesignSystemR.drawable.svg_unlock
                }
            SecurityItem(
                iconResId = lockIconResId,
                iconColor = MaterialTheme.colorScheme.onBackground,
                title = stringResource(
                    id = DesignSystemR.string.password_setting,
                ),
                onClick = { onPasswordSettingClick() },
            )

            if (hasExistingPassword) {
                val fingerPrintIconColor =
                    if (hasBiometricEnabled) {
                        MaterialTheme.colorScheme.onBackground
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                SecurityItem(
                    iconResId = DesignSystemR.drawable.svg_fingerprint,
                    iconColor = fingerPrintIconColor,
                    title = stringResource(id = DesignSystemR.string.biometric_authentication_setting),
                    onClick = { onBiometricEnabledChanged(!hasBiometricEnabled) },
                )
            }
        }
    }
}

@Composable
private fun SecurityItem(
    iconResId: Int,
    iconColor: Color,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
    ) {
        Icon(
            modifier = Modifier.size(size = 18.dp),
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            tint = iconColor,
        )

        Text(
            text = title,
            style = TodoTheme.typography.medium_16,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SecurityScreenPreview() {
    TodoTheme {
        SecurityScreen(
            hasExistingPassword = true,
            hasBiometricEnabled = true,
            onBiometricEnabledChanged = { _ -> },
            onPasswordSettingClick = {},
            popBackStack = {},
        )
    }
}
