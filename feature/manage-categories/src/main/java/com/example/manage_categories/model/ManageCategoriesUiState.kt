package com.example.manage_categories.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
sealed interface ManageCategoriesUiState {
    @Immutable
    data object Loading : ManageCategoriesUiState

    @Immutable
    data class Success(
        val categories: ImmutableList<Category> = persistentListOf()
    ) : ManageCategoriesUiState
}