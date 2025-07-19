package com.example.robogyan.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "members_table")
@Serializable
data class MemberData(
    @PrimaryKey // Marks 'id' as the primary key for this table
    val id: String, // This should be the Supabase Auth UID
    val name: String,
    val email: String,
    val mobile: String,
    val batch: String,
    val current_pos: String,
    val pos_period: String,
    val clearance: String,
    val image: String
)
