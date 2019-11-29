package com.example.profesores.activities

import android.content.Context
import android.util.Log


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import com.example.profesores.R
import com.example.profesores.utils.SESSION_ID_KEY
import com.example.profesores.utils.SHARED_PREFERENCES
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
                mPassword.getText().toString()){ user, _ ->
                    if (user != null) {
                        saveSessionToken(user.sessionToken)
                        startActivity<ActivityMain>()
                    } else {
                        ParseUser.logOut()
                        Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun saveSessionToken(sessionToken: String) {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(SESSION_ID_KEY, sessionToken)
        editor.apply()
    }
}
