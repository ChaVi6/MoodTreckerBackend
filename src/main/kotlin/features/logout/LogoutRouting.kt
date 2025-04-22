package com.example.features.logout

import com.example.features.login.LoginController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureLogoutRouting() {

    routing {
        post("/logout") {
            val logoutController = LogoutController(call)
            logoutController.performLogout()
        }
    }
}