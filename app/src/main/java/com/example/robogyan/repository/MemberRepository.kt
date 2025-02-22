package com.example.robogyan.repository

import com.example.robogyan.api.RetrofitClient
import com.example.robogyan.model.MemberResponse
import retrofit2.Response

class MemberRepository {
    suspend fun getMembers(): Response<MemberResponse> {
        return RetrofitClient.instance.getMembers()
    }
}