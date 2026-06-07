package com.example.smarthouse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthouse.databinding.ActivityDeviceSettingsBinding
import android.widget.SeekBar

class DeviceSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeviceSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeviceSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val deviceName =
            intent.getStringExtra("DEVICE_NAME") ?: ""

        val deviceType =
            intent.getStringExtra("DEVICE_TYPE") ?: ""

        binding.tvTitle.text = deviceName

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.seekTemperature.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {

                    binding.tvTemperature.text =
                        "$progress°C"
                }

                override fun onStartTrackingTouch(
                    seekBar: SeekBar?
                ) {}

                override fun onStopTrackingTouch(
                    seekBar: SeekBar?
                ) {}
            }
        )

        if (deviceType == "hood") {

            binding.layoutHood.visibility =
                android.view.View.VISIBLE

            binding.layoutFloor.visibility =
                android.view.View.GONE

        } else if (deviceType == "floor_heating") {

            binding.layoutHood.visibility =
                android.view.View.GONE

            binding.layoutFloor.visibility =
                android.view.View.VISIBLE
        }
    }
}