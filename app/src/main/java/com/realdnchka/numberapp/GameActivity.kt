package com.realdnchka.numberapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.furkankaplan.fkblurview.FKBlurView
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    private var randomNumber: Int = getRandom()
    private var count = 0
    private var currentScore: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportActionBar?.hide()
        val btnOne: Button = findViewById(R.id.btn_one)
        val btnTwo: Button = findViewById(R.id.btn_two)
        val btnThree: Button = findViewById(R.id.btn_three)
        val btnFour: Button = findViewById(R.id.btn_four)
        val btnFive: Button = findViewById(R.id.btn_five)
        val tvTimer: TextView = findViewById(R.id.tv_timer)

        gameStart(btnOne, btnTwo, btnThree, btnFour, btnFive)

        val timer = object : NewCountDownTimer(tvTimer) {
            override fun onFinish() {
                val inflater: LayoutInflater =
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                // Inflate a custom view using layout inflater
                val view = inflater.inflate(R.layout.time_over_popup, null)

                // Initialize a new instance of popup window

                val popupBlurView: FKBlurView = view.findViewById(R.id.popup_layout)
                popupBlurView.setBlurBackground(this@GameActivity, popupBlurView)

                val popupWindow = PopupWindow(
                    view, // Custom view to show in popup window
                    LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
                    LinearLayout.LayoutParams.MATCH_PARENT // Window height
                )

                TransitionManager.beginDelayedTransition(root_layout)
                popupWindow.showAtLocation(
                    root_layout, // Location to display popup window
                    Gravity.CENTER, // Exact position of layout to display popup
                    0, // X offset
                    0 // Y offset
                )

                fun disableButton(btn: Button) {
                    btn.isEnabled = false
                }

                disableButton(btnOne)
                disableButton(btnTwo)
                disableButton(btnThree)
                disableButton(btnFour)
                disableButton(btnFive)
            }
        }

        timer.start()

        btnOne.setOnClickListener() {
            gameBtnOnClick(btnOne, btnOne, btnTwo, btnThree, btnFour, btnFive)
        }

        btnTwo.setOnClickListener() {
            gameBtnOnClick(btnTwo, btnOne, btnTwo, btnThree, btnFour, btnFive)
        }

        btnThree.setOnClickListener() {
            gameBtnOnClick(btnThree, btnOne, btnTwo, btnThree, btnFour, btnFive)
        }

        btnFour.setOnClickListener() {
            gameBtnOnClick(btnFour, btnOne, btnTwo, btnThree, btnFour, btnFive)
        }

        btnFive.setOnClickListener() {
            gameBtnOnClick(btnFive, btnOne, btnTwo, btnThree, btnFour, btnFive)
        }
    }

    private fun gameBtnOnClick(
        btn: Button,
        btnOne: Button,
        btnTwo: Button,
        btnThree: Button,
        btnFour: Button,
        btnFive: Button
    ) {
        btn.isSelected = !btn.isSelected
        findViewById<TextView>(R.id.tv_number).text = "Number: ${randomNumber}"
        if (btn.isSelected) {
            count += btn.text.toString().toInt()
            if (count == randomNumber) {
                currentScore += (80..120).random()
                randomNumber = getRandom()
                gameStart(btnOne, btnTwo, btnThree, btnFour, btnFive)
            }
        } else {
            count -= btn.text.toString().toInt()
        }
    }

    private fun gameStart(
        btnOne: Button,
        btnTwo: Button,
        btnThree: Button,
        btnFour: Button,
        btnFive: Button
    ) {
        count = 0
        findViewById<TextView>(R.id.tv_number).text = "Number: ${randomNumber}"
        findViewById<TextView>(R.id.tv_current_score).text = "Current score: ${currentScore}"
        setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))
        btnOne.isSelected = false
        btnTwo.isSelected = false
        btnThree.isSelected = false
        btnFour.isSelected = false
        btnFive.isSelected = false
    }

    open class NewCountDownTimer(private val tv: TextView) : CountDownTimer(60000, 1000) {
        override fun onTick(p0: Long) {
            if (p0 >= 10000) {
                tv.text = "00:${p0 / 1000}"
            } else {
                tv.text = "00:0${p0 / 1000}"
            }
        }

        override fun onFinish() {
            tv.text = "Time over!"
        }
    }

    private fun setNumbers(
        btnOne: Button,
        btnTwo: Button,
        btnThree: Button,
        btnFour: Button,
        btnFive: Button,
        arrayOfNumbers: List<Int>
    ) {
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
        val arr = IntArray(5) { 0 }
        for (i in 0 until countOfNumbers - 1) {
            if (sum - arr.sum() > 1) {
                val randNumber = (1 until sum - arr.sum()).random()
                arr[i] = randNumber
            }
        }
        arr[countOfNumbers - 1] = sum - arr.sum() + arr[countOfNumbers - 1]
        for (i in arr.indices) {
            while (arr[i] == 0) {
                val rand = (1 until sum).random()
                arr[i] = rand
            }
        }
        return arr.toList().shuffled()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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
}
