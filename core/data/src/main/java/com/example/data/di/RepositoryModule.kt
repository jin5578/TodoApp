package com.example.data.di

import android.content.Context
import com.example.data.remote.github.GithubDeviceCodeApi
import com.example.data.remote.github.GithubGraphQlApi
import com.example.data.remote.openWeather.OpenWeatherApi
import com.example.data.repository.DefaultCategoryRepository
import com.example.data.repository.DefaultGithubRepository
import com.example.data.repository.DefaultLocationRepository
import com.example.data.repository.DefaultOpenWeatherRepository
import com.example.data.repository.DefaultSubTaskRepository
import com.example.data.repository.DefaultSystemRepository
import com.example.data.repository.DefaultTaskRepository
import com.example.dataApi.repository.CategoryRepository
import com.example.dataApi.repository.GithubRepository
import com.example.dataApi.repository.LocationRepository
import com.example.dataApi.repository.OpenWeatherRepository
import com.example.dataApi.repository.SubTaskRepository
import com.example.dataApi.repository.SystemRepository
import com.example.dataApi.repository.TaskRepository
import com.example.database.datasource.CategoryDatabaseDataSource
import com.example.database.datasource.SubTaskDatabaseDataSource
import com.example.database.datasource.TaskDatabaseDataSource
import com.example.datastore.datasource.GithubTokenDataSource
import com.example.datastore.datasource.SystemPreferencesDataSource
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {
    @Provides
    @Singleton
    fun providesSystemRepository(systemDataSource: SystemPreferencesDataSource): SystemRepository = DefaultSystemRepository(systemDataSource = systemDataSource)

    @Provides
    @Singleton
    fun providesCategoryRepository(categoryDataSource: CategoryDatabaseDataSource): CategoryRepository = DefaultCategoryRepository(categoryDataSource = categoryDataSource)

    @Provides
    @Singleton
    fun providesTaskRepository(taskDataSource: TaskDatabaseDataSource): TaskRepository = DefaultTaskRepository(taskDataSource = taskDataSource)

    @Provides
    @Singleton
    fun providesSubTaskRepository(subTaskDataSource: SubTaskDatabaseDataSource): SubTaskRepository = DefaultSubTaskRepository(subTaskDataSource = subTaskDataSource)

    @Provides
    @Singleton
    fun providesGithubRepository(
        deviceCodeApi: GithubDeviceCodeApi,
        graphQlApi: GithubGraphQlApi,
        tokenDataSource: GithubTokenDataSource,
    ): GithubRepository = DefaultGithubRepository(
        deviceCodeApi = deviceCodeApi,
        graphQlApi = graphQlApi,
        tokenDataSource = tokenDataSource,
    )

    @Provides
    @Singleton
    fun providesOpenWeatherRepository(openWeatherApi: OpenWeatherApi): OpenWeatherRepository = DefaultOpenWeatherRepository(
        openWeatherApi = openWeatherApi,
    )

    @Provides
    @Singleton
    fun providesLocationRepository(
        @ApplicationContext context: Context,
        fusedLocationProviderClient: FusedLocationProviderClient,
    ): LocationRepository = DefaultLocationRepository(
        context = context,
        fusedLocationProviderClient = fusedLocationProviderClient,
    )
}
