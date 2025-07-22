package com.example.robogyan.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Entity(tableName = "projects_table")
@Serializable
data class Projects (
    @PrimaryKey
    val id : Int,
    val name : String,
    val project_head: String,
    val status : String,
    val github_link: String?,
    val pdf_link: String?,
    val category: String,
    val description : String,
    val start_date: String,
    val completion_date: String?,
    val is_archived: Boolean,
    val money_spent: Float,
    val team: String,
    val components: String?,
    val created: String ?= "",
    val updated: String ?= "",
)