package com.hajarslamah.task_management

import androidx.lifecycle.ViewModel

class TaskViewModel :ViewModel(){
    private val taskRepository = TaskRepository.get()
    val taskListLiveData  = taskRepository.getTasks()
    val taskToDoListLiveData  = taskRepository.getTodo()
    val taskPrograssListLiveData  = taskRepository.getPrograss()
    val taskDoneListLiveData  = taskRepository.getDone()


    fun addTask(task: TaskMang){
        taskRepository.addTask(task)

    }
    fun updateTask(task: TaskMang){
        taskRepository.updateTask(task)

    }
       fun deleteTask(task: TaskMang){
            taskRepository.deleteTask(task)
 }
}