package com.example.smarthouse.network.models

data class RoomDto(
    val id: String? = null,
    val user_id: String,
    val room_name: String,
    val room_type: String,
    val room_icon: Int
)