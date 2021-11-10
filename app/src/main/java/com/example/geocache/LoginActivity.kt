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

        val textViewRegister = findViewById<TextView>(R.id.textViewRegister)

        textViewRegister?.setOnClickListener{
            startActivity(Intent(this@LoginActivity,
                                 RegisterActivity::class.java))
        }

    }


}