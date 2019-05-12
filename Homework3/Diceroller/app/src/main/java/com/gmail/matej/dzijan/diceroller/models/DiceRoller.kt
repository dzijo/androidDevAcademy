package com.gmail.matej.dzijan.diceroller.models

import android.widget.Toast
import com.gmail.matej.dzijan.diceroller.MainActivity

object DiceRoller {
    var timesRolled: Int = 0
    var dieImages = mutableListOf<DieImage>()
    var mainActivity: MainActivity = MainActivity()

    fun rollTheDice() {
        timesRolled++
        for (s in dieImages) {
            s.rollTheDie()
        }
        mainActivity.setRollCounterText(timesRolled)
        if (timesRolled >= 3){
            timesRolled = 0
            mainActivity.setRollCounterText(timesRolled)
            Toast.makeText(mainActivity, "Round over!", Toast.LENGTH_SHORT).show()
            resetDice()
        }
    }

    private fun resetDice(){
        for (s in dieImages){
            s.rollable = true
            s.view.clearColorFilter()
        }
    }
}