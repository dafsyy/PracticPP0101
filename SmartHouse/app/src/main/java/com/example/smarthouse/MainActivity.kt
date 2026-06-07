package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarthouse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val rooms = mutableListOf<RoomModel>()

    private val addRoomLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                val roomName =
                    result.data?.getStringExtra("ROOM_NAME")
                        ?: return@registerForActivityResult

                val roomType =
                    result.data?.getStringExtra("ROOM_TYPE")
                        ?: ""

                var roomIcon =
                    result.data?.getIntExtra(
                        "ROOM_ICON",
                        -1
                    ) ?: -1

                if (roomIcon == -1) {

                    roomIcon = when (roomType) {

                        "living_room" -> R.drawable.ic_living_room

                        "kitchen" -> R.drawable.ic_kitchen

                        "bathroom" -> R.drawable.ic_bathroom

                        "office" -> R.drawable.ic_office

                        "bedroom" -> R.drawable.ic_bedroom

                        "hall" -> R.drawable.ic_hall

                        else -> R.drawable.ic_add_room
                    }
                }

                rooms.add(
                    RoomModel(
                        roomName,
                        roomIcon,
                        roomType
                    )
                )

                binding.rvRooms.adapter?.notifyDataSetChanged()

                updateState()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgSettings.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
        }


        binding.tvAddress.text =
            intent.getStringExtra("USER_ADDRESS") ?: ""

        binding.btnAddRoom.setOnClickListener {

            addRoomLauncher.launch(
                Intent(
                    this,
                    AddRoomActivity::class.java
                )
            )
        }

        setupRecycler()
        updateState()
    }

    private fun setupRecycler() {

        binding.rvRooms.layoutManager =
            LinearLayoutManager(this)

        binding.rvRooms.adapter =
            RoomAdapter(rooms) { room ->

                val intent =
                    Intent(
                        this,
                        RoomDevicesActivity::class.java
                    )

                intent.putExtra(
                    "ROOM_NAME",
                    room.roomName
                )

                intent.putExtra(
                    "ROOM_TYPE",
                    room.type
                )

                startActivity(intent)
            }
    }

    private fun updateState() {

        if (rooms.isEmpty()) {

            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvRooms.visibility = View.GONE

        } else {

            binding.tvEmpty.visibility = View.GONE
            binding.rvRooms.visibility = View.VISIBLE
        }
    }
}