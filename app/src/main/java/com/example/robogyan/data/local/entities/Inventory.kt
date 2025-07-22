package com.example.robogyan.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "inventory_table")
@Serializable
data class Inventory (
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val description: String,
    val image: String?,
    val quantity: Int,
    val available: Int,
    val created: String ?= "",
    val updated: String ?= "",
)