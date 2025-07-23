package com.example.robogyan.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "resources_table")
@Serializable
data class Resources(
    @PrimaryKey
    val id: Int,
    val type: String,
    val image: String,
    val title: String,
    val description: String?,
    val resource_url: String,
    val created_at: String ?= ""
)
