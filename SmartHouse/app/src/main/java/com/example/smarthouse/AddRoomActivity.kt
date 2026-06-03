package com.example.smarthouse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthouse.databinding.ActivityAddRoomBinding

class AddRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}