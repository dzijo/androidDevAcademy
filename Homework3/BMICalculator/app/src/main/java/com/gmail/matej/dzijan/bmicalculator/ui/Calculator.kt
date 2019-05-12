package com.gmail.matej.dzijan.bmicalculator.ui

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.gmail.matej.dzijan.bmicalculator.R
import com.gmail.matej.dzijan.bmicalculator.ui.input.InputActivity
import com.gmail.matej.dzijan.bmicalculator.ui.model.Output
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlin.math.pow


object Calculator {

    var activity = InputActivity()

    fun calculate(
        heightInput: EditText,
        weightInput: EditText,
        imageView: ImageView,
        resultIndexText: TextView,
        resultTitleText: TextView,
        resultDescriptionText: TextView
    ) {
        var inputIsValid = false
        var bmiGroup: Output

        heightInput.validator().nonEmpty().validNumber().greaterThanOrEqual(1.4).lessThanOrEqual(2.5).addErrorCallback {
            Toast.makeText(activity, activity.getString(R.string.invalidHeightInput), Toast.LENGTH_SHORT).show()
            activity.resetResult()
        }.addSuccessCallback {
            inputIsValid = weightInput.validator().nonEmpty().validNumber().greaterThanOrEqual(40).lessThanOrEqual(350)
                .addErrorCallback {
                    Toast.makeText(activity, activity.getString(R.string.invalidWeightInput), Toast.LENGTH_SHORT).show()
                    activity.resetResult()
                }.check()
        }.check()

        if (inputIsValid) {
            val bmi: Double = weightInput.text.toString().toDouble() / (heightInput.text.toString().toDouble()).pow(2)

            bmiGroup = when {
                bmi < 18.5 -> Output.UNDERWEIGHT
                bmi < 25 -> Output.NORMAL
                bmi < 30 -> Output.OVERWEIGHT
                else -> Output.OBESE
            }

            imageView.setImageResource(bmiGroup.image)
            resultIndexText.text = activity.getString(R.string.bmiFormatString).format(bmi)
            resultTitleText.text = activity.getString(bmiGroup.title)
            resultDescriptionText.text = activity.getString(bmiGroup.description)
        }
    }
}