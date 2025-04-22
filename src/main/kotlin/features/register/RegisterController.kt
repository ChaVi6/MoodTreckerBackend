package com.example.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import com.example.database.users.*
import org.mindrot.jbcrypt.BCrypt

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()

        val userDTO = Users.fetchUser(registerReceiveRemote.login)
        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {
            val hashedPassword = BCrypt.hashpw(registerReceiveRemote.password, BCrypt.gensalt())
            try {
                Users.insert(
                    UserDTO(
                        login = registerReceiveRemote.login,
                        password = hashedPassword
                    )
                )
                call.respond(HttpStatusCode.Created, "User created successfully")
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Can't create user ${e.localizedMessage}")
            }
        }
    }
}
