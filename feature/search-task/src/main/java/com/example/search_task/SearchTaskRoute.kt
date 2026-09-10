package com.example.search_task

import androidx.compose.runtime.Composable

@Composable
internal fun SearchTaskRoute(
    popBackStack: () -> Unit
) {
    SearchTaskContent(popBackStack = popBackStack)
}

@Composable
private fun SearchTaskContent(
    popBackStack: () -> Unit
) {
    SearchTaskScreen(popBackStack = popBackStack)
}