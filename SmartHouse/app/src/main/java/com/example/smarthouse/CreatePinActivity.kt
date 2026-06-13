package com.example.smarthouse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.smarthouse.databinding.ActivityCreatePinBinding
import com.example.smarthouse.network.RetrofitClient
import kotlinx.coroutines.launch

class CreatePinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePinBinding
    private var pin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("USER_ID") ?: ""

        val buttons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener { addDigit(index.toString(), userId) }
        }

        binding.btnDelete.setOnClickListener {
            if (pin.isNotEmpty()) {
                pin = pin.dropLast(1)
                updateDots()
            }
        }
    }

    private fun addDigit(digit: String, userId: String) {
        if (pin.length < 4) {
            pin += digit
            updateDots()

            if (pin.length == 4) {
                savePinToSupabase(userId, pin)
            }
        }
    }

    private fun updateDots() {
        val dots = listOf(binding.dot1, binding.dot2, binding.dot3, binding.dot4)
        for (i in dots.indices) {
            dots[i].setBackgroundResource(if (i < pin.length) R.drawable.pin_dot_filled else R.drawable.pin_dot_empty)
        }
    }

    private fun savePinToSupabase(userId: String, pinCode: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.updatePin(
                    userId = "eq.$userId",
                    pinUpdate = mapOf("pin" to pinCode)
                )

                if (response.isSuccessful) {
                    val prefs = getSharedPreferences("SmartHouse", Context.MODE_PRIVATE)
                    prefs.edit().apply {
                        putBoolean("HAS_PIN", true)
                        putString("USER_PIN", pinCode) // Сохраняем ПИН локально для проверки
                        apply()
                    }

                    Toast.makeText(this@CreatePinActivity, "ПИН-код создан", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@CreatePinActivity, PinLoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@CreatePinActivity, "Ошибка сохранения", Toast.LENGTH_SHORT).show()
                    pin = ""
                    updateDots()
                }
            } catch (e: Exception) {
                Toast.makeText(this@CreatePinActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                pin = ""
                updateDots()
            }
        }
    }
}
