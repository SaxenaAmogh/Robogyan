package com.example.robogyan.model
import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val id: String,
    val name: String,
    val enrollment: String,
    val email: String,
    val mobile: String,
    val batch: String,
    val current_pos: String,
    val pos_period: String,
    val is_alumni: Boolean,
    val lab_access: Boolean = false,
    val clearance: String,
    val image: String,
    val created: String = "",
)
