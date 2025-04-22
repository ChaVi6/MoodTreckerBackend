package com.example.features.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginTokenReceiveRemote(
    val login: String,
    val token: String
)