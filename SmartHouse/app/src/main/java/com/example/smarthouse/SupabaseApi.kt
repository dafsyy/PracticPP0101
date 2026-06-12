package com.example.smarthouse.network

import com.example.smarthouse.network.models.UserDto
import retrofit2.Response
import retrofit2.http.*

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
}