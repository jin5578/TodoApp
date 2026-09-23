package com.example.manage_categories.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
sealed interface ManageCategoriesUiState {
    @Immutable
    data object Loading : ManageCategoriesUiState

    @Immutable
    data class Screen(
        val categoryUiModels: ImmutableList<ManageCategoryUiModel> = persistentListOf(),
    ) : ManageCategoriesUiState
}
