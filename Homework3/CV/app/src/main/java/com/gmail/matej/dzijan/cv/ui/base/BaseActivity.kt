package com.gmail.matej.dzijan.cv.ui.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResourceId())
        setUpUi()
    }

    abstract fun getLayoutResourceId(): Int
    abstract fun setUpUi()
}