package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthouse.databinding.ActivityAddRoomBinding
import androidx.activity.result.contract.ActivityResultContracts

class AddRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRoomBinding
    private var selectedRoom = "living_room"

    private val customRoomLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                setResult(
                    RESULT_OK,
                    result.data
                )

                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        selectLivingRoom()

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

        binding.imgOther.setOnClickListener {
            selectOther()
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

            resultIntent.putExtra("ROOM_NAME", roomName)
            resultIntent.putExtra("ROOM_TYPE", selectedRoom)

            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }

    private fun resetIcons() {

        binding.imgLivingRoom.setImageResource(R.drawable.ic_living_room_gray)
        binding.imgKitchen.setImageResource(R.drawable.ic_kitchen_gray)
        binding.imgBathroom.setImageResource(R.drawable.ic_bathroom_gray)
        binding.imgOffice.setImageResource(R.drawable.ic_office_gray)
        binding.imgBedroom.setImageResource(R.drawable.ic_bedroom_gray)
        binding.imgHall.setImageResource(R.drawable.ic_hall_gray)
        binding.imgOther.setImageResource(R.drawable.ic_add_room_gray)
    }

    private fun selectLivingRoom() {

        resetIcons()

        binding.imgLivingRoom.setImageResource(
            R.drawable.ic_living_room
        )

        binding.etRoomName.setText("Гостиная")
        binding.etRoomName.isEnabled = false

        selectedRoom = "living_room"
    }

    private fun selectKitchen() {

        resetIcons()

        binding.imgKitchen.setImageResource(
            R.drawable.ic_kitchen
        )

        binding.etRoomName.setText("Кухня")
        binding.etRoomName.isEnabled = false

        selectedRoom = "kitchen"
    }

    private fun selectBathroom() {

        resetIcons()

        binding.imgBathroom.setImageResource(
            R.drawable.ic_bathroom
        )

        binding.etRoomName.setText("Ванная")
        binding.etRoomName.isEnabled = false

        selectedRoom = "bathroom"
    }

    private fun selectOffice() {

        resetIcons()

        binding.imgOffice.setImageResource(
            R.drawable.ic_office
        )

        binding.etRoomName.setText("Кабинет")
        binding.etRoomName.isEnabled = false

        selectedRoom = "office"
    }

    private fun selectBedroom() {

        resetIcons()

        binding.imgBedroom.setImageResource(
            R.drawable.ic_bedroom
        )

        binding.etRoomName.setText("Спальня")
        binding.etRoomName.isEnabled = false

        selectedRoom = "bedroom"
    }

    private fun selectHall() {

        resetIcons()

        binding.imgHall.setImageResource(
            R.drawable.ic_hall
        )

        binding.etRoomName.setText("Зал")
        binding.etRoomName.isEnabled = false

        selectedRoom = "hall"
    }
    private fun selectOther() {

        customRoomLauncher.launch(
            Intent(
                this,
                CustomRoomActivity::class.java
            )
        )
    }
}