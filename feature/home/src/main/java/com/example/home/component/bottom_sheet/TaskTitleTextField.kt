package com.example.home.component.bottom_sheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.design_system.R as DesignSystemR

@Composable
internal fun TaskTitleTextField(
    modifier: Modifier = Modifier,
    taskTitle: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = Modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 8.dp)),
        value = taskTitle,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            cursorColor = MaterialTheme.colorScheme.onBackground,
        ),
        textStyle = TodoTheme.typography.medium_16,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = stringResource(id = DesignSystemR.string.please_enter_what_you_need_to_do),
                color = MaterialTheme.colorScheme.onBackground,
                style = TodoTheme.typography.medium_16,
            )
        },
        shape = RoundedCornerShape(size = 8.dp),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskTitleTextFieldPreview() {
    TodoTheme {
        TaskTitleTextField(
            taskTitle = "Title",
            onValueChange = {}
        )
    }
}