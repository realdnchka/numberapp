package com.realdnchka.numberapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


//private var count = 0
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
        val tvCurrentScore: TextView = findViewById(R.id.tv_current_score)
        val countTV: TextView = findViewById(R.id.count_curr)
        val stateTV: TextView = findViewById(R.id.state_curr)
        val tvTimer: TextView = findViewById(R.id.tv_timer)
        var currentScore: Int = 0
        var randomNumber: Int = getRandom()
        tvNumber.text = "Number: ${randomNumber}"
        setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))
        var count = 0

        btnOne.setOnClickListener() {
            stateTV.text = btnOne.isSelected.toString()
            btnOne.isSelected = !btnOne.isSelected
            if (btnOne.isSelected) {
                count += btnOne.text.toString().toInt()
                countTV.text = count.toString()
                if (count == randomNumber) {
                    count = 0
                    currentScore += (80..120).random()
                    tvCurrentScore.text = "Current score: ${currentScore}"
                    randomNumber = getRandom()
                    tvNumber.text = "Number: ${randomNumber}"
                    setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))
                    btnOne.isSelected = false
                    btnTwo.isSelected = false
                    btnThree.isSelected = false
                    btnFour.isSelected = false
                    btnFive.isSelected = false
                }
            } else {
                count -= btnOne.text.toString().toInt()
                countTV.text = count.toString()
            }
        }

        btnTwo.setOnClickListener() {
            stateTV.text = btnTwo.isSelected.toString()
            btnTwo.isSelected = !btnTwo.isSelected
            if (btnTwo.isSelected) {
                count += btnTwo.text.toString().toInt()
                countTV.text = count.toString()
                if (count == randomNumber) {
                    count = 0
                    currentScore += (80..120).random()
                    tvCurrentScore.text = "Current score: ${currentScore}"
                    randomNumber = getRandom()
                    tvNumber.text = "Number: ${randomNumber}"
                    setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))
                    btnOne.isSelected = false
                    btnTwo.isSelected = false
                    btnThree.isSelected = false
                    btnFour.isSelected = false
                    btnFive.isSelected = false
                }
            } else {
                count -= btnTwo.text.toString().toInt()
                countTV.text = count.toString()
            }
        }

        btnThree.setOnClickListener() {
            stateTV.text = btnThree.isSelected.toString()
            btnThree.isSelected = !btnThree.isSelected
            if (btnThree.isSelected) {
                count += btnThree.text.toString().toInt()
                countTV.text = count.toString()
                if (count == randomNumber) {
                    count = 0
                    currentScore += (80..120).random()
                    tvCurrentScore.text = "Current score: ${currentScore}"
                    randomNumber = getRandom()
                    tvNumber.text = "Number: ${randomNumber}"
                    setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))
                    btnOne.isSelected = false
                    btnTwo.isSelected = false
                    btnThree.isSelected = false
                    btnFour.isSelected = false
                    btnFive.isSelected = false
                }
            } else {
                count -= btnThree.text.toString().toInt()
                countTV.text = count.toString()
            }
        }

        btnFour.setOnClickListener() {
            stateTV.text = btnFour.isSelected.toString()
            btnFour.isSelected = !btnFour.isSelected
            if (btnFour.isSelected) {
                count += btnFour.text.toString().toInt()
                countTV.text = count.toString()
                if (count == randomNumber) {
                    count = 0
                    currentScore += (80..120).random()
                    tvCurrentScore.text = "Current score: ${currentScore}"
                    randomNumber = getRandom()
                    tvNumber.text = "Number: ${randomNumber}"
                    setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))
                    btnOne.isSelected = false
                    btnTwo.isSelected = false
                    btnThree.isSelected = false
                    btnFour.isSelected = false
                    btnFive.isSelected = false
                }
            } else {
                count -= btnFour.text.toString().toInt()
                countTV.text = count.toString()
            }
        }

        btnFive.setOnClickListener() {
            stateTV.text = btnFive.isSelected.toString()
            btnFive.isSelected = !btnFive.isSelected
            if (btnFive.isSelected) {
                count += btnFive.text.toString().toInt()
                countTV.text = count.toString()
                if (count == randomNumber) {
                    count = 0
                    currentScore += (80..120).random()
                    tvCurrentScore.text = "Current score: ${currentScore}"
                    randomNumber = getRandom()
                    tvNumber.text = "Number: ${randomNumber}"
                    setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))
                    btnOne.isSelected = false
                    btnTwo.isSelected = false
                    btnThree.isSelected = false
                    btnFour.isSelected = false
                    btnFive.isSelected = false
                }
            } else {
                count -= btnFive.text.toString().toInt()
                countTV.text = count.toString()
            }
        }
        NewCountDownTimer(tvTimer).start()
    }

    class NewCountDownTimer(private val tv: TextView) : CountDownTimer(60000, 1000) {
        override fun onTick(p0: Long) {
            tv.text = "00:${p0 / 1000}"
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
}
