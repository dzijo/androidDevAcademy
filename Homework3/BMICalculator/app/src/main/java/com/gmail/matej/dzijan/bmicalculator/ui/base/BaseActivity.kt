package com.gmail.matej.dzijan.bmicalculator.ui.base

import android.app.Activity
import android.os.Bundle

abstract class BaseActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResourceId())
        setUpUi()
    }

    abstract fun getLayoutResourceId(): Int
    abstract fun setUpUi()
}