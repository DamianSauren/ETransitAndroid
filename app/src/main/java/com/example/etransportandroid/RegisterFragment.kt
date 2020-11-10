package com.example.etransportandroid

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
import com.example.etransportandroid.enumClasses.Fragments
import com.example.etransportandroid.interfaces.FragmentManagement
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment(private val fragmentManagement: FragmentManagement): Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var layout: View

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repeatPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_register, container, false)

        emailEditText = layout.findViewById(R.id.email_register_edittext)
        passwordEditText = layout.findViewById(R.id.password_register_edittext)
        repeatPasswordEditText = layout.findViewById(R.id.repeat_password_register_edittext)
        registerButton = layout.findViewById(R.id.register_button)
        loginTextView = layout.findViewById(R.id.login_textview)

        mAuth = FirebaseAuth.getInstance()
        setupRegisterForm()
        return layout
    }

    private fun setupRegisterForm() {
        registerButton  .setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val repeatPassword = repeatPasswordEditText.text.toString()

            var canRegister = true

            if(email == "") {
                Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show()
                canRegister = false
            }

            if(password == "") {
                Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                canRegister = false
            }

            if(repeatPassword == "") {
                Toast.makeText(context, "Repeat password cannot be empty", Toast.LENGTH_SHORT).show()
                canRegister = false
            }

            if(password != repeatPassword) {
                Toast.makeText(context, "Password and Repeat password don't match", Toast.LENGTH_SHORT).show()
                canRegister = false
            }

            if(canRegister) {
                register(email, password)
            }
        }

        loginTextView.setOnClickListener {
            fragmentManagement.changeFragment(Fragments.LOGIN)
        }
    }

    private fun register(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LOGIN", "createUserWithEmail:success")
                    val user = mAuth.currentUser
                    (activity as StartActivity).loadMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LOGIN", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}