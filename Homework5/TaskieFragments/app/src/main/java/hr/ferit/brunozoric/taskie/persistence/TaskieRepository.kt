package hr.ferit.brunozoric.taskie.persistence

import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task

interface TaskieRepository {

    fun getAllTasks(): MutableList<Task>

    fun getTaskById(id: Int): Task

    fun addTask(task: Task)

    fun deleteTask(task: Task)

    fun editTask(task: Task, title: String, description: String, priority: Priority)
}