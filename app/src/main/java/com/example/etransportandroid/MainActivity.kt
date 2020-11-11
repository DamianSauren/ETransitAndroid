package com.example.etransportandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.etransportandroid.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var currentFragment: Fragment = HomeFragment()
    private lateinit var mAuth: FirebaseAuth

    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(findViewById<ConstraintLayout>(R.id.container) != null) {
            if(savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, currentFragment)
                    .commit()
            }
        }
        mAuth = FirebaseAuth.getInstance()

        // Write a message to the database
        database = FirebaseDatabase.getInstance()
        writeNewUser(mAuth.currentUser?.uid.toString(), mAuth.currentUser?.displayName.toString(), mAuth.currentUser?.email.toString())

        Log.d("MainActivity", "User id is ${mAuth.currentUser?.uid}")
        
        setupMenuButtons()
    }

    private fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(
            name,
            "Commercial",
            User.Orders(
                commercial = mutableListOf("C_Entry1", "C_Entry2") as ArrayList<String>,
                private = mutableListOf("") as ArrayList<String>
            )
        )
        database.reference.child("users").child(userId).setValue(user)
    }
    
    private fun setupMenuButtons(){
        booking_button.setOnClickListener {
            if(currentFragment != BookingFragment()) {
                addFragmentToActivity(BookingFragment())
            }
        }

        home_button.setOnClickListener {
            if(currentFragment != HomeFragment()){
                addFragmentToActivity(HomeFragment())
            }
        }

        settings_button.setOnClickListener {
            if(currentFragment != SettingsFragment()) {
                addFragmentToActivity(SettingsFragment())
            }
        }
    }

    private fun addFragmentToActivity(fragment: Fragment){
        val fm = supportFragmentManager
        val tr = fm.beginTransaction()
        tr.replace(R.id.container, fragment)
        tr.commit()
        currentFragment = fragment
    }
}