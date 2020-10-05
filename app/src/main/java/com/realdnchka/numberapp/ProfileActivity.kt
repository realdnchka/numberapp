package com.realdnchka.numberapp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.realdnchka.numberapp.storage.AppPreferences
import com.realdnchka.numberapp.storage.Firestore

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        val tvHighScore: TextView = findViewById(R.id.tv_high_score)
        val btnBack: Button = findViewById(R.id.btn_back)
        val tvTotalScore: TextView = findViewById(R.id.tv_total_score)
        btnBack.setOnClickListener() {
            soundOnClick()
            btnBack.isSelected = true
            onBackPressed()
        }
        tvTotalScore.text = AppPreferences(this).getTotalScore().toString()
        tvHighScore.text = AppPreferences(this).getHighScore().toString()
        val etUsername: EditText = findViewById(R.id.et_username)
        val saveBtn: Button = findViewById(R.id.save_button)
        saveBtn.setOnClickListener() {
            AppPreferences(this).addUser(etUsername.text.toString(), this)
        }
        etUsername.setText(AppPreferences(this).getUser())
        val userId: TextView = findViewById(R.id.user_id)
        userId.text = AppPreferences(this).getUserId()
        Firestore.highScore = AppPreferences(this).getHighScore()
        Firestore.totalScore = AppPreferences(this).getTotalScore()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun soundOnClick() {
        if (AppPreferences(this).getSoundsMode()) {
            val mp: MediaPlayer = MediaPlayer.create(this, R.raw.button_select)
            mp.start()
        }
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