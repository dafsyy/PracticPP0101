package com.example.smarthouse

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthouse.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs =
            getSharedPreferences(
                "profile",
                MODE_PRIVATE
            )

        binding.etName.setText(
            prefs.getString(
                "name",
                ""
            )
        )

        binding.etEmail.setText(
            prefs.getString(
                "email",
                ""
            )
        )

        binding.etAddress.setText(
            prefs.getString(
                "address",
                ""
            )
        )

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {

            prefs.edit()
                .putString(
                    "name",
                    binding.etName.text.toString()
                )
                .putString(
                    "email",
                    binding.etEmail.text.toString()
                )
                .putString(
                    "address",
                    binding.etAddress.text.toString()
                )
                .apply()

            finish()
        }
    }
}