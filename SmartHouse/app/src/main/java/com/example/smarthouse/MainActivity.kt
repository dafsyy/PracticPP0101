package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarthouse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val rooms = mutableListOf<RoomModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvAddress.text =
            intent.getStringExtra("USER_ADDRESS") ?: ""

        binding.btnAddRoom.setOnClickListener {

            startActivity(
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
            RoomAdapter(rooms) {

                /*
                Здесь позже откроем
                RoomDevicesActivity
                 */

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