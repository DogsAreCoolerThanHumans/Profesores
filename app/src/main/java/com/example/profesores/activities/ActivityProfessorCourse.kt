package com.example.profesores.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.profesores.R
import com.parse.ParseUser
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class ActivityCourseProfessor : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profesores_cursos)

    }
}
