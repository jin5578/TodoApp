package com.example.design_system.component.dialog.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.design_system.theme.TodoTheme
import com.example.design_system.R as DesignSystemR

@Composable
fun ForgotPasswordDialog(
    onClose: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = onClose
    ) {
        ForgotPasswordDialogContent(
            onClose = onClose,
            onConfirm = onConfirm
        )
    }
}

@Composable
private fun ForgotPasswordDialogContent(
    onClose: () -> Unit,
    onConfirm: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(fraction = 1f),
        shape = RoundedCornerShape(size = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        )
    ) {
        Column(
            modifier = Modifier.padding(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(space = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = DesignSystemR.string.forgot_password_dialog_title),
                style = TodoTheme.typography.bold_20,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                text = stringResource(id = DesignSystemR.string.forgot_password_dialog_message),
                style = TodoTheme.typography.medium_16,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space = 10.dp)
            ) {
                ForgotPasswordTextButton(
                    modifier = Modifier.weight(weight = 1f),
                    backgroundColor = MaterialTheme.colorScheme.tertiary,
                    textColor = MaterialTheme.colorScheme.onTertiary,
                    title = stringResource(id = DesignSystemR.string.no),
                    onClick = onClose
                )

                ForgotPasswordTextButton(
                    modifier = Modifier.weight(weight = 1f),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    title = stringResource(id = DesignSystemR.string.yes),
                    onClick = onConfirm
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordDialogPreview() {
    TodoTheme {
        ForgotPasswordDialog(
            onClose = {},
            onConfirm = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordDialogContentPreview() {
    TodoTheme {
        ForgotPasswordDialogContent(
            onClose = {},
            onConfirm = {}
        )
    }
}