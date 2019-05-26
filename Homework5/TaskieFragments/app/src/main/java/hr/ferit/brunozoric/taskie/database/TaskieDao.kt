package hr.ferit.brunozoric.taskie.database

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task

@Dao
interface TaskieDao {

    @Query("SELECT * FROM Task")
    fun getAllTasks(): MutableList<Task>

    @Query("SELECT * FROM Task WHERE id =:id")
    fun getTaskById(id: Int): Task

    @Insert(onConflict = IGNORE)
    fun addTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE FROM Task")
    fun deleteAllTasks()

    @Query("UPDATE Task SET title =:title WHERE id=:id")
    fun changeTaskTitle(id: Int, title: String)

    @Query("UPDATE Task SET description =:description WHERE id=:id")
    fun changeTaskDescription(id: Int, description: String)

    @Query("UPDATE Task SET priority =:priority WHERE id=:id")
    fun changeTaskPriority(id: Int, priority: Priority)

}