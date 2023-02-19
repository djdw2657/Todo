package com.dj.todo.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.todo.data.ToDoDatabase
import com.dj.todo.data.models.Priority
import com.dj.todo.data.models.ToDoTask
import com.dj.todo.data.repositories.DataStoreRepository
import com.dj.todo.data.repositories.ToDoRepository
import com.dj.todo.util.*
import com.dj.todo.util.Constants.MAX_TITLE_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository,
    application: Application

): ViewModel() {

    val drawerBarState: MutableState<DrawerBarState> =
        mutableStateOf(DrawerBarState.CLOSED)

    val sortByFavorites : StateFlow<List<ToDoTask>> =
        repository.sortByFavorites.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val sortByToday : StateFlow<List<ToDoTask>> =
        repository.sortByToday.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    private val database = ToDoDatabase.getDatabase(application)
    private val toDoDao = database.toDoDao()

    private val getAllTasks: Flow<List<ToDoTask>> = toDoDao.getAllTasks()

    val action : MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val id : MutableState<Int> = mutableStateOf(0)
    val title : MutableState<String> = mutableStateOf("")
    val description : MutableState<String> = mutableStateOf("")
    val priority : MutableState<Priority> = mutableStateOf(Priority.LOW)
    val date : MutableState<String> = mutableStateOf("")
    val favorite : MutableState<Boolean> = mutableStateOf(false)
    val creatorState : MutableState<CreatorState> = mutableStateOf(CreatorState.CLOSED)

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _searchTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchTasks

    fun searchDatabase(searchQuery:String){
        _searchTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%$searchQuery%")
                    .collect { searchedTasks ->
                        _searchTasks.value = RequestState.Success(searchedTasks)
                    }
            }
        } catch (e: Exception) {
                _searchTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    val lowPriorityTasks: StateFlow<List<ToDoTask>> =
        repository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )
    val highPriorityTasks: StateFlow<List<ToDoTask>> =
        repository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    private val _sortTasks =
        MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortTasks: StateFlow<RequestState<Priority>> = _sortTasks

    fun sortingTasks() {
        _sortTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map {Priority.valueOf(it)}
                    .collect {
                    _sortTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _sortTasks.value = RequestState.Error(e)
        }
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    private val _allTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    fun getAllTasks(){
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }



    private fun addTask() {
        viewModelScope.launch {
            val toDoTask = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value,
                date = date.value,
                favorite = favorite.value
            )
            repository.addTask(toDoTask = toDoTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value,
                date = date.value,
                favorite = favorite.value
            )
            repository.updateTask(toDoTask = toDoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value,
                date = date.value,
                favorite = favorite.value
            )
            repository.deleteTask(toDoTask = toDoTask)
        }
    }

    private fun deleteAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }
            Action.UPDATE -> {
                updateTask()
            }
            Action.DELETE -> {
                deleteTask()
            }
            Action.DELETE_ALL -> {
                deleteAllTask()
            }
            Action.UNDO -> {
                addTask()
            }
            else -> {

            }
        }
        this.action.value = Action.NO_ACTION
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null){
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
            date.value = selectedTask.date
            favorite.value = selectedTask.favorite



        }  else {

            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
            date.value = ""
            favorite.value = false


        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title.value = newTitle
        }
    }

    fun validateFields(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty() && date.value.isNotEmpty()
    }
}