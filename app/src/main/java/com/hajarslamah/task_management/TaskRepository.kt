package com.hajarslamah.task_management
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room

import database.TaskMangDatabase
import java.util.concurrent.Executors
private const val DATABASE_NAME = "Task-database"
class TaskRepository  private constructor(context: Context) {
    //////////////////////////////////// Object RoOM///////////////////////
    private val database : TaskMangDatabase = Room.databaseBuilder(
        context.applicationContext,
        TaskMangDatabase::class.java,
        DATABASE_NAME).build()
    //////////////////////////////////////////////////////
    private val taskDao = database.taskDao()

    fun getTasks(): LiveData<List<TaskMang>> = taskDao.getTasks()
    fun getTodo(): LiveData<List<TaskMang>> = taskDao.getTodo()
    fun getPrograss(): LiveData<List<TaskMang>> = taskDao.getPrograss()
    fun getDone(): LiveData<List<TaskMang>> = taskDao.getDone()
    private val executor = Executors.newSingleThreadExecutor()

    fun getTask(id: Int): LiveData<TaskMang?> = taskDao.getTask(id)
    companion object {
        private var INSTANCE: TaskRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }        }
        fun get(): TaskRepository {
            return INSTANCE ?:
            throw IllegalStateException("TaskRepository must be initialized")
        }    }
    fun addTask(task: TaskMang) {
        executor.execute {
            taskDao.addTask(task)
        }  }
    fun deleteTask(task: TaskMang) {
        executor.execute {
            taskDao.deleteTask(task)
        }  }
    fun updateTask(task: TaskMang) {
        executor.execute {
            taskDao.updateTask(task)
        }  }

}
