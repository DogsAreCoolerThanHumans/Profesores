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
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseRelation

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
        val query = ParseQuery<ParseObject>("Profesores")
        val profesorTitle = view.findViewById<TextView>(R.id.fragment_profesores_tv_title)
        val names = arrayListOf<String>()


        val n = arguments?.getString("profesorId")
        query.whereEqualTo("objectId", n)
        query.include("cursos")
        query.getFirstInBackground { prof, e ->
            if(e == null){
                profesorTitle.setText(prof.get("name").toString())
                var listOfCursos = (prof["cursos"] as ParseRelation<*>).query
                listOfCursos.findInBackground { cursoList, err ->
                    if(err == null){
                        for(curso in cursoList){
                            names.add(curso.get("name").toString())
                        }
                        adapter = AdapterProfessorCourse(names)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(view.context)
                    }
                    else {
                        Log.v("ERROR","Hubo un error con la relación Profesor-Curso en Parse")
                    }
                }
            }
            else {
                Log.e("ERROR", "Ha habido un problema con el query para la vista " +
                        "de profesores-curso")
            }
        }
        return view
    }

    override fun onItemClick(position: Int) {
        Log.v("PROF", adapter.names[position])
    }

}