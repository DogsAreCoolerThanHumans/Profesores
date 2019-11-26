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
import com.example.profesores.adapters.AdapterCourseProfessor
import com.example.profesores.adapters.AdapterCurso
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseRelation
import com.parse.ParseUser

class FragmentCursoProfesores : Fragment(), AdapterCourseProfessor.OnItemClickListener,
    AdapterCourseProfessor.makeFavListener, ProfesoresContract.View {
    private lateinit var adapter: AdapterCourseProfessor
    private val currentUser = ParseUser.getCurrentUser()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cursos_profesores, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.cr_pr_rv)
        val query = ParseQuery<ParseObject>("Cursos")

        val n = arguments?.getString("cursoId")

        val cursoTitle = view.findViewById<TextView>(R.id.cr_pr_tv_curso)
        query.whereEqualTo("objectId", n)
        query.include("cursos")
        query.include("profesores")
        query.getFirstInBackground { curso, e ->
            if (e == null) {
                cursoTitle.setText(curso.get("name").toString())
                var listOfProfs = (curso["profesores"] as ParseRelation<*>).query
                listOfProfs.findInBackground { profList, err ->
                    if (err == null) {
                        adapter = AdapterCourseProfessor(profList)
                        adapter.setListener(this)
                        adapter.setFavListener(this)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(view.context)
                    } else {
                        Log.v("ERROR", "Hubo un error con la relación Profesor-Curso en Parse")
                    }
                }
            } else {
                Log.e(
                    "ERROR", "Ha habido un problema con el query para la vista " +
                            "de profesores-curso"
                )
            }
        }
        return view
    }

    override fun onItemClick(position: Int) {
        val fragment = FragmentComCursosProfesores()
        val args = Bundle()
        (activity as ActivityMain).openFragment(fragment, args)
    }

    override fun favItemClick(position: Int) {

        val userProfes = currentUser.getRelation<ParseObject>("profesoresFav")

        userProfes.query.whereEqualTo("name", adapter.names[position]["name"]).getFirstInBackground {
                _, e->
            if(e == null){
                adapter.names[position].saveInBackground {
                    userProfes.remove(adapter.names[position])
                }
            }
            else {
                adapter.names[position].saveInBackground {
                    userProfes.add(adapter.names[position])
                }
            }
        }

        currentUser.saveInBackground()
        adapter.notifyDataSetChanged()

    }
}