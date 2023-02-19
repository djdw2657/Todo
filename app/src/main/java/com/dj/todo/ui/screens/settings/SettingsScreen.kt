package com.dj.todo.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dj.todo.ui.theme.topAppBarBackgroundColor
import com.dj.todo.ui.theme.topAppBarContentColor
import com.dj.todo.util.Action

@Composable
fun SettingsScreen(
    navigateToListScreen: (Action) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", color = MaterialTheme.colors.topAppBarContentColor) },
                backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
                navigationIcon = {
                IconButton(
                    onClick = {
                        navigateToListScreen(Action.NO_ACTION)
                    }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back",
                        tint = MaterialTheme.colors.topAppBarContentColor
                        )
                }
            })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SettingItem(
                title = "Notifications",
                description = "Control your notifications",
                icon = Icons.Default.Notifications
            )
            Divider()
            SettingItem(
                title = "Privacy",
                description = "Manage your privacy settings",
                icon = Icons.Default.Warning
            )
            Divider()
            SettingItem(
                title = "Account",
                description = "Manage your account settings",
                icon = Icons.Default.AccountCircle
            )
        }
    }
}

@Composable
fun SettingItem(title: String, description: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = { /* Handle item click */ })
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = description,
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.size(24.dp)
        )
    }
}
