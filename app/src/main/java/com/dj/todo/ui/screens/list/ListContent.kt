package com.dj.todo.ui.screens.list

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.dj.todo.R
import com.dj.todo.data.models.Priority
import com.dj.todo.data.models.ToDoTask
import com.dj.todo.ui.theme.*
import com.dj.todo.util.Action
import com.dj.todo.util.DrawerBarState
import com.dj.todo.util.RequestState
import com.dj.todo.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListContent(
    allTasks: RequestState<List<ToDoTask>>,
    searchedTasks: RequestState<List<ToDoTask>>,
    sortByFavorites : List<ToDoTask>,
    sortByToday : List<ToDoTask>,
    drawerBarState: DrawerBarState,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortingTasks: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,


) {

    if (sortingTasks is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {

                    HandleListContent(
                        tasks = searchedTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen,
                        )
                }
            }
            drawerBarState == DrawerBarState.IMPORTANT -> {
                Log.d("importantex", "ListContent: $sortByFavorites")
                HandleListContent(
                    tasks = sortByFavorites,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToTaskScreen,
                )
            }

            drawerBarState == DrawerBarState.TODAY -> {

                HandleListContent(
                    tasks = sortByToday,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToTaskScreen,
                )
                Log.d("todaydate", "ListContent: $sortByToday")
            }




            sortingTasks.data == Priority.NONE -> {
                if (allTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = allTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen,
                    )
                }
            }
            sortingTasks.data == Priority.HIGH -> {
                Log.d("importantex", "ListContent: $highPriorityTasks")
                    HandleListContent(
                        tasks = highPriorityTasks,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen,
                    )
            }
            sortingTasks.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToTaskScreen,
                )
            }
        }
    }
}

@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(
            tasks = tasks,
            navigateToTaskScreen = navigateToTaskScreen,
            onSwipeToDelete = onSwipeToDelete,
            )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    LazyColumn {
        items(
            items = tasks,
            key = { task ->
                task.id
            }) { task ->

            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                SideEffect {
                    scope.launch {
                        delay(300)
                        onSwipeToDelete(Action.DELETE, task)
                    }
                }
            }

            val degrees by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default)
                    0f
                else
                    -45f
            )

            var itemAppeared by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = true){
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(fraction = 0.7f) },
                    background = { RedBackground(degrees = degrees) },
                    dismissContent = {
                        TaskItem(
                            toDoTask = task,
                            navigateToTaskScreen = navigateToTaskScreen,
                        )


                    }
                )
            }
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(UrgentPriorityColor)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White
        )
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {

    Surface(modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(toDoTask.id)
        }) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .width(PRIORITY_INDICATOR_SIZE)
                            .height(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = toDoTask.priority.color
                        )
                    }
                }
            }
            Row {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(8f),
                    text = toDoTask.description,
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Complete Date: ${toDoTask.date}",
                    color = MaterialTheme.colors.timeCreated,
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = 1
                )
        }
    }
}


