package com.example.smarthouse.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://ptdzraitlefsamgelyar.supabase.co/"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InB0ZHpyYWl0bGVmc2FtZ2VseWFyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODEyNDgyNTIsImV4cCI6MjA5NjgyNDI1Mn0.QzyFGI4NQnpsmURnnUymNkol3sl6SDxpR4RQ4lISH4s" // Твой полный ключ сюда

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("apikey", SUPABASE_KEY)
                .addHeader("Authorization", "Bearer $SUPABASE_KEY")
                .build()
            chain.proceed(request)
        }
        // Добавим логи, чтобы видеть ошибки запросов в консоли (Logcat)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val api: SupabaseApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SupabaseApi::class.java)
    }
}