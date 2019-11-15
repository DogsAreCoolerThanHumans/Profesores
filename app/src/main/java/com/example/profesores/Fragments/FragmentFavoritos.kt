package com.example.profesores.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.Fragments.profesores.ProfesoresContract
import com.example.profesores.R
import com.example.profesores.adapters.AdapterCurso
import com.example.profesores.adapters.AdapterFavoritos
import org.jetbrains.anko.find

class FragmentFavoritos : Fragment(), ProfesoresContract.View {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)
        val title = view.findViewById<TextView>(R.id.fragment_favoritos_tv_title) //profesores

        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_name_favoritos_rv)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }
}