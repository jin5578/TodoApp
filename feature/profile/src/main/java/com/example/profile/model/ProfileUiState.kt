package com.example.profile.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.design_system.model.HeatmapEntry
import kotlinx.collections.immutable.ImmutableList
import java.util.Locale

@Stable
sealed interface ProfileUiState {
    @Immutable
    data object Loading : ProfileUiState

    @Immutable
    data class Screen(
        val completedTasksCount: Int,
        val incompletedTasksCount: Int,
        val heatmapEntries: ImmutableList<HeatmapEntry>,
        val locale: Locale,
    ) : ProfileUiState
}