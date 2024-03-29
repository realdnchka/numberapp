package com.realdnchka.numberapp

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.github.furkankaplan.fkblurview.FKBlurView
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.realdnchka.numberapp.storage.AppPreferences
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {
    private val adLoadCallback = object : RewardedAdLoadCallback() {
        override fun onRewardedAdLoaded() {
            //Toast.makeText(this@GameActivity, "Ad Loaded", Toast.LENGTH_LONG).show()
        }

        override fun onRewardedAdFailedToLoad(adError: LoadAdError) {
            //Toast.makeText(this@GameActivity, "Ad no loaded ${adError}", Toast.LENGTH_LONG).show()
        }
    }

    private lateinit var mp: MediaPlayer
    private var soundsOn: Boolean = true
    private lateinit var mpScores: MediaPlayer
    private lateinit var mpEndGame: MediaPlayer
    private lateinit var btnOne: Button
    private lateinit var btnTwo: Button
    private lateinit var btnThree: Button
    private lateinit var btnFour: Button
    private lateinit var btnFive: Button
    private lateinit var tvTimer: TextView
    private lateinit var btnBack: Button
    private lateinit var rewardedAd: RewardedAd
    private lateinit var popupWindow: PopupWindow

    private var rewardGet: Boolean = false
    private var randomNumber: Int = getRandom()
    private var count = 0
    private var currentScore: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportActionBar?.hide()

        rewardedAd = RewardedAd(
            this,
            "ca-app-pub-6283297848022132/2603369653"
        )

        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
        mp = MediaPlayer.create(this, R.raw.button_select)
        mpScores = MediaPlayer.create(this, R.raw.get_scores)
        mpEndGame = MediaPlayer.create(this, R.raw.end_game)
        btnOne = findViewById(R.id.btn_one)
        btnTwo = findViewById(R.id.btn_two)
        btnThree = findViewById(R.id.btn_three)
        btnFour = findViewById(R.id.btn_four)
        btnFive = findViewById(R.id.btn_five)
        tvTimer = findViewById(R.id.tv_timer)
        btnBack = findViewById(R.id.btn_back)

        soundsOn = AppPreferences(this).getSoundsMode()
        btnBack.setOnClickListener() {
            btnBack.isSelected = true
            soundOnClick()
            timer.cancel()
            if (rewardGet) {
                timerBonus.cancel()
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        gameStart()

        timerBonus.tv = tvTimer
        timer.tv = tvTimer
        timer.start()

        btnOne.setOnClickListener() {
            gameBtnOnClick(btnOne)
        }

        btnTwo.setOnClickListener() {
            gameBtnOnClick(btnTwo)
        }

        btnThree.setOnClickListener() {
            gameBtnOnClick(btnThree)
        }

        btnFour.setOnClickListener() {
            gameBtnOnClick(btnFour)
        }

        btnFive.setOnClickListener() {
            gameBtnOnClick(btnFive)
        }
    }

    private fun popUpDisplay() {
        if (soundsOn) {
            if (mpEndGame.isPlaying) {
                mpEndGame.pause()
            }
            mpEndGame.start()
        }

        val inflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.time_over_popup, null)

        // Initialize a new instance of popup window
        val popupBlurView: FKBlurView = view.findViewById(R.id.popup_layout)
        popupBlurView.setBlurBackground(this@GameActivity, popupBlurView)
        popupWindow = PopupWindow(
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
        view.findViewById<LinearLayout>(R.id.popup_layout_bg).alpha = 0f
        popupBlurView.alpha = 0f
        val animDuration: Long = 1200
        ObjectAnimator.ofFloat(
            view.findViewById<LinearLayout>(R.id.popup_layout_bg),
            "alpha",
            1f
        ).apply {
            duration = animDuration
            start()
        }
        ObjectAnimator.ofFloat(popupBlurView, "alpha", 1f).apply {
            duration = animDuration
            start()
        }

        val tvTotalScores: TextView = view.findViewById(R.id.tv_total_score)
        val tvCongratsLabel: TextView = view.findViewById(R.id.tv_congrats_label)
        val ivScoreIcon: ImageView = view.findViewById(R.id.iv_score_icon)

        val congratsWords: Array<String> =
            arrayOf("VERY GOOD", "IMPRESSIVE", "BRILLIANT", "AWESOME")
        tvTotalScores?.text = "+${currentScore}"

        if (AppPreferences(this@GameActivity).getHighScore() >= currentScore) {
            tvCongratsLabel?.text = "${congratsWords.random()}!"
        } else {
            tvCongratsLabel?.text = "NEW RECORD!"
            AppPreferences(this@GameActivity).saveHighScore(currentScore)
        }

        pulsingAnimImage(ivScoreIcon, 48, 60)
        pulsingAnim(tvTotalScores, 24, 30)
        pulsingAnim(tvCongratsLabel, 16, 20)
        val btnMenu: Button = view.findViewById(R.id.popup_main_menu)
        val btnAd: Button = view.findViewById(R.id.popup_watch_ad)
        val btnStartGame: Button = view.findViewById(R.id.popup_start_game)
        btnAd.isSelected = !rewardedAd.isLoaded
        btnAd.isEnabled = rewardedAd.isLoaded
        btnMenu.setOnClickListener() {
            btnMenu.isSelected = true
            AppPreferences(this).saveTotalScore(tvTotalScores.text.toString().toLong())
            soundOnClick()
            onBackPressed()
        }

        btnAd.setOnClickListener() {
            soundOnClick()
            if (rewardedAd.isLoaded) {
                btnAd.isSelected = true
                val activityContext: Activity = this
                val adCallback = object : RewardedAdCallback() {
                    override fun onRewardedAdClosed() {
                        if (rewardGet) {
                            timerBonus.start()
                            rewardGet = false
                        } else {
                            btnAd.isSelected = false
                            btnAd.isEnabled = true
                        }
                    }

                    override fun onUserEarnedReward(@NonNull reward: RewardItem) {
                        popupWindow.dismiss()
                        btnOne.isEnabled = true
                        btnTwo.isEnabled = true
                        btnThree.isEnabled = true
                        btnFour.isEnabled = true
                        btnFive.isEnabled = true
                        rewardGet = true
                    }

                    override fun onRewardedAdFailedToShow(
                        adError:
                        AdError
                    ) {
//                        Toast.makeText(this@GameActivity, "Error. Please, try again later", Toast.LENGTH_LONG).show()
                    }

                    override fun onRewardedAdOpened() {
                        rewardedAd = RewardedAd(
                            this@GameActivity,
                            "ca-app-pub-6283297848022132/2603369653"
                        )
                        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
                    }
                }
                rewardedAd.show(activityContext, adCallback)
            } else {
//                Toast.makeText(this@GameActivity, "Error. Please, try again later", Toast.LENGTH_LONG).show()
            }
        }

        btnStartGame.setOnClickListener() {
            btnStartGame.isSelected = true
            AppPreferences(this).saveTotalScore(tvTotalScores.text.toString().toLong())
            soundOnClick()
            val intent = Intent(this@GameActivity, GameActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pulsingAnimImage(view: View, startSize: Int, endSize: Int) {
        val animDuration: Long = 750

        val animator = ValueAnimator.ofFloat(startSize.toFloat(), endSize.toFloat())
        animator.duration = animDuration

        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            view.layoutParams.height = animatedValue.toInt()
            view.layoutParams.width = animatedValue.toInt()
        }

        animator.repeatMode = REVERSE
        animator.repeatCount = INFINITE
        animator.start()
    }

    private fun pulsingAnim(view: TextView, startSize: Int, endSize: Int) {
        val animDuration: Long = 750

        val animator = ValueAnimator.ofFloat(startSize.toFloat(), endSize.toFloat())
        animator.duration = animDuration

        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            view.textSize = animatedValue
        }

        animator.repeatMode = REVERSE
        animator.repeatCount = INFINITE
        animator.start()
    }

    private fun gameBtnOnClick(btn: Button) {
        btn.isSelected = !btn.isSelected
        soundOnClick()
        findViewById<TextView>(R.id.tv_number).text = "$randomNumber"
        if (btn.isSelected) {
            count += btn.text.toString().toInt()
            if (count == randomNumber) {
                if (soundsOn) {
                    if (mpScores.isPlaying) {
                        mpScores.pause()
                    }
                    mpScores.start()
                }
                val tvGetScore: TextView = findViewById(R.id.tv_get_score)
                val getScore = (80..120).random()
                tvGetScore.alpha = 1f
                tvGetScore.translationY = 20f
                tvGetScore.text = getScore.toString()
                val animDuration: Long = 1000
                ObjectAnimator.ofFloat(tvGetScore, "translationY", -75f).apply {
                    duration = animDuration
                    start()
                }
                ObjectAnimator.ofFloat(tvGetScore, "alpha", 0f).apply {
                    duration = animDuration
                    start()
                }
                currentScore += getScore
                randomNumber = getRandom()
                gameStart()
            }
        } else {
            count -= btn.text.toString().toInt()
            if (count == randomNumber) {
                if (soundsOn) {
                    if (mpScores.isPlaying) {
                        mpScores.pause()
                    }
                    mpScores.start()
                }
                val tvGetScore: TextView = findViewById(R.id.tv_get_score)
                val getScore = (80..120).random()
                tvGetScore.alpha = 1f
                tvGetScore.translationY = 20f
                tvGetScore.text = getScore.toString()
                val animDuration: Long = 1000
                ObjectAnimator.ofFloat(tvGetScore, "translationY", -75f).apply {
                    duration = animDuration
                    start()
                }
                ObjectAnimator.ofFloat(tvGetScore, "alpha", 0f).apply {
                    duration = animDuration
                    start()
                }
                currentScore += getScore
                randomNumber = getRandom()
                gameStart()
            }
        }
    }

    private fun gameStart() {
        count = 0
        findViewById<TextView>(R.id.tv_number).text = "$randomNumber"
        findViewById<TextView>(R.id.tv_current_score).text = "$currentScore"
        setNumbers(getNumbers(randomNumber))
        btnOne.isSelected = false
        btnTwo.isSelected = false
        btnThree.isSelected = false
        btnFour.isSelected = false
        btnFive.isSelected = false
    }

    private fun setNumbers(

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
                arr[i] = (1 until sum - arr.sum()).random() / 2
            }
        }
        arr[countOfNumbers - 1] = sum - arr.sum() + arr[countOfNumbers - 1]
        for (i in arr.indices) {
            if (arr[i] == 0 || arr[i] == sum) {
                arr[i] = (1 until sum).random()
            }
        }

        return arr.toMutableList().shuffled()
    }

    private fun soundOnClick() {
        if (soundsOn) {
            if (mp.isPlaying) {
                mp.pause()
            }
            mp.start()
        }
    }

    override fun onBackPressed() {
        soundsOn = false
        if (popupWindow.isShowing) {
            AppPreferences(this).saveTotalScore(currentScore.toLong())
        }
        timer.cancel()
        if (rewardGet) {
            timerBonus.cancel()
        }
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

    open class NewCountDownTimer(millisInFuture: Long) : CountDownTimer(millisInFuture, 1000) {
        lateinit var tv: TextView
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

    private var timer = object : NewCountDownTimer(60000) {
        override fun onFinish() {
            btnOne.isEnabled = false
            btnTwo.isEnabled = false
            btnThree.isEnabled = false
            btnFour.isEnabled = false
            btnFive.isEnabled = false
            tv.text = "Time over!"
            popUpDisplay()
        }
    }

    private var timerBonus = object : NewCountDownTimer(15000) {
        override fun onFinish() {
            btnOne.isEnabled = false
            btnTwo.isEnabled = false
            btnThree.isEnabled = false
            btnFour.isEnabled = false
            btnFive.isEnabled = false
            tv.text = "Time over!"
            popUpDisplay()
        }
    }

    override fun onPause() {
        soundsOn = false
        super.onPause()
    }

    override fun onResume() {
        soundsOn = AppPreferences(this).getSoundsMode()
        super.onResume()
    }

    override fun onDestroy() {
        soundsOn = false
        if (popupWindow.isShowing) {
            AppPreferences(this).saveTotalScore(currentScore.toLong())
        }
        timer.cancel()
        if (rewardGet) {
            timerBonus.cancel()
        }
        super.onDestroy()
    }
}
