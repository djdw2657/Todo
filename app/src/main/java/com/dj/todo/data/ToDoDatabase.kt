package com.dj.todo.data

import android.content.Context
import androidx.room.*
import com.dj.todo.data.models.ToDoTask
import com.dj.todo.util.Constants.DATABASE_NAME
import dagger.Provides
import dagger.hilt.android.scopes.ViewModelScoped
import java.time.LocalDate


@Database(entities = [ToDoTask::class], version = 2)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
