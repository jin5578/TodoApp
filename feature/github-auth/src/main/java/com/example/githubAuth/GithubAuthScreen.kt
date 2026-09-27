package com.example.githubAuth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designSystem.theme.TodoTheme
import com.example.githubAuth.model.GithubAuthErrorReason
import com.example.githubAuth.model.GithubAuthUiState
import com.example.designSystem.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GithubAuthScreen(
    modifier: Modifier = Modifier,
    uiState: GithubAuthUiState,
    onOpenGithubClick: (String) -> Unit,
    onDisconnectClick: () -> Unit,
    onRetryClick: () -> Unit,
    popBackStack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    Text(
                        text = stringResource(
                            id = DesignSystemR.string.github_auth_title,
                        ),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(size = 24.dp),
                            imageVector = ImageVector.vectorResource(
                                id = DesignSystemR.drawable.svg_arrow_left,
                            ),
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
                .padding(all = 24.dp),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (uiState) {
                is GithubAuthUiState.Loading -> {
                    Text(
                        text = stringResource(
                            id = DesignSystemR.string.github_auth_waiting,
                        ),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                is GithubAuthUiState.Connected -> {
                    Text(
                        text = stringResource(
                            id = DesignSystemR.string.github_auth_connected_as,
                            uiState.username,
                        ),
                        style = TodoTheme.typography.bold_20,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                    Button(onClick = onDisconnectClick) {
                        Text(
                            text = stringResource(
                                id = DesignSystemR.string.github_auth_disconnect,
                            ),
                        )
                    }
                }

                is GithubAuthUiState.AwaitingUser -> {
                    Text(
                        text = stringResource(
                            id = DesignSystemR.string.github_auth_code_description,
                        ),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = uiState.userCode,
                        style = TodoTheme.typography.bold_20,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                    Button(
                        onClick = { onOpenGithubClick(uiState.verificationUri) },
                    ) {
                        Text(
                            text = stringResource(
                                id = DesignSystemR.string.github_auth_open_button,
                            ),
                        )
                    }

                    if (uiState.isPolling) {
                        Text(
                            text = stringResource(
                                id = DesignSystemR.string.github_auth_waiting,
                            ),
                            style = TodoTheme.typography.medium_12,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }

                is GithubAuthUiState.Success -> {
                    Text(
                        text = stringResource(
                            id = DesignSystemR.string.github_auth_success,
                        ),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                is GithubAuthUiState.Error -> {
                    val messageResId = when (uiState.reason) {
                        GithubAuthErrorReason.EXPIRED ->
                            DesignSystemR.string.github_auth_expired

                        GithubAuthErrorReason.DENIED ->
                            DesignSystemR.string.github_auth_denied

                        GithubAuthErrorReason.NETWORK ->
                            DesignSystemR.string.error_message_network

                        GithubAuthErrorReason.UNKNOWN ->
                            DesignSystemR.string.github_auth_unknown_error
                    }
                    Text(
                        text = stringResource(id = messageResId),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                    Button(onClick = onRetryClick) {
                        Text(
                            text = stringResource(
                                id = DesignSystemR.string.github_auth_retry,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GithubAuthScreenPreview() {
    TodoTheme {
        val uiState = GithubAuthUiState.Connected(username = "username")
        GithubAuthScreen(
            uiState = uiState,
            onOpenGithubClick = {},
            onDisconnectClick = {},
            onRetryClick = {},
            popBackStack = {},
        )
    }
}
