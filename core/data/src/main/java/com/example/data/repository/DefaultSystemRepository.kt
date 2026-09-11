package com.example.data.repository

import com.example.data_api.repository.SystemRepository
import com.example.datastore.datasource.SystemPreferencesDataSource
import com.example.model.LanguageType
import com.example.model.SortByType
import com.example.model.ThemeType
import com.example.model.TimePickerType
import com.example.model.add_task.AddTaskSystem
import com.example.model.calendar.CalendarSystem
import com.example.model.completed_tasks.CompletedTasksSystem
import com.example.model.edit_task.EditTaskSystem
import com.example.model.lock_setup.LockSetupSystem
import com.example.model.memo.MemoSystem
import com.example.model.search_task.SearchTaskSystem
import com.example.model.security.SecuritySystem
import com.example.model.setting.SettingSystem
import com.example.model.tasks.TasksSystem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject

class DefaultSystemRepository @Inject constructor(
    private val systemDataSource: SystemPreferencesDataSource
) : SystemRepository {
    override fun checkPassword(password: String): Flow<Boolean> =
        systemDataSource.systemData.map { data ->
            data.password == password
        }

    override fun getThemeType(): Flow<ThemeType> =
        systemDataSource.systemData.map { data ->
            data.themeType.toThemeType()
        }

    override fun getSettingSystem(): Flow<SettingSystem> =
        systemDataSource.systemData.map { data ->
            SettingSystem(
                languageType = data.languageType.toLanguageType(),
                themeType = data.themeType.toThemeType(),
                sleepTime = LocalTime.parse(data.sleepTime),
                timePickerType = data.timePickerType.toTimePickerType(),
                buildVersion = data.buildVersion,
                hasExistingPassword = data.password.isNotEmpty()
            )
        }

    override fun getAddTaskSystem(): Flow<AddTaskSystem> =
        systemDataSource.systemData.map { data ->
            AddTaskSystem(
                locale = data.locale.toLocale(),
                timePickerType = data.timePickerType.toTimePickerType()
            )
        }

    override fun getEditTaskSystem(): Flow<EditTaskSystem> =
        systemDataSource.systemData.map { data ->
            EditTaskSystem(
                locale = data.locale.toLocale(),
                timePickerType = data.timePickerType.toTimePickerType()
            )
        }

    override fun getMemoSystem(): Flow<MemoSystem> =
        systemDataSource.systemData.map { data ->
            MemoSystem(
                locale = data.locale.toLocale(),
            )
        }

    override fun getCalendarSystem(): Flow<CalendarSystem> =
        systemDataSource.systemData.map { data ->
            CalendarSystem(
                locale = data.locale.toLocale(),
                sortByType = data.sortByType.toSortByType(),
                timePickerType = data.timePickerType.toTimePickerType(),
            )
        }

    override fun getTasksSystem(): Flow<TasksSystem> =
        systemDataSource.systemData.map { data ->
            TasksSystem(
                sortByType = data.sortByType.toSortByType(),
                locale = data.locale.toLocale(),
                timePickerType = data.timePickerType.toTimePickerType()
            )
        }

    override fun getLockSetupSystem(): Flow<LockSetupSystem> =
        systemDataSource.systemData.map { data ->
            LockSetupSystem(
                hasExistingPassword = data.password.isNotEmpty(),
                locale = data.locale.toLocale()
            )
        }

    override fun getSecuritySystem(): Flow<SecuritySystem> =
        systemDataSource.systemData.map { data ->
            SecuritySystem(
                hasExistingPassword = data.password.isNotEmpty(),
                hasBiometricEnabled = data.isBiometricEnabled
            )
        }

    override fun getCompletedTasksSystem(): Flow<CompletedTasksSystem> =
        systemDataSource.systemData.map { data ->
            CompletedTasksSystem(
                locale = data.locale.toLocale()
            )
        }

    override fun getSearchTaskSystem(): Flow<SearchTaskSystem> =
        systemDataSource.systemData.map { data ->
            SearchTaskSystem(
                locale = data.locale.toLocale()
            )
        }

    override fun hasExistingPassword(): Flow<Boolean> =
        systemDataSource.systemData.map { data ->
            data.password.isNotEmpty()
        }

    override fun hasBiometricEnabled(): Flow<Boolean> =
        systemDataSource.systemData.map { data ->
            data.isBiometricEnabled
        }

    override suspend fun updateSortByType(sortByType: SortByType) =
        systemDataSource.updateSortByType(sortByType = sortByType.key)

    override suspend fun updateLanguage(languageType: LanguageType) =
        systemDataSource.updateLanguage(languageType = languageType.key)

    override suspend fun updateLocale(locale: Locale) =
        systemDataSource.updateLocale(locale = locale.country)

    override suspend fun updateThemeType(themeType: ThemeType) =
        systemDataSource.updateThemeType(themeType = themeType.key)

    override suspend fun updateTimePickerType(timePickerType: TimePickerType) =
        systemDataSource.updateTimePickerType(timePickerType = timePickerType.key)

    override suspend fun updatePassword(password: String) =
        systemDataSource.updatePassword(password = password)

    override suspend fun updateBiometricEnabled(enabled: Boolean) =
        systemDataSource.updateBiometricEnabled(enabled = enabled)

    override suspend fun deleteAllData() =
        systemDataSource.deleteAllData()

    private fun String.toLanguageType() = when (this) {
        LanguageType.KOREAN.key -> LanguageType.KOREAN
        else -> LanguageType.ENGLISH
    }

    private fun String.toLocale() = when (this) {
        "KR" -> Locale.KOREA
        else -> Locale.US
    }

    private fun String.toThemeType() = when (this) {
        ThemeType.SYSTEM.key -> ThemeType.SYSTEM
        ThemeType.SUN_RISE.key -> ThemeType.SUN_RISE
        ThemeType.OCEAN.key -> ThemeType.OCEAN
        ThemeType.MEADOW.key -> ThemeType.MEADOW
        ThemeType.MIDNIGHT.key -> ThemeType.MIDNIGHT
        ThemeType.DEEP_SPACE.key -> ThemeType.DEEP_SPACE
        else -> ThemeType.EMBER
    }

    private fun String.toSortByType() = when (this) {
        SortByType.DUE_DATE_AND_TIME.key -> SortByType.DUE_DATE_AND_TIME
        SortByType.TASK_CREATION_TIME_ASC.key -> SortByType.TASK_CREATION_TIME_ASC
        else -> SortByType.TASK_CREATION_TIME_DESC
        /*SortByType.TASK_CREATION_TIME_DESC.key -> SortByType.TASK_CREATION_TIME_DESC
        else -> SortByType.MANUAL*/
    }

    private fun String.toTimePickerType() = when (this) {
        TimePickerType.SCROLL_TIME_PICKER.key -> TimePickerType.SCROLL_TIME_PICKER
        else -> TimePickerType.CLOCK_TIME_PICKER
    }
}