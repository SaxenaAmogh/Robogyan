package com.example.robogyan.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "asset_usage_table")
@Serializable
data class AssetUsage(
    @PrimaryKey val id: Int? = null,
    val asset_id: Int,
    val granted_to: String,
    val quantity: Int,
    val use_case: String,
    val project_name: String? = null,
    val status: String,
    val return_date: String? = null,
    val approved_by: String,
    val notes: String? = null,
    val created: String? = null,
)
