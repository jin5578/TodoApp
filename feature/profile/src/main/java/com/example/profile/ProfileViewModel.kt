package com.example.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.design_system.model.HeatmapEntry
import com.example.domain.GetProfileDataUseCase
import com.example.model.Task
import com.example.profile.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

private const val HEATMAP_WEEK_COUNT = 53L
private const val HEATMAP_MAX_LEVEL = 4

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileDataUseCase: GetProfileDataUseCase
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<ProfileUiState> =
        MutableStateFlow(value = ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchProfileUiState()
    }

    private fun fetchProfileUiState() = viewModelScope.launch {
        val today = LocalDate.now()
        val fromDate = today.minusWeeks(HEATMAP_WEEK_COUNT)

        getProfileDataUseCase(
            fromDate = fromDate,
            toDate = today
        ).map { profile ->
            val profileSystem = profile.profileSystem
            val totalTasksCount = profile.tasks.count()
            val completedTasksCount = profile.tasks.count { it.isCompleted }
            val incompletedTasksCount = totalTasksCount - completedTasksCount
            ProfileUiState.Screen(
                completedTasksCount = completedTasksCount,
                incompletedTasksCount = incompletedTasksCount,
                heatmapEntries = profile.tasks.toHeatmapEntries(),
                locale = profileSystem.locale,
            )
        }
            .catch { throwable ->
                _errorFlow.emit(value = throwable)
            }
            .collect { profileUiState ->
                _uiState.value = profileUiState
            }
    }

    private fun List<Task>.toHeatmapEntries(): ImmutableList<HeatmapEntry> =
        this.filter { it.isCompleted }
            .mapNotNull { it.completedAt }
            .groupingBy { it.toLocalDate() }
            .eachCount()
            .map { (date, count) ->
                HeatmapEntry(
                    date = date,
                    level = count.coerceAtMost(maximumValue = HEATMAP_MAX_LEVEL)
                )
            }.toPersistentList()
}