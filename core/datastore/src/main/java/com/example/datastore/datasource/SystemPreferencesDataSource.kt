package com.example.datastore.datasource

import com.example.datastore.model.SystemData
import kotlinx.coroutines.flow.Flow

interface SystemPreferencesDataSource {
    val systemData: Flow<SystemData>
    suspend fun updateSortByType(sortByType: String)
    suspend fun updateLanguage(languageType: String)
    suspend fun updateLocale(locale: String)
    suspend fun updateThemeType(themeType: String)
    suspend fun updateTimePickerType(timePickerType: String)
    suspend fun updatePassword(password: String)
    suspend fun updateBiometricEnabled(enabled: Boolean)
    suspend fun updateLastLocation(latitude: Double, longitude: Double)
    suspend fun deleteAllData()
}