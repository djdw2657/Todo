package com.dj.todo.data.repositories

import android.app.Application
import com.dj.todo.data.ToDoDao
import com.dj.todo.data.ToDoDatabase
import com.dj.todo.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(
    application: Application,
    ){

    private val database = ToDoDatabase.getDatabase(application)
    private val toDoDao = database.toDoDao()

    val sortByLowPriority: Flow<List<ToDoTask>> = toDoDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<ToDoTask>> = toDoDao.sortByHighPriority()

    val sortByFavorites: Flow<List<ToDoTask>> = toDoDao.getFavoritesList()

    val sortByToday : Flow<List<ToDoTask>> = toDoDao.getTodayList()

    val querySelectAllDefault: Flow<List<ToDoTask>> = toDoDao.querySelectAllDefault()
    val querySelectAllOrderByLast: Flow<List<ToDoTask>> = toDoDao.querySelectAllOrderByLast()

    fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return toDoDao.getSelectedTask(taskId = taskId)
    }

    suspend fun addTask(toDoTask: ToDoTask) {
        toDoDao.addTask(toDoTask = toDoTask)
    }

    suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDao.updateTask(toDoTask = toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.deleteTask(toDoTask = toDoTask)
    }

    suspend fun deleteAllTasks() {
        toDoDao.deleteAllTasks()
    }

    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> {
        return toDoDao.searchDatabase(searchQuery = searchQuery)
    }


}