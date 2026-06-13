package com.example.smarthouse.network

import com.example.smarthouse.network.models.UserDto
import retrofit2.Response
import retrofit2.http.*
import com.example.smarthouse.network.models.RoomDto
import com.example.smarthouse.network.models.DeviceDto
interface SupabaseApi {

    @POST("rest/v1/users")
    suspend fun registerUser(
        @Body user: UserDto,
        @Header("Prefer") prefer: String = "return=representation"
    ): Response<List<UserDto>>

    @GET("rest/v1/users")
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("select") select: String = "*"
    ): Response<List<UserDto>>

    @GET("rest/v1/users")
    suspend fun getUserById(
        @Query("id") userId: String,
        @Query("select") select: String = "*"
    ): Response<List<UserDto>>

    @PATCH("rest/v1/users")
    suspend fun updatePin(
        @Query("id") userId: String,
        @Body pinUpdate: Map<String, String>,
        @Header("Prefer") prefer: String = "return=minimal"
    ): Response<Unit>

    @PATCH("rest/v1/users")
    suspend fun updateAddress(
        @Query("id") userId: String,
        @Body addressUpdate: Map<String, String>,
        @Header("Prefer") prefer: String = "return=minimal"
    ): Response<Unit>

    @POST("rest/v1/rooms")
    suspend fun createRoom(
        @Body room: RoomDto,
        @Header("Prefer") prefer: String = "return=representation"
    ): Response<List<RoomDto>>

    @GET("rest/v1/rooms")
    suspend fun getRooms(
        @Query("user_id") userId: String,
        @Query("select") select: String = "*"
    ): Response<List<RoomDto>>

    @POST("rest/v1/devices")
    suspend fun createDevice(
        @Body device: DeviceDto,
        @Header("Prefer") prefer: String = "return=representation"
    ): Response<List<DeviceDto>>

    @GET("rest/v1/devices")
    suspend fun getDevices(
        @Query("room_id") roomId: String,
        @Query("select") select: String = "*"
    ): Response<List<DeviceDto>>

    @DELETE("rest/v1/devices")
    suspend fun deleteDevice(
        @Query("id") deviceId: String
    ): Response<Unit>

    @PATCH("rest/v1/devices")
    suspend fun updateDevice(
        @Query("id") deviceId: String,
        @Body update: Map<String, Any>
    ): Response<Unit>
}