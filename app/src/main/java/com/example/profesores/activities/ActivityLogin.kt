package com.example.profesores.activities

import android.util.Log


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import com.example.profesores.R
import com.google.android.material.textfield.TextInputLayout

import com.parse.LogInCallback;
import com.parse.ParseUser;

class ActivityLogin : AppCompatActivity() {
    private lateinit var mLogin: Button
    private lateinit var mUsername: EditText
    private lateinit var mPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mUsername = findViewById(R.id.activity_login_username) as EditText
        val mPassword = findViewById(R.id.activity_login_password) as EditText



        mLogin = find(R.id.activity_login_btn_login)
        mLogin.setOnClickListener {
            ParseUser.logInInBackground(mUsername.getText().toString(),
                mPassword.getText().toString()){ user, e ->
                    if (user != null) {
                        startActivity<ActivityMain>()
                    } else {
                        ParseUser.logOut()
                        Log.e("Error", "Incorrect username or password")
                    }
                }
        }
    }
}
