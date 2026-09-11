package com.example.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Tasks : Route

    @Serializable
    data object Calendar : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data object Setting : Route

    @Serializable
    data object ManageCategories : Route

    @Serializable
    data class EditTask(
        val taskId: Long
    ) : Route

    @Serializable
    data object LockSetup : Route

    @Serializable
    data object Security

    @Serializable
    data class Memo(
        val taskId: Long
    )

    @Serializable
    data object CompletedTasks

    @Serializable
    data object SearchTask
}