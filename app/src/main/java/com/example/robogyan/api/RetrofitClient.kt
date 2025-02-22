package com.example.robogyan.api

import com.example.robogyan.repository.MemberApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://meets.pockethost.io/"

    val instance: MemberApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MemberApi::class.java)
    }
}