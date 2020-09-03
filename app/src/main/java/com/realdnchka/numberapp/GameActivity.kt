package com.realdnchka.numberapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

var buttonTwoPressed = false
var buttonThreePressed = false
var buttonFourPressed = false
var buttonFivePressed = false

private var count = 0
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

        var currentScore: Int = 0
        var randomNumber: Int = getRandom()

        tvNumber.text = "Number: ${randomNumber}"
        setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))

        btnOne.setOnClickListener() {
            btnOne.isSelected = !btnOne.isSelected
            if (btnOne.isSelected) {
                count += btnOne.text.toString().toInt()
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
            }
        }

        btnTwo.setOnClickListener() {
            buttonTwoPressed = !buttonTwoPressed
            if (buttonTwoPressed) {
                count += btnTwo.text.toString().toInt()
                btnTwo.setBackgroundColor(getColor(R.color.btn_pressed))
                if (count == randomNumber) {
                    count = 0
                    currentScore += (80..120).random()
                    tvCurrentScore.text = "Current score: ${currentScore}"
                    randomNumber = getRandom()
                    tvNumber.text = "Number: ${randomNumber}"
                    setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))
                    btnOne.setBackgroundColor(getColor(R.color.cream_white))
                    btnTwo.setBackgroundColor(getColor(R.color.cream_white))
                    btnThree.setBackgroundColor(getColor(R.color.cream_white))
                    btnFour.setBackgroundColor(getColor(R.color.cream_white))
                    btnFive.setBackgroundColor(getColor(R.color.cream_white))
                    btnOne.isPressed = false
                    btnTwo.isPressed = false
                    btnThree.isPressed = false
                    btnFour.isPressed = false
                    btnFive.isPressed = false
                }
            } else {
                count -= btnTwo.text.toString().toInt()
                btnTwo.setBackgroundColor(getColor(R.color.cream_white))
            }
        }
        btnThree.setOnClickListener() {
            buttonThreePressed = !buttonThreePressed
            if (buttonThreePressed) {
                count += btnThree.text.toString().toInt()
                btnThree.setBackgroundColor(getColor(R.color.btn_pressed))
                if (count == randomNumber) {
                    count = 0
                    currentScore += (80..120).random()
                    tvCurrentScore.text = "Current score: ${currentScore}"
                    randomNumber = getRandom()
                    tvNumber.text = "Number: ${randomNumber}"
                    setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))
                    btnOne.setBackgroundColor(getColor(R.color.cream_white))
                    btnTwo.setBackgroundColor(getColor(R.color.cream_white))
                    btnThree.setBackgroundColor(getColor(R.color.cream_white))
                    btnFour.setBackgroundColor(getColor(R.color.cream_white))
                    btnFive.setBackgroundColor(getColor(R.color.cream_white))
                    btnOne.isPressed = false
                    btnTwo.isPressed = false
                    btnThree.isPressed = false
                    btnFour.isPressed = false
                    btnFive.isPressed = false
                }
            } else {
                count -= btnThree.text.toString().toInt()
                btnThree.setBackgroundColor(getColor(R.color.cream_white))
            }
        }
        btnFour.setOnClickListener() {
            buttonFourPressed = !buttonFourPressed
            if (buttonFourPressed) {
                count += btnFour.text.toString().toInt()
                btnFour.setBackgroundColor(getColor(R.color.btn_pressed))
                if (count == randomNumber) {
                    count = 0
                    currentScore += (80..120).random()
                    tvCurrentScore.text = "Current score: ${currentScore}"
                    randomNumber = getRandom()
                    tvNumber.text = "Number: ${randomNumber}"
                    setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))
                    btnOne.setBackgroundColor(getColor(R.color.cream_white))
                    btnTwo.setBackgroundColor(getColor(R.color.cream_white))
                    btnThree.setBackgroundColor(getColor(R.color.cream_white))
                    btnFour.setBackgroundColor(getColor(R.color.cream_white))
                    btnFive.setBackgroundColor(getColor(R.color.cream_white))
                    btnOne.isPressed = false
                    btnTwo.isPressed = false
                    btnThree.isPressed = false
                    btnFour.isPressed = false
                    btnFive.isPressed = false
                }
            } else {
                count -= btnFour.text.toString().toInt()
                btnFour.setBackgroundColor(getColor(R.color.cream_white))
            }
        }
        btnFive.setOnClickListener() {
            buttonFivePressed = !buttonFivePressed
            if (buttonFivePressed) {
                count += btnFive.text.toString().toInt()
                btnFive.setBackgroundColor(getColor(R.color.btn_pressed))
                if (count == randomNumber) {
                    count = 0
                    currentScore += (80..120).random()
                    tvCurrentScore.text = "Current score: ${currentScore}"
                    randomNumber = getRandom()
                    tvNumber.text = "Number: ${randomNumber}"
                    setNumbers(btnOne, btnTwo, btnThree, btnFour, btnFive, getNumbers(randomNumber))
                    btnOne.setBackgroundColor(getColor(R.color.cream_white))
                    btnTwo.setBackgroundColor(getColor(R.color.cream_white))
                    btnThree.setBackgroundColor(getColor(R.color.cream_white))
                    btnFour.setBackgroundColor(getColor(R.color.cream_white))
                    btnFive.setBackgroundColor(getColor(R.color.cream_white))
                    btnOne.isPressed = false
                    btnTwo.isPressed = false
                    btnThree.isPressed = false
                    btnFour.isPressed = false
                    btnFive.isPressed = false
                }
            } else {
                count -= btnFive.text.toString().toInt()
                btnFive.setBackgroundColor(getColor(R.color.cream_white))
            }
        }
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
