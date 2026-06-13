package com.example.smarthouse

data class DeviceModel(
    val id: String = "",
    val name: String,
    val deviceCode: String,
    val type: String,
    val icon: Int,
    var enabled: Boolean = false,
    var power: Int = 0
)