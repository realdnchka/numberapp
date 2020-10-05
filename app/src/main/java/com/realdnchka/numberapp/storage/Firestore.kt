package com.realdnchka.numberapp.storage

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Firestore {
    private val db = Firebase.firestore
    var docId: String = "Please, try again"
    var totalScore: Long = 0
    var highScore: Int = 0

    fun addUser(username: String, context: Context) {
        val user = hashMapOf(
            "user_name" to username,
            "total_score" to totalScore,
            "high_score" to highScore
        )
        db.collection("users").add(user).addOnSuccessListener { documentReference ->
            docId = documentReference.id
            AppPreferences(context).saveUserId(docId)
        }.addOnFailureListener {
            docId = "Please, try again"
        }
    }

    fun updateUsername(username: String) {
        val user: HashMap<String, Any> = hashMapOf(
            "user_name" to username
        )
        db.collection("users").document(docId).update(user)
    }

    fun updateTotalScore(username: String, total: Long) {
        val user: HashMap<String, Any> = hashMapOf(
            "user_name" to username,
            "total_score" to total
        )
        db.collection("users").document(docId).update(user)
    }

    fun updateHighScore(username: String, high: Int) {
        val user: HashMap<String, Any> = hashMapOf(
            "user_name" to username,
            "high_score" to high
        )
        db.collection("users").document(docId).update(user)
    }

    fun getUserData(): List<Any> {
        val user = db.collection("users").document(docId).get()
        val totalScore: Long = user.result.get("total_score").toString().toLong()
        val highScore: Int = user.result.get("high_score").toString().toInt()
        val username: String = user.result.get("user_name").toString()
        return listOf(
            totalScore,
            highScore,
            username
        )
    }
}
