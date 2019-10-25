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

class FragmentProfesores : Fragment(), ProfesoresContract.View, AdapterProfesor.OnItemClickListener {
    private lateinit var adapter: AdapterProfesor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profesores, container, false)
        val title = view.findViewById<TextView>(R.id.fragment_profesores_tv_title) //profesores

        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_name_professors_rv)
        val names = arrayListOf<HashMap<String, String>>()
        names.add(HashMap())
        names[0].put("name", "Erick")
        names[0].put("lastName", "De Santiago")
        names.add(HashMap())
        names[1].put("name", "Edmundo")
        names[1].put("lastName", "Vidalvarez")
        names.add(HashMap())
        names[2].put("name", "Rogelio")
        names[2].put("lastName", "Sandoval")
        names.add(HashMap())
        names[3].put("name", "Javier")
        names[3].put("lastName", "Gil")
        names.add(HashMap())
        names[4].put("name", "Alberto")
        names[4].put("lastName", "Miramontes")
        adapter = AdapterProfesor(names)
        adapter.setListener(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }

    override fun onItemClick(position: Int) {
        //(activity as ActivityMain).openProfesorCurso()
        val fragment = FragmentProfesorCurso()
        val args = Bundle()
        args.putString("profesor", adapter.names[position].get("name") + " " +
                        adapter.names[position].get("lastName"))
        (activity as ActivityMain).openFragment(fragment, args)
    }
}