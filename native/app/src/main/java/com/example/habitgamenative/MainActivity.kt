package com.example.habitgamenative

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.habitgamenative.game.GameActivity
import java.time.Instant
import java.time.OffsetDateTime



class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(getString(R.string.shared_prefs), Context.MODE_PRIVATE)
        enableEdgeToEdge()
        checkTokenDate()
    }

    private fun checkTokenDate() {

        val dateString = sharedPreferences.getString("date", "")
        if(dateString == null || dateString == "") {
            startLoginActivity()
            return
        }
        val odt = OffsetDateTime.parse(dateString)
        val instant = odt.toInstant()

        if(Instant.now().isAfter(instant)) {
            startLoginActivity()
        } else {
            startActivity(Intent(this, GameActivity::class.java))
        }
    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}

