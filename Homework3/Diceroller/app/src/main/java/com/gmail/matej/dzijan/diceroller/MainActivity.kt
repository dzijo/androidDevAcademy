package com.gmail.matej.dzijan.diceroller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.gmail.matej.dzijan.diceroller.R.string.rollCounterTextRoll
import com.gmail.matej.dzijan.diceroller.models.DiceRoller
import com.gmail.matej.dzijan.diceroller.models.DieImage
import com.gmail.matej.dzijan.diceroller.models.setOnClickListeners
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonRoll.setOnClickListener { DiceRoller.rollTheDice() }
        setUpDice(listOf(die0, die1, die2, die3, die4, die5))
        setRollCounterText(DiceRoller.timesRolled)
        DiceRoller.mainActivity = this
    }

    private fun setUpDice(list: List<ImageView>) {
        for (s in list) {
            DiceRoller.dieImages.add(DieImage(s, true))
        }
        DiceRoller.dieImages.setOnClickListeners()
    }

    fun setRollCounterText(timesRolled: Int){
        rollCounterText.text = this.getString(rollCounterTextRoll) + (timesRolled + 1)
    }

}
