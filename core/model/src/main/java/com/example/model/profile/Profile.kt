package com.example.model.profile

import com.example.model.Category
import com.example.model.Task

data class Profile(
    val tasks: List<Task>,
    val categories: List<Category>,
    val profileSystem: ProfileSystem
)