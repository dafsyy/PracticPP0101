package com.example.smarthouse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthouse.databinding.ItemDeviceBinding

class DeviceAdapter(
    private val devices: List<DeviceModel>,
    private val onDeviceClick: (Int, DeviceModel) -> Unit // Добавляем обработчик клика
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    inner class DeviceViewHolder(
        private val binding: ItemDeviceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, device: DeviceModel) {
            binding.tvDeviceName.text = device.name

            // Отображаем мощность только если устройство включено
            updatePowerDisplay(device)

            binding.imgDevice.setImageResource(device.icon)
            
            // Отключаем слушатель перед установкой состояния, чтобы избежать зацикливания
            binding.switchPower.setOnCheckedChangeListener(null)
            binding.switchPower.isChecked = device.enabled

            // Клик по иконке открывает настройки через RoomDevicesActivity
            binding.imgDevice.setOnClickListener {
                onDeviceClick(position, device)
            }

            binding.switchPower.setOnCheckedChangeListener { _, checked ->
                device.enabled = checked
                
                // Если мощность еще не была задана (равна 0), ставим дефолтную при включении
                if (checked && device.power == 0) {
                    device.power = when(device.type) {
                        "hood" -> 120
                        "floor_heating" -> 500
                        else -> 0
                    }
                }
                
                updatePowerDisplay(device)
            }
        }

        private fun updatePowerDisplay(device: DeviceModel) {
            if (device.enabled) {
                binding.tvPower.text = "Мощность: ${device.power} Вт"
            } else {
                binding.tvPower.text = "Выключено (0 Вт)"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = ItemDeviceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DeviceViewHolder(binding)
    }

    override fun getItemCount() = devices.size

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(position, devices[position])
    }
}
