package com.example.robogyan.repository

import com.example.robogyan.data.local.entities.AllMembers
import com.example.robogyan.model.Member
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class MemberRepository(private val supabase: SupabaseClient) {

    suspend fun getAllMembers(): List<AllMembers> {
        return supabase
            .postgrest["members"]
            .select()
            .decodeList<AllMembers>()
    }
}
