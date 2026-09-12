package com.example.model.profile

import com.example.model.Task

data class Profile(
    val tasks: List<Task>,
    val profileSystem: ProfileSystem
)