package com.example.features.records

import com.example.database.records.RecordDTO
import com.example.database.records.Records
import com.example.database.records.Records.deleteRecordByDate
import com.example.database.tokens.TokenDTO
import com.example.database.tokens.Tokens
import com.example.database.users.Users

import com.example.features.login.LoginReceiveRemote
import com.example.features.login.LoginResponseRemote
import com.example.features.login.LoginTokenDateReceiveRemote
import com.example.features.login.LoginTokenReceiveRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class RecordsController(private val call: ApplicationCall) {

    suspend fun addNewRecord() {
        println("receiving")
        val receive = call.receive<RecordsReceiveRemote>()
        println(receive)
        val recordDTO = Records.fetchRecord(receive.login, receive.date)
        println(recordDTO)

        if (recordDTO != null) {
            call.respond(HttpStatusCode.BadRequest, "Record already exists")
        } else {
            Records.insert(
                RecordDTO(
                    data = receive.date,
                    login = receive.login,
                    mood = receive.mood,
                    note = receive.note
                )
            )
            call.respond(HttpStatusCode.OK)
        }
    }

    suspend fun getAllRecords() {
        val receive = call.receive<LoginTokenReceiveRemote>()

        val userDTO = Users.fetchUser(receive.login)
        val tokens = Tokens.fetchTokens()
        val validTokens = tokens.map { it.token }
        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        }
        else if (!validTokens.contains(receive.token)) {
            call.respond(HttpStatusCode.BadRequest, "Token expired")
        }
        val dates = Records.getDatesForUser(receive.login)
        call.respond(HttpStatusCode.OK, dates)

    }

    suspend fun getRecord() {
        println("receiving")
        val receive = call.receive<RecordDataReceiveRemote>()
        println(receive)
        val userDTO = Users.fetchUser(receive.login)
        val tokens = Tokens.fetchTokens()
        val validTokens = tokens.map { it.token }
        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        }
        else if (!validTokens.contains(receive.token)) {
            call.respond(HttpStatusCode.BadRequest, "Token expired")
        }
        val record = Records.getRecordByDate(receive.login, receive.date)!!
        call.respond(HttpStatusCode.OK, record)
    }

    suspend fun deleteRecord() {
        println("receiving")
        val receive = call.receive<LoginTokenDateReceiveRemote>()
        println(receive)
        val userDTO = Users.fetchUser(receive.login)
        val tokens = Tokens.fetchTokens()
        val validTokens = tokens.map { it.token }
        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        }
        else if (!validTokens.contains(receive.token)) {
            call.respond(HttpStatusCode.BadRequest, "Token expired")
        }
        deleteRecordByDate(receive.login, receive.date)
        call.respond(HttpStatusCode.OK)
    }
}