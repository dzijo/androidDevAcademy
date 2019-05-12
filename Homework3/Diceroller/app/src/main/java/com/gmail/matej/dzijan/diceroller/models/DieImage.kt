package com.gmail.matej.dzijan.diceroller.models

import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import com.gmail.matej.dzijan.diceroller.R
import kotlin.random.Random

class DieImage(val view: ImageView, var rollable: Boolean) {

    companion object {
        val dicePngs = listOf(
            R.drawable.dots_1,
            R.drawable.dots_2,
            R.drawable.dots_3,
            R.drawable.dots_4,
            R.drawable.dots_5,
            R.drawable.dots_6
        )
    }

    fun setOnClickListener() {
        view.setOnClickListener { toggle() }
    }

    private fun toggle() {
        if (DiceRoller.timesRolled != 0) {
            if (rollable) view.setColorFilter(Color.argb(50, 0, 0, 0), PorterDuff.Mode.DARKEN)
            else view.clearColorFilter()
            rollable = rollable.not()
        }
    }

    fun rollTheDie() {
        if (rollable) view.setImageResource(dicePngs[Random.nextInt(0, 5)])
    }

}

fun List<DieImage>.setOnClickListeners() {
    for (s in this) {
        s.setOnClickListener()
    }
}