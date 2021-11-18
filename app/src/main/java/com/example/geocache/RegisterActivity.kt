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



class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton           = findViewById<Button>(R.id.btnRegister)
        val registerEmailTextView    = findViewById<TextView>(R.id.editTextRegisterEmail)
        val registerPasswordTextView = findViewById<TextView>(R.id.editTextRegisterPassword)
        val textViewLogin            = findViewById<TextView>(R.id.textViewLogin)

        textViewLogin?.setOnClickListener{
            onBackPressed()
        }

        registerButton?.setOnClickListener()
        {
            when
            {
                // Empty user input
                TextUtils.isEmpty(registerEmailTextView.text.toString().trim { it <= ' '}) ->
                {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter your email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(registerPasswordTextView.text.toString().trim { it <= ' '}) ->
                {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter your password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> // Inputs given
                {
                    val email:    String = registerEmailTextView.text.toString().trim { it <= ' '}
                    val password: String = registerPasswordTextView.text.toString().trim { it <= ' '}

                    // Register the user
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                        { task ->
                                if (task.isSuccessful)
                                {
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "You are now registered.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // After successful, send user to Main activity
                                    val intent = Intent( this@RegisterActivity, MapsActivity::class.java)
                                                 intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                 intent.putExtra("userID", firebaseUser.uid)
                                                 intent.putExtra("userEmail", email)
                                    startActivity(intent)
                                    finish()
                                }
                                else
                                {
                                    // Register error
                                    Toast.makeText(
                                        this@RegisterActivity,
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