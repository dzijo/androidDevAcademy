package hr.ferit.brunozoric.taskie.persistence

import hr.ferit.brunozoric.taskie.Taskie
import hr.ferit.brunozoric.taskie.database.DaoProvider
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task

class TaskieRoomRepository : TaskieRepository {

    private var database = DaoProvider.getInstance(Taskie.getAppContext())
    private var taskieDao = database.taskieDao()

    override fun getAllTasks(): MutableList<Task> = taskieDao.getAllTasks()

    override fun getTaskById(id: Int): Task = taskieDao.getTaskById(id)

    override fun addTask(task: Task) {
        taskieDao.addTask(task)
    }

    override fun deleteTask(task: Task) {
        taskieDao.deleteTask(task)
    }

    override fun deleteAllTasks() {
        taskieDao.deleteAllTasks()
    }

    override fun editTask(task: Task, title: String, description: String, priority: Priority) {
        taskieDao.changeTaskTitle(task.id, title)
        taskieDao.changeTaskDescription(task.id, description)
        taskieDao.changeTaskPriority(task.id, priority)
    }

}