package com.example.profesores.Fragments

import android.os.Bundle
import android.util.Log
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
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseRelation
import com.parse.ParseUser
import org.jetbrains.anko.find

class FragmentFavoritos : Fragment(), ProfesoresContract.View {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_name_favoritos_rv)
/*        val query = ParseQuery<ParseObject>("User")
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().objectId)
        query.include("Profesores")
        query.include("Cursos")

        query.getFirstInBackground { list, e ->
            if(e == null){
                var listOfProfs = (list["profesoresFav"] as ParseRelation<*>).query
                //POR COMPLETAR
            }
            else {
                Log.e("ERROR", "ERROR EN FAVORITOS")
            }

        }*/

        var names = arrayListOf<String>()
        names.add("Desarrollo de Aplicaciones Moviles")
        names.add("Erick Anaya De Santiago")

        recyclerView.adapter = AdapterFavoritos(names)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        return view
    }

}