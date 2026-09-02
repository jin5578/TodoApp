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

    @Serializable
    data class EditTask(
        val taskId: Long
    ) : Route

    @Serializable
    data class Tasks(
        val type: String
    )

    @Serializable
    data object Calendar : Route

    @Serializable
    data object LockSetup : Route

    @Serializable
    data object Security

    @Serializable
    data class Memo(
        val taskId: Long
    )
}