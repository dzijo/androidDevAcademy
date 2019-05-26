package hr.ferit.brunozoric.taskie.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import hr.ferit.brunozoric.taskie.ui.adapters.AboutPagerAdapter
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : BaseFragment() {

    override fun getLayoutResourceId() = R.layout.fragment_about

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onResume() {
        super.onResume()
        this.setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = AboutPagerAdapter(this.context!!, childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

}
