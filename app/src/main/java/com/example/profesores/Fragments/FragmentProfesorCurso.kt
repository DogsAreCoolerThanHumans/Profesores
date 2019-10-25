package com.example.profesores.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.Fragments.profesores.ProfesoresContract
import com.example.profesores.R
import com.example.profesores.adapters.AdapterProfesor
import com.example.profesores.adapters.AdapterProfessorCourse

class FragmentProfesorCurso: Fragment(), AdapterProfesor.OnItemClickListener,
     ProfesoresContract.View {
    private lateinit var adapter: AdapterProfessorCourse
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profesores_cursos, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_name_profesores_cursos_rv)
        val names = arrayListOf<String>()

        val n = arguments?.getString("profesor")

        val profesorTitle = view.findViewById<TextView>(R.id.fragment_profesores_tv_title)

        profesorTitle.setText(n)
        names.add("Desarrollo de Aplicaciones Moviles")
        adapter = AdapterProfessorCourse(names)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }

    override fun onItemClick(position: Int) {
        Log.v("PROF", adapter.names[position])
    }

}