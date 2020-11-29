package com.hajarslamah.task_management

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class TaskMang(@PrimaryKey val id : UUID = UUID.randomUUID(),
                     var title_task:String="",
                     var details:String="",
                     var time_end:Date=Date(),
                     var status_task:Int=1)