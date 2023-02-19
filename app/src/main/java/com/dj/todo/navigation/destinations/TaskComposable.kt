package com.dj.todo.navigation.destinations


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dj.todo.ui.screens.task.TaskScreen
import com.dj.todo.ui.viewmodels.ViewModel
import com.dj.todo.util.Action
import com.dj.todo.util.Constants.TASK_ARGUMENT_KEY
import com.dj.todo.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    viewModel: ViewModel
){
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY){
            type = NavType.IntType
        })
    ){
        val taskId = it.arguments!!.getInt(TASK_ARGUMENT_KEY)
        LaunchedEffect(key1 = taskId) {
            viewModel.getSelectedTask(taskId = taskId)
        }

        val selectedTask by viewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = selectedTask) {
            if (selectedTask != null || taskId == -1) {
                viewModel.updateTaskFields(selectedTask = selectedTask)
            }
        }

        TaskScreen(navigateToListScreen = navigateToListScreen, viewModel = viewModel, selectedTask = selectedTask)

    }
}