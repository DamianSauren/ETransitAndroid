package com.example.etransportandroid.data

import android.util.Log
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DataBase {

    fun insert() {
        Database.connect("jdbc:postgresql:localhost:5432/ETransport", driver = "org.postgresql.Driver", user = "postgres", password = "password")

        transaction {
            addLogger(StdOutSqlLogger)

            val result = transaction {
                USERS.selectAll().toList()
            }

            Log.d("DataBase", "result is $result")

        }
    }

    object USERS: Table() {
        val user_id = integer("USER_ID").autoIncrement()
        val user_name = varchar("USER_NAME", 50)

        override val primaryKey = PrimaryKey(user_id, name = "user_id")
    }
}