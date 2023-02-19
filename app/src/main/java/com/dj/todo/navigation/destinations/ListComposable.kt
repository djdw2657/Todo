package com.dj.todo.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dj.todo.ui.screens.list.ListScreen
import com.dj.todo.ui.viewmodels.ViewModel
import com.dj.todo.util.Constants.LIST_ARGUMENT_KEY
import com.dj.todo.util.Constants.LIST_SCREEN
import com.dj.todo.util.toAction

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    navigateToSettingScreen: () -> Unit,
    viewModel: ViewModel,
    onNavigationIconClick: () -> Unit,
){
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY){
            type = NavType.StringType
        })
    ){
        val action = it.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        LaunchedEffect(key1 = action) {
            viewModel.action.value = action
        }

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            viewModel = viewModel,
            onNavigationIconClick = onNavigationIconClick,
            navigateToSettingScreen = navigateToSettingScreen
        )
    }
}