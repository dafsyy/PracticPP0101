package com.example.smarthouse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.smarthouse.databinding.ActivityAddressBinding
import com.example.smarthouse.network.RetrofitClient
import kotlinx.coroutines.launch

class AddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding
    private var isApartment = false 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.btnPrivateHouse.setOnClickListener {
            isApartment = false
            updateSelectionUI()
        }

        binding.btnApartment.setOnClickListener {
            isApartment = true
            updateSelectionUI()
        }

        binding.btnSave.setOnClickListener {
            val address = binding.etAddress.text.toString().trim()

            if (address.isEmpty()) {
                Toast.makeText(this, "Введите адрес", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Регулярные выражения для строгой проверки формата
            val houseRegex = Regex("^г\\.\\s[А-Яа-яЁё\\s-]+\\,\\sул\\.\\s[А-Яа-яЁё\\s-]+\\,\\sд\\.\\s[0-9А-Яа-я/]+$")
            val apartmentRegex = Regex("^г\\.\\s[А-Яа-яЁё\\s-]+\\,\\sул\\.\\s[А-Яа-яЁё\\s-]+\\,\\sд\\.\\s[0-9А-Яа-я/]+\\,\\sкв\\.\\s[0-9]+$")

            if (isApartment) {
                if (!apartmentRegex.matches(address)) {
                    Toast.makeText(this, "Соблюдайте формат: г. Москва, ул. Ленина, д. 1, кв. 10", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            } else {
                if (!houseRegex.matches(address)) {
                    Toast.makeText(this, "Соблюдайте формат: г. Москва, ул. Ленина, д. 1", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            }

            saveAddress(address)
        }

        updateSelectionUI()
    }

    private fun updateSelectionUI() {
        if (isApartment) {
            binding.imgApartment.setImageResource(R.drawable.ic_apartment)
            binding.imgPrivateHouse.setImageResource(R.drawable.ic_private_house_gray)
            binding.etAddress.hint = "г. Москва, ул. Ленина, д. 1, кв. 10"
        } else {
            binding.imgApartment.setImageResource(R.drawable.ic_apartment_gray)
            binding.imgPrivateHouse.setImageResource(R.drawable.ic_private_house)
            binding.etAddress.hint = "г. Москва, ул. Ленина, д. 1"
        }
    }

    private fun saveAddress(address: String) {
        val prefs = getSharedPreferences("SmartHouse", Context.MODE_PRIVATE)
        val userId = prefs.getString("USER_ID", "") ?: ""

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.updateAddress(
                    userId = "eq.$userId",
                    addressUpdate = mapOf("address" to address)
                )

                if (response.isSuccessful) {
                    prefs.edit().apply {
                        putBoolean("HAS_ADDRESS", true)
                        putString("USER_ADDRESS", address)
                        apply()
                    }

                    Toast.makeText(this@AddressActivity, "Адрес сохранён", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@AddressActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@AddressActivity, "Ошибка сохранения", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AddressActivity, "Ошибка сети", Toast.LENGTH_LONG).show()
            }
        }
    }
}
