package com.example.smarthouse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.smarthouse.databinding.ActivityLoginBinding
import com.example.smarthouse.network.RetrofitClient
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.loginUser(
                    email = "eq.$email",
                    password = "eq.$password"
                )

                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    val user = response.body()!![0]
                    val prefs = getSharedPreferences("SmartHouse", Context.MODE_PRIVATE)
                    
                    // Сохраняем ID и PIN (для локальной проверки)
                    prefs.edit().apply {
                        putString("USER_ID", user.id)
                        putString("USER_PIN", user.pin)
                        apply()
                    }

                    Toast.makeText(this@LoginActivity, "Успешный вход!", Toast.LENGTH_SHORT).show()

                    if (!user.pin.isNullOrEmpty()) {
                        prefs.edit().putBoolean("HAS_PIN", true).apply()
                        startActivity(Intent(this@LoginActivity, PinLoginActivity::class.java))
                    } else {
                        val intent = Intent(this@LoginActivity, CreatePinActivity::class.java)
                        intent.putExtra("USER_ID", user.id)
                        startActivity(intent)
                    }
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Неверный email или пароль", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
