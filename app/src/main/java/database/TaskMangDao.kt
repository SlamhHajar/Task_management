package database
import androidx.lifecycle.LiveData
import androidx.room.*
import com.hajarslamah.task_management.TaskMang

@Dao
interface TaskMangDao {
    @Query("SELECT * FROM TaskMang")
       fun getTasks(): LiveData<List<TaskMang>>
    @Query("SELECT * FROM TaskMang WHERE id=(:id)")
      fun getTask(id: Int): LiveData<TaskMang?>
    @Query("SELECT * FROM TaskMang WHERE status_task=1")
    fun getTodo():LiveData<List<TaskMang>>
    @Query("SELECT * FROM TaskMang WHERE status_task=2")
    fun getPrograss():LiveData<List<TaskMang>>
    @Query("SELECT * FROM TaskMang WHERE status_task=3")
    fun getDone(): LiveData<List<TaskMang>>

  @Update
    fun updateTask(taskMang : TaskMang)
    @Insert
    fun addTask(taskMang : TaskMang)
    @Delete
    fun deleteTask(taskMang : TaskMang)

}