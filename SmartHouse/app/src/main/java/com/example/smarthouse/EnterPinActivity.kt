package com.example.smarthouse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.smarthouse.databinding.ActivityPinLoginBinding
import com.example.smarthouse.network.RetrofitClient
import kotlinx.coroutines.launch

class EnterPinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPinLoginBinding
    private var enteredPin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Слушатели для цифровых кнопок
        val buttons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener { addDigit(index.toString()) }
        }

        // 2. Кнопка удаления последней цифры
        binding.btnDelete.setOnClickListener {
            if (enteredPin.isNotEmpty()) {
                enteredPin = enteredPin.dropLast(1)
                updateDots()
            }
        }

        // 3. Кнопка выхода из аккаунта (полный логаут)
        binding.btnLogout.setOnClickListener {
            val prefs = getSharedPreferences("SmartHouse", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun addDigit(digit: String) {
        if (enteredPin.length < 4) {
            enteredPin += digit
            updateDots()

            if (enteredPin.length == 4) {
                verifyPinWithServer(enteredPin)
            }
        }
    }

    private fun updateDots() {
        val dots = listOf(
            binding.dot1,
            binding.dot2,
            binding.dot3,
            binding.dot4
        )

        for (i in dots.indices) {
            if (i < enteredPin.length) {
                dots[i].setBackgroundResource(R.drawable.pin_dot_filled)
            } else {
                dots[i].setBackgroundResource(R.drawable.pin_dot_empty)
            }
        }
    }

    private fun verifyPinWithServer(pin: String) {
        val prefs = getSharedPreferences("SmartHouse", Context.MODE_PRIVATE)
        val userId = prefs.getString("USER_ID", "") ?: ""

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getUserById("eq.$userId")

                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    val user = response.body()!![0]

                    if (user.pin == pin) {
                        Toast.makeText(this@EnterPinActivity, "Добро пожаловать!", Toast.LENGTH_SHORT).show()

                        // ВНИМАНИЕ: Проверьте, что класс RoomsActivity существует.
                        // Если он называется по другому, измените имя ниже:
                        val intent = Intent(this@EnterPinActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@EnterPinActivity, "Неверный ПИН-код", Toast.LENGTH_SHORT).show()
                        resetPin()
                    }
                } else {
                    Toast.makeText(this@EnterPinActivity, "Ошибка: пользователь не найден", Toast.LENGTH_SHORT).show()
                    resetPin()
                }
            } catch (e: Exception) {
                Toast.makeText(this@EnterPinActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                resetPin()
            }
        }
    }

    private fun resetPin() {
        enteredPin = ""
        updateDots()
    }
}