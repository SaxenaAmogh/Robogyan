package com.example.robogyan.model

data class Member(
    val id: String,
    val name: String,
    val pos: String,
    val batch: String,
    val email: String,
    val mobileNum: Long,
    val enrollment: Long,
    val techStack: String,
    val labAccess: Boolean,
    val created: String,
    val updated: String
)

data class MemberResponse(
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
    val totalItems: Int,
    val items: List<Member>
)
