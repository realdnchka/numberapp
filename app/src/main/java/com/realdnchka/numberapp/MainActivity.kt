package com.realdnchka.numberapp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.realdnchka.numberapp.storage.AppPreferences
import com.google.android.gms.ads.MobileAds;


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        MobileAds.initialize(this, "ca-app-pub-6283297848022132~9827113932")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val btnStartGame: Button = findViewById(R.id.btn_start_game)
        val btnExit: Button = findViewById(R.id.btn_exit)
        val btnProfile: Button = findViewById(R.id.btn_profile)
        val btnSettings: Button = findViewById(R.id.btn_settings)
        val btnLeaderboard: Button = findViewById(R.id.btn_leaderboard)
        btnLeaderboard.isEnabled = false
        btnLeaderboard.isSelected = true
        btnStartGame.setOnClickListener(this::onBtnNewGameClick)
        btnExit.setOnClickListener(this::exitGame)
        btnProfile.setOnClickListener(this::onBtnProfileCLick)
        btnSettings.setOnClickListener(this::onBtnSettingsClick)
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

    private fun onBtnSettingsClick(view: View) {
        changeState(view)
        soundOnClick()
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun onBtnProfileCLick(view: View) {
        changeState(view)
        soundOnClick()
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun onBtnNewGameClick(view: View) {
        changeState(view)
        soundOnClick()
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun exitGame(view: View) {
        changeState(view)
        soundOnClick()
        finishAffinity()
    }

    private fun changeState(view: View) {
        view.isSelected = true
        view.isEnabled = false
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun soundOnClick() {
        if (AppPreferences(this).getSoundsMode()) {
            val mp: MediaPlayer = MediaPlayer.create(this, R.raw.button_select)
            mp.start()
        }
    }
}