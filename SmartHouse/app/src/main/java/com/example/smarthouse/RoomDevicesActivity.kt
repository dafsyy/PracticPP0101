package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smarthouse.databinding.ActivityRoomDevicesBinding
import androidx.lifecycle.lifecycleScope
import com.example.smarthouse.network.RetrofitClient
import com.example.smarthouse.network.models.DeviceDto
import kotlinx.coroutines.launch

class RoomDevicesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomDevicesBinding
    private val devices = mutableListOf<DeviceModel>()
    private var clickedPosition = -1

    private var roomId = ""

    private val addDeviceLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val name = result.data?.getStringExtra("DEVICE_NAME") ?: return@registerForActivityResult
                val id = result.data?.getStringExtra("DEVICE_ID") ?: ""
                val type = result.data?.getStringExtra("DEVICE_TYPE") ?: ""
                val icon = result.data?.getIntExtra("DEVICE_ICON", R.drawable.ic_hood) ?: R.drawable.ic_hood

                saveDevice(
                    name,
                    id,
                    type,
                    icon
                )
                binding.rvDevices.adapter?.notifyItemInserted(devices.size - 1)
            }
        }

    private val settingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && clickedPosition != -1) {
                val newPower = result.data?.getIntExtra("NEW_POWER", 0) ?: 0
                devices[clickedPosition].power = newPower
                binding.rvDevices.adapter?.notifyItemChanged(clickedPosition)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomDevicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRoomName.text = intent.getStringExtra("ROOM_NAME")
        binding.btnBack.setOnClickListener { finish() }

        binding.rvDevices.layoutManager = GridLayoutManager(this, 2)
        
        // Передаем лямбду для обработки клика по устройству
        binding.rvDevices.adapter = DeviceAdapter(devices) { position, device ->
            clickedPosition = position
            val intent = Intent(this, DeviceSettingsActivity::class.java).apply {
                putExtra("DEVICE_NAME", device.name)
                putExtra("DEVICE_TYPE", device.type)
                putExtra("DEVICE_POWER", device.power)
            }
            settingsLauncher.launch(intent)
        }

        binding.btnAddDevice.setOnClickListener {
            addDeviceLauncher.launch(Intent(this, AddDeviceActivity::class.java))
        }
    }

    private fun saveDevice(
        name: String,
        code: String,
        type: String,
        icon: Int
    ) {

        lifecycleScope.launch {

            try {

                val device =
                    DeviceDto(
                        room_id = roomId,
                        device_name = name,
                        device_code = code,
                        device_type = type,
                        device_icon = icon
                    )

                val response =
                    RetrofitClient.api.createDevice(
                        device
                    )

                if (response.isSuccessful) {

                    loadDevices()
                }

            } catch (_: Exception) {

            }
        }
    }

    private fun loadDevices() {

        lifecycleScope.launch {

            try {

                val response =
                    RetrofitClient.api.getDevices(
                        "eq.$roomId"
                    )

                if (
                    response.isSuccessful &&
                    response.body() != null
                ) {

                    devices.clear()

                    response.body()!!.forEach {

                        devices.add(
                            DeviceModel(
                                id = it.id ?: "",
                                name = it.device_name,
                                deviceCode = it.device_code,
                                type = it.device_type,
                                icon = it.device_icon,
                                enabled = it.enabled,
                                power = it.power
                            )
                        )
                    }

                    binding.rvDevices.adapter?.notifyDataSetChanged()
                }

            } catch (_: Exception) {

            }
        }
    }

}
