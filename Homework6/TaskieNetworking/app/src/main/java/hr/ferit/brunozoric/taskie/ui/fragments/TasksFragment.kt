package hr.ferit.brunozoric.taskie.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.*
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.DeleteTaskRequest
import hr.ferit.brunozoric.taskie.model.response.BasicResponse
import hr.ferit.brunozoric.taskie.model.response.GetTasksResponse
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import hr.ferit.brunozoric.taskie.persistence.Repository
import hr.ferit.brunozoric.taskie.ui.activities.ContainerActivity
import hr.ferit.brunozoric.taskie.ui.adapters.TaskAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener {

    private val adapter by lazy { TaskAdapter({ onItemSelected(it) }, { onLongClick(it) }) }

    private val interactor = BackendFactory.getTaskieInteractor()

    companion object {
        fun newInstance(): Fragment {
            return TasksFragment()
        }
    }

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    private fun initUi() {
        progress.visible()
        noData.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter
        refreshLayout.setOnRefreshListener { onRefreshListener() }
        getAllTasks()
    }

    private fun onRefreshListener()  {
        getAllTasks()
    }

    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
    }

    override fun onResume() {
        super.onResume()
        getAllTasks()
    }

    private fun onItemSelected(task: BackendTask) {
        Toast.makeText(this.context, task.id, Toast.LENGTH_LONG).show()
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID, task.id)
        }
        startActivity(detailsIntent)
    }

    private fun onLongClick(it: BackendTask): Boolean {
        val c: Context? = this.context
        if (c != null) {
            AlertDialog.Builder(c)
                .setTitle(getString(R.string.delete_this_task) + it.title + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    interactor.delete(DeleteTaskRequest(it.id), deleteTaskCallback())
                    Toast.makeText(c, "Task deleted", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton(android.R.string.no, null).show()
        }
        return true
    }

    override fun onTaskAdded(task: BackendTask) {
        adapter.addData(task)
        noData.gone()
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun getAllTasks() {
        progress.visible()
        interactor.getTasks(getTaskieCallback())
    }

    private fun getTaskieCallback(): Callback<GetTasksResponse> = object : Callback<GetTasksResponse> {
        override fun onFailure(call: Call<GetTasksResponse>?, t: Throwable?) {
            progress.gone()
            refreshLayout.isRefreshing = false
        }

        override fun onResponse(call: Call<GetTasksResponse>?, response: Response<GetTasksResponse>) {
            progress.gone()
            noData.gone()
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse(response)
                    else -> handleSomethingWentWrong()
                }
            }
        }
    }

    private fun deleteTaskCallback(): Callback<BasicResponse> = object : Callback<BasicResponse> {
        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            progress.gone()

        }

        override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
            progress.gone()

            getAllTasks()

        }
    }

    private fun handleOkResponse(response: Response<GetTasksResponse>) {
        response.body()?.notes?.run {
            checkList(this)
            onTaskiesReceived(this)
        }
        refreshLayout.isRefreshing = false
    }

    private fun handleSomethingWentWrong() {
        refreshLayout.isRefreshing = false
        this.activity?.displayToast("Something went wrong!")
    }

    private fun checkList(notes: MutableList<BackendTask>) {
        if (notes.isEmpty()) {
            noData.visible()
        } else {
            noData.gone()
        }
    }

    private fun onTaskiesReceived(taskies: MutableList<BackendTask>) = adapter.setData(taskies)

}