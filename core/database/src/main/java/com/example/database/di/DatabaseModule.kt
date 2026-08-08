package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.category.CategoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesCategoryDatabase(
        @ApplicationContext context: Context,
    ): CategoryDatabase =
        Room.databaseBuilder(
            context = context,
            klass = CategoryDatabase::class.java,
            name = "category"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
}