package com.dj.todo.ui.screens.list

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dj.todo.R
import com.dj.todo.components.IntroductionPage2
import com.dj.todo.ui.screens.drawer.DrawerContent
import com.dj.todo.ui.theme.fabBackgroundColor
import com.dj.todo.ui.viewmodels.ViewModel
import com.dj.todo.util.Action
import com.dj.todo.util.CreatorState
import com.dj.todo.util.DrawerBarState
import com.dj.todo.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    navigateToSettingScreen: () -> Unit,
    viewModel: ViewModel,
    onNavigationIconClick: () -> Unit,
) {
    LaunchedEffect(key1 = true) {
        viewModel.getAllTasks()
        viewModel.sortingTasks()
    }

    val action by viewModel.action
    val allTasks by viewModel.allTasks.collectAsState()

    val searchedTasks by viewModel.searchTasks.collectAsState()
    val sortingTasks by viewModel.sortTasks.collectAsState()

    val lowPriorityTasks by viewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by viewModel.highPriorityTasks.collectAsState()

    val sortByFavorites by viewModel.sortByFavorites.collectAsState()
    val sortByToday by viewModel.sortByToday.collectAsState()
    val drawerBarState: DrawerBarState by viewModel.drawerBarState

    val searchAppBarState: SearchAppBarState by viewModel.searchAppBarState
    val searchTextState: String by viewModel.searchTextState

    val creatorState by viewModel.creatorState

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { viewModel.handleDatabaseActions(action = action) },
        onUndoClicked = {
            viewModel.action.value = it
        },
        taskTitle = viewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                viewModel = viewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState,
                onNavigationIconClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {

            DrawerContent(
                viewModel = viewModel,
                navigateToSettingScreen = navigateToSettingScreen,
                closeOnClick = {
                    coroutineScope.launch {
                        delay(timeMillis = 250)
                        scaffoldState.drawerState.close()
                    }
                }
            )
        },
        content = {

            if (creatorState == CreatorState.OPEN) {
                IntroductionPage2(creatorState = {
                    viewModel.creatorState.value = it
                })
            } else {
                ListContent(
                    allTasks = allTasks,
                    searchedTasks = searchedTasks,
                    lowPriorityTasks = lowPriorityTasks,
                    highPriorityTasks = highPriorityTasks,
                    sortingTasks = sortingTasks,
                    searchAppBarState = searchAppBarState,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = { action, task ->
                        viewModel.action.value = action
                        viewModel.updateTaskFields(selectedTask = task)
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()

                    },
                    sortByFavorites = sortByFavorites,
                    sortByToday = sortByToday,
                    drawerBarState = drawerBarState
                )
            }
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}



@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        backgroundColor = MaterialTheme.colors.fabBackgroundColor,
        onClick = {
            onFabClicked(-1)
        }) {
        Icon(
            imageVector = Icons.Filled.Add,

            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White,
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    handleDatabaseActions()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action = action, taskTitle = taskTitle),
                    actionLabel = setActionLabel(action)
                )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

private fun setMessage(action: Action, taskTitle: String): String {
    return when(action) {
        Action.DELETE_ALL -> "All Tasks Removed"
        else -> "${action.name}: $taskTitle"
    }
}


private fun setActionLabel(action: Action): String {
    return when (action.name) {
        "DELETE" -> {
            "UNDO"
        }
        else -> {
            "OK"
        }
    }
}



private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}
