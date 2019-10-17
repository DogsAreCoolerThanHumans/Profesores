package com.example.profesores.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import com.example.profesores.R

class ActivityLogin : AppCompatActivity() {
    private lateinit var mLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mLogin = find(R.id.activity_login_btn_login)
        mLogin.setOnClickListener {
            startActivity<ActivityMain>()
        }
    }
}
