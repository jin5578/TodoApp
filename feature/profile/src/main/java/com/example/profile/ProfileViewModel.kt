package com.example.profile

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.designSystem.model.HeatmapEntry
import com.example.domain.GetGithubContributionsUseCase
import com.example.domain.GetGithubUsernameUseCase
import com.example.domain.GetProfileDataUseCase
import com.example.model.Category
import com.example.model.Task
import com.example.model.github.GithubContributionDay
import com.example.profile.model.ProfileCategoryEntry
import com.example.profile.model.ProfileTaskDuration
import com.example.profile.model.ProfileTaskState
import com.example.profile.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

private const val HEATMAP_WEEK_COUNT = 53L
private const val HEATMAP_MAX_LEVEL = 4

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileDataUseCase: GetProfileDataUseCase,
    private val getGithubUsernameUseCase: GetGithubUsernameUseCase,
    private val getGithubContributionsUseCase: GetGithubContributionsUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<ProfileUiState> =
        MutableStateFlow(value = ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val categoryTaskStateFlow: MutableStateFlow<ProfileTaskState> =
        MutableStateFlow(value = ProfileTaskState.COMPLETED)
    private val categoryTaskDurationFlow: MutableStateFlow<ProfileTaskDuration> =
        MutableStateFlow(value = ProfileTaskDuration.ALL)
    private val dailyDateRangeFlow: MutableStateFlow<List<LocalDate>> =
        MutableStateFlow(
            value = listOf(
                LocalDate.now().minusDays(6),
                LocalDate.now(),
            ),
        )
    private val githubHeatmapEntriesFlow: MutableStateFlow<ImmutableList<HeatmapEntry>> =
        MutableStateFlow(value = persistentListOf())

    init {
        fetchProfileUiState()
        fetchGithubHeatmapEntries()
    }

    fun onTaskStateChanged(state: ProfileTaskState) {
        categoryTaskStateFlow.value = state
    }

    fun onTaskDurationChanged(duration: ProfileTaskDuration) {
        categoryTaskDurationFlow.value = duration
    }

    fun onDailyDateRangeChanged(fromDate: LocalDate, toDate: LocalDate) {
        dailyDateRangeFlow.value = listOf(fromDate, toDate)
    }

    private fun fetchProfileUiState() = viewModelScope.launch {
        val today = LocalDate.now()

        combine(
            flow = getProfileDataUseCase(),
            flow2 = categoryTaskStateFlow,
            flow3 = categoryTaskDurationFlow,
            flow4 = dailyDateRangeFlow,
            flow5 = githubHeatmapEntriesFlow,
        ) { profile, categoryTaskState, categoryTaskDuration, dailyDateRange, githubHeatmapEntries ->
            val profileSystem = profile.profileSystem
            val totalTasksCount = profile.tasks.count()
            val completedTasksCount = profile.tasks.count { it.isCompleted }
            val incompletedTasksCount = totalTasksCount - completedTasksCount
            ProfileUiState.Screen(
                completedTasksCount = completedTasksCount,
                incompletedTasksCount = incompletedTasksCount,
                heatmapEntries = profile.tasks.toHeatmapEntries(
                    fromDate = today.minusWeeks(HEATMAP_WEEK_COUNT),
                    toDate = today,
                ),
                githubHeatmapEntries = githubHeatmapEntries,
                categoryEntries = profile.tasks.toCategoryEntries(
                    today = today,
                    isCompleted = categoryTaskState.isCompleted,
                    durationDays = categoryTaskDuration.days,
                    categories = profile.categories,
                ),
                categoryTaskState = categoryTaskState,
                categoryTaskDuration = categoryTaskDuration,
                dailyEntries = profile.tasks.toDailyEntries(
                    fromDate = dailyDateRange[0],
                    toDate = dailyDateRange[1],
                ),
                dailyFromDate = dailyDateRange[0],
                dailyToDate = dailyDateRange[1],
                locale = profileSystem.locale,
            )
        }.catch { throwable ->
            _errorFlow.emit(value = throwable)
        }.collect { profileUiState ->
            _uiState.value = profileUiState
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchGithubHeatmapEntries() = viewModelScope.launch {
        getGithubUsernameUseCase()
            .flatMapLatest { username ->
                if (username == null) {
                    flowOf(persistentListOf())
                } else {
                    flow { emit(getGithubContributionsUseCase().toGithubHeatmapEntries()) }
                        .catch { emit(persistentListOf()) }
                }
            }
            .collect { entries ->
                githubHeatmapEntriesFlow.value = entries
            }
    }

    private fun List<Task>.toHeatmapEntries(
        fromDate: LocalDate,
        toDate: LocalDate,
    ): ImmutableList<HeatmapEntry> = this.filter { it.isCompleted }
        .mapNotNull { it.completedAt?.toLocalDate() }
        .filter { date -> !date.isBefore(fromDate) && !date.isAfter(toDate) }
        .groupingBy { it }
        .eachCount()
        .map { (date, count) ->
            HeatmapEntry(
                date = date,
                level = count.coerceAtMost(maximumValue = HEATMAP_MAX_LEVEL),
            )
        }.toPersistentList()

    private fun List<GithubContributionDay>.toGithubHeatmapEntries(): ImmutableList<HeatmapEntry> = this.map { day ->
        HeatmapEntry(
            date = day.date,
            level = day.contributionCount.toHeatmapLevel(),
        )
    }.toPersistentList()

    private fun Int.toHeatmapLevel(): Int = when {
        this <= 0 -> 0
        this <= 2 -> 1
        this <= 4 -> 2
        this <= 6 -> 3
        else -> 4
    }

    private fun List<Task>.toCategoryEntries(
        today: LocalDate,
        isCompleted: Boolean,
        durationDays: Long?,
        categories: List<Category>,
    ): ImmutableList<ProfileCategoryEntry> {
        val categoriesById = categories.associateBy { it.id }
        val fromDate = durationDays?.let { today.minusDays(it) }
        return this.filter { task ->
            if (task.isCompleted != isCompleted) return@filter false
            val completedDate = task.completedAt?.toLocalDate()
            fromDate == null || (
                completedDate != null && !completedDate.isBefore(
                    fromDate,
                )
                )
        }
            .groupingBy { it.categoryId }
            .eachCount()
            .mapNotNull { (categoryId, count) ->
                val category =
                    categoriesById[categoryId] ?: return@mapNotNull null
                ProfileCategoryEntry(
                    name = category.title,
                    value = count.toFloat(),
                    color = Color(color = category.colorValue),
                )
            }
            .toPersistentList()
    }

    private fun List<Task>.toDailyEntries(
        fromDate: LocalDate,
        toDate: LocalDate,
    ): ImmutableList<Float> {
        val countsByDate = this.filter { it.isCompleted }
            .mapNotNull { it.completedAt?.toLocalDate() }
            .groupingBy { it }
            .eachCount()
        return generateSequence(seed = fromDate) { it.plusDays(1) }
            .takeWhile { date -> !date.isAfter(toDate) }
            .map { date -> (countsByDate[date] ?: 0).toFloat() }
            .toPersistentList()
    }
}
