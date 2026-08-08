package com.example.database.category

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CategoryEntity::class], version = 1, exportSchema = false)
abstract class CategoryDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}