package com.example.etransportandroid.data

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DataBase {

    object Users: Table() {
        val id = varchar("id", 10)
        val name = varchar("name", 50)

        override val primaryKey = PrimaryKey(id, name = "PK_User_ID")
    }

    fun insert() {
        Database.connect("jdbc:postgresql://localhost:5432/ETransport", driver = "org.postgresql.Driver", user = "postgres", password = "password")

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Users)

            Users.insert {
                it[id] = "damian"
                it[name] = "Damian"
            }
        }
    }
}