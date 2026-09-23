package com.example.design_system.component.dialog.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.model.CategoryColorType
import com.example.design_system.R as DesignSystemR

private const val COLORS_PER_ROW = 7

@Composable
fun CategoryDialog(
    titleResId: Int,
    id: Long = 0L,
    categoryTitle: String = "",
    categoryColorType: CategoryColorType = CategoryColorType.entries.first(),
    onCloseClick: () -> Unit,
    onSaveClick: ((title: String, type: CategoryColorType) -> Unit)? = null,
    onUpdateClick: ((id: Long, title: String, type: CategoryColorType) -> Unit)? = null,
) {
    val focusRequester = remember { FocusRequester() }

    var categoryTitle by remember { mutableStateOf(value = categoryTitle) }
    var selectedColorType by remember { mutableStateOf(value = categoryColorType) }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Dialog(
        onDismissRequest = { onCloseClick() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(size = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 18.dp,
                    vertical = 16.dp,
                ),
            ) {
                Text(
                    text = stringResource(id = titleResId),
                    style = TodoTheme.typography.bold_20,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(height = 16.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth()
                        .focusRequester(focusRequester = focusRequester),
                    value = categoryTitle,
                    singleLine = true,
                    shape = RoundedCornerShape(size = 8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        cursorColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    textStyle = TodoTheme.typography.medium_12,
                    onValueChange = { categoryTitle = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.input_here),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = TodoTheme.typography.medium_12,
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done,
                    ),
                )

                Spacer(modifier = Modifier.height(height = 16.dp))

                Text(
                    text = stringResource(id = DesignSystemR.string.category_color),
                    style = TodoTheme.typography.medium_12,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = DesignSystemR.string.category_color_explanation),
                    style = TodoTheme.typography.regular_08,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(height = 16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                ) {
                    CategoryColorType.entries.chunked(size = COLORS_PER_ROW)
                        .forEach { rowColorTypes ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                rowColorTypes.forEach { type ->
                                    CategoryColorItem(
                                        type = type,
                                        isSelected = selectedColorType == type,
                                        onSelect = { selectedColorType = it },
                                    )
                                }
                            }
                        }
                }

                Spacer(modifier = Modifier.height(height = 16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            onCloseClick()
                        }.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp,
                        ),
                        text = stringResource(id = DesignSystemR.string.cancel),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.inversePrimary,
                    )

                    Text(
                        modifier = Modifier.clickable {
                            if (categoryTitle.trim().isEmpty()) return@clickable

                            if (id == 0L) {
                                onSaveClick?.let {
                                    it(
                                        categoryTitle,
                                        selectedColorType,
                                    )
                                }
                            } else {
                                onUpdateClick?.let {
                                    it(
                                        id,
                                        categoryTitle,
                                        selectedColorType,
                                    )
                                }
                            }
                        }.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp,
                        ),
                        text = stringResource(id = DesignSystemR.string.save),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryColorItem(
    modifier: Modifier = Modifier,
    type: CategoryColorType,
    isSelected: Boolean,
    onSelect: (CategoryColorType) -> Unit,
) {
    Box(
        modifier = modifier.size(size = 32.dp)
            .clip(shape = CircleShape)
            .background(
                color = Color(color = type.colorValue),
            )
            .clickable { onSelect(type) },
        contentAlignment = Alignment.Center,
        content = {
            if (isSelected) {
                Icon(
                    modifier = Modifier.size(size = 12.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.svg_check),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun CategoryDialogPreview() {
    TodoTheme {
        CategoryDialog(
            titleResId = DesignSystemR.string.create_new_category,
            onCloseClick = {},
            onSaveClick = { _, _ -> },
            onUpdateClick = { _, _, _ -> },
        )
    }
}
