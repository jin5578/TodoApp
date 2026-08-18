package com.example.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastore.model.SystemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Named

class DefaultSystemPreferencesDataSource @Inject constructor(
    @param:Named(value = "system") private val dataStore: DataStore<Preferences>
) : SystemPreferencesDataSource {
    object PreferencesKey {
        val SLEEP_TIME_KEY = stringPreferencesKey(name = "sleep_time_key")
        val SORT_TASK_KEY = stringPreferencesKey(name = "sort_task_key")
        val LANGUAGE_TYPE_KEY = stringPreferencesKey(name = "language_type_key")
        val THEME_KEY = stringPreferencesKey(name = "theme_key")
        val TIME_PICKER_KEY = stringPreferencesKey(name = "time_picker_key")
        val LOCALE_KEY = stringPreferencesKey(name = "locale_key")
        val BUILD_VERSION_KEY = stringPreferencesKey(name = "build_version_key")
        val PASSWORD_KEY = stringPreferencesKey(name = "password_key")
    }

    override val systemData: Flow<SystemData> =
        dataStore.data.map { preferences ->
            SystemData(
                sleepTime = preferences[PreferencesKey.SLEEP_TIME_KEY]
                    ?: DEFAULT_SLEEP_TIME,
                sortTaskType = preferences[PreferencesKey.SORT_TASK_KEY]
                    ?: DEFAULT_SORT_TASK,
                languageType = preferences[PreferencesKey.LANGUAGE_TYPE_KEY]
                    ?: DEFAULT_LANGUAGE_TYPE,
                themeType = preferences[PreferencesKey.THEME_KEY]
                    ?: DEFAULT_THEME,
                timePickerType = preferences[PreferencesKey.TIME_PICKER_KEY]
                    ?: DEFAULT_TIME_PICKER,
                locale = preferences[PreferencesKey.LOCALE_KEY]
                    ?: DEFAULT_LOCALE,
                buildVersion = preferences[PreferencesKey.BUILD_VERSION_KEY]
                    ?: DEFAULT_BUILD_VERSION,
                password = preferences[PreferencesKey.PASSWORD_KEY]
                    ?: DEFAULT_PASSWORD
            )
        }

    override suspend fun updateSortTaskType(sortTaskType: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.SORT_TASK_KEY] = sortTaskType
        }
    }

    override suspend fun updateLanguage(languageType: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.LANGUAGE_TYPE_KEY] = languageType
        }
    }

    override suspend fun updateLocale(locale: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.LOCALE_KEY] = locale
        }
    }

    override suspend fun updateThemeType(themeType: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.THEME_KEY] = themeType
        }
    }

    override suspend fun updateTimePickerType(timePickerType: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.TIME_PICKER_KEY] = timePickerType
        }
    }

    override suspend fun updatePassword(password: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.PASSWORD_KEY] = password
        }
    }

    override suspend fun removePassword() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.PASSWORD_KEY] = ""
        }
    }

    companion object {
        private val DEFAULT_SLEEP_TIME = LocalTime.of(23, 59).toString()
        private const val DEFAULT_SORT_TASK = "byCreateTimeDescending"
        private const val DEFAULT_LANGUAGE_TYPE = "korean"
        private const val DEFAULT_THEME = "system"
        private const val DEFAULT_TIME_PICKER = "clockTimePicker"
        private const val DEFAULT_LOCALE = "KR"
        private const val DEFAULT_BUILD_VERSION = "1.0.0"
        private const val DEFAULT_PASSWORD = ""
    }
}