package com.example.robogyan.data.local.entities

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class ProjectUpdateData (
    @PrimaryKey val id : Int ?= null,
    val name : String?= null,
    val project_head: String?= null,
    val status : String?= null,
    val github_link: String? = null,
    val pdf_link: String? = null,
    val category: String?= null,
    val description : String?= null,
    val start_date: String?= null,
    val completion_date: String? = null,
    val money_spent: Float? = null,
    val team: String?= null,
    val components: String? = null,
)