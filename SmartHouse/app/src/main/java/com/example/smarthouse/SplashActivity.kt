package com.example.smarthouse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val prefs = getSharedPreferences("SmartHouse", Context.MODE_PRIVATE)
            val hasPin = prefs.getBoolean("HAS_PIN", false)

            if (hasPin) {
                // Если ПИН есть, значит пользователь уже входил
                startActivity(Intent(this, PinLoginActivity::class.java))
            } else {
                // Если ПИН нет, значит это новый пользователь
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            finish()
        }, 2000)
    }
}
