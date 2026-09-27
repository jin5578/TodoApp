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
object GithubDatastoreModule {
    private const val GITHUB_TOKEN_DATASTORE_NAME = "GITHUB_TOKEN_PREFERENCES"

    private val Context.githubTokenDataStore by preferencesDataStore(
        GITHUB_TOKEN_DATASTORE_NAME,
    )

    @Provides
    @Singleton
    @Named(value = "githubToken")
    fun providesGithubTokenDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.githubTokenDataStore
}
