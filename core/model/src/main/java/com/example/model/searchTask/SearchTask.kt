package com.example.model.searchTask

import com.example.model.Task

data class SearchTask(
    val tasks: List<Task>,
    val searchTaskSystem: SearchTaskSystem,
)
