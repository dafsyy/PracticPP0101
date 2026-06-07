package com.example.smarthouse

data class DeviceModel(
    val name: String,
    val id: String,
    val type: String,
    val icon: Int,
    var enabled: Boolean = false,
    var power: Int = 0
)