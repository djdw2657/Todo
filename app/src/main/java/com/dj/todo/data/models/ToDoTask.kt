package com.dj.todo.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dj.todo.util.Constants.DATABASE_TABLE
import java.time.LocalDate

@Entity(tableName = DATABASE_TABLE)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "priority") val priority: Priority,
    @ColumnInfo(name = "date") val date: String,
//    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "favorite") var favorite: Boolean)

