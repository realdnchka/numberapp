package com.realdnchka.numberapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val btnStartGame: Button = findViewById(R.id.btn_start_game)
        val btnExit: Button = findViewById(R.id.btn_exit)
        val btnOptions: Button = findViewById(R.id.btn_settings)
        btnStartGame.isSelected = false
        btnStartGame.setOnClickListener(this::onBtnNewGameClick)
        btnExit.setOnClickListener(this::exitGame)
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

    private fun onBtnNewGameClick(view: View) {
        changeState(view)
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun exitGame(view: View) {
        changeState(view)
        finishAffinity()
    }

    private fun changeState(view: View) {
        view.isSelected = true
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}