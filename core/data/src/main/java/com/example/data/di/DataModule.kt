package com.example.data.di

import com.example.datastore.datasource.DefaultSystemPreferencesDataSource
import com.example.datastore.datasource.SystemPreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindsSystemPreferencesDataSource(
        dataSource: DefaultSystemPreferencesDataSource
    ): SystemPreferencesDataSource
}