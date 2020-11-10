package com.example.etransportandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.etransportandroid.enumClasses.Fragments
import com.example.etransportandroid.interfaces.FragmentManagement
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class StartActivity: AppCompatActivity(), FragmentManagement {
    private lateinit var mAuth: FirebaseAuth

    private var currentFragment: Fragment = LoginFragment(this)

    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()

        //Check if the current user is singed in (non-null) and proceed to MainActivity
        val currentUser = mAuth.currentUser
        if(currentUser!= null) {
            loadMainActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_login)

        if(findViewById<ConstraintLayout>(R.id.container) != null) {
            if(savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, currentFragment)
                    .commit()
            }
        }
    }

    public fun loadMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun addFragmentToActivity(fragment: Fragment){
        val fm = supportFragmentManager
        val tr = fm.beginTransaction()
        tr.replace(R.id.container, fragment)
        tr.commit()
        currentFragment = fragment
    }

    override fun changeFragment(fragment: Fragments) {
        when(fragment) {
            Fragments.LOGIN -> {
                addFragmentToActivity(LoginFragment(this))
            }
            Fragments.REGISTER -> {
                addFragmentToActivity(RegisterFragment(this))
            }
        }
    }
}