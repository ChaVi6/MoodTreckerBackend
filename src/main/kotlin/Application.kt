package com.example

import com.example.features.login.configureLoginRouting
import com.example.features.logout.configureLogoutRouting
import com.example.features.records.configureRecordsRouting
import com.example.features.registration.configureRegisterRouting
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database


fun main() {
    Database.connect("jdbc:oracle:thin:@localhost:1521", "oracle.jdbc.OracleDriver", "MOOD", "mood")
    embeddedServer(CIO, port = 9090, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
    configureLogoutRouting()
    configureRecordsRouting()
    configureSerialization()
}
