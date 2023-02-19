package com.dj.todo.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Orange = Color(0xFFF7B554)
val DarkOrange = Color(0xFF94710A)
val Red = Color(0xFFA53512)
val Green = Color(0xFF2AC430)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val LowPriorityColor = Color(0xFF75CBF1)
val MediumPriorityColor = Color(0xFF134FE4)
val HighPriorityColor = Color(0xFF0A3C9C)
val UrgentPriorityColor = Color(0xFF920909)
val NonePriorityColor = Color(0xFF979797)

val Colors.timeCreated: Color
    @Composable
    get() = if (isLight) HighPriorityColor else LowPriorityColor

val Colors.splashScreenBackground: Color
    @Composable
    get() = if (isLight) Orange else Color.Black

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGray else LightGray

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) Orange else Orange

val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else LightGray

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Orange else Color.Black