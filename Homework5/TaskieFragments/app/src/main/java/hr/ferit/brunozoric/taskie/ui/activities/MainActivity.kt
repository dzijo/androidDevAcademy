package hr.ferit.brunozoric.taskie.ui.activities

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.activities.base.BaseActivity
import hr.ferit.brunozoric.taskie.ui.fragments.AboutFragment
import hr.ferit.brunozoric.taskie.ui.fragments.ClearAllListeners
import hr.ferit.brunozoric.taskie.ui.fragments.SortBy
import hr.ferit.brunozoric.taskie.ui.fragments.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), TasksFragment.SetListener {

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigationTasks -> {
                showFragment(TasksFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationAbout -> {
                showFragment(AboutFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        true
    }

    private var clearAllListener: ClearAllListeners? = null
    private var sortBy: SortBy? = null

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        supportFragmentManager.fragments.last()?.let {
            if (it is TasksFragment) {
                menuInflater.inflate(R.menu.menu_taskie, menu)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sortByPriority -> sort(item)
            R.id.clearAllTasksMenuItem -> showClearAllDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sort(item: MenuItem) {
        if (item.title == getString(R.string.sort_tasks_by_priority)) {
            item.title = getString(R.string.sort_tasks_by_title)
            sortBy!!.sortByPriority()
        } else {
            item.title = getString(R.string.sort_tasks_by_priority)
            sortBy!!.sortByTitle()
        }

    }

    override fun setListener(fragment: TasksFragment) {
        clearAllListener = fragment
        sortBy = fragment
    }

    private fun showClearAllDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_all_tasks))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes) { _, _ -> clearAllListener!!.clearAllTasks() }
            .setNegativeButton(android.R.string.no, null).show()
    }

    override fun setUpUi() {
        val fragment = TasksFragment.newInstance()
        showFragment(fragment)
        bottomNavigation.setOnNavigationItemSelectedListener(this.navListener)
    }


}