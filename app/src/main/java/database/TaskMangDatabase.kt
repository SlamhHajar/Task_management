package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hajarslamah.task_management.TaskMang


@Database(entities = [TaskMang::class] , version=1)
@TypeConverters(TaskTypeConverters::class)
abstract class TaskMangDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskMangDao
}
