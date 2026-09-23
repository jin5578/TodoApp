package com.example.search_task

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.EmptyContent
import com.example.design_system.component.TaskCard
import com.example.design_system.theme.TodoTheme
import com.example.model.TaskUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@Composable
internal fun SearchTaskScreen(
    modifier: Modifier = Modifier,
    tasks: ImmutableList<TaskUiModel>,
    locale: Locale,
    onKeywordChanged: (String) -> Unit,
    onTaskToggleClick: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskEditClick: (Long) -> Unit,
    onDeleteSymbolClick: (Long) -> Unit,
    onSymbolClick: (taskId: Long, symbolId: Int) -> Unit,
    onSubTaskToggleClick: (subTaskId: Long, isCompleted: Boolean) -> Unit,
    popBackStack: () -> Unit,
) {
    var searchedText by remember { mutableStateOf(value = "") }

    Scaffold(
        topBar = {
            SearchTaskTopAppBar(
                modifier = modifier,
                text = searchedText,
                onValueChange = { text ->
                    searchedText = text
                    onKeywordChanged(text)
                },
                popBackStack = popBackStack
            )
        }
    ) { paddingValues ->
        if (tasks.isEmpty())
            EmptyContent(
                modifier = modifier.padding(paddingValues = paddingValues),
                title = stringResource(id = DesignSystemR.string.no_tasks)
            )
        else
            LazyColumn(
                modifier = modifier.fillMaxSize()
                    .padding(paddingValues = paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(
                    items = tasks,
                    key = { index, task -> task.id }
                ) { index, task ->
                    val bottomPadding =
                        if (index != tasks.size - 1) 8.dp
                        else 0.dp
                    TaskCard(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = bottomPadding
                            ),
                        task = task,
                        locale = locale,
                        onTaskToggleClick = onTaskToggleClick,
                        onTaskEditClick = onTaskEditClick,
                        onDeleteSymbolClick = onDeleteSymbolClick,
                        onSymbolClick = onSymbolClick,
                        onSubTaskToggleClick = onSubTaskToggleClick
                    )
                }
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTaskTopAppBar(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    popBackStack: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            val interactionSource = remember { MutableInteractionSource() }
            BasicTextField(
                modifier = modifier.fillMaxWidth()
                    .focusRequester(focusRequester = focusRequester),
                value = text,
                onValueChange = { onValueChange(it) },
                textStyle = TodoTheme.typography.medium_16.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                interactionSource = interactionSource,
                decorationBox = { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = text,
                        innerTextField = innerTextField,
                        enabled = true,
                        singleLine = false,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        placeholder = {
                            Text(
                                text = stringResource(id = DesignSystemR.string.search),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = TodoTheme.typography.medium_16
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            cursorColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        contentPadding = PaddingValues(0.dp),
                    )
                }
            )
        },
        navigationIcon = {
            IconButton(onClick = popBackStack) {
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

@Preview(showBackground = true)
@Composable
private fun SearchTaskScreenPreview() {
    TodoTheme {
        SearchTaskScreen(
            tasks = persistentListOf(),
            locale = Locale.KOREA,
            onKeywordChanged = {},
            onTaskToggleClick = { _, _ -> },
            onTaskEditClick = {},
            onDeleteSymbolClick = {},
            onSymbolClick = { _, _ -> },
            onSubTaskToggleClick = { _, _ -> },
            popBackStack = {}
        )
    }
}