package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.smarthouse.databinding.ActivityRegisterBinding
import com.example.smarthouse.network.SupabaseClient
import com.example.smarthouse.network.models.UserDto
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {

            val name =
                binding.etUsername.text.toString().trim()

            val email =
                binding.etEmail.text.toString().trim()

            val password =
                binding.etPassword.text.toString().trim()

            if (name.isEmpty()) {

                Toast.makeText(
                    this,
                    "Введите имя",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (email.isEmpty()) {

                Toast.makeText(
                    this,
                    "Введите email",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Введите пароль",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            registerUser(
                name,
                email,
                password
            )
        }
    }

    private fun registerUser(
        name: String,
        email: String,
        password: String
    ) {

        lifecycleScope.launch {

            try {

                val response =
                    SupabaseClient.api.registerUser(
                        UserDto(
                            name = name,
                            email = email,
                            password = password
                        )
                    )

                if (response.isSuccessful) {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Регистрация успешна",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this@RegisterActivity,
                            CreatePinActivity::class.java
                        )
                    )

                    finish()

                } else {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Ошибка регистрации",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@RegisterActivity,
                    e.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}