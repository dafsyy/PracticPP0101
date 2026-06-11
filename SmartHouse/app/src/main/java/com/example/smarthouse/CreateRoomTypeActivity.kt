package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthouse.databinding.ActivityCreateRoomTypeBinding

class CreateRoomTypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateRoomTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityCreateRoomTypeBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {

            val typeName =
                binding.etTypeName.text.toString().trim()

            if (typeName.isEmpty()) {

                Toast.makeText(
                    this,
                    "Введите тип комнаты",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            CustomRoomStorage.roomTypes.add(
                RoomTypeModel(
                    typeName,
                    R.drawable.ic_custom_room,
                    R.drawable.ic_custom_room_gray
                )
            )

            val resultIntent = Intent()

            resultIntent.putExtra(
                "CUSTOM_ROOM_TYPE",
                typeName
            )

            setResult(
                RESULT_OK,
                resultIntent
            )

            finish()
        }
    }
}