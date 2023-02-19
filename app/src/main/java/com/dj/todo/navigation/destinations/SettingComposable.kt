package com.dj.todo.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dj.todo.ui.screens.settings.SettingsScreen
import com.dj.todo.ui.viewmodels.ViewModel
import com.dj.todo.util.Action
import com.dj.todo.util.Constants

fun NavGraphBuilder.settingComposable(
    navigateToListScreen: (Action) -> Unit,
) {
    composable(
        route = Constants.SETTING_SCREEN,
    ) {
        SettingsScreen(navigateToListScreen = navigateToListScreen)
    }
}