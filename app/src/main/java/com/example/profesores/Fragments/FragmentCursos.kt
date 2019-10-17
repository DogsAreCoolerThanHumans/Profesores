package com.example.profesores.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.Fragments.profesores.ProfesoresContract
import com.example.profesores.R
import com.example.profesores.adapters.AdapterCurso
import com.example.profesores.adapters.AdapterProfesor

class FragmentCursos : Fragment(), ProfesoresContract.View {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cursos, container, false)
        val title = view.findViewById<TextView>(R.id.fragment_cursos_tv_title) //profesores

        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_name_courses_rv)
        val names = arrayListOf<HashMap<String, String>>()
        names.add(HashMap())
        names[0].put("name", "Erick")
        names[0].put("lastName", "De Santiago")
        names.add(HashMap())
        names[1].put("name", "Raul")
        names[1].put("lastName", "Alvarez")
        names.add(HashMap())
        names[2].put("name", "Luis")
        names[2].put("lastName", "Beltran")
        names.add(HashMap())
        names[3].put("name", "Javier")
        names[3].put("lastName", "Torres")
        names.add(HashMap())
        names[4].put("name", "Isaac")
        names[4].put("lastName", "Cabrera")
        recyclerView.adapter = AdapterCurso(names)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }
}