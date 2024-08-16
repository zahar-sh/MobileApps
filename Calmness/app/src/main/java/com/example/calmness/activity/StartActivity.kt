package com.example.calmness.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calmness.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class StartActivity : AppCompatActivity() {

    private lateinit var btSign: Button
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var tvReg: TextView
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btSign = findViewById(R.id.bt_sign)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        tvReg = findViewById(R.id.textViewReg)
        auth = Firebase.auth

        btSign.setOnClickListener {
            signIn(editEmail.text.toString(), editPassword.text.toString())
        }
        tvReg.setOnClickListener {
            createAccount(editEmail.text.toString(), editPassword.text.toString())
        }
    }

    private fun createAccount(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        } catch (e: IllegalArgumentException) {
            Toast.makeText(
                this, "Invalid email or password.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun signIn(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        } catch (e: IllegalArgumentException) {
            Toast.makeText(
                this, "Invalid email or password.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }
}