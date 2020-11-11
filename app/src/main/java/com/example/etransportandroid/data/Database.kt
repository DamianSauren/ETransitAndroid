package com.example.etransportandroid.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Database {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    enum class GroupID {
        COMMERCIAL,
        PRIVATE
    }

    fun writeNewUser(userId: String, groupId: GroupID) {
        val group = when(groupId){
            GroupID.COMMERCIAL -> {
                "Commercial"
            }
            GroupID.PRIVATE -> {
                "Private"
            }
        }

        val user = User(
            groupID = group,
            orders = User.Orders(
                commercial = ArrayList(),
                private = ArrayList()
            )
        )
        database.reference.child("users").child(userId).setValue(user)
    }

    fun writeNewOrder(userId: String, order: Order) {
        var isDone = false
        val dbRef = database.reference

        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!isDone) {
                    //Get user groupID
                    val groupId = snapshot.child("users").child(userId).child("groupID").value.toString()
                    
                    var orderId = "error"
                    if(groupId == "Commercial") {
                        orderId = "C_Entry1"
                    } else if(groupId == "Private") {
                        orderId = "P_Entry1"
                    }

                    if(snapshot.child("orders").hasChildren()) { //Orders has children
                        //Get the last orderId
                        val lastOrderId = snapshot.child("orders").children.last().key.toString()

                        var number = 0
                        if(lastOrderId.contains("C_Entry")) {
                            number = lastOrderId.removePrefix("C_Entry").toInt()
                        } else if(lastOrderId.contains("P_Entry")) {
                            number = lastOrderId.removePrefix("P_Entry").toInt()
                        }

                        if(groupId == "Commercial" && number != 0) {
                            orderId = "C_Entry${number+1}"
                        } else if(groupId == "Private" && number != 0) {
                            orderId = "P_Entry${number+1}"
                        }
                    }

                    //Add new order
                    dbRef.child("orders").child(orderId).setValue(order)

                    val orderList = ArrayList<String>()
                    for(i in snapshot.child("users").child(userId).child("orders").child(groupId).children) {
                        orderList.add(i.value.toString())
                    }

                    //Add new order to order list
                    orderList.add(orderId)

                    //Update user with new order list
                    dbRef.child("users").child(userId).child("orders").child(groupId).setValue(orderList)
                    isDone = true
                }
            }
            override fun onCancelled(error: DatabaseError) { }
        }
        dbRef.addValueEventListener(postListener)
    }
}