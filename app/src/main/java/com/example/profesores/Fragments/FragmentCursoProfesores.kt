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
import com.example.profesores.adapters.AdapterCourseProfessor
import com.example.profesores.adapters.AdapterCurso
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseRelation

class FragmentCursoProfesores: Fragment(), AdapterCurso.OnItemClickListener,
    ProfesoresContract.View {
    private lateinit var adapter: AdapterCourseProfessor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cursos_profesores, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_name_cursos_profesores_rv)
        val query = ParseQuery<ParseObject>("Cursos")
        val names = arrayListOf<String>()

        val n = arguments?.getString("cursoId")

        val cursoTitle = view.findViewById<TextView>(R.id.com_cr_pr_tv_profesor)
        query.whereEqualTo("objectId", n)
        query.include("profesores")
        query.getFirstInBackground { curso, e ->
            if(e == null){
                cursoTitle.setText(curso.get("name").toString())
                var listOfProfs = (curso["profesores"] as ParseRelation<*>).query
                listOfProfs.findInBackground { profList, err ->
                    if(err == null){
                        for(prof in profList){
                            names.add(prof.get("name").toString())
                        }
                        adapter = AdapterCourseProfessor(names)
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