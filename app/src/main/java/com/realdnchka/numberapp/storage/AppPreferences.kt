package com.realdnchka.numberapp.storage

import android.content.Context
import android.content.SharedPreferences

class AppPreferences (context: Context) {
    private var data: SharedPreferences = context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)

    fun soundsOn() {
        data.edit().putBoolean("SOUNDS", true).apply()
    }

    fun soundsOff() {
        data.edit().putBoolean("SOUNDS", false).apply()
    }

    fun getSoundsMode(): Boolean {
        return data.getBoolean("SOUNDS", true)
    }

    fun saveTotalScore(totalScore: Long) {
        data.edit().putLong("TOTAL_SCORE", data.getLong("TOTAL_SCORE", 0) + totalScore).apply()
    }

    fun getTotalScore(): Long {
        return data.getLong("TOTAL_SCORE", 0)
    }

    fun saveHighScore(highScore: Int) {
        data.edit().putInt("HIGH_SCORE", highScore).apply()
    }

    fun getHighScore(): Int {
        return data.getInt("HIGH_SCORE", 0)
    }
}