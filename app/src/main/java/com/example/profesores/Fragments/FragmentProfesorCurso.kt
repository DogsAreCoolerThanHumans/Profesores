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
import com.example.profesores.activities.ActivityMain
import com.example.profesores.adapters.AdapterProfessorCourse
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseRelation
import com.parse.ParseUser

class FragmentProfesorCurso: Fragment(), AdapterProfessorCourse.OnItemClickListener,
     AdapterProfessorCourse.makeFavListener, ProfesoresContract.View {
    private lateinit var adapter: AdapterProfessorCourse
    private val currentUser = ParseUser.getCurrentUser()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profesores_cursos, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_name_profesores_cursos_rv)
        val query = ParseQuery<ParseObject>("Profesores")
        val profesorTitle = view.findViewById<TextView>(R.id.com_cr_pr_tv_curso)


        val n = arguments?.getString("profesorId")
        query.whereEqualTo("objectId", n)
        query.include("cursos")
        query.getFirstInBackground { prof, e ->
            if(e == null){
                profesorTitle.setText(prof.get("name").toString())
                var listOfCursos = (prof["cursos"] as ParseRelation<*>).query
                listOfCursos.findInBackground { cursoList, err ->
                    if(err == null){
                        adapter = AdapterProfessorCourse(cursoList)
                        adapter.setListener(this)
                        adapter.setFavListener(this)
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
        //AQUI TENEMOS QUE MANDARLE EL NOMBRE DEL PROFESOR, CURSO Y LOS IDS PARA LA BD
        val fragment = FragmentComProfesoresCursos()
        val args = Bundle()
        (activity as ActivityMain).openFragment(fragment, args)
    }

    override fun favItemClick(position: Int) {

        val userCursos = currentUser.getRelation<ParseObject>("cursosFav")

        userCursos.query.whereEqualTo("name", adapter.names[position]["name"]).getFirstInBackground {
                favCurso, e->
            if(e == null){
                adapter.names[position].saveInBackground {
                    userCursos.remove(adapter.names[position])
                }
            }
            else {
                adapter.names[position].saveInBackground {
                    userCursos.add(adapter.names[position])
                }
            }
        }

        currentUser.saveInBackground()
        adapter.notifyDataSetChanged()
    }

}