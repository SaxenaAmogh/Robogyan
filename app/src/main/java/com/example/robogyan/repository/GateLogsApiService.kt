package com.example.robogyan.repository

import com.example.robogyan.model.GateLogsResponse
import retrofit2.Response
import retrofit2.http.GET

interface GateLogsApi {
    @GET("/api/collections/gateLogs/records")
    suspend fun getGateLogs(): Response<GateLogsResponse>
}