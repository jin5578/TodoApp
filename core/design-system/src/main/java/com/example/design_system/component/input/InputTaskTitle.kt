package com.example.design_system.component.input

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.design_system.theme.priorityColors

@Composable
fun InputTaskTitle(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    backgroundColor: Color,
    title: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(space = 20.dp),
    ) {
        Text(
            text = stringResource(id = R.string.title),
            style = TodoTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        TextField(
            modifier = Modifier.fillMaxWidth()
                .focusRequester(focusRequester)
                .border(
                    width = 2.dp,
                    color = backgroundColor,
                    shape = RoundedCornerShape(size = 8.dp)
                ),
            value = title,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedIndicatorColor = Color.Transparent,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                cursorColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
            textStyle = TodoTheme.typography.taskTextStyle,
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.please_enter_what_you_need_to_do),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = TodoTheme.typography.taskTextStyle
                )
            },
            shape = RoundedCornerShape(size = 8.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputTaskTitlePreview() {
    TodoTheme {
        InputTaskTitle(
            focusRequester = remember { FocusRequester() },
            backgroundColor = priorityColors[0],
            title = "Task",
            onValueChange = {}
        )
    }
}