package com.example.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data object Setting : Route

    @Serializable
    data object ManageCategories : Route

    @Serializable
    data class AddTask(
        val date: String,
    ) : Route
}