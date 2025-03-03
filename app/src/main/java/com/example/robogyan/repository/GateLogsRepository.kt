package com.example.robogyan.repository

import com.example.robogyan.api.RetrofitClient
import com.example.robogyan.model.GateLogsResponse
import com.example.robogyan.model.MemberResponse
import retrofit2.Response

class GateLogsRepository {
    suspend fun getGateLogs(): Response<GateLogsResponse> {
        return RetrofitClient.instance2.getGateLogs()
    }
}