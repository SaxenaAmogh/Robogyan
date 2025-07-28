package com.example.robogyan.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Entity(tableName = "projects_table")
@Serializable
data class Projects (
    @PrimaryKey val id : Int ?= null,
    val name : String,
    val project_head: String,
    val status : String,
    val github_link: String? = null,
    val pdf_link: String? = null,
    val category: String,
    val description : String,
    val start_date: String,
    val completion_date: String? = null,
    val is_archived: Boolean? = null,
    val money_spent: Float? = null,
    val team: String,
    val components: String? = null,
    val created: String ?= null,
    val updated: String ?= null,
)