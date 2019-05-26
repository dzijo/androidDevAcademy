package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.view.View
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.EXTRA_TASK_ID
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.persistence.TaskieRoomRepository
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*

class TaskDetailsFragment : BaseFragment(), ChangePriorityFragmentDialog.PriorityChangedListener {

    private val repository = TaskieRoomRepository()
    private var taskID = NO_TASK
    private lateinit var priority: Priority

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_task_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
    }

    private fun tryDisplayTask(id: Int) {
        try {
            val task = repository.getTaskById(id)
            displayTask(task)
        } catch (e: NoSuchElementException) {
            context?.displayToast(getString(R.string.noTaskFound))
        }
    }

    private fun displayTask(task: Task) {
        detailsSaveChanges.setOnClickListener { saveChanges(task) }
        detailsTaskTitle.setText(task.title)
        detailsTaskDescription.setText(task.description)
        priority = task.priority
        detailsPriorityView.setBackgroundResource(priority.getColor())
        detailsPriorityView.setOnClickListener { changePriority() }
    }

    private fun saveChanges(task: Task) {
        repository.editTask(task, detailsTaskTitle.text.toString(), detailsTaskDescription.text.toString(), priority)
        activity?.onBackPressed()
    }

    private fun changePriority() {
        val dialog: ChangePriorityFragmentDialog = ChangePriorityFragmentDialog.newInstance()
        dialog.setPriorityChangedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onPriorityChanged(priority: Priority) {
        this.priority = priority
        detailsPriorityView.setBackgroundResource(priority.getColor())
    }



    companion object {
        const val NO_TASK = -1

        fun newInstance(taskId: Int): TaskDetailsFragment {
            val bundle = Bundle().apply { putInt(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}
