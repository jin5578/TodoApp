package com.example.dataApi.repository

import android.location.Location
import com.example.model.LanguageType
import com.example.model.SortByType
import com.example.model.ThemeType
import com.example.model.TimePickerType
import com.example.model.addTask.AddTaskSystem
import com.example.model.calendar.CalendarSystem
import com.example.model.completedTasks.CompletedTasksSystem
import com.example.model.editTask.EditTaskSystem
import com.example.model.lockSetup.LockSetupSystem
import com.example.model.memo.MemoSystem
import com.example.model.profile.ProfileSystem
import com.example.model.searchTask.SearchTaskSystem
import com.example.model.security.SecuritySystem
import com.example.model.setting.SettingSystem
import com.example.model.tasks.TasksSystem
import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface SystemRepository {
    fun checkPassword(password: String): Flow<Boolean>
    fun getThemeType(): Flow<ThemeType>
    fun getSettingSystem(): Flow<SettingSystem>
    fun getAddTaskSystem(): Flow<AddTaskSystem>
    fun getEditTaskSystem(): Flow<EditTaskSystem>
    fun getMemoSystem(): Flow<MemoSystem>
    fun getCalendarSystem(): Flow<CalendarSystem>
    fun getTasksSystem(): Flow<TasksSystem>
    fun getLockSetupSystem(): Flow<LockSetupSystem>
    fun getSecuritySystem(): Flow<SecuritySystem>
    fun getCompletedTasksSystem(): Flow<CompletedTasksSystem>
    fun getSearchTaskSystem(): Flow<SearchTaskSystem>
    fun getProfileSystem(): Flow<ProfileSystem>
    fun hasExistingPassword(): Flow<Boolean>
    fun hasBiometricEnabled(): Flow<Boolean>
    fun getLastLocation(): Flow<Location>
    suspend fun updateSortByType(sortByType: SortByType)
    suspend fun updateLanguage(languageType: LanguageType)
    suspend fun updateLocale(locale: Locale)
    suspend fun updateThemeType(themeType: ThemeType)
    suspend fun updateTimePickerType(timePickerType: TimePickerType)
    suspend fun updatePassword(password: String)
    suspend fun updateBiometricEnabled(enabled: Boolean)
    suspend fun updateLastLocation(latitude: Double, longitude: Double)
    suspend fun deleteAllData()
}
