package com.example.robogyan.repository

import com.example.robogyan.model.MemberResponse
import retrofit2.Response
import retrofit2.http.GET

interface MemberApi {
    @GET("api/collections/members/records")
    suspend fun getMembers(): Response<MemberResponse>
}