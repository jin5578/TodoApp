package com.example.search_task

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.design_system.R as DesignSystemR

@Composable
internal fun SearchTaskScreen(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
) {
    var searchedText by remember { mutableStateOf(value = "") }

    Scaffold(
        topBar = {
            SearchTaskTopAppBar(
                text = searchedText,
                onValueChange = { text -> searchedText = text },
                popBackStack = popBackStack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {

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
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
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
                textStyle = TodoTheme.typography.medium_16,
                onValueChange = { onValueChange(it) },
                placeholder = {
                    Text(
                        text = stringResource(id = DesignSystemR.string.select),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = TodoTheme.typography.medium_16
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
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
            popBackStack = {}
        )
    }
}