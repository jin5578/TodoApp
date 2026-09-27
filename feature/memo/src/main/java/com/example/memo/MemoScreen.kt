package com.example.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designSystem.theme.TodoTheme
import com.example.memo.component.MemoTextField
import com.example.memo.component.MemoTopAppBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.example.designSystem.R as DesignSystemR

@Composable
internal fun MemoScreen(
    modifier: Modifier = Modifier,
    taskId: Long,
    title: String,
    content: String,
    updatedAt: LocalDateTime?,
    locale: Locale,
    onTitleValueChanged: (taskId: Long, title: String) -> Unit,
    onContentValueChanged: (taskId: Long, content: String) -> Unit,
    popBackStack: () -> Unit,
) {
    var memoTitle by remember { mutableStateOf(value = title) }
    var memoContent by remember { mutableStateOf(value = content) }

    Scaffold(
        topBar = {
            MemoTopAppBar(popBackStack = popBackStack)
        },
    ) { paddingValues ->
        Column(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            MemoTextField(
                text = memoTitle,
                hintTextResId = DesignSystemR.string.title,
                textStyle = TodoTheme.typography.medium_20,
                onValueChange = { title ->
                    memoTitle = title
                    onTitleValueChanged(taskId, title)
                },
            )

            if (updatedAt != null) {
                val dateTimeFormat =
                    DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a", locale)
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(
                        id = DesignSystemR.string.last_update_time,
                        updatedAt.format(dateTimeFormat),
                    ),
                    style = TodoTheme.typography.medium_12,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            MemoTextField(
                text = memoContent,
                hintTextResId = DesignSystemR.string.content,
                textStyle = TodoTheme.typography.medium_16,
                onValueChange = { content ->
                    memoContent = content
                    onContentValueChanged(taskId, content)
                },
            )
        }
    }
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
            onTitleValueChanged = { _, _ -> },
            onContentValueChanged = { _, _ -> },
            popBackStack = { },
        )
    }
}
