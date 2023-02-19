package com.dj.todo.data

import androidx.room.*
import com.dj.todo.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table WHERE id=:taskId")
    fun getSelectedTask(taskId: Int): Flow<ToDoTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(toDoTask: ToDoTask)

    @Update
    suspend fun updateTask(toDoTask: ToDoTask)

    @Delete
    suspend fun deleteTask(toDoTask: ToDoTask)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 WHEN priority LIKE 'U%' THEN 4 END")
    fun sortByLowPriority(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'U%' THEN 1 WHEN priority LIKE 'H%' THEN 2 WHEN priority LIKE 'M%' THEN 3 WHEN priority LIKE 'L%' THEN 4 END")
    fun sortByHighPriority(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table WHERE favorite = 1")
    fun getFavoritesList(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table WHERE date(date) = date('now')")
    fun getTodayList(): Flow<List<ToDoTask>>

    @Query(value = "select * from todo_table") //만들어진 순서대로
    fun querySelectAllDefault(): Flow<List<ToDoTask>>

    @Query("select * from todo_table order by id desc") //최근날짜
    fun querySelectAllOrderByLast(): Flow<List<ToDoTask>>
}