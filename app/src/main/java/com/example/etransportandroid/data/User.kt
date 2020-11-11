package com.example.etransportandroid.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var username: String? = "",
    var groupID: String = "",
    var orders: Orders
) {
    data class Orders (
        var commercial: ArrayList<String> = mutableListOf("") as ArrayList<String>,
        var private: ArrayList<String> = mutableListOf("") as ArrayList<String>

    )
}