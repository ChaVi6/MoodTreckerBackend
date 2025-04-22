package com.example.features.logout

import com.example.database.tokens.TokenDTO
import com.example.database.tokens.Tokens
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class LogoutController(private val call: ApplicationCall) {
//    suspend fun performLogout() {
//        val authHeader = call.request.headers["Authorization"]
//
//        if (authHeader.isNullOrEmpty() || !authHeader.startsWith("Bearer ")) {
//            call.respond(HttpStatusCode.Unauthorized, "Missing or invalid Authorization header")
//            return
//        }
//
//        val token = authHeader.removePrefix("Bearer ").trim()
//        val rowsAffected = Tokens.deleteToken(token)
//
//        if (rowsAffected > 0) {
//            call.respond(HttpStatusCode.OK, "Successfully logged out")
//        } else {
//            call.respond(HttpStatusCode.NotFound, "Token not found or already invalidated")
//        }
//    }
    suspend fun performLogout() {
        val receive = call.receive<LogoutReceiveRemote>()
        val rowsAffected = Tokens.deleteToken(receive.token)

        if (rowsAffected > 0) {
            call.respond(HttpStatusCode.OK, "Successfully logged out")
        } else {
            call.respond(HttpStatusCode.NotFound, "Token not found or already invalidated")
        }
    }
}