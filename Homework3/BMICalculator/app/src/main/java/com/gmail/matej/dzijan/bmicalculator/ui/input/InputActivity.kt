package com.gmail.matej.dzijan.bmicalculator.ui.input

import com.gmail.matej.dzijan.bmicalculator.R
import com.gmail.matej.dzijan.bmicalculator.ui.Calculator
import com.gmail.matej.dzijan.bmicalculator.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_input.*

class InputActivity : BaseActivity() {
    override fun getLayoutResourceId(): Int = R.layout.activity_input

    override fun setUpUi() {
        Calculator.activity = this
        calculateButton.setOnClickListener {
            Calculator.calculate(
                heightInput,
                weightInput,
                imageView,
                resultIndexText,
                resultTitleText,
                resultDescriptionText
            )
        }
        resetResult()
    }

    fun resetResult() {
        imageView.setImageResource(0)
        resultIndexText.text = ""
        resultTitleText.text = ""
        resultDescriptionText.text = ""
    }
}
