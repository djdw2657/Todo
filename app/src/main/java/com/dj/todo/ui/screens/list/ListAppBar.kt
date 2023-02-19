package com.dj.todo.ui.screens.list


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.dj.todo.R
import com.dj.todo.components.DisplayAlertDialog
import com.dj.todo.components.PriorityItem
import com.dj.todo.data.models.Priority
import com.dj.todo.ui.theme.*
import com.dj.todo.ui.viewmodels.ViewModel
import com.dj.todo.util.Action
import com.dj.todo.util.CreatorState
import com.dj.todo.util.SearchAppBarState
import com.dj.todo.util.TrailingIconState


@Composable
fun ListAppBar(
    viewModel: ViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    onNavigationIconClick: () -> Unit
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    viewModel.searchAppBarState.value =
                        SearchAppBarState.OPENED
                    viewModel.creatorState.value = CreatorState.CLOSED
                },
                onSortClicked = {
                    viewModel.persistSortState(it)

                },
                onDeleteAllClicked = {
                    viewModel.action.value = Action.DELETE_ALL

                },
                creatorState = {
                    viewModel.creatorState.value = CreatorState.OPEN
                },
                onNavigationIconClick = onNavigationIconClick
            )
        }

        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = {
                    viewModel.searchTextState.value = it
                    viewModel.creatorState.value = CreatorState.CLOSED
                               },
                onCloseClicked = {
                    viewModel.searchAppBarState.value =
                        SearchAppBarState.CLOSED
                    viewModel.searchTextState.value = ""
                    viewModel.creatorState.value = CreatorState.CLOSED

                },
                onSearchClicked = {
                    viewModel.searchDatabase(searchQuery = it)
                    viewModel.creatorState.value = CreatorState.CLOSED
                }
            )
        }
    }


}

@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllClicked: (Action) -> Unit,
    creatorState: (CreatorState) -> Unit,
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.list_screen_title),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllClicked = onDeleteAllClicked,
                creatorState = creatorState
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Drawer Icon",
                    tint = MaterialTheme.colors.topAppBarContentColor)
            }
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllClicked: (Action) -> Unit,
    creatorState: (CreatorState) -> Unit,

    ) {
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    ListAppBarAction(onDeleteAllClicked = { onDeleteAllClicked(Action.DELETE_ALL) }, creatorState = creatorState)
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(
        onClick = { onSearchClicked() }
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(id = R.string.sort_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false
            }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.LOW)
                },

                ) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.HIGH)
                },

                ) {
                PriorityItem(priority = Priority.HIGH)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.NONE)
                },

                ) {
                PriorityItem(priority = Priority.NONE)
            }
        }
    }
}

@Composable
fun ListAppBarAction(
    onDeleteAllClicked: () -> Unit,
    creatorState: (CreatorState) -> Unit,

    ) {
    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = "Delete All",
        message = "Are you sure you want to delete all tasks?",
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { onDeleteAllClicked()}
    )

    DeleteAllAction(onDeleteAllClicked = {
        openDialog = true
    },
        creatorState = creatorState
        )
}

@Composable
fun DeleteAllAction(
    onDeleteAllClicked: () -> Unit,
    creatorState: (CreatorState) -> Unit,

    ) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = stringResource(id = R.string.delete_all_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }

        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onDeleteAllClicked()
            }
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = LARGE_PADDING),
                    text = stringResource(id = R.string.delete_all_action),
                    style = Typography.subtitle2
                )
            }
            // 만든이 소개
            DropdownMenuItem(onClick = {
                expanded = false
                creatorState(CreatorState.OPEN)
            }
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = LARGE_PADDING)
                        .fillMaxSize()
                    ,
                    text = stringResource(id = R.string.creator),
                    style = Typography.subtitle2
                )
            }
        }
    }
}



@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    var trailingIconState by remember { mutableStateOf(TrailingIconState.READY_TO_DELETE) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = stringResource(id = R.string.search_placeholder),
                    color = Color.LightGray
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.disabled),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search_icon),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    when (trailingIconState) {
                        TrailingIconState.READY_TO_DELETE -> {
                            if (text.isEmpty()) {
                                onCloseClicked()
                            } else {
                                onTextChange("")
                                trailingIconState = TrailingIconState.READY_TO_CLOSE
                            }
                        }
                        TrailingIconState.READY_TO_CLOSE -> {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                                trailingIconState = TrailingIconState.READY_TO_DELETE
                            }
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close_icon),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}


//@Composable
//@Preview
//private fun DefaultListAppBarPreview() {
//    DefaultListAppBar(
//        onSearchClicked = {},
//        onSortClicked = {},
//        onDeleteClicked = {},
//    )
//}

@Composable
@Preview
private fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Search",
        onTextChange = { },
        onCloseClicked = { },
        onSearchClicked = { }
    )
}
