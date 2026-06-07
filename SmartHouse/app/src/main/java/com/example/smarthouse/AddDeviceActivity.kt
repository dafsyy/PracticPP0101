package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthouse.databinding.ActivityAddDeviceBinding

class AddDeviceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDeviceBinding

    private var selectedType = ""
    private var selectedIcon = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.imgLight.setOnClickListener {

            Toast.makeText(
                this,
                "Устройство пока недоступно",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.imgConditioner.setOnClickListener {

            Toast.makeText(
                this,
                "Устройство пока недоступно",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.imgFan.setOnClickListener {

            Toast.makeText(
                this,
                "Устройство пока недоступно",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.imgHood.setOnClickListener {

            selectedType = "hood"
            selectedIcon = R.drawable.ic_hood

            binding.imgHood.setImageResource(
                R.drawable.ic_hood
            )

            binding.imgFloorHeating.setImageResource(
                R.drawable.ic_floor_heating_gray
            )
        }

        binding.imgFloorHeating.setOnClickListener {

            selectedType = "floor_heating"
            selectedIcon = R.drawable.ic_floor_heating

            binding.imgFloorHeating.setImageResource(
                R.drawable.ic_floor_heating
            )

            binding.imgHood.setImageResource(
                R.drawable.ic_hood_gray
            )
        }

        binding.btnSave.setOnClickListener {

            val name =
                binding.etDeviceName.text.toString().trim()

            val id =
                binding.etDeviceId.text.toString().trim()

            if (name.isEmpty()) {

                Toast.makeText(
                    this,
                    "Введите название устройства",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (id.isEmpty()) {

                Toast.makeText(
                    this,
                    "Введите идентификатор",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (selectedType.isEmpty()) {

                Toast.makeText(
                    this,
                    "Выберите устройство",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val resultIntent = Intent()

            resultIntent.putExtra(
                "DEVICE_NAME",
                name
            )

            resultIntent.putExtra(
                "DEVICE_ID",
                id
            )

            resultIntent.putExtra(
                "DEVICE_TYPE",
                selectedType
            )

            resultIntent.putExtra(
                "DEVICE_ICON",
                selectedIcon
            )

            setResult(
                RESULT_OK,
                resultIntent
            )

            finish()
        }
    }
}