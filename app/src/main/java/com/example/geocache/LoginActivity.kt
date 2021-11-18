package com.example.geocache

import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.app.Activity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton           = findViewById<Button>(R.id.btnLogin)
        val loginEmailTextView    = findViewById<TextView>(R.id.editTextLoginEmail)
        val loginPasswordTextView = findViewById<TextView>(R.id.editTextLoginPassword)
        val textViewRegister      = findViewById<TextView>(R.id.textViewRegister)

        textViewRegister?.setOnClickListener{
            startActivity(Intent(this@LoginActivity,
                                 RegisterActivity::class.java))
        }

        loginButton?.setOnClickListener()
        {
            when
            {
                // Empty user input
                TextUtils.isEmpty(loginEmailTextView.text.toString().trim { it <= ' '}) ->
                {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter your email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(loginPasswordTextView.text.toString().trim { it <= ' '}) ->
                {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter your password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> // Inputs given
                {
                    val email:    String = loginEmailTextView.text.toString().trim { it <= ' '}
                    val password: String = loginPasswordTextView.text.toString().trim { it <= ' '}

                    // Log-in the user
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            { task ->
                                if (task.isSuccessful)
                                {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Log in successful.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // After successful, send user to Main activity
                                    val intent = Intent( this@LoginActivity, MapsActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("userID", FirebaseAuth.getInstance().currentUser!!.uid)
                                    intent.putExtra("userEmail", email)
                                    startActivity(intent)
                                    finish()
                                }
                                else
                                {
                                    // Login error
                                    Toast.makeText(
                                        this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                }
            }
        }
    }
}