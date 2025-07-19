package com.example.robogyan.utils
import com.example.robogyan.repository.GateLogsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://meets.pockethost.io/"


    val instance2: GateLogsApi by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GateLogsApi::class.java)
    }

}