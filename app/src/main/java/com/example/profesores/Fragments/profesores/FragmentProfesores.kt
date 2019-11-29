package com.example.profesores.Fragments.profesores

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
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
import com.parse.ParseRelation
import com.parse.ParseUser

class FragmentProfesores : Fragment(), ProfesoresContract.View, AdapterProfesor.OnItemClickListener, AdapterProfesor.makeFavListener {
    private lateinit var adapter: AdapterProfesor
    private val currentUser = ParseUser.getCurrentUser()
    private lateinit var searchView: EditText //para filters
    private lateinit var profesList: Array<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_profesores, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.pr_rv)
        val query = ParseQuery<ParseObject>("Profesores")

        //Para filterd:
        //searchView =  view.findViewById(R.id.pr_searchEdit)
        query.include("cursos")
        query.findInBackground { list, e ->
            if (e == null) {
                adapter = AdapterProfesor(list)
                adapter.setListener(this)
                adapter.setFavListener(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this.context)
            } else
                error { "Error $e" }  // Log.e using anko
        }

        /*
        val textView = view.findViewById<AutoCompleteTextView>(R.id.pr_searchEdit)
                as AutoCompleteTextView//id del textview en layout

        query.findInBackground { profes, e ->
            if (e == null) {
                profesList = Array(profes.size) { "" }
                for (i in 0..profes.size - 1) {
                    profesList[i] = (profes[i]["name"].toString())
                }

                val adapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_dropdown_item_1line, profesList
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




    override fun onItemClick(position: Int) {
        //(activity as ActivityMain).openProfesorCurso()
        val fragment = FragmentProfesorCurso()
        val args = Bundle()
        args.putString("profesorDeCurso", adapter.names[position].objectId)
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
        adapter.names[position].saveInBackground()
        adapter.notifyDataSetChanged()

    }
}