package com.example.etransportandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.etransportandroid.R
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment: Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_settings, container, false)
        mAuth = FirebaseAuth.getInstance()

        val button = inflate.findViewById<Button>(R.id.logout_button)
        button.setOnClickListener {
            mAuth.signOut() //Logout user
        }

        //Wait for the logout response and then send the user back to startActivity
        mAuth.addAuthStateListener {
            if(mAuth.currentUser == null) {
                //TODO go to startActivity
            }
        }

        return inflate
    }
}