package com.example.database.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.database.utils.LocalDateConverter
import com.example.database.utils.LocalTimeConverter
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "task")
@TypeConverters(
    LocalTimeConverter::class,
    LocalDateConverter::class
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "uuid")
    val uuid: String,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean = false,
    @ColumnInfo(name = "isRemind")
    val isRemind: Boolean = false,
    @ColumnInfo(name = "time")
    val time: LocalTime = LocalTime.now(),
    @ColumnInfo(name = "date")
    val date: LocalDate = LocalDate.now(),
    @ColumnInfo(name = "epochDay")
    val epochDay: Long = LocalDate.now().toEpochDay(),
    @ColumnInfo(name = "memo")
    val memo: String = "",
    @ColumnInfo(name = "priority")
    val priority: Int = 0,
    @ColumnInfo(name = "categoryId")
    val categoryId: Long = -1L,
    @ColumnInfo(name = "reminderTime")
    val reminderTime: Int = 0,
)