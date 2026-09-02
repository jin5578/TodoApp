package com.example.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MemoScreen(
    modifier: Modifier = Modifier,
    taskId: Long,
    title: String,
    content: String,
    updatedAt: LocalDateTime?,
    locale: Locale,
    popBackStack: (taskId: Long, memoTitle: String, memoContent: String) -> Unit,
) {
    var memoTitle by remember { mutableStateOf(value = title) }
    var memoContent by remember { mutableStateOf(value = content) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            popBackStack(taskId, memoTitle, memoContent)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(size = 24.dp),
                            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            MemoTextField(
                text = memoTitle,
                hintTextResId = DesignSystemR.string.title,
                textStyle = TodoTheme.typography.medium_18,
                onValueChange = { title -> memoTitle = title }
            )

            if (updatedAt != null) {
                val dateTimeFormat =
                    DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a", locale)
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(
                        id = DesignSystemR.string.last_update_time,
                        updatedAt.format(dateTimeFormat)
                    ),
                    style = TodoTheme.typography.medium_12,
                    color = MaterialTheme.colorScheme.surfaceDim,
                )
            }

            MemoTextField(
                text = memoContent,
                hintTextResId = DesignSystemR.string.content,
                textStyle = TodoTheme.typography.medium_14,
                onValueChange = { content -> memoContent = content }
            )
        }
    }
}

@Composable
private fun MemoTextField(
    modifier: Modifier = Modifier,
    text: String,
    hintTextResId: Int,
    textStyle: TextStyle,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            cursorColor = MaterialTheme.colorScheme.onBackground,
        ),
        textStyle = textStyle,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = stringResource(id = hintTextResId),
                color = MaterialTheme.colorScheme.onBackground,
                style = textStyle
            )
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun MemoScreenPreview() {
    TodoTheme {
        MemoScreen(
            taskId = -1L,
            title = "",
            content = "",
            updatedAt = LocalDateTime.now(),
            locale = Locale.KOREA,
            popBackStack = { _, _, _ -> }
        )
    }
}