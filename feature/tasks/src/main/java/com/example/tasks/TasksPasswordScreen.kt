package com.example.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.design_system.component.dialog.forgot_password.ForgotPasswordDialog
import com.example.design_system.theme.TodoTheme
import com.example.tasks.model.TasksPasswordProcessType
import com.example.tasks.utils.getTitleResId
import com.example.utils.randomNumberPadRows
import com.example.design_system.R as DesignSystemR

private const val PASSWORD_LENGTH = 6

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TasksPasswordScreen(
    modifier: Modifier = Modifier,
    tasksPasswordProcessType: TasksPasswordProcessType,
    exitApp: () -> Unit,
    onPasswordCheck: (String) -> Unit,
    onDeleteAllData: () -> Unit,
) {
    val numberPadRows = remember { randomNumberPadRows() }
    val inputPassword = remember { mutableStateListOf<String>() }

    var isShowForgotPasswordDialog by remember { mutableStateOf(value = false) }

    LaunchedEffect(key1 = tasksPasswordProcessType) {
        inputPassword.clear()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {},
                navigationIcon = {
                    IconButton(onClick = exitApp) {
                        Icon(
                            modifier = modifier.size(size = 24.dp),
                            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isShowForgotPasswordDialog) {
            ForgotPasswordDialog(
                onClose = { isShowForgotPasswordDialog = false },
                onConfirm = onDeleteAllData
            )
        }

        Column(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(weight = 0.3f).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = tasksPasswordProcessType.getTitleResId()),
                    style = TodoTheme.typography.bold_16,
                    color = MaterialTheme.colorScheme.onBackground,
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
                modifier = Modifier.weight(weight = 0.7f).fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
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
                                        onPasswordCheck(password)
                                    }
                                },
                                onDeleteClick = {
                                    inputPassword.removeLastOrNull()
                                }
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier.padding(all = 20.dp)
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                        shape = RoundedCornerShape(size = 16.dp),
                        onClick = { isShowForgotPasswordDialog = true }
                    ) {
                        Text(
                            modifier = Modifier.padding(all = 8.dp),
                            text = stringResource(id = DesignSystemR.string.forgot_password),
                            style = TodoTheme.typography.bold_16
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TasksPasswordScreenPreview() {
    TodoTheme {
        TasksPasswordScreen(
            tasksPasswordProcessType = TasksPasswordProcessType.ENTER_EXISTING_PASSWORD,
            exitApp = {},
            onPasswordCheck = { _ -> },
            onDeleteAllData = {},
        )
    }
}