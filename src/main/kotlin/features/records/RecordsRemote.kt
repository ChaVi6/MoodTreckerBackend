package com.example.features.records

import kotlinx.serialization.Serializable

@Serializable
data class RecordsReceiveRemote(
    val date: String,
    val token: String,
    val login: String,
    val mood: Int,
    val note: String
)

@Serializable
data class RecordDataReceiveRemote(
    val date: String,
    val token: String,
    val login: String
)