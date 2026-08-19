package com.example.lock_setup

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.CircleIndicator
import com.example.design_system.component.NumberPadButton
import com.example.design_system.theme.TodoTheme
import com.example.model.LockSetupProcessType
import com.example.utils.randomNumberPadRows
import com.example.design_system.R as DesignSystemR

private const val PASSWORD_LENGTH = 6

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LockSetupScreen(
    modifier: Modifier = Modifier,
    lockSetupProcessType: LockSetupProcessType,
    popBackStack: () -> Unit,
    onPasswordCheck: (String) -> Unit,
    onNewInputPasswordCheck: (String) -> Unit,
    onPasswordUpdate: (String) -> Unit,
    onPasswordRemove: () -> Unit,
) {
    val numberPadRows = remember { randomNumberPadRows() }
    val inputPassword = remember { mutableStateListOf<String>() }

    LaunchedEffect(key1 = lockSetupProcessType) {
        inputPassword.clear()
    }

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
                },
                actions = {
                    IconButton(
                        onClick = onPasswordRemove
                    ) {
                        Icon(
                            modifier = modifier.size(size = 21.dp),
                            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_unlock),
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
                .padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(weight = 0.4f).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val title =
                    getTitle(lockSetupProcessType = lockSetupProcessType)
                Text(
                    text = title,
                    style = TodoTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(height = 20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp)
                ) {
                    repeat(times = PASSWORD_LENGTH) { index ->
                        CircleIndicator(
                            backgroundColor = if (index < inputPassword.size)
                                Color.Gray
                            else
                                Color.LightGray
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.weight(weight = 0.6f).fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                numberPadRows.forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        row.forEach { number ->
                            NumberPadButton(
                                title = number,
                                onNumberClick = { clickedNumber ->
                                    if (inputPassword.size < PASSWORD_LENGTH) {
                                        inputPassword.add(element = clickedNumber)
                                    }

                                    if (inputPassword.size == PASSWORD_LENGTH) {
                                        val password =
                                            inputPassword.joinToString(separator = "")
                                        when (lockSetupProcessType) {
                                            LockSetupProcessType.ENTER_EXISTING_PASSWORD,
                                            LockSetupProcessType.EXISTING_PASSWORD_MISMATCHED,
                                            LockSetupProcessType.UNLOCK_PASSWORD ->
                                                onPasswordCheck(password)

                                            LockSetupProcessType.ENTER_NEW_PASSWORD ->
                                                onNewInputPasswordCheck(password)

                                            LockSetupProcessType.CONFIRM_NEW_PASSWORD,
                                            LockSetupProcessType.CONFIRM_NEW_PASSWORD_MISMATCHED ->
                                                onPasswordUpdate(password)
                                        }
                                    }
                                },
                                onDeleteClick = {
                                    inputPassword.removeLastOrNull()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun getTitle(lockSetupProcessType: LockSetupProcessType): String {
    val resId = when (lockSetupProcessType) {
        LockSetupProcessType.ENTER_EXISTING_PASSWORD -> DesignSystemR.string.please_enter_your_current_password
        LockSetupProcessType.EXISTING_PASSWORD_MISMATCHED -> DesignSystemR.string.password_doesnt_match
        LockSetupProcessType.ENTER_NEW_PASSWORD -> DesignSystemR.string.please_enter_your_new_password
        LockSetupProcessType.CONFIRM_NEW_PASSWORD -> DesignSystemR.string.please_enter_your_password_once_more
        LockSetupProcessType.CONFIRM_NEW_PASSWORD_MISMATCHED -> DesignSystemR.string.password_doesnt_match_new_password
        LockSetupProcessType.UNLOCK_PASSWORD -> DesignSystemR.string.please_enter_your_current_password_to_unlock
    }
    return stringResource(id = resId)
}

@Preview(showBackground = true)
@Composable
private fun LockSetupScreenPreview() {
    TodoTheme {
        LockSetupScreen(
            lockSetupProcessType = LockSetupProcessType.ENTER_EXISTING_PASSWORD,
            popBackStack = {},
            onPasswordCheck = { _ -> },
            onNewInputPasswordCheck = { _ -> },
            onPasswordUpdate = { _ -> },
            onPasswordRemove = {}
        )
    }
}