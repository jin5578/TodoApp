package com.example.data.di

import com.example.data.remote.github.GithubDeviceCodeApi
import com.example.data.remote.github.GithubGraphQlApi
import com.example.data.repository.DefaultCategoryRepository
import com.example.data.repository.DefaultGithubRepository
import com.example.data.repository.DefaultSubTaskRepository
import com.example.data.repository.DefaultSystemRepository
import com.example.data.repository.DefaultTaskRepository
import com.example.data_api.repository.CategoryRepository
import com.example.data_api.repository.GithubRepository
import com.example.data_api.repository.SubTaskRepository
import com.example.data_api.repository.SystemRepository
import com.example.data_api.repository.TaskRepository
import com.example.database.datasource.CategoryDatabaseDataSource
import com.example.database.datasource.SubTaskDatabaseDataSource
import com.example.database.datasource.TaskDatabaseDataSource
import com.example.datastore.datasource.GithubTokenDataSource
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

    @Provides
    @Singleton
    fun providesCategoryRepository(
        categoryDataSource: CategoryDatabaseDataSource,
    ): CategoryRepository =
        DefaultCategoryRepository(categoryDataSource = categoryDataSource)

    @Provides
    @Singleton
    fun providesTaskRepository(
        taskDataSource: TaskDatabaseDataSource,
    ): TaskRepository =
        DefaultTaskRepository(taskDataSource = taskDataSource)

    @Provides
    @Singleton
    fun providesSubTaskRepository(
        subTaskDataSource: SubTaskDatabaseDataSource
    ): SubTaskRepository =
        DefaultSubTaskRepository(subTaskDataSource = subTaskDataSource)

    @Provides
    @Singleton
    fun providesGithubRepository(
        deviceCodeApi: GithubDeviceCodeApi,
        graphQlApi: GithubGraphQlApi,
        tokenDataSource: GithubTokenDataSource
    ): GithubRepository =
        DefaultGithubRepository(
            deviceCodeApi = deviceCodeApi,
            graphQlApi = graphQlApi,
            tokenDataSource = tokenDataSource
        )
}