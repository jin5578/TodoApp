package com.example.profile.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.designSystem.model.HeatmapEntry
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate
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
        val githubHeatmapEntries: ImmutableList<HeatmapEntry>,
        val categoryEntries: ImmutableList<ProfileCategoryEntry>,
        val categoryTaskState: ProfileTaskState,
        val categoryTaskDuration: ProfileTaskDuration,
        val dailyEntries: ImmutableList<Float>,
        val dailyFromDate: LocalDate,
        val dailyToDate: LocalDate,
        val locale: Locale,
    ) : ProfileUiState
}
