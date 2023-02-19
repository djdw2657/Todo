package com.dj.todo.ui.screens.task
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.dj.todo.R
import com.dj.todo.components.DisplayAlertDialog
import com.dj.todo.data.models.ToDoTask
import com.dj.todo.ui.theme.topAppBarBackgroundColor
import com.dj.todo.ui.theme.topAppBarContentColor
import com.dj.todo.util.Action

@Composable
fun TaskAppBar(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit
) {
    if (selectedTask == null) {
        NewTaskAppBar(navigateToListScreen = navigateToListScreen)
    } else {
        ExistingTaskAppBar(selectedTask = selectedTask, navigateToListScreen = navigateToListScreen)
    }

}

@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
                         BackAction(onBackClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = stringResource(id = R.string.add_task),
            color = MaterialTheme.colors.topAppBarContentColor)

        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            AddAction(onAddClicked = navigateToListScreen)

        }
    )
}



@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(onClick = {onBackClicked(Action.NO_ACTION)}) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back_arrow),
        tint = MaterialTheme.colors.topAppBarContentColor)
    }
}

@Composable
fun AddAction(
    onAddClicked: (Action) -> Unit
) {
    IconButton(onClick = {onAddClicked(Action.ADD)}) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(id = R.string.add_button),
            tint = MaterialTheme.colors.topAppBarContentColor)
    }
}

@Composable
fun ExistingTaskAppBar(
    selectedTask: ToDoTask,
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToListScreen)
        },
        title = {
            Text(
                text =  selectedTask.title,
                color = MaterialTheme.colors.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )

        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            ExistingTaskAppBarActions(selectedTask = selectedTask, navigateToListScreen = navigateToListScreen)
        }
    )
}

@Composable
fun ExistingTaskAppBarActions(
    selectedTask: ToDoTask,
    navigateToListScreen: (Action) -> Unit ,

) {
    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = selectedTask.title,
        message = "Are you sure you want to delete this task?",
        openDialog = openDialog,
        closeDialog = { openDialog = false }
    ) { navigateToListScreen(Action.DELETE) }




    DeleteAction(onDeleteClicked = {
        openDialog = true
    })

    UpdateAction(onUpdateClicked = navigateToListScreen)
}



@Composable
fun CloseAction(
    onCloseClicked: (Action) -> Unit
) {
    IconButton(onClick = {onCloseClicked(Action.NO_ACTION)}) {
        Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(id = R.string.close_icon),
            tint = MaterialTheme.colors.topAppBarContentColor)
    }
}

@Composable
fun DeleteAction(
    onDeleteClicked: (Action) -> Unit
) {
    IconButton(onClick = {onDeleteClicked(Action.DELETE)}) {
        Icon(imageVector = Icons.Filled.Delete, contentDescription = stringResource(id = R.string.delete_icon),
            tint = MaterialTheme.colors.topAppBarContentColor)
    }
}

@Composable
fun UpdateAction(
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(onClick = {onUpdateClicked(Action.UPDATE)}) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(id = R.string.update_icon),
            tint = MaterialTheme.colors.topAppBarContentColor)
    }
}


@Composable
@Preview
fun NewTaskAppBarPreview() {
    NewTaskAppBar(navigateToListScreen = {})
}

