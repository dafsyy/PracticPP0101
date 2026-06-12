package com.example.smarthouse.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SupabaseClient {

    private const val BASE_URL =
        "https://ptdzraitlefsamgelyar.supabase.co/"

    private const val API_KEY =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InB0ZHpyYWl0bGVmc2FtZ2VseWFyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODEyNDgyNTIsImV4cCI6MjA5NjgyNDI1Mn0.QzyFGI4NQnpsmURnnUymNkol3sl6SDxpR4RQ4lISH4s"

    private val logging =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private val client =
        OkHttpClient.Builder()
            .addInterceptor { chain ->

                val request =
                    chain.request()
                        .newBuilder()
                        .addHeader("apikey", API_KEY)
                        .addHeader("Authorization", "Bearer $API_KEY")
                        .addHeader("Content-Type", "application/json")
                        .build()

                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()

    val api: SupabaseApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(SupabaseApi::class.java)
}