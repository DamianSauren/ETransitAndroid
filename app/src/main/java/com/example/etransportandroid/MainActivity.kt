package com.example.etransportandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var currentFragment: Fragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)

        if(findViewById<ConstraintLayout>(R.id.container) != null) {
            if(savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, currentFragment)
                    .commit()
            }
        }
        
        setupMenuButtons()
//        val dataBase = DataBase()
//        dataBase.insert()
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