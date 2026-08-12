package com.example.data.repository

import com.example.data_api.repository.SystemRepository
import com.example.datastore.datasource.SystemPreferencesDataSource
import com.example.model.LanguageType
import com.example.model.SortTaskType
import com.example.model.ThemeType
import com.example.model.TimePickerType
import com.example.model.addtask.AddTaskSystem
import com.example.model.edittask.EditTaskSystem
import com.example.model.home.HomeSystem
import com.example.model.setting.SettingSystem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject

class DefaultSystemRepository @Inject constructor(
    private val systemDataSource: SystemPreferencesDataSource
) : SystemRepository {
    override fun getThemeType(): Flow<ThemeType> =
        systemDataSource.systemData.map { data ->
            data.themeType.toThemeType()
        }

    override fun getHomeSystem(): Flow<HomeSystem> =
        systemDataSource.systemData.map { data ->
            HomeSystem(
                sleepTime = LocalTime.parse(data.sleepTime),
                sortTaskType = data.sortTaskType.toSortTaskType(),
                themeType = data.themeType.toThemeType(),
                buildVersion = data.buildVersion,
                locale = data.locale.toLocale(),
            )
        }

    override fun getSettingSystem(): Flow<SettingSystem> =
        systemDataSource.systemData.map { data ->
            SettingSystem(
                languageType = data.languageType.toLanguageType(),
                themeType = data.themeType.toThemeType(),
                sleepTime = LocalTime.parse(data.sleepTime),
                timePickerType = data.timePickerType.toTimePickerType(),
                buildVersion = data.buildVersion
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

    override suspend fun updateLanguage(languageType: LanguageType) =
        systemDataSource.updateLanguage(languageType = languageType.key)

    override suspend fun updateLocale(locale: Locale) =
        systemDataSource.updateLocale(locale = locale.country)

    override suspend fun updateThemeType(themeType: ThemeType) =
        systemDataSource.updateThemeType(themeType = themeType.key)

    override suspend fun updateTimePickerType(timePickerType: TimePickerType) =
        systemDataSource.updateTimePickerType(timePickerType = timePickerType.key)

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
        ThemeType.SKY_BLUE.key -> ThemeType.SKY_BLUE
        ThemeType.MIST_GRAY.key -> ThemeType.MIST_GRAY
        ThemeType.MIDNIGHT_BLUE.key -> ThemeType.MIDNIGHT_BLUE
        ThemeType.CHARCOAL_BLACK.key -> ThemeType.CHARCOAL_BLACK
        else -> ThemeType.DEEP_FOREST_GREEN
    }

    private fun String.toSortTaskType() = when (this) {
        SortTaskType.BY_PRIORITY_ASCENDING.key -> SortTaskType.BY_PRIORITY_ASCENDING
        SortTaskType.BY_PRIORITY_DESCENDING.key -> SortTaskType.BY_PRIORITY_DESCENDING
        SortTaskType.BY_TIME_ASCENDING.key -> SortTaskType.BY_TIME_ASCENDING
        SortTaskType.BY_TIME_DESCENDING.key -> SortTaskType.BY_TIME_DESCENDING
        SortTaskType.BY_CREATE_TIME_ASCENDING.key -> SortTaskType.BY_CREATE_TIME_ASCENDING
        else -> SortTaskType.BY_CREATE_TIME_DESCENDING
    }

    private fun String.toTimePickerType() = when (this) {
        TimePickerType.SCROLL_TIME_PICKER.key -> TimePickerType.SCROLL_TIME_PICKER
        else -> TimePickerType.CLOCK_TIME_PICKER
    }
}