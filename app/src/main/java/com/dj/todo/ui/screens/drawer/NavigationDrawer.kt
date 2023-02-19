package com.dj.todo.ui.screens.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dj.todo.R
import com.dj.todo.ui.theme.topAppBarContentColor
import com.dj.todo.ui.viewmodels.ViewModel
import com.dj.todo.util.DrawerBarState


@Composable
fun DrawerContent(
    closeOnClick: () -> Unit,
    viewModel: ViewModel,
    gradientColors: List<Color> = listOf(Color(0xFFDAB74E), Color(0xFFC46608)),
    navigateToSettingScreen: () -> Unit,

    ) {

  Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColors)),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
      Image(
            modifier = Modifier
                .size(size = 120.dp)
                .padding(top = 36.dp)
                .clip(shape = CircleShape),
            painter = painterResource(id = R.drawable.ic_remember_me),
            contentDescription = "Profile Image",
        )
        // user's name
        Text(
            modifier = Modifier
                .padding(top = 12.dp),
            text = "Dongjin, Lee",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        // user's email
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 30.dp),
            text = "djdw2657@gmail.com",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.White
        )

      Column(modifier = Modifier
          .fillMaxSize()
          .background(brush = Brush.verticalGradient(colors = gradientColors))
          .padding(10.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Top
      ) {

          DrawerItem(icon = painterResource(id = R.drawable.ic_sun), label = "오늘 할일") {
              viewModel.drawerBarState.value = DrawerBarState.TODAY
              closeOnClick()
          }
          DrawerItem(icon = painterResource(id = R.drawable.ic_star), label = "중요") {
              viewModel.drawerBarState.value = DrawerBarState.IMPORTANT
              closeOnClick()
          }
          DrawerItem(icon = painterResource(id = R.drawable.ic_calendar_dark), label = "모든 일정") {
              viewModel.drawerBarState.value = DrawerBarState.PLANNED
              closeOnClick()
          }
          DrawerItem(icon = painterResource(id = R.drawable.ic_settings), label = "Settings")  {
                  navigateToSettingScreen()
          }
          DrawerItem(icon = painterResource(id = R.drawable.ic_sad_face), label = "Logout") {
              viewModel.drawerBarState.value = DrawerBarState.LOGOUT
          }
      }
    }
}


@Composable
fun DrawerItem(
    icon: Painter,
    label: String,
    onClick: () -> Unit
) {
    val iconColor = MaterialTheme.colors.topAppBarContentColor
    val textColor = MaterialTheme.colors.topAppBarContentColor

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                color = textColor,
                style = MaterialTheme.typography.subtitle1,

            )
        }
    }
}





