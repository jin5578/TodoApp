package com.example.editTask.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designSystem.R
import com.example.designSystem.theme.TodoTheme

@Composable
internal fun EditTaskTitleTextField(
    modifier: Modifier = Modifier,
    title: String,
    isCompleted: Boolean,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 16.dp),
        value = title,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            cursorColor = MaterialTheme.colorScheme.onBackground,
        ),
        textStyle = TodoTheme.typography.bold_20.copy(
            textDecoration = if (isCompleted) TextDecoration.LineThrough else null,
        ),
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = stringResource(id = R.string.please_enter_what_you_need_to_do),
                color = MaterialTheme.colorScheme.onBackground,
                style = TodoTheme.typography.bold_20,
            )
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun EditTaskTitleTextFieldPreview() {
    TodoTheme {
        EditTaskTitleTextField(
            title = "Title",
            isCompleted = true,
            onValueChange = {},
        )
    }
}
