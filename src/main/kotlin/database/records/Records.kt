package com.example.database.records

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Records: Table() {
    private val record_date = Records.varchar("record_date", 10)
    private val login = Records.varchar("login", 25)
    private val mood = Records.integer("mood")
    private val note = Records.varchar("note", 1000)

    fun insert(recordDTO: RecordDTO) {
        transaction {
            Records.insert {
                it[record_date] = recordDTO.data
                it[login] = recordDTO.login
                it[mood] = recordDTO.mood
                it[note] = recordDTO.note
            }
        }
    }

    // возврат списка записей: пользователь присылает свой логин и токен,
    // сервер возвращает список дат, для которых есть записи у пользователя
    fun getDatesForUser(login: String): List<String> {
        return try {
                transaction {
                    Records
                        .select { Records.login.eq(login) }
                        .map { it[record_date] }
                        .toList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    //возврат одной записи: пользователь присылает свой логин, токен и дату,
    // сервер возвращает настроение, и записку
    fun getRecordByDate(login: String, date: String): Pair<Int, String>? {
        return try {
            transaction {
                Records
                    .select { (Records.login.eq(login)); (record_date.eq(date)) }
                    .single() // Получаем первую строку или null, если ничего не найдено
                    .let { row ->
                        val mood = row[mood] // Безопасное извлечение значения mood
                        val note = row[note] // Безопасное извлечение значения note
                        mood to note // Возвращаем пару (mood, note)
                    }
            }
        } catch (e: Exception) {
            e.printStackTrace() // Логирование ошибки
            null
        }
    }


    fun fetchRecord(login: String, data: String): RecordDTO? {
        return try {
            transaction {
                Records
                    .select { (Records.login.eq(login)) and (Records.record_date.eq(data)) }
                    .firstOrNull()
                    ?.let { row ->
                        RecordDTO(
                            data = row[Records.record_date],
                            login = row[Records.login],
                            mood = row[Records.mood],
                            note = row[Records.note]
                        )
                    }
            }
        } catch (e: Exception) {
            null
        }
    }


    //удаление записи: пользователь присылает свой логин, токен и дату,
    // сервер удаляет запись из бд
    fun deleteRecordByDate(login: String, data: String): Boolean {
        return try {
                transaction {
                    Records.deleteWhere { Records.login.eq(login); record_date.eq(data) }
                }
                true
        } catch (e: Exception) {
            false
        }
    }

}
