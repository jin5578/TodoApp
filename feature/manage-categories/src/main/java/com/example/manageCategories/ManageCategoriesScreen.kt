package com.example.manageCategories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designSystem.component.dialog.category.CategoryDialog
import com.example.designSystem.theme.TodoTheme
import com.example.manageCategories.component.CategoryCard
import com.example.manageCategories.model.ManageCategoryUiModel
import com.example.model.CategoryColorType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import com.example.designSystem.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ManageCategoriesScreen(
    modifier: Modifier = Modifier,
    categoryUiModels: ImmutableList<ManageCategoryUiModel>,
    onCategoryAdd: (title: String, type: CategoryColorType) -> Unit,
    onCategoryDelete: (Long) -> Unit,
    onCategoryUpdate: (id: Long, title: String, type: CategoryColorType) -> Unit,
    popBackStack: () -> Unit,
) {
    var isShowAddCategoryDialog by remember { mutableStateOf(value = false) }
    var isShowEditCategoryDialog by remember { mutableStateOf(value = false) }

    var editId by remember { mutableLongStateOf(value = 0L) }
    var editTitle by remember { mutableStateOf(value = "") }
    var editColorType by remember { mutableStateOf(value = CategoryColorType.RED) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    Text(
                        text = stringResource(id = DesignSystemR.string.manage_categories),
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
            )
        },
    ) { paddingValues ->
        if (isShowAddCategoryDialog) {
            CategoryDialog(
                titleResId = DesignSystemR.string.create_new_category,
                onCloseClick = { isShowAddCategoryDialog = false },
                onSaveClick = { title, type ->
                    onCategoryAdd(title, type)
                    isShowAddCategoryDialog = false
                },
            )
        }

        if (isShowEditCategoryDialog) {
            CategoryDialog(
                titleResId = DesignSystemR.string.edit_category,
                id = editId,
                categoryTitle = editTitle,
                categoryColorType = editColorType,
                onCloseClick = { isShowEditCategoryDialog = false },
                onUpdateClick = { id, title, type ->
                    onCategoryUpdate(id, title, type)
                    isShowEditCategoryDialog = false
                },
            )
        }

        Column(
            modifier = modifier.fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            LazyColumn {
                itemsIndexed(
                    items = categoryUiModels,
                    key = { _, categoryUiModel ->
                        categoryUiModel.category.id
                    },
                ) { _, categoryUiModel ->
                    val category = categoryUiModel.category
                    val type = CategoryColorType.entries.filter {
                        it.colorValue == category.colorValue
                    }.getOrNull(index = 0) ?: CategoryColorType.RED
                    CategoryCard(
                        id = category.id,
                        title = category.title,
                        type = type,
                        taskCount = categoryUiModel.taskCount,
                        onEditClick = { id, title, type ->
                            editId = id
                            editTitle = title
                            editColorType = type
                            isShowEditCategoryDialog = true
                        },
                        onDeleteClick = onCategoryDelete,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        isShowAddCategoryDialog = true
                    }
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(size = 20.dp),
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_plus_small),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null,
                )

                Text(
                    modifier = Modifier.weight(weight = 1f),
                    text = stringResource(id = DesignSystemR.string.create_new),
                    style = TodoTheme.typography.medium_16,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageCategoriesScreenPreview() {
    TodoTheme {
        ManageCategoriesScreen(
            categoryUiModels = persistentListOf(),
            onCategoryAdd = { _, _ -> },
            onCategoryDelete = {},
            onCategoryUpdate = { _, _, _ -> },
            popBackStack = {},
        )
    }
}
