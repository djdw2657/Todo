package com.dj.todo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dj.todo.navigation.destinations.listComposable
import com.dj.todo.navigation.destinations.settingComposable
import com.dj.todo.navigation.destinations.splashComposable
import com.dj.todo.navigation.destinations.taskComposable
import com.dj.todo.ui.viewmodels.ViewModel
import com.dj.todo.util.Constants.SPLASH_SCREEN

@Composable
fun SetupNavigation(
    navController: NavHostController,
    viewModel: ViewModel,
    onNavigationIconClick: () -> Unit,
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable(
            navigateToListScreen = screen.splash
        )
        settingComposable(
            navigateToListScreen = screen.task,
        )
        listComposable(
            navigateToSettingScreen = screen.setting,
            navigateToTaskScreen = screen.list,
            viewModel = viewModel,
            onNavigationIconClick = onNavigationIconClick,

        )
        taskComposable(
            navigateToListScreen = screen.task,
            viewModel = viewModel
        )
    }
}