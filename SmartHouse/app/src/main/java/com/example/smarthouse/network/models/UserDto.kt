package com.example.smarthouse.network.models

data class UserDto(
    val id: String? = null,
    val email: String,
    val password: String,
    val name: String? = null,
    val address: String? = null,
    val pin: String? = null
)