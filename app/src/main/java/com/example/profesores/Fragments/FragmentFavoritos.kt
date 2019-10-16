package com.example.profesores.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.example.profesores.adapters.AdapterFavoritos
import org.jetbrains.anko.find

class FragmentFavoritos : Fragment() {
    private lateinit var mAdapter: AdapterFavoritos //
    private lateinit var mRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)
        /* TODO fetch data from parse and replace it inside AdapterRecipe*/ //de tarea previa
        mAdapter = AdapterFavoritos(arrayListOf()) //
        mRecycler = view.find(R.id.fragment_favoritos_rv_favoritos)
        mRecycler.adapter = mAdapter
        mRecycler.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        return view
    }
}