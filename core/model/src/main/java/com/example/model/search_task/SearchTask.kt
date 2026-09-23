package com.example.model.search_task

import com.example.model.Task

data class SearchTask(
    val tasks: List<Task>,
    val searchTaskSystem: SearchTaskSystem,
)
