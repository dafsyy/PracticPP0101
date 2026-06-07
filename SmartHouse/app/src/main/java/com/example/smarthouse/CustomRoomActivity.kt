package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthouse.databinding.ActivityCustomRoomBinding

class CustomRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomRoomBinding

    private var selectedIcon = R.drawable.ic_living_room

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectLivingRoom()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.imgLivingRoom.setOnClickListener {
            selectLivingRoom()
        }

        binding.imgKitchen.setOnClickListener {
            selectKitchen()
        }

        binding.imgBathroom.setOnClickListener {
            selectBathroom()
        }

        binding.imgOffice.setOnClickListener {
            selectOffice()
        }

        binding.imgBedroom.setOnClickListener {
            selectBedroom()
        }

        binding.imgHall.setOnClickListener {
            selectHall()
        }

        binding.btnSave.setOnClickListener {

            val roomName =
                binding.etRoomName.text.toString().trim()

            if (roomName.isEmpty()) {

                Toast.makeText(
                    this,
                    "Введите название комнаты",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val resultIntent = Intent()

            resultIntent.putExtra(
                "ROOM_NAME",
                roomName
            )

            resultIntent.putExtra(
                "ROOM_TYPE",
                "custom"
            )

            resultIntent.putExtra(
                "ROOM_ICON",
                selectedIcon
            )

            setResult(
                RESULT_OK,
                resultIntent
            )

            finish()
        }
    }

    private fun resetIcons() {

        binding.imgLivingRoom.setImageResource(
            R.drawable.ic_living_room_gray
        )

        binding.imgKitchen.setImageResource(
            R.drawable.ic_kitchen_gray
        )

        binding.imgBathroom.setImageResource(
            R.drawable.ic_bathroom_gray
        )

        binding.imgOffice.setImageResource(
            R.drawable.ic_office_gray
        )

        binding.imgBedroom.setImageResource(
            R.drawable.ic_bedroom_gray
        )

        binding.imgHall.setImageResource(
            R.drawable.ic_hall_gray
        )
    }

    private fun selectLivingRoom() {

        resetIcons()

        binding.imgLivingRoom.setImageResource(
            R.drawable.ic_living_room
        )

        selectedIcon = R.drawable.ic_living_room
    }

    private fun selectKitchen() {

        resetIcons()

        binding.imgKitchen.setImageResource(
            R.drawable.ic_kitchen
        )

        selectedIcon = R.drawable.ic_kitchen
    }

    private fun selectBathroom() {

        resetIcons()

        binding.imgBathroom.setImageResource(
            R.drawable.ic_bathroom
        )

        selectedIcon = R.drawable.ic_bathroom
    }

    private fun selectOffice() {

        resetIcons()

        binding.imgOffice.setImageResource(
            R.drawable.ic_office
        )

        selectedIcon = R.drawable.ic_office
    }

    private fun selectBedroom() {

        resetIcons()

        binding.imgBedroom.setImageResource(
            R.drawable.ic_bedroom
        )

        selectedIcon = R.drawable.ic_bedroom
    }

    private fun selectHall() {

        resetIcons()

        binding.imgHall.setImageResource(
            R.drawable.ic_hall
        )

        selectedIcon = R.drawable.ic_hall
    }
}