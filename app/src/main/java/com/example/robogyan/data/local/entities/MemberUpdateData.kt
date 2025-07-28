package com.example.robogyan.data.local.entities

import kotlinx.serialization.Serializable

@Serializable
data class MemberUpdateData(
    val name: String? = null,
    val enrollment: String? = null,
    val email: String? = null,
    val mobile: String? = null,
    val batch: String? = null,
    val current_pos: String? = null,
    val pos_period: String? = null,
    val is_alumni: Boolean? = null,
    val lab_access: Boolean? = null,
    val clearance: String? = null,
    val image: String? = null
)
