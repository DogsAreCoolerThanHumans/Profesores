package com.example.profesores.Fragments.profesores

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.Fragments.FragmentProfesorCurso
import com.example.profesores.R
import com.example.profesores.activities.ActivityMain
import com.example.profesores.adapters.AdapterProfesor
import com.parse.ParseObject
import com.parse.ParseQuery

class FragmentProfesores : Fragment(), ProfesoresContract.View, AdapterProfesor.OnItemClickListener {
    private lateinit var adapter: AdapterProfesor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profesores, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_name_professors_rv)
        val query = ParseQuery<ParseObject>("Profesores")
        query.include("cursos")
        query.findInBackground { list, e ->
            if (e == null) {
                adapter = AdapterProfesor(list)
                adapter.setListener(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this.context)
            } else
                error { "Error $e" }  // Log.e using anko
        }

        return view
    }

    override fun onItemClick(position: Int) {
        //(activity as ActivityMain).openProfesorCurso()
        val fragment = FragmentProfesorCurso()
        val args = Bundle()
        args.putString("profesorId", adapter.names[position].objectId)
        (activity as ActivityMain).openFragment(fragment, args)
    }
}