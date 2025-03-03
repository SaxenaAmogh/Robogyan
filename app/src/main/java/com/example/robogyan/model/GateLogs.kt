package com.example.robogyan.model

data class GateLogs(
    val id: String,
    val uid: String,
    val created: String,
    val updated: String
)

data class GateLogsResponse(
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
    val totalItems: Int,
    val items: List<GateLogs>
)
