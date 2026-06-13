package com.example.smarthouse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthouse.databinding.ActivityPinLoginBinding

class PinLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPinLoginBinding
    private var pin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener { addDigit(index.toString()) }
        }

        binding.btnDelete.setOnClickListener {
            if (pin.isNotEmpty()) {
                pin = pin.dropLast(1)
                updateDots()
            }
        }

        binding.btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun addDigit(digit: String) {
        if (pin.length < 4) {
            pin += digit
            updateDots()

            if (pin.length == 4) {
                val prefs = getSharedPreferences("SmartHouse", Context.MODE_PRIVATE)
                val savedPin = prefs.getString("USER_PIN", "")

                if (pin == savedPin) {
                    val hasAddress = prefs.getBoolean("HAS_ADDRESS", false)
                    if (hasAddress) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        startActivity(Intent(this, AddressActivity::class.java))
                    }
                    finish()
                } else {
                    Toast.makeText(this, "Неверный ПИН-код", Toast.LENGTH_SHORT).show()
                    pin = ""
                    updateDots()
                }
            }
        }
    }

    private fun updateDots() {
        val dots = listOf(binding.dot1, binding.dot2, binding.dot3, binding.dot4)
        for (i in dots.indices) {
            dots[i].setBackgroundResource(if (i < pin.length) R.drawable.pin_dot_filled else R.drawable.pin_dot_empty)
        }
    }
}
