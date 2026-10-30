package com.weatherapp

import android.app.Application
import android.content.Intent
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class WeatherApp : Application() {

    companion object {
        const val FLAGS =
            Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    override fun onCreate() {
        super.onCreate()

        Firebase.auth.addAuthStateListener { auth ->
            val activity = if (auth.currentUser != null) {
                MainActivity::class.java
            } else {
                LoginActivity::class.java
            }

            startActivity(
                Intent(this, activity).setFlags(FLAGS)
            )
        }
    }
}