package com.example.robogyan.repository

import com.example.robogyan.api.RetrofitClient
import com.example.robogyan.model.Member
import com.example.robogyan.model.MemberResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import retrofit2.Response

class MemberRepository(private val supabase: SupabaseClient) {

    suspend fun getAllMembers(): List<Member> {
        return supabase
            .postgrest["members"]
            .select()
            .decodeList<Member>()
    }
}
