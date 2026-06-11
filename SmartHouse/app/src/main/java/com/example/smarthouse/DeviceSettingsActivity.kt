package com.example.smarthouse

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthouse.databinding.ActivityDeviceSettingsBinding
import java.util.Calendar

class DeviceSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeviceSettingsBinding
    private var minPower = 0
    private var currentPower = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val deviceName = intent.getStringExtra("DEVICE_NAME") ?: "Настройки"
        val deviceType = intent.getStringExtra("DEVICE_TYPE") ?: ""
        currentPower = intent.getIntExtra("DEVICE_POWER", 0)

        binding.tvTitle.text = deviceName

        binding.btnBack.setOnClickListener {
            finish()
        }

        when (deviceType) {
            "hood" -> {
                binding.layoutHood.visibility = View.VISIBLE
                binding.layoutFloor.visibility = View.GONE
                setupHoodLogic()
            }
            "floor_heating" -> {
                binding.layoutHood.visibility = View.GONE
                binding.layoutFloor.visibility = View.VISIBLE
                setupFloorHeatingLogic()
            }
            else -> {
                binding.layoutHood.visibility = View.GONE
                binding.layoutFloor.visibility = View.GONE
            }
        }

        binding.btnSave.setOnClickListener {
            val resultIntent = Intent()
            // Если вытяжка, берем значение из её ползунка, если пол — можно добавить свою логику
            val powerToSave = if (deviceType == "hood") currentPower else currentPower
            
            resultIntent.putExtra("NEW_POWER", powerToSave)
            setResult(RESULT_OK, resultIntent)
            
            Toast.makeText(this, "Настройки сохранены", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupHoodLogic() {
        // Логика выбора режима на основе текущей мощности
        when {
            currentPower <= 50 -> {
                binding.rbLow.isChecked = true
                updateHoodLimits(10, 40, currentPower)
            }
            currentPower <= 150 -> {
                binding.rbMedium.isChecked = true
                updateHoodLimits(60, 90, currentPower)
            }
            else -> {
                binding.rbHigh.isChecked = true
                updateHoodLimits(160, 140, currentPower)
            }
        }

        binding.radioSpeed.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbLow -> updateHoodLimits(10, 40, 10)
                R.id.rbMedium -> updateHoodLimits(60, 90, 60)
                R.id.rbHigh -> updateHoodLimits(160, 140, 160)
            }
        }

        binding.seekHoodPower.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                currentPower = minPower + progress
                binding.tvHoodPower.text = "$currentPower Вт"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateHoodLimits(min: Int, range: Int, initialValue: Int) {
        minPower = min
        binding.seekHoodPower.max = range
        currentPower = initialValue
        binding.seekHoodPower.progress = initialValue - min
        binding.tvHoodPower.text = "$initialValue Вт"
    }

    private fun setupFloorHeatingLogic() {
        binding.seekTemperature.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvTemperature.text = "$progress°C"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.tvTimeFrom.setOnClickListener { showTimePicker(binding.tvTimeFrom) }
        binding.tvTimeTo.setOnClickListener { showTimePicker(binding.tvTimeTo) }
    }

    private fun showTimePicker(textView: TextView) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(this, { _, h, m ->
            textView.text = String.format("%02d:%02d", h, m)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
    }
}
