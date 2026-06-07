package com.example.smarthouse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthouse.databinding.ItemDeviceBinding
import android.content.Intent

class DeviceAdapter(
    private val devices: List<DeviceModel>
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    inner class DeviceViewHolder(
        private val binding: ItemDeviceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(device: DeviceModel) {

            binding.tvDeviceName.text =
                device.name

            binding.tvPower.text =
                "Мощность: ${device.power} Вт"

            binding.imgDevice.setImageResource(
                device.icon
            )

            binding.switchPower.isChecked =
                device.enabled

            binding.imgDevice.setOnClickListener {

                val intent =
                    Intent(
                        binding.root.context,
                        DeviceSettingsActivity::class.java
                    )

                intent.putExtra(
                    "DEVICE_NAME",
                    device.name
                )

                intent.putExtra(
                    "DEVICE_TYPE",
                    device.type
                )

                binding.root.context.startActivity(intent)
            }

            binding.switchPower.setOnCheckedChangeListener { _, checked ->

                device.enabled = checked

                if (checked) {

                    device.power = when(device.type) {

                        "hood" -> 120

                        "floor_heating" -> 500

                        else -> 0
                    }

                } else {

                    device.power = 0
                }

                binding.tvPower.text =
                    "Мощность: ${device.power} Вт"
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeviceViewHolder {

        val binding =
            ItemDeviceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return DeviceViewHolder(binding)
    }

    override fun getItemCount() =
        devices.size

    override fun onBindViewHolder(
        holder: DeviceViewHolder,
        position: Int
    ) {
        holder.bind(devices[position])
    }
}