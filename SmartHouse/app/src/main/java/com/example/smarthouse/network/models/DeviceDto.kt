package com.example.smarthouse.network.models

data class DeviceDto(
    val id: String? = null,
    val room_id: String,
    val device_name: String,
    val device_code: String,
    val device_type: String,
    val device_icon: Int,
    val enabled: Boolean = false,
    val power: Int = 0,
    val temperature: Int = 0
)