package com.example.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastore.model.SystemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class DefaultSystemPreferencesDataSource @Inject constructor(
    @param:Named(value = "system") private val dataStore: DataStore<Preferences>
) : SystemPreferencesDataSource {
    object PreferencesKey {
        val THEME_KEY = stringPreferencesKey(name = "theme_key")
    }

    override val systemData: Flow<SystemData> =
        dataStore.data.map { preferences ->
            SystemData(
                themeType = preferences[PreferencesKey.THEME_KEY]
                    ?: DEFAULT_THEME
            )
        }

    companion object {
        private const val DEFAULT_THEME = "system"
    }
}