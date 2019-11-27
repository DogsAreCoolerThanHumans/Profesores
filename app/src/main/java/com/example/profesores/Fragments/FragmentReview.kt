package com.example.profesores.Fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.profesores.Fragments.profesores.ProfesoresContract
import com.example.profesores.R


import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseRelation
import com.parse.ParseUser


class FragmentReview : Fragment(), ProfesoresContract.View {
    private lateinit var profesList: Array<String>
    private lateinit var cursosList: Array<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_review, container, false)
//        val title = view.findViewById<TextView>(R.id.fragment_review_tv_title) //profesores


        val query = ParseQuery<ParseObject>("Profesores")
        val queryC = ParseQuery<ParseObject>("Cursos")

        val textView = view.findViewById<AutoCompleteTextView>(R.id.re_input_profesor)
                as AutoCompleteTextView//id del textview en layout
        val textViewC = view.findViewById<AutoCompleteTextView>(R.id.re_input_curso)
                as AutoCompleteTextView //id del textview en layout
        val comment = view.findViewById<EditText>(R.id.re_comentario)
        val submitReview = view.findViewById<Button>(R.id.re_finish_btn)

        if(textViewC.text.toString() != "" && textView.text.toString() == ""){
            queryC.whereEqualTo("name", textViewC.text)
            if(queryC.first != null){
                queryC.getFirstInBackground { curso, e ->
                    if(e == null) {
                        var listOfProfes = (curso["profesores"] as ParseRelation<*>).query
                        listOfProfes.findInBackground { profes, err ->
                            if(err == null){
                                profesList = Array(profes.size) { "" }
                                for(i in 0..profes.size - 1){
                                    profesList[i] = (profes[i]["name"].toString())
                                }

                                val adapter = ArrayAdapter(requireActivity(),
                                    android.R.layout.simple_dropdown_item_1line, profesList)
                                textView.setAdapter(adapter)
                            }
                        }
                    }
                    else
                        Log.e("ERROR", "PROF NOT FOUND")
                }
            }
        }
        else if(textView.text.toString() != "" && textViewC.text.toString() == ""){
            query.whereEqualTo("name", textView.text)
            if(query.first != null){
                query.getFirstInBackground { prof, e ->
                    if(e == null) {
                        var listOfCursos = (prof["cursos"] as ParseRelation<*>).query
                        listOfCursos.findInBackground { cursos, err ->
                            if(err == null){
                                cursosList = Array(cursos.size) {""}
                                for(i in 0..cursos.size - 1){
                                    cursosList[i] = (cursos[i]["name"].toString())
                                }

                                val adapterC = ArrayAdapter(requireActivity(),
                                    android.R.layout.simple_dropdown_item_1line, cursosList)
                                textView.setAdapter(adapterC)
                            }
                        }
                    }
                    else
                        Log.e("ERROR", "PROF NOT FOUND")
                }
            }
        }
        else {
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
            queryC.findInBackground { cursos, e ->
                if(e == null){
                    cursosList = Array(cursos.size) {""}
                    for(i in 0..cursos.size - 1){
                        cursosList[i] = (cursos[i]["name"].toString())
                    }

                    val adapterC = ArrayAdapter(requireActivity(),
                        android.R.layout.simple_dropdown_item_1line, cursosList) //simple_list_item_1
                    textViewC.setAdapter(adapterC)
                }
            }
        }


        textView.threshold = 1 //número de caracteres escritos para mostrar sugerencias

        // Set a focus change listener for auto complete text view
        textView.onFocusChangeListener = View.OnFocusChangeListener{
                view, b ->
            if(b){
                // Display the suggestion dropdown on focus
                textView.showDropDown()
            }
        }


        textViewC.threshold = 1 //número de caracteres escritos para mostrar sugerencias

        // Set a focus change listener for auto complete text view
        textViewC.onFocusChangeListener = View.OnFocusChangeListener{
                view, b ->
            if(b){
                // Display the suggestion dropdown on focus
                textViewC.showDropDown()
            }
        }




        submitReview.setOnClickListener {
            val comObject = ParseObject("Comments")
            if(textView.text.toString() != "" && textViewC.text.toString() != ""
                && comment!!.text.toString() != "" && comment.text.length > 6){
                comObject.put("userComm", ParseUser.getCurrentUser())
                val queryProfe = ParseQuery<ParseObject>("Profesores")
                val queryCurso = ParseQuery<ParseObject>("Cursos")
                queryCurso.whereMatches("name", textViewC.text.toString())
                queryProfe.whereMatches("name", textView.text.toString())
                var profId = ""
                var cursoId = ""
                queryProfe.getFirstInBackground { prof, e ->
                    if (e == null){
                        profId = prof.objectId
                        queryCurso.getFirstInBackground { curso, err ->
                            if(err == null){
                                cursoId = curso.objectId
                                comObject.put("profesorComm", prof)
                                comObject.put("cursoComm", curso)
                                Log.v("PROFID, CURSOID COMMENT", profId + " " + cursoId + " "
                                        + comment.text.toString())
                                comObject.put("Comment", comment.text.toString())
                                comObject.put("Likes", 0)
                                comObject.put("Dislikes", 0)
                                comObject.save()
                            }
                            else {
                                val curso = ParseObject("Cursos")
                                curso.put("name", textViewC.text)
                                curso.saveInBackground().isCompleted
                                cursoId = curso.objectId
                            }
                        }

                    }
                    else {
                        val profe = ParseObject("Profesores")
                        profe.put("name", textView.text)
                        profe.saveInBackground().isCompleted
                        profId = profe.objectId
                    }
                }
                Toast.makeText(this.context, "Review submitted!", Toast.LENGTH_SHORT).show()
            }
        }

        return view

    }

}