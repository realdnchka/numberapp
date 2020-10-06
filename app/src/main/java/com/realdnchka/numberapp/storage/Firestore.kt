package com.realdnchka.numberapp.storage

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Firestore(private val context: Context) {
    private val db = Firebase.firestore
    private val docId = AppPreferences(context).getUserId()
    fun addUser(username: String) {
        val user = hashMapOf(
            "user_name" to username,
            "total_score" to 0,
            "high_score" to 0
        )
        db.collection("users").add(user).addOnSuccessListener { documentReference ->
            AppPreferences(context).saveUserId(documentReference.id)
            AppPreferences(context).addUser(username, context)
        }.addOnFailureListener {
            AppPreferences(context).addUser(username, context)
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
        var list = List<Any>(3) { 111 }
        db.collection("users").document(docId).get().addOnSuccessListener { result ->
            val totalScore: Long = result.get("total_score").toString().toLong()
            val highScore: Int = result.get("high_score").toString().toInt()
            val username: String = result.get("user_name").toString()
            list = listOf(
                totalScore,
                highScore,
                username
            )
        }.addOnFailureListener {
            list = listOf(
                AppPreferences(context).getTotalScore(),
                AppPreferences(context).getHighScore(),
                AppPreferences(context).getUser()
            )
        }
        return list
    }
}
