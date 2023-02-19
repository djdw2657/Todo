package com.dj.todo.navigation

import androidx.navigation.NavHostController
import com.dj.todo.util.Action
import com.dj.todo.util.Constants.LIST_SCREEN
import com.dj.todo.util.Constants.SETTING_SCREEN
import com.dj.todo.util.Constants.SPLASH_SCREEN

class Screens (navController: NavHostController) {

    val splash: () -> Unit = {
        navController.navigate(route = "list/${Action.NO_ACTION}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }
    val setting: () -> Unit = {
        navController.navigate(route = "setting") {
            popUpTo(SETTING_SCREEN) { inclusive = true }
        }
    }

    val list: (Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")
    }
    val task: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }


}