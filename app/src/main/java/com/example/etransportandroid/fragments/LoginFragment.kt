package com.example.etransportandroid.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.etransportandroid.R
import com.example.etransportandroid.StartActivity
import com.example.etransportandroid.enumClasses.Fragments
import com.example.etransportandroid.interfaces.FragmentManagement
import com.google.firebase.auth.FirebaseAuth

class LoginFragment(private val fragmentManagement: FragmentManagement): Fragment() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var layout: View

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_login, container, false)
        mAuth = FirebaseAuth.getInstance()

        emailEditText = layout.findViewById(R.id.email_login_edittext)
        passwordEditText = layout.findViewById(R.id.password_login_edittext)
        loginButton = layout.findViewById(R.id.login_button)
        registerTextView = layout.findViewById(R.id.register_textview)

        setupLoginForm()
        return layout
    }

    private fun setupLoginForm() {
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            var canLogin = true

            if(email == "") {
                Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show()
                canLogin = false
            }

            if(password == "") {
                Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                canLogin = false
            }

            if(canLogin) {
                login(email, password)
            }
        }

        registerTextView.setOnClickListener {
            fragmentManagement.changeFragment(Fragments.REGISTER)
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    //Sign in success
                    Log.d("LOGIN", "signInWithEmail:success")
                    (activity as StartActivity).loadMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LOGIN", "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}