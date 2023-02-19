package com.dj.todo.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.dj.todo.data.models.Priority
import com.dj.todo.data.models.ToDoTask
import com.dj.todo.ui.viewmodels.ViewModel
import com.dj.todo.util.Action


@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit,
    viewModel: ViewModel
) {

    val title: String by viewModel.title
    val description: String by viewModel.description
    val priority: Priority by viewModel.priority
    val date: String by viewModel.date
    val favorite: Boolean by viewModel.favorite

    val context = LocalContext.current

    Scaffold(
        topBar = {
                 TaskAppBar(
                     selectedTask = selectedTask
                 ) {
                     if (it == Action.NO_ACTION) {
                         navigateToListScreen(it)
                     } else {
                         if (viewModel.validateFields()) {
                             navigateToListScreen(it)
                         } else {
                             displayToast(context = context)
                         }
                     }
                 }
        },
        content = {
            TaskContent(
                title = title,
                onTitleChange = {
                    viewModel.updateTitle(it)
                },
                description = description,
                onDescriptionChange = {
                    viewModel.description.value = it
                },
                priority = priority,
                onPrioritySelected = {
                    viewModel.priority.value = it
                },
                date = date,
                onDateChange = {
                    viewModel.date.value = it
                },
                favorite = favorite,
                onFavoriteSelected = {
                    viewModel.favorite.value = it
                }
            )
        },
        )
}


fun displayToast(context: Context) {
    Toast.makeText(
        context, "Fields Empty", Toast.LENGTH_SHORT
    ).show()

}