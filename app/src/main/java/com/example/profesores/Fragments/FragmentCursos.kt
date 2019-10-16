package com.example.profesores.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.profesores.Fragments.profesores.ProfesoresContract
import com.example.profesores.R

class FragmentCursos : Fragment(), ProfesoresContract.View {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cursos, container, false)
        val title = view.findViewById<TextView>(R.id.fragment_cursos_tv_title) //profesores

        return view
    }
}