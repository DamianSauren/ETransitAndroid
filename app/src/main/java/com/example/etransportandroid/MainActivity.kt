package com.example.etransportandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.etransportandroid.data.Database
import com.example.etransportandroid.data.Order
import com.example.etransportandroid.fragments.BookingFragment
import com.example.etransportandroid.fragments.HomeFragment
import com.example.etransportandroid.fragments.SettingsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.LocalDateTime

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

        Log.d("MainActivity", "User id is ${mAuth.currentUser?.uid}")


        Database().writeNewOrder(mAuth.currentUser?.uid.toString(), Order(
            itemDescription = "test",
            weight = "pittig zwaar",
            PickUpDate = "",
            hazards = "",
            timeFrame = 5,
            bookingDate = "",
            dimensions = Order.Dimensions (
                height = "",
                length = "",
                depth = ""
            ),
            locations = Order.Locations(
                to = "",
                from = ""
            )
        ))
        
        setupMenuButtons()
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