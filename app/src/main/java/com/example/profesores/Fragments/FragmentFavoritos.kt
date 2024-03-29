package com.example.profesores.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.Fragments.profesores.ProfesoresContract
import com.example.profesores.R
import com.example.profesores.activities.ActivityMain
import com.example.profesores.adapters.AdapterCurso
import com.example.profesores.adapters.AdapterFavoritos
import com.example.profesores.adapters.AdapterProfesor
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseRelation
import com.parse.ParseUser
import org.jetbrains.anko.find

class FragmentFavoritos : Fragment(), ProfesoresContract.View, AdapterFavoritos.OnItemClickListener,
        AdapterFavoritos.makeFavListener{
    private lateinit var adapterProf: AdapterFavoritos
    private lateinit var adapterCurso: AdapterFavoritos

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)
        val profRecyclerView = view.findViewById<RecyclerView>(R.id.fv_rv_profesores)
        val cursosRecyclerView = view.findViewById<RecyclerView>(R.id.fv_rv_cursos)

        //var cursosList = mutableListOf<String>()
        //var profesList = mutableListOf<String>()

        var currentUser = ParseUser.getCurrentUser()
        (currentUser["profesoresFav"] as ParseRelation<*>).query.findInBackground { profList, e ->
            if(e == null){
                adapterProf = AdapterFavoritos(profList, 1)
                adapterProf.setListener(this)
                adapterProf.setFavListener(this)
                profRecyclerView.adapter = adapterProf
                profRecyclerView.layoutManager = LinearLayoutManager(view.context)
            }
            else {
                Log.e("ERROR", "Error finding profesores favoritos")
            }
        }

        (currentUser["cursosFav"] as ParseRelation<*>).query.findInBackground { cursosList, e ->
            if(e == null){
                adapterCurso = AdapterFavoritos(cursosList, 2)
                adapterCurso.setListener(this)
                adapterCurso.setFavListener(this)
                cursosRecyclerView.adapter = adapterCurso
                cursosRecyclerView.layoutManager = LinearLayoutManager(view.context)
            }
            else {
                Log.e("ERROR", "Error finding cursos favoritos")
            }
        }

        /*
        val query = ParseQuery<ParseObject>("Profesores")
        val queryC = ParseQuery<ParseObject>("Cursos")

        val textView = view.findViewById<AutoCompleteTextView>(R.id.fv_searchEdit)
                as AutoCompleteTextView//id del textview en layout

        queryC.findInBackground { profes, e ->
            if (e == null) {
                for (i in 0..profes.size - 1) {
                    profesList.add(profes[i]["name"].toString())
                }

            }
        }

        query.findInBackground { cursos, e ->
            if (e == null) {
                for (i in 0..cursos.size - 1) {
                    cursosList.add(cursos[i]["name"].toString())
                }

                var comboList = (cursosList + profesList) //para mostrar ambos profes y cursos en la misma lista de autocomplete

                val adapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_dropdown_item_1line, comboList
                ) //simple_list_item_1
                textView.setAdapter(adapter)

            }
        }

        textView.threshold = 1

        textView.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) {
                // Display the suggestion dropdown on focus
                textView.showDropDown()
            }
        }

         */

        return view
    }

    override fun onItemClick(position: Int, isProf: Int) {
        //(activity as ActivityMain).openProfesorCurso()
        lateinit var fragment: Fragment
        val args = Bundle()
        if(isProf == 1) {
            fragment = FragmentProfesorCurso()
            args.putString("profesorDeCurso", adapterProf.recipes[position].objectId)
        }

        else {
            fragment = FragmentCursoProfesores()
            args.putString("cursoId", adapterCurso.recipes[position].objectId)
        }

        (activity as ActivityMain).openFragment(fragment, args)
    }

    override fun favItemClick(position: Int, isProf: Int) {
        val currentUser = ParseUser.getCurrentUser()
        if(isProf == 1) {
            val userProfes = currentUser.getRelation<ParseObject>("profesoresFav")

            userProfes.query.whereEqualTo("name", adapterProf.recipes[position]["name"]).getFirstInBackground {
                    _, e->
                if(e == null){
                    adapterProf.recipes[position].saveInBackground {
                        userProfes.remove(adapterProf.recipes[position])
                    }
                }
            }
            adapterProf.notifyDataSetChanged()
        }

        else {
            val userCursos = currentUser.getRelation<ParseObject>("cursosFav")
            userCursos.query.whereEqualTo("name", adapterCurso.recipes[position]["name"]).getFirstInBackground {
                    _, e->
                if(e == null){
                    adapterCurso.recipes[position].saveInBackground {
                        userCursos.remove(adapterCurso.recipes[position])
                    }
                }
            }

            adapterCurso.notifyDataSetChanged()
        }

        currentUser.saveInBackground()
    }
}