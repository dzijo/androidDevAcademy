package com.gmail.matej.dzijan.bmicalculator.ui.model

import com.gmail.matej.dzijan.bmicalculator.R

enum class Output(val image: Int, val title: Int, val description: Int) {

    UNDERWEIGHT(R.drawable.underweight, R.string.underweightTextTitle, R.string.underweightTextDescription),
    NORMAL(R.drawable.normal, R.string.normalTextTitle, R.string.normalTextDescription),
    OVERWEIGHT(R.drawable.overweight, R.string.overweightTextTitle, R.string.overweightTextDescription),
    OBESE(R.drawable.obese, R.string.obeseTextTitle, R.string.obeseTextDescription)

}