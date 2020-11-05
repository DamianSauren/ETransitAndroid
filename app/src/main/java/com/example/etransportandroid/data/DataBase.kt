package com.example.etransportandroid.data

import android.util.Log
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DataBase {

    fun insert() {
        Database.connect("jdbc:h2:mem:ETransport", driver = "org.h2.Driver", user = "postgres", password = "password")

        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(Cities)
//
//            //Insert new city
//            val stPete = City.new {
//                name = "St. Petersburg"
//            }
//
                val results = City.all()
            Log.d("Cities:", "result: $results")
        }
    }

    object Cities: IntIdTable() {
        val name = varchar("name", 50)
    }

    class City(id: EntityID<Int>) : IntEntity(id) {
        companion object: IntEntityClass<City>(Cities)

        var name by Cities.name
    }
}