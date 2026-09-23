package com.example.data.di

import android.app.NotificationManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NotificationModule {
    @Provides
    @Singleton
    fun providesNotificationManager(
        @ApplicationContext context: Context,
    ): NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}
