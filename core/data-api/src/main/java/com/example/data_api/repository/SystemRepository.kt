package com.example.data_api.repository

import com.example.model.LanguageType
import com.example.model.SortTaskType
import com.example.model.ThemeType
import com.example.model.TimePickerType
import com.example.model.addtask.AddTaskSystem
import com.example.model.calendar.CalendarSystem
import com.example.model.edittask.EditTaskSystem
import com.example.model.home.HomeSystem
import com.example.model.lock_setup.LockSetupSystem
import com.example.model.setting.SettingSystem
import com.example.model.tasks.TasksSystem
import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface SystemRepository {
    fun getThemeType(): Flow<ThemeType>
    fun getHomeSystem(): Flow<HomeSystem>
    fun getSettingSystem(): Flow<SettingSystem>
    fun getAddTaskSystem(): Flow<AddTaskSystem>
    fun getEditTaskSystem(): Flow<EditTaskSystem>
    fun getCalendarSystem(): Flow<CalendarSystem>
    fun getTasksSystem(): Flow<TasksSystem>
    fun getLockSetupSystem(): Flow<LockSetupSystem>
    suspend fun updateSortTaskType(sortTaskType: SortTaskType)
    suspend fun updateLanguage(languageType: LanguageType)
    suspend fun updateLocale(locale: Locale)
    suspend fun updateThemeType(themeType: ThemeType)
    suspend fun updateTimePickerType(timePickerType: TimePickerType)
    suspend fun updatePassword(password: String)
}