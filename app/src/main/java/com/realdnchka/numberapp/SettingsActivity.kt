package com.realdnchka.numberapp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.realdnchka.numberapp.storage.AppPreferences

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()
        val cbSound: CheckBox = findViewById(R.id.cb_settings)
        val btnBack: Button = findViewById(R.id.btn_back)
        btnBack.setOnClickListener() {
            btnBack.isSelected = true
            soundOnClick()
            onBackPressed()
        }
        cbSound.isChecked = AppPreferences(this).getSoundsMode()
        cbSound.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->
            if (compoundButton.isChecked) {
                AppPreferences(this).soundsOn()
            } else {
                AppPreferences(this).soundsOff()
            }
        }
        cbSound.setOnClickListener() {
            soundOnClick()
        }
    }

    private fun soundOnClick() {
        if (AppPreferences(this).getSoundsMode()) {
            val mp: MediaPlayer = MediaPlayer.create(this, R.raw.button_select)
            mp.start()
        }
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