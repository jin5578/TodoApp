package com.example.design_system.component.dialog.forgot_password

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme

@Composable
fun ForgotPasswordTextButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color,
    title: String,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(size = 8.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = backgroundColor
        )
    ) {
        Text(
            text = title,
            style = TodoTheme.typography.bold_14,
            color = textColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordTextButtonPreview() {
    TodoTheme {
        ForgotPasswordTextButton(
            backgroundColor = MaterialTheme.colorScheme.error,
            textColor = MaterialTheme.colorScheme.onError,
            title = "예",
            onClick = {}
        )
    }
}