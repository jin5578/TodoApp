package com.example.database.task

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.utils.LocalDateConverter
import com.example.database.utils.LocalDateTimeConverter
import com.example.database.utils.LocalTimeConverter

@Database(
    entities = [TaskEntity::class, SubTaskEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    LocalTimeConverter::class,
    LocalDateConverter::class,
    LocalDateTimeConverter::class
)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun subTaskDao(): SubTaskDao
}
