package com.dj.todo.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dj.todo.R
import com.dj.todo.components.DatePicker
import com.dj.todo.components.FavButton

import com.dj.todo.components.PriorityDropDown
import com.dj.todo.data.models.Priority
import com.dj.todo.ui.theme.*


@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    date : String,
    onDateChange: (String) -> Unit,
    favorite: Boolean,
    onFavoriteSelected: (Boolean) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(all = LARGE_PADDING)
    ) {
        Row(modifier = Modifier.height(PRIORITY_DROP_DOWN_HEIGHT)) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f),
                value = title,
                onValueChange = { onTitleChange(it) },
                label = { Text(text = stringResource(id = R.string.title)) },
                textStyle = MaterialTheme.typography.body1,
                singleLine = true
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = CenterHorizontally) {
                    if (favorite) {
                        Text(modifier = Modifier.padding(all = 3.dp), text = "중요")
                    } else {
                        Text(modifier = Modifier.padding(all = 3.dp), text = "버튼", color = MediumGray)
                    }
                    FavButton(
                        favorite = favorite,
                        onFavoriteSelected = onFavoriteSelected)
                }
            }
        }

        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )
        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )
        DatePicker(date = date, onDateChange = onDateChange)
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(id = R.string.description)) },
            textStyle = MaterialTheme.typography.body1
        )
    }
}



