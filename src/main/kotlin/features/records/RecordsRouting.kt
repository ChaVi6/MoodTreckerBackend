package com.example.features.records

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRecordsRouting() {

    routing {
        post("/records") {
            val recordsController = RecordsController(call)
            recordsController.addNewRecord()
        }

        post("/getRecords") {
            val recordsController = RecordsController(call)
            recordsController.getAllRecords()
        }

        post("/getRecord") {
            val recordsController = RecordsController(call)
            recordsController.getRecord()
        }

        post("/deleteRecord") {
            val recordsController = RecordsController(call)
            recordsController.deleteRecord()
        }
    }
}


