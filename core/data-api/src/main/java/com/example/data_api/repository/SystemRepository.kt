package com.example.data_api.repository

import com.example.model.ThemeType
import com.example.model.setting.SettingSystem
import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface SystemRepository {
    fun getThemeType(): Flow<ThemeType>
    fun getSettingSystem(): Flow<SettingSystem>
    suspend fun updateLocale(locale: Locale)
}