package com.example.data_api.repository

import com.example.model.ThemeType
import kotlinx.coroutines.flow.Flow

interface SystemRepository {
    fun getThemeType(): Flow<ThemeType>
}