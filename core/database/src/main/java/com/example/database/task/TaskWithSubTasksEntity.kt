package com.example.database.task

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithSubTasksEntity(
    @Embedded val taskEntity: TaskEntity,
    @Relation(parentColumn = "id", entityColumn = "parentId")
    val subTaskEntities: List<SubTaskEntity>,
)
