package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smarthouse.databinding.ActivityRoomDevicesBinding

class RoomDevicesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomDevicesBinding

    private val devices = mutableListOf<DeviceModel>()

    private val addDeviceLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                val name =
                    result.data?.getStringExtra("DEVICE_NAME")
                        ?: return@registerForActivityResult

                val id =
                    result.data?.getStringExtra("DEVICE_ID")
                        ?: ""

                val type =
                    result.data?.getStringExtra("DEVICE_TYPE")
                        ?: ""

                val icon =
                    result.data?.getIntExtra(
                        "DEVICE_ICON",
                        R.drawable.ic_hood
                    ) ?: R.drawable.ic_hood

                devices.add(
                    DeviceModel(
                        name,
                        id,
                        type,
                        icon
                    )
                )

                binding.rvDevices.adapter?.notifyDataSetChanged()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoomDevicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRoomName.text =
            intent.getStringExtra("ROOM_NAME")

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.rvDevices.layoutManager =
            GridLayoutManager(this, 2)

        binding.rvDevices.adapter =
            DeviceAdapter(devices)

        binding.btnAddDevice.setOnClickListener {

            addDeviceLauncher.launch(
                Intent(
                    this,
                    AddDeviceActivity::class.java
                )
            )
        }
    }
}