package com.dj.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dj.todo.navigation.SetupNavigation
import com.dj.todo.ui.theme.TodoTheme
import com.dj.todo.ui.viewmodels.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val viewModel : ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                navController = rememberNavController()
                SetupNavigation(navController = navController, viewModel = viewModel, onNavigationIconClick = {  })
            }
        }
    }
}

