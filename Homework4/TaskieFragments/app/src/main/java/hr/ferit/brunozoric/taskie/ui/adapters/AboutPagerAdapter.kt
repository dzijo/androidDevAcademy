package hr.ferit.brunozoric.taskie.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.fragments.AboutAppFragment
import hr.ferit.brunozoric.taskie.ui.fragments.AboutAuthorFragment


class AboutPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    // This determines the fragment for each tab
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            AboutAuthorFragment()
        } else {
            AboutAppFragment()
        }
    }

    // This determines the number of tabs
    override fun getCount(): Int {
        return 2
    }

    // This determines the title for each tab
    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return when (position) {
            0 -> mContext.getString(R.string.categoryAboutAuthor)
            1 -> mContext.getString(R.string.categoryAboutApp)
            else -> null
        }
    }

}