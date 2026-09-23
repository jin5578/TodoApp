package com.example.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastore.model.SystemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Named

class DefaultSystemPreferencesDataSource
@Inject
constructor(
    @param:Named(value = "system") private val dataStore: DataStore<Preferences>,
) : SystemPreferencesDataSource {
    override val systemData: Flow<SystemData> =
        dataStore.data.map { preferences ->
            SystemData(
                sleepTime =
                preferences[PreferencesKey.SLEEP_TIME_KEY]
                    ?: DEFAULT_SLEEP_TIME,
                sortByType =
                preferences[PreferencesKey.SORT_BY_KEY]
                    ?: DEFAULT_SORT_BY,
                languageType =
                preferences[PreferencesKey.LANGUAGE_TYPE_KEY]
                    ?: DEFAULT_LANGUAGE_TYPE,
                themeType =
                preferences[PreferencesKey.THEME_KEY]
                    ?: DEFAULT_THEME,
                timePickerType =
                preferences[PreferencesKey.TIME_PICKER_KEY]
                    ?: DEFAULT_TIME_PICKER,
                locale =
                preferences[PreferencesKey.LOCALE_KEY]
                    ?: DEFAULT_LOCALE,
                buildVersion =
                preferences[PreferencesKey.BUILD_VERSION_KEY]
                    ?: DEFAULT_BUILD_VERSION,
                password =
                preferences[PreferencesKey.PASSWORD_KEY]
                    ?: DEFAULT_PASSWORD,
                isBiometricEnabled =
                preferences[PreferencesKey.BIOMETRIC_ENABLED_KEY]
                    ?: DEFAULT_BIOMETRIC_ENABLED,
                lastLatitude =
                preferences[PreferencesKey.LAST_LATITUDE_KEY]
                    ?: DEFAULT_LAST_LATITUDE,
                lastLongitude =
                preferences[PreferencesKey.LAST_LONGITUDE_KEY]
                    ?: DEFAULT_LAST_LONGITUDE,
            )
        }

    override suspend fun updateSortByType(sortByType: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.SORT_BY_KEY] = sortByType
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

    override suspend fun updateBiometricEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.BIOMETRIC_ENABLED_KEY] = enabled
        }
    }

    override suspend fun updateLastLocation(
        latitude: Double,
        longitude: Double,
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.LAST_LATITUDE_KEY] = latitude
            preferences[PreferencesKey.LAST_LONGITUDE_KEY] = longitude
        }
    }

    override suspend fun deleteAllData() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.SLEEP_TIME_KEY] =
                DEFAULT_SLEEP_TIME
            preferences[PreferencesKey.SORT_BY_KEY] =
                DEFAULT_SORT_BY
            preferences[PreferencesKey.LANGUAGE_TYPE_KEY] =
                DEFAULT_LANGUAGE_TYPE
            preferences[PreferencesKey.THEME_KEY] =
                DEFAULT_THEME
            preferences[PreferencesKey.TIME_PICKER_KEY] =
                DEFAULT_TIME_PICKER
            preferences[PreferencesKey.LOCALE_KEY] =
                DEFAULT_LOCALE
            preferences[PreferencesKey.BUILD_VERSION_KEY] =
                DEFAULT_BUILD_VERSION
            preferences[PreferencesKey.PASSWORD_KEY] =
                DEFAULT_PASSWORD
            preferences[PreferencesKey.BIOMETRIC_ENABLED_KEY] =
                DEFAULT_BIOMETRIC_ENABLED
            preferences[PreferencesKey.LAST_LATITUDE_KEY] =
                DEFAULT_LAST_LATITUDE
            preferences[PreferencesKey.LAST_LONGITUDE_KEY] =
                DEFAULT_LAST_LONGITUDE
        }
    }

    private object PreferencesKey {
        val SLEEP_TIME_KEY =
            stringPreferencesKey(name = "sleep_time_key")
        val SORT_BY_KEY =
            stringPreferencesKey(name = "sort_by_key")
        val LANGUAGE_TYPE_KEY =
            stringPreferencesKey(name = "language_type_key")
        val THEME_KEY =
            stringPreferencesKey(name = "theme_key")
        val TIME_PICKER_KEY =
            stringPreferencesKey(name = "time_picker_key")
        val LOCALE_KEY =
            stringPreferencesKey(name = "locale_key")
        val BUILD_VERSION_KEY =
            stringPreferencesKey(name = "build_version_key")
        val PASSWORD_KEY =
            stringPreferencesKey(name = "password_key")
        val BIOMETRIC_ENABLED_KEY =
            booleanPreferencesKey(name = "biometric_enabled_key")
        val LAST_LATITUDE_KEY =
            doublePreferencesKey(name = "last_latitude_key")
        val LAST_LONGITUDE_KEY =
            doublePreferencesKey(name = "last_longitude_key")
    }

    companion object {
        private val DEFAULT_SLEEP_TIME = LocalTime.of(23, 59).toString()
        private const val DEFAULT_SORT_BY = "dueDateAndTime"
        private const val DEFAULT_LANGUAGE_TYPE = "korean"
        private const val DEFAULT_THEME = "system"
        private const val DEFAULT_TIME_PICKER = "clockTimePicker"
        private const val DEFAULT_LOCALE = "KR"
        private const val DEFAULT_BUILD_VERSION = "1.0.0"
        private const val DEFAULT_PASSWORD = ""
        private const val DEFAULT_BIOMETRIC_ENABLED = false
        private const val DEFAULT_LAST_LATITUDE = 37.56682420267543
        private const val DEFAULT_LAST_LONGITUDE = 126.978652258823
    }
}
