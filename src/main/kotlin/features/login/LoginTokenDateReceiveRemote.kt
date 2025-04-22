package com.example.features.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginTokenDateReceiveRemote(
    val login: String,
    val token: String,
    val date: String
)
