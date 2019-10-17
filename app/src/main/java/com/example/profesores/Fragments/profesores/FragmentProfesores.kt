package com.example.profesores.Fragments.profesores

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.profesores.R

class FragmentProfesores : Fragment(), ProfesoresContract.View {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profesores, container, false)
        val title = view.findViewById<TextView>(R.id.fragment_profesores_tv_title) //profesores


        return view
    }
}