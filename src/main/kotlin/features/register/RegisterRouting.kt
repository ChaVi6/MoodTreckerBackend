package com.example.features.registration

import com.example.features.register.RegisterController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRegisterRouting() {

    routing {
        post("/register") {
            val registerController = RegisterController(call)
            registerController.registerNewUser()
        }
    }
}