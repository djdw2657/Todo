package com.dj.todo

import android.app.Application
import com.dj.todo.data.ToDoDatabase
import com.dj.todo.data.repositories.ToDoRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToDoApplication: Application()