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
import com.example.profesores.activities.ActivityMain
import com.example.profesores.adapters.AdapterCurso
import com.example.profesores.adapters.AdapterProfesor
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class FragmentCursos : Fragment(), ProfesoresContract.View, AdapterCurso.OnItemClickListener,
    AdapterCurso.makeFavListener {
    private lateinit var adapter: AdapterCurso
    private val currentUser = ParseUser.getCurrentUser()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cursos, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_name_courses_rv)
        val query = ParseQuery<ParseObject>("Cursos")
        query.include("profesores")
        query.findInBackground { list, e ->
            if (e == null) {
                adapter = AdapterCurso(list)
                adapter.setListener(this)
                adapter.setFavListener(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this.context)
            } else
                error { "Error $e" }  // Log.e using anko
        }

        return view
    }

    override fun onItemClick(position: Int) {
        //(activity as ActivityMain).openProfesorCurso()
        val fragment = FragmentCursoProfesores()
        val args = Bundle()
        args.putString("cursoId", adapter.names[position].objectId)
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