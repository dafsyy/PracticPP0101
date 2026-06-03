package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthouse.databinding.ActivityAddressBinding

class AddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {

            val address = binding.etAddress.text.toString()

            val intent = Intent(
                this,
                MainActivity::class.java
            )

            intent.putExtra(
                "USER_ADDRESS",
                address
            )

            startActivity(intent)

            finish()
        }
    }
}