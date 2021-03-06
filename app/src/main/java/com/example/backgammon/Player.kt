package com.example.backgammon

import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text

data class Player (val user: User?, var color: String, var mode:Int = MODE_PLAYER) {
    var score = 0
    var scoreBox: LinearLayout? = null
    var direction:Int = 1 // 1 = clockwise, -1 = clockwise
    var dices:ArrayList<Int> = arrayListOf(0, 0)
        set(value) {
            field = value
        }
    var diceset :MutableSet<ArrayList<Int>> = mutableSetOf()
        get() = this.generateDiceset()

    private fun generateDiceset(): MutableSet<ArrayList<Int>> {
        var movesSet:MutableSet<ArrayList<Int>> = mutableSetOf()
        for (matrix in 0 until Math.pow(2.0, dices.size.toDouble()).toInt()) {
            var moves :ArrayList<Int> = arrayListOf()
            var pattern = matrix
            for (index in dices.indices) {
                if (pattern %2 == 1) {
                    moves.add(dices[index])
                }
                pattern = pattern shr 1
            }
            if (moves.size > 0) {
                moves.sort()
                movesSet.add(moves)
            }
        }
        Log.i("diceSet", movesSet.toString())
        return movesSet
    }

    fun addScorebox(elem:LinearLayout) {
        this.scoreBox = elem
        (this.scoreBox!!.getChildAt(0) as TextView).text = this.getUsername()
    }

    fun addScore(points: Int) {
        this.score += points
        (this.scoreBox!!.getChildAt(1) as TextView).text = this.score.toString()
    }

    fun getUsername(): String {
        if (this.user != null) {
            return this.user.login
        }
        if (this.mode == MODE_PLAYER) {
            return "Enemy"
        }
        return "AI"
    }

    fun isAI(): Boolean {
        return this.mode == MODE_AI
    }


    companion object {
        const val MODE_PLAYER = 0
        const val MODE_AI = 1
    }
}