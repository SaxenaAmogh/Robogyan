package com.example.robogyan.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Entity(tableName = "all_members_table")
@Serializable
data class AllMembers(
    @PrimaryKey
    val id: String,
    val name: String,
    val enrollment: String? = null,
    val email: String,
    val mobile: String,
    val batch: String,
    val current_pos: String,
    val pos_period: String,
    val is_alumni: Boolean ?= false,
    val lab_access: Boolean ?= false,
    val clearance: String,
    val image: String,
    val created: String ?= "",
)