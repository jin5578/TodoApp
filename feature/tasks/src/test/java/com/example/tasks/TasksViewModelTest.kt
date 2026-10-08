package com.example.tasks

import android.location.Location
import app.cash.turbine.test
import com.example.domain.CheckPasswordUseCase
import com.example.domain.DeleteAllDataUseCase
import com.example.domain.GetCurrentLocationUseCase
import com.example.domain.GetHasBiometricEnabledUseCase
import com.example.domain.GetHasExistingPasswordUseCase
import com.example.domain.GetLastLocationUseCase
import com.example.domain.GetOpenWeatherUseCase
import com.example.domain.GetTasksDataUseCase
import com.example.domain.InsertCategoryUseCase
import com.example.domain.InsertTaskUseCase
import com.example.domain.UpdateLastLocationUseCase
import com.example.domain.UpdateSortByTypeUseCase
import com.example.domain.UpdateSubTaskCompletedUseCase
import com.example.domain.UpdateTaskCompletedUseCase
import com.example.domain.UpdateTaskSymbolUseCase
import com.example.model.Category
import com.example.model.SortByType
import com.example.model.Task
import com.example.model.TimePickerType
import com.example.model.location.Coordinates
import com.example.model.openWeather.OpenWeatherMain
import com.example.model.openWeather.OpenWeatherWind
import com.example.model.openWeather.WeatherInfo
import com.example.model.tasks.Tasks
import com.example.model.tasks.TasksSystem
import com.example.tasks.model.TaskState
import com.example.tasks.model.TasksPasswordProcessType
import com.example.tasks.model.TasksUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
class TasksViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getHasBiometricEnabledUseCase: GetHasBiometricEnabledUseCase = mockk()
    private val getHasExistingPasswordUseCase: GetHasExistingPasswordUseCase = mockk()
    private val getTasksDataUseCase: GetTasksDataUseCase = mockk()
    private val checkPasswordUseCase: CheckPasswordUseCase = mockk()
    private val deleteAllDataUseCase: DeleteAllDataUseCase = mockk(relaxed = true)
    private val insertTaskUseCase: InsertTaskUseCase = mockk(relaxed = true)
    private val insertCategoryUseCase: InsertCategoryUseCase = mockk(relaxed = true)
    private val updateTaskSymbolUseCase: UpdateTaskSymbolUseCase = mockk(relaxed = true)
    private val updateTaskCompletedUseCase: UpdateTaskCompletedUseCase = mockk(relaxed = true)
    private val updateSubTaskCompletedUseCase: UpdateSubTaskCompletedUseCase = mockk(relaxed = true)
    private val updateSortByTypeUseCase: UpdateSortByTypeUseCase = mockk(relaxed = true)
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase = mockk()
    private val getOpenWeatherUseCase: GetOpenWeatherUseCase = mockk()
    private val getLastLocationUseCase: GetLastLocationUseCase = mockk()
    private val updateLastLocationUseCase: UpdateLastLocationUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        // Default: no lock screen, empty task list. Individual tests override what they need.
        every { getHasBiometricEnabledUseCase() } returns flowOf(false)
        every { getHasExistingPasswordUseCase() } returns flowOf(false)
        every { getTasksDataUseCase(any()) } returns flowOf(tasksDataOf(tasks = emptyList()))
    }

    private fun createViewModel() = TasksViewModel(
        getHasBiometricEnabledUseCase = getHasBiometricEnabledUseCase,
        getHasExistingPasswordUseCase = getHasExistingPasswordUseCase,
        getTasksDataUseCase = getTasksDataUseCase,
        checkPasswordUseCase = checkPasswordUseCase,
        deleteAllDataUseCase = deleteAllDataUseCase,
        insertTaskUseCase = insertTaskUseCase,
        insertCategoryUseCase = insertCategoryUseCase,
        updateTaskSymbolUseCase = updateTaskSymbolUseCase,
        updateTaskCompletedUseCase = updateTaskCompletedUseCase,
        updateSubTaskCompletedUseCase = updateSubTaskCompletedUseCase,
        updateSortByTypeUseCase = updateSortByTypeUseCase,
        getCurrentLocationUseCase = getCurrentLocationUseCase,
        getOpenWeatherUseCase = getOpenWeatherUseCase,
        getLastLocationUseCase = getLastLocationUseCase,
        updateLastLocationUseCase = updateLastLocationUseCase,
    )

    // ---- Lock flow branching (init) ----

    @Test
    fun `init shows Biometric state when biometric lock is enabled`() = runTest {
        every { getHasBiometricEnabledUseCase() } returns flowOf(true)

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(TasksUiState.Biometric, viewModel.uiState.value)
    }

    @Test
    fun `init shows password entry when password lock is enabled`() = runTest {
        every { getHasExistingPasswordUseCase() } returns flowOf(true)

        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is TasksUiState.Password)
        assertEquals(
            TasksPasswordProcessType.ENTER_EXISTING_PASSWORD,
            (state as TasksUiState.Password).tasksPasswordProcessType,
        )
    }

    @Test
    fun `init fetches tasks directly when no lock is configured`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is TasksUiState.Screen)
    }

    // ---- Password check ----

    @Test
    fun `checkPassword with correct password unlocks and fetches tasks`() = runTest {
        every { getHasExistingPasswordUseCase() } returns flowOf(true)
        every { checkPasswordUseCase(password = "1234") } returns flowOf(true)
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.checkPassword(password = "1234")
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is TasksUiState.Screen)
    }

    @Test
    fun `checkPassword with wrong password marks mismatch and keeps lock screen`() = runTest {
        every { getHasExistingPasswordUseCase() } returns flowOf(true)
        every { checkPasswordUseCase(password = "wrong") } returns flowOf(false)
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.checkPassword(password = "wrong")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is TasksUiState.Password)
        assertEquals(
            TasksPasswordProcessType.EXISTING_PASSWORD_MISMATCHED,
            (state as TasksUiState.Password).tasksPasswordProcessType,
        )
    }

    // ---- Task grouping & sorting ----

    @Test
    fun `tasks are split into previous and completed-today groups`() = runTest {
        val previousTask = buildTask(id = 1, title = "미완료", isCompleted = false)
        val completedToday = buildTask(
            id = 2,
            title = "오늘 완료",
            isCompleted = true,
            completedAt = LocalDateTime.now(),
        )
        val completedYesterday = buildTask(
            id = 3,
            title = "어제 완료",
            isCompleted = true,
            completedAt = LocalDateTime.now().minusDays(1),
        )

        every { getTasksDataUseCase(any()) } returns flowOf(
            tasksDataOf(tasks = listOf(previousTask, completedToday, completedYesterday)),
        )

        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value as TasksUiState.Screen
        assertEquals(2, state.taskStateGroups.size)
        assertEquals(TaskState.PREVIOUS, state.taskStateGroups[0].taskState)
        assertEquals(listOf(1L), state.taskStateGroups[0].tasks.map { it.id })
        assertEquals(TaskState.COMPLETED_TODAY, state.taskStateGroups[1].taskState)
        assertEquals(listOf(2L), state.taskStateGroups[1].tasks.map { it.id })
        // completedYesterday falls into neither group but still flips the "has completed task" flag.
        assertTrue(state.isVisibleCompletedTask)
    }

    @Test
    fun `tasks are sorted by due date when sortByType is DUE_DATE_AND_TIME`() = runTest {
        val later = buildTask(id = 1, title = "later", date = LocalDate.now().plusDays(2))
        val sooner = buildTask(id = 2, title = "sooner", date = LocalDate.now().plusDays(1))

        every { getTasksDataUseCase(any()) } returns flowOf(
            tasksDataOf(tasks = listOf(later, sooner), sortByType = SortByType.DUE_DATE_AND_TIME),
        )

        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value as TasksUiState.Screen
        val orderedIds = state.taskStateGroups.first().tasks.map { it.id }
        assertEquals(listOf(2L, 1L), orderedIds)
    }

    // ---- Simple delegation to use cases ----

    @Test
    fun `updateSortByType delegates to the use case`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.updateSortByType(sortByType = SortByType.TASK_CREATION_TIME_DESC)
        advanceUntilIdle()

        coVerify { updateSortByTypeUseCase(sortByType = SortByType.TASK_CREATION_TIME_DESC) }
    }

    @Test
    fun `updateTaskCompleted delegates to the use case`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.updateTaskCompleted(id = 42L, isCompleted = true)
        advanceUntilIdle()

        coVerify { updateTaskCompletedUseCase(id = 42L, isCompleted = true) }
    }

    // ---- Weather ----

    @Test
    fun `fetchWeather uses current location and persists it when available`() = runTest {
        coEvery { getCurrentLocationUseCase() } returns Coordinates(latitude = 37.5, longitude = 127.0)
        coEvery { getOpenWeatherUseCase(lat = 37.5, lon = 127.0) } returns sampleWeatherInfo()

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.fetchWeather()
        advanceUntilIdle()

        val state = viewModel.uiState.value as TasksUiState.Screen
        assertEquals(sampleWeatherInfo(), state.weatherInfo)
        coVerify { updateLastLocationUseCase(latitude = 37.5, longitude = 127.0) }
    }

    @Test
    fun `fetchWeather falls back to last known location when current location is unavailable`() = runTest {
        coEvery { getCurrentLocationUseCase() } returns null
        val lastLocation = mockk<Location>()
        every { lastLocation.latitude } returns 35.0
        every { lastLocation.longitude } returns 129.0
        every { getLastLocationUseCase() } returns flowOf(lastLocation)
        coEvery { getOpenWeatherUseCase(lat = 35.0, lon = 129.0) } returns sampleWeatherInfo()

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.fetchWeather()
        advanceUntilIdle()

        val state = viewModel.uiState.value as TasksUiState.Screen
        assertEquals(sampleWeatherInfo(), state.weatherInfo)
        coVerify(exactly = 0) { updateLastLocationUseCase(any(), any()) }
    }

    @Test
    fun `fetchWeather failure is reported through errorFlow instead of crashing`() = runTest {
        coEvery { getCurrentLocationUseCase() } returns Coordinates(latitude = 0.0, longitude = 0.0)
        coEvery { getOpenWeatherUseCase(lat = 0.0, lon = 0.0) } throws IllegalStateException("network down")

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.errorFlow.test {
            viewModel.fetchWeather()
            val error = awaitItem()
            assertTrue(error is IllegalStateException)
        }
    }

    private fun buildTask(
        id: Long,
        title: String,
        isCompleted: Boolean = false,
        date: LocalDate = LocalDate.now(),
        completedAt: LocalDateTime? = null,
    ): Task = Task(
        id = id,
        uuid = "uuid-$id",
        title = title,
        isCompleted = isCompleted,
        date = date,
        time = null,
        reminderTime = null,
        completedAt = completedAt,
        createdAt = LocalDateTime.now(),
    )

    private fun tasksDataOf(
        tasks: List<Task>,
        categories: List<Category> = emptyList(),
        sortByType: SortByType = SortByType.DUE_DATE_AND_TIME,
    ): Tasks = Tasks(
        tasks = tasks,
        categories = categories,
        tasksSystem = TasksSystem(
            sortByType = sortByType,
            locale = Locale.KOREA,
            timePickerType = TimePickerType.SCROLL_TIME_PICKER,
        ),
    )

    private fun sampleWeatherInfo(): WeatherInfo = WeatherInfo(
        weather = null,
        main = OpenWeatherMain(
            temp = 20.0,
            feelsLike = 20.0,
            tempMin = 18.0,
            tempMax = 22.0,
            pressure = 1000.0,
            humidity = 50.0,
        ),
        wind = OpenWeatherWind(speed = 1.0, deg = 90),
        name = "Seoul",
    )
}
