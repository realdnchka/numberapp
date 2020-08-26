package com.realdnchka.numberapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        val btnStartGame: Button = findViewById(R.id.start_game)
        val btnExit: Button = findViewById(R.id.exit)

        btnStartGame.setOnClickListener(this::onBtnNewGameClick)
        btnExit.setOnClickListener(this::exitGame)
    }

    private fun onBtnNewGameClick(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun exitGame(view: View) {
        exitProcess(0)
    }

}