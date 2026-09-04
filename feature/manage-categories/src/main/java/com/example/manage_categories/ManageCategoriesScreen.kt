package com.example.manage_categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.component.EmptyContent
import com.example.design_system.theme.TodoTheme
import com.example.manage_categories.component.AddCategoryBottomSheetContent
import com.example.manage_categories.component.CategoryCard
import com.example.manage_categories.component.EditCategoryBottomSheetContent
import com.example.manage_categories.model.BottomSheetType
import com.example.model.Category
import com.example.model.CategoryColorType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import com.example.design_system.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ManageCategoriesScreen(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    popBackStack: () -> Unit,
    onCategoryAdd: (title: String, type: CategoryColorType) -> Unit,
    onCategoryDelete: (Long) -> Unit,
    onCategoryUpdate: (id: Long, title: String, type: CategoryColorType) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(value = BottomSheetType.IDLE) }

    var editId by remember { mutableLongStateOf(value = 0L) }
    var editTitle by remember { mutableStateOf(value = "") }
    var editColorType by remember { mutableStateOf(value = CategoryColorType.RED) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = stringResource(id = DesignSystemR.string.category),
                        style = TodoTheme.typography.bold_20,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = modifier.size(size = 24.dp),
                            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            showBottomSheet = BottomSheetType.ADD_CATEGORY
                        }
                    ) {
                        Icon(
                            modifier = modifier.size(size = 21.dp),
                            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_add_category),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (showBottomSheet != BottomSheetType.IDLE) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = BottomSheetType.IDLE },
                sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                Box() {
                    when (showBottomSheet) {
                        BottomSheetType.ADD_CATEGORY -> {
                            AddCategoryBottomSheetContent(
                                onCancelClick = {
                                    showBottomSheet = BottomSheetType.IDLE
                                },
                                onCreateClick = { title, type ->
                                    onCategoryAdd(title, type)
                                    showBottomSheet = BottomSheetType.IDLE
                                }
                            )
                        }

                        else -> {
                            EditCategoryBottomSheetContent(
                                id = editId,
                                title = editTitle,
                                type = editColorType,
                                onCancelClick = {
                                    showBottomSheet = BottomSheetType.IDLE
                                },
                                onEditClick = { id, title, type ->
                                    onCategoryUpdate(
                                        id,
                                        title,
                                        type
                                    )
                                    showBottomSheet = BottomSheetType.IDLE
                                }
                            )
                        }
                    }
                }
            }
        }

        if (categories.isEmpty()) {
            EmptyContent(
                modifier = modifier.fillMaxSize(),
                title = stringResource(id = DesignSystemR.string.no_categories)
            )
        } else {
            LazyColumn(
                modifier = modifier.fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ) {
                itemsIndexed(
                    items = categories,
                    key = { _, category ->
                        category.id
                    }
                ) { _, category ->
                    val type = CategoryColorType.entries.filter {
                        it.colorValue == category.colorValue
                    }.getOrNull(index = 0) ?: CategoryColorType.RED
                    CategoryCard(
                        id = category.id,
                        title = category.title,
                        type = type,
                        onEditClick = { id, title, type ->
                            editId = id
                            editTitle = title
                            editColorType = type
                            showBottomSheet = BottomSheetType.EDIT_CATEGORY
                        },
                        onDeleteClick = onCategoryDelete
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageCategoriesScreenPreview() {
    TodoTheme {
        ManageCategoriesScreen(
            categories = persistentListOf(),
            popBackStack = {},
            onCategoryAdd = { _, _ -> },
            onCategoryDelete = {},
            onCategoryUpdate = { _, _, _ -> }
        )
    }
}