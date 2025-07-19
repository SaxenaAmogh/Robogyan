package com.example.robogyan.repository

import com.example.robogyan.utils.RetrofitClient
import com.example.robogyan.model.GateLogsResponse
import retrofit2.Response

class GateLogsRepository {
    suspend fun getGateLogs(): Response<GateLogsResponse> {
        return RetrofitClient.instance2.getGateLogs()
    }
}