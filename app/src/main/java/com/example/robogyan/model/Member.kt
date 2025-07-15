package com.example.robogyan.model
import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val id: String,
    val name: String,
    val enrollment: String,
    val email: String,
    val mobile: Long,
    val batch: String,
    val current_pos: String,
    val pos_period: String,
    val past_pos: List<String>? = null,
    val is_alumni: Boolean,
    val lab_access: Boolean = false,
    val clearance: String,
    val image: String,
    val created: String = "",
)

data class MemberResponse(
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
    val totalItems: Int,
    val items: List<Member>
)
