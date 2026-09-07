package com.example.database.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "task")
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
    @ColumnInfo(name = "date")
    val date: LocalDate = LocalDate.now(),
    @ColumnInfo(name = "time")
    val time: LocalDateTime?,
    @ColumnInfo(name = "reminderTime")
    val reminderTime: LocalDateTime?,
    @ColumnInfo(name = "epochDay")
    val epochDay: Long = LocalDate.now().toEpochDay(),
    @ColumnInfo(name = "memoTitle")
    val memoTitle: String = "",
    @ColumnInfo(name = "memoContent")
    val memoContent: String = "",
    @ColumnInfo(name = "memoUpdatedAt")
    val memoUpdatedAt: LocalDateTime?,
    @ColumnInfo(name = "priority")
    val priority: Int = 0,
    @ColumnInfo(name = "categoryId")
    val categoryId: Long = -1L,
    @ColumnInfo(name = "symbol")
    val symbol: Int = -1,
)