package com.example.data.repository

import com.example.data_api.repository.SystemRepository
import com.example.datastore.datasource.SystemPreferencesDataSource
import com.example.model.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultSystemRepository @Inject constructor(
    private val systemDataSource: SystemPreferencesDataSource
) : SystemRepository {
    override fun getThemeType(): Flow<ThemeType> =
        systemDataSource.systemData.map { data ->
            data.themeType.toThemeType()
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
}