package com.example.model.home

import com.example.model.Category
import com.example.model.Task

data class Home(
    val tasks: List<Task>,
    val categories: List<Category>,
    val homeSystem: HomeSystem
)