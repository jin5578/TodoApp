package com.example.memo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.memo.MemoRoute
import com.example.navigation.Route

fun NavGraphBuilder.memoNavGraph(
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
) = composable<Route.Memo> { navBackStackEntry ->
    val taskId = navBackStackEntry.toRoute<Route.Memo>().taskId
    MemoRoute(
        taskId = taskId,
        popBackStack = popBackStack,
        onShowErrorSnackbar = onShowErrorSnackbar,
    )
}

fun NavController.navigateMemo(taskId: Long) =
    navigate(route = Route.Memo(taskId = taskId))