package com.example.data.di

import com.example.data.repository.DefaultSystemRepository
import com.example.data_api.repository.SystemRepository
import com.example.datastore.datasource.SystemPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {
    @Provides
    @Singleton
    fun providesSystemRepository(
        systemDataSource: SystemPreferencesDataSource
    ): SystemRepository =
        DefaultSystemRepository(systemDataSource = systemDataSource)
}