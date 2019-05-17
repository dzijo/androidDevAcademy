package hr.ferit.brunozoric.taskie.ui.activities

import com.google.android.material.bottomnavigation.BottomNavigationView
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.activities.base.BaseActivity
import hr.ferit.brunozoric.taskie.ui.fragments.AboutFragment
import hr.ferit.brunozoric.taskie.ui.fragments.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

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

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {
        showFragment(TasksFragment.newInstance())
        bottomNavigation.setOnNavigationItemSelectedListener(this.navListener)
    }


}