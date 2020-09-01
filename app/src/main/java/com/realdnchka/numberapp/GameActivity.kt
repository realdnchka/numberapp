package com.realdnchka.numberapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportActionBar?.hide()

        val btnOne: Button = findViewById(R.id.btn_one)
        val btnTwo: Button = findViewById(R.id.btn_two)
        val btnThree: Button = findViewById(R.id.btn_three)
        val btnFour: Button = findViewById(R.id.btn_four)
        val btnFive: Button = findViewById(R.id.btn_five)
        val tvNumber: TextView = findViewById(R.id.tv_number)
        val randomNumber: Int = getRandom()
        var count = 0

        tvNumber.text = "Number: ${randomNumber}"
        setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val decorView = window.decorView
        if (hasFocus) {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    private fun countSum(btn: Button): Int {
        return btn.text.toString().toInt()
    }

    private fun setNumbers(btnOne: Button, btnTwo: Button, btnThree: Button, btnFour: Button, btnFive: Button, arrayOfNumbers: List<Int>) {
        btnOne.text = arrayOfNumbers[0].toString()
        btnTwo.text = arrayOfNumbers[1].toString()
        btnThree.text = arrayOfNumbers[2].toString()
        btnFour.text = arrayOfNumbers[3].toString()
        btnFive.text = arrayOfNumbers[4].toString()
    }

    private fun getRandom(): Int {
        return (15..50).random()
    }

    private fun getNumbers(sum: Int): List<Int> {

        val countOfNumbers = (2..5).random()
        val arr = IntArray(5) {0}
        for (i in 0 until countOfNumbers - 1) {
            if (sum - arr.sum() > 0) {
                val randNumber = (1 until sum - arr.sum()).random()
                arr[i] = randNumber
            }
        }
        arr[countOfNumbers - 1] = sum - arr.sum() + arr[countOfNumbers - 1]
        for (i in arr.indices) {
            if (i > countOfNumbers - 2) {
                while (arr[i] == 0) {
                    val rand = (1 until sum).random()
                    var switchNOD: Boolean = true
                    var switchSUM: Boolean = true
                    if (!arr.contains(rand) && switchNOD && switchSUM) {
                        arr[i] = rand
                    }
                }
            }
        }
        return arr.toList().shuffled()
    }
}