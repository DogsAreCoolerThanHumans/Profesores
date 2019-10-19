package com.example.profesores.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.example.profesores.adapters.AdapterProfessorCourse
import org.jetbrains.anko.contentView

class ActivityProfessorCourse : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profesores_cursos)

        val recyclerView = findViewById<RecyclerView>(R.id.activity_name_profesores_cursos_rv)
        val names = arrayListOf<String>()
        names.add("Desarrollo de Aplicaciones Moviles")
        recyclerView.adapter = AdapterProfessorCourse(names)
        recyclerView.layoutManager = LinearLayoutManager(this.contentView?.context)
    }
}
