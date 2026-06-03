package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthouse.databinding.ActivityCreatePinBinding

class CreatePinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePinBinding

    private var pin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatePinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener { addDigit("1") }
        binding.btn2.setOnClickListener { addDigit("2") }
        binding.btn3.setOnClickListener { addDigit("3") }

        binding.btn4.setOnClickListener { addDigit("4") }
        binding.btn5.setOnClickListener { addDigit("5") }
        binding.btn6.setOnClickListener { addDigit("6") }

        binding.btn7.setOnClickListener { addDigit("7") }
        binding.btn8.setOnClickListener { addDigit("8") }
        binding.btn9.setOnClickListener { addDigit("9") }

        binding.btn0.setOnClickListener { addDigit("0") }

        binding.btnDelete.setOnClickListener {

            if (pin.isNotEmpty()) {
                pin = pin.dropLast(1)
                updateDots()
            }
        }
    }

    private fun addDigit(digit: String) {

        if (pin.length < 4) {

            pin += digit

            updateDots()

            if (pin.length == 4) {

                startActivity(
                    Intent(
                        this,
                        PinLoginActivity::class.java
                    )
                )

                finish()
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

            if (i < pin.length) {
                dots[i].setBackgroundResource(R.drawable.pin_dot_filled)
            } else {
                dots[i].setBackgroundResource(R.drawable.pin_dot_empty)
            }
        }
    }
}