package com.example.datastore.datasource

import com.example.datastore.model.SystemData
import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface SystemPreferencesDataSource {
    val systemData: Flow<SystemData>
    suspend fun updateLanguage(languageType: String)
    suspend fun updateLocale(locale: String)
    suspend fun updateThemeType(themeType: String)
}