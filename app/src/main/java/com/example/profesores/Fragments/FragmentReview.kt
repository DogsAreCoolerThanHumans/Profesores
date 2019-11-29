package com.example.profesores.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
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
import com.example.profesores.Fragments.profesores.FragmentProfesores
import com.example.profesores.activities.ActivityMain
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseRelation
import com.parse.ParseUser
import java.util.*


class FragmentReview : Fragment(), ProfesoresContract.View {
    private lateinit var profesList: Array<String>
    private lateinit var cursosList: Array<String>
    private lateinit var comboList: Array<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_review, container, false)

        val query = ParseQuery<ParseObject>("Profesores")

        val textView = view.findViewById<AutoCompleteTextView>(R.id.re_input_profesor)
                as AutoCompleteTextView//id del textview en layout
        val textViewC = view.findViewById<AutoCompleteTextView>(R.id.re_input_curso)
                as AutoCompleteTextView //id del textview en layout
        textViewC.isEnabled = false

        val comment = view.findViewById<EditText>(R.id.re_comentario)
        comment.isEnabled = false
        val submitReview = view.findViewById<Button>(R.id.re_finish_btn)


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
        textView.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(s.length == 0) {
                    textViewC.isEnabled = false
                    textViewC.setText("")
                }
                else {
                    textViewC.isEnabled = true
                    query.whereEqualTo("name", textView.text.toString())
                    query.getFirstInBackground { prof, e ->
                        if (e == null) {
                            var listOfCursos = (prof["cursos"] as ParseRelation<*>).query
                            listOfCursos.findInBackground { cursoList, err ->
                                if (err == null) {
                                    cursosList = Array(cursoList.size) { "" }
                                    for (i in 0..cursoList.size - 1) {
                                        cursosList[i] = cursoList[i]["name"].toString()
                                    }
                                    val adapterC = ArrayAdapter(
                                        requireActivity(),
                                        android.R.layout.simple_dropdown_item_1line, cursosList
                                    ) //simple_list_item_1
                                    textViewC.setAdapter(adapterC)
                                } else {
                                    Log.e("ERRORCURSOREVIEW", "Cursos not found from Profesor")
                                }
                            }
                        }
                    }
                }
            }
        })
        textViewC.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(s.length == 0) {
                    comment.setText("")
                    comment.isEnabled = false
                }
                else {
                    comment.isEnabled = true
                }
            }
        })


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
            if(comment.text.length > 6){
                comObject.put("userComm", ParseUser.getCurrentUser())
                val queryProfe = ParseQuery<ParseObject>("Profesores")
                val queryCurso = ParseQuery<ParseObject>("Cursos")
                queryCurso.whereMatches("name", textViewC.text.toString())
                queryProfe.whereMatches("name", textView.text.toString())
                queryProfe.getFirstInBackground { prof, e ->
                    if (e == null){
                        queryCurso.getFirstInBackground { curso, err ->
                            if(err == null){
                                comObject.put("profesorComm", prof)
                                comObject.put("cursoComm", curso)
                                comObject.put("Comment", comment.text.toString())
                                comObject.put("Likes", 0)
                                comObject.put("Dislikes", 0)
                                comObject.save()
                                Toast.makeText(this.context, "Review submitted!", Toast.LENGTH_SHORT).show()
                                val fragment = FragmentProfesores()
                                val args = Bundle()
                                (activity as ActivityMain).openFragment(fragment, args)
                            }
                            else {
                                Toast.makeText(this.context, "Curso not found!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    else {
                        Toast.makeText(this.context, "Profesor not found!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            else {
                Toast.makeText(this.context, "Your comment must be above 6 characters of length", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

}