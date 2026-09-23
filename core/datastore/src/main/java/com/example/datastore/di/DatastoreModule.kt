package com.example.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {
    private const val SYSTEM_DATASTORE_NAME = "SYSTEM_PREFERENCES"
    private val Context.systemDataStore by preferencesDataStore(
        SYSTEM_DATASTORE_NAME,
    )

    @Provides
    @Singleton
    @Named(value = "system")
    fun providesSystemDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.systemDataStore
}
