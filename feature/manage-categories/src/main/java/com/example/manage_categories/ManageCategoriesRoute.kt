package com.example.manage_categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.manage_categories.model.ManageCategoriesUiState
import com.example.model.CategoryColorType
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun ManageCategoriesRoute(
    viewModel: ManageCategoriesViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    ManageCategoriesContent(
        uiState = uiState,
        onCategoryAdd = { title, type ->
            viewModel.insertCategory(
                title = title,
                colorValue = type.colorValue
            )
        },
        onCategoryDelete = viewModel::deleteCategory,
        onCategoryUpdate = { id, title, type ->
            viewModel.updateCategory(
                id = id,
                title = title,
                colorValue = type.colorValue
            )
        },
        popBackStack = popBackStack,
    )
}

@Composable
private fun ManageCategoriesContent(
    uiState: ManageCategoriesUiState,
    onCategoryAdd: (title: String, type: CategoryColorType) -> Unit,
    onCategoryDelete: (Long) -> Unit,
    onCategoryUpdate: (id: Long, title: String, type: CategoryColorType) -> Unit,
    popBackStack: () -> Unit,
) {
    when (uiState) {
        is ManageCategoriesUiState.Loading ->
            Loading()

        is ManageCategoriesUiState.Screen ->
            ManageCategoriesScreen(
                categoryUiModels = uiState.categoryUiModels,
                onCategoryAdd = onCategoryAdd,
                onCategoryDelete = onCategoryDelete,
                onCategoryUpdate = onCategoryUpdate,
                popBackStack = popBackStack,
            )
    }
}