package hr.ferit.brunozoric.taskie.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.*
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.persistence.TaskieRoomRepository
import hr.ferit.brunozoric.taskie.ui.activities.ContainerActivity
import hr.ferit.brunozoric.taskie.ui.adapters.TaskAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*
import hr.ferit.brunozoric.taskie.ui.activities.MainActivity


class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener, ClearAllListeners, SortBy {

    private val repository = TaskieRoomRepository()
    private val adapter by lazy { TaskAdapter({ onItemSelected(it) }, { onItemDelete(it) }) }
    private var sortByPriority = false

    interface SetListener {

        fun setListener(fragment: TasksFragment)

    }

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        refreshTasks()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setListener(this)
        refreshTasks()
    }


    private fun initUi() {
        this.setHasOptionsMenu(true)
        progress.visible()
        noData.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter
    }

    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
    }

    private fun onItemSelected(task: Task) {
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID, task.id)
        }
        startActivity(detailsIntent)
    }

    private fun onItemDelete(task: Task) {
        val c: Context? = this.context
        if (c != null) {
            AlertDialog.Builder(c)
                .setTitle(getString(R.string.delete_this_task))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    repository.deleteTask(task)
                    Toast.makeText(c, "Task deleted", Toast.LENGTH_LONG).show()
                    refreshTasks()
                }
                .setNegativeButton(android.R.string.no, null).show()
        }
    }

    private fun refreshTasks() {
        if (progress != null) progress.gone()
        val data = repository.getAllTasks()
        if (data.isNotEmpty()) {
            noData.gone()
            if (sortByPriority) data.sortBy { it.priority.ordinal }
            else data.sortBy { it.title }
        } else {
            noData.visible()
        }
        adapter.setData(data)
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onTaskAdded(task: Task) {
        refreshTasks()
    }

    override fun clearAllTasks() {
        val data = repository.getAllTasks()
        if (data.isNotEmpty()) {
            for (d in data) {
                repository.deleteTask(d)
            }
            refreshTasks()
        }
    }

    override fun sortByPriority() {
        sortByPriority = true
        refreshTasks()
    }

    override fun sortByTitle() {
        sortByPriority = false
        refreshTasks()
    }

    companion object {
        fun newInstance(): TasksFragment {
            return TasksFragment()
        }
    }
}