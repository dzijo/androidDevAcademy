package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.model.Priority
import kotlinx.android.synthetic.main.fragment_dialog_change_priority.*

class ChangePriorityFragmentDialog : DialogFragment() {

    private lateinit var priorityChangedListener: PriorityChangedListener

    interface PriorityChangedListener {
        fun onPriorityChanged(priority: Priority)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    fun setPriorityChangedListener(listener: PriorityChangedListener) {
        priorityChangedListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_change_priority, container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }


    private fun initListeners() {
        changePriorityLow.setOnClickListener { changePriority(Priority.LOW) }
        changePriorityMedium.setOnClickListener { changePriority(Priority.MEDIUM) }
        changePriorityHigh.setOnClickListener { changePriority(Priority.HIGH) }
    }

    private fun changePriority(priority: Priority) {
        priorityChangedListener.onPriorityChanged(priority)
        dismiss()
    }

    companion object {
        fun newInstance(): ChangePriorityFragmentDialog {
            return ChangePriorityFragmentDialog()
        }
    }
}