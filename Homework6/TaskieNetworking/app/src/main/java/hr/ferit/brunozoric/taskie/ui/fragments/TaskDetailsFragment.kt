package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.EXTRA_TASK_ID
import hr.ferit.brunozoric.taskie.common.RESPONSE_OK
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.PriorityColor
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.model.request.EditTaskRequest
import hr.ferit.brunozoric.taskie.model.request.GetTaskByIdRequest
import hr.ferit.brunozoric.taskie.networking.BackendFactory
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskDetailsFragment : BaseFragment(), ChangePriorityFragmentDialog.PriorityChangedListener {

    //private val repository = Repository
    private var taskID = ""
    private val interactor = BackendFactory.getTaskieInteractor()
    private var priority: Int = 1

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_task_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
    }

    private fun tryDisplayTask(id: String) {
        try {
            interactor.getTaskById(GetTaskByIdRequest(id), getTaskCallback())
        } catch (e: NoSuchElementException) {
            context?.displayToast(getString(R.string.noTaskFound))
        }
    }

    private fun displayTask(task: BackendTask) {
        detailsSaveChanges.setOnClickListener { saveChanges() }
        detailsTaskTitle.setText(task.title)
        detailsTaskDescription.setText(task.content)
        priority = task.taskPriority
        setPriorityColor(priority)
        detailsPriorityView.setOnClickListener { changePriority() }
    }

    private fun saveChanges() {
        interactor.edit(EditTaskRequest(taskID, detailsTaskTitle.text.toString(), detailsTaskDescription.text.toString(), priority), editTaskCallback())
    }

    private fun editTaskCallback(): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {
        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when(response.code()){
                    RESPONSE_OK -> activity?.onBackPressed()
                    else -> context?.displayToast("Something went wrong")
                }

            }
        }

    }

    private fun getTaskCallback(): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {

        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when(response.code()){
                    RESPONSE_OK -> displayTask(response.body()!!)
                    else -> context?.displayToast("Something went wrong")
                }

            }
        }
    }

    private fun changePriority() {
        val dialog: ChangePriorityFragmentDialog = ChangePriorityFragmentDialog.newInstance()
        dialog.setPriorityChangedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onPriorityChanged(priority: Int) {
        this.priority = priority
        setPriorityColor(priority)
    }

    private fun setPriorityColor(priority: Int) {
        detailsPriorityView.setBackgroundResource(
            when (priority) {
                1 -> PriorityColor.LOW.getColor()
                2 -> PriorityColor.MEDIUM.getColor()
                else -> PriorityColor.HIGH.getColor()
            }
        )
    }

    companion object {
        fun newInstance(taskId: String): TaskDetailsFragment {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}
