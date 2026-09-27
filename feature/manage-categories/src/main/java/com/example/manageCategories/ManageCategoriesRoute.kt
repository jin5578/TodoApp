package com.example.manageCategories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designSystem.component.Loading
import com.example.designSystem.utils.LocalSnackbarHostState
import com.example.designSystem.utils.toErrorMessage
import com.example.manageCategories.model.ManageCategoriesUiState
import com.example.model.CategoryColorType
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun ManageCategoriesRoute(
    viewModel: ManageCategoriesViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = LocalSnackbarHostState.current
    val contextResources = LocalResources.current

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackbarHostState.showSnackbar(
                message = throwable.toErrorMessage(resources = contextResources),
            )
        }
    }

    ManageCategoriesContent(
        uiState = uiState,
        onCategoryAdd = { title, type ->
            viewModel.insertCategory(
                title = title,
                colorValue = type.colorValue,
            )
        },
        onCategoryDelete = viewModel::deleteCategory,
        onCategoryUpdate = { id, title, type ->
            viewModel.updateCategory(
                id = id,
                title = title,
                colorValue = type.colorValue,
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
