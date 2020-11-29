package com.hajarslamah.task_management
import android.app.Application
class TaskApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        TaskRepository.initialize(this)
    }
}