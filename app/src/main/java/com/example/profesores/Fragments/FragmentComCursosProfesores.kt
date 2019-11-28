package com.example.profesores.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.profesores.R
import com.parse.ParseObject
import com.parse.ParseQuery


class FragmentComCursosProfesores : Fragment() {
    private lateinit var cursosList: Array<String>
    private lateinit var profesList: Array<String>
    private lateinit var comboList: Array<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_com_cursos_profesores, container, false)


        val queryP = ParseQuery<ParseObject>("Profesores")
        val queryC = ParseQuery<ParseObject>("Cursos")

        val textView = view.findViewById<AutoCompleteTextView>(R.id.com_cr_pr_searchText)
                as AutoCompleteTextView//id del textview en layout

        queryC.findInBackground { profes, e ->
            if (e == null) {
                profesList = Array(profes.size) { "" }
                for (i in 0..profes.size - 1) {
                    profesList[i] = (profes[i]["name"].toString())
                }

            }
        }

        queryP.findInBackground { cursos, e ->
            if (e == null) {
                cursosList = Array(cursos.size) { "" }
                for (i in 0..cursos.size - 1) {
                    cursosList[i] = (cursos[i]["name"].toString())
                }

                comboList = cursosList + profesList //para mostrar ambos profes y cursos en la misma lista de autocomplete

                val adapter1 = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_dropdown_item_1line, comboList
                ) //simple_list_item_1
                textView.setAdapter(adapter1)

            }
        }

        textView.threshold = 1

        textView.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) {
                // Display the suggestion dropdown on focus
                textView.showDropDown()
            }
        }
        return view
    }

}