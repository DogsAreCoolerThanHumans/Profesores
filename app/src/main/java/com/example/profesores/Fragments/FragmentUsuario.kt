package com.example.profesores.Fragments

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.Fragments.profesores.ProfesoresContract
import com.example.profesores.R
import com.example.profesores.activities.ActivityLogin
import com.example.profesores.adapters.AdapterComentario
import com.parse.ParseObject
import com.parse.ParseQuery
import com.example.profesores.utils.SESSION_ID_KEY


class FragmentUsuario : Fragment(), ProfesoresContract.View {
    private lateinit var cursosList: Array<String>
    private lateinit var profesList: Array<String>
    private lateinit var comboList: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_usuario, container, false)
        val logOutButton = view.findViewById<Button>(R.id.log_out_button)

        val recyclerView = view.findViewById<RecyclerView>(R.id.us_rv)
        val names = arrayListOf<HashMap<String, String>>()
        names.add(HashMap())
        names[0].put("name", "Me gustó mucho su clase porque el coral blanco o el ambiente que estamos manejando, lo estamos contaminando de una manera inigmi, i, im, inimaginablemente, inig, inigmante. Esto quiere decir que el coral blanco se está maltratando, eh, pues esas algas rojas. Eh, las algas verde puede servir como cobustible ")
        names[0].put("lastName", "")
        names.add(HashMap())
        names[1].put("name", "Mi profe y clase favorita los tkm mil")
        names[1].put("lastName", "")
        names.add(HashMap())
        names[2].put("name", "Excelente profesor, me gustarían más profes así en el ITESO")
        names[2].put("lastName", "")
        names.add(HashMap())
        names[3].put("name", "Es una materia muy pesada porque el profesor no ayuda porque no me deja entrar a clase con mi botella de vodka")
        names[3].put("lastName", "")
        names.add(HashMap())
        names[4].put("name", "Es el profe más cool de todo el ITESO. Me gustó muchísimo la parte en la que nos daba clase libre - 10/10")
        names[4].put("lastName", "")
        names.add(HashMap())
        names[5].put("name", "Más clases en el HUECO PLISSSSS ")
        names[5].put("lastName", "")

        logOutButton.setOnClickListener {

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context)
            val editor = sharedPreferences.edit()
            editor.putString(SESSION_ID_KEY, "")
            editor.apply()

            val intent = Intent(this.context, ActivityLogin::class.java)
            startActivity(intent)
        }

        recyclerView.adapter = AdapterComentario(names)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        val query = ParseQuery<ParseObject>("Profesores")
        val queryC = ParseQuery<ParseObject>("Cursos")

        val textView = view.findViewById<AutoCompleteTextView>(R.id.us_searchEdit)
                as AutoCompleteTextView//id del textview en layout

        queryC.findInBackground { profes, e ->
            if (e == null) {
                profesList = Array(profes.size) { "" }
                for (i in 0..profes.size - 1) {
                    profesList[i] = (profes[i]["name"].toString())
                }

            }
        }

        query.findInBackground { cursos, e ->
            if (e == null) {
                cursosList = Array(cursos.size) { "" }
                for (i in 0..cursos.size - 1) {
                    cursosList[i] = (cursos[i]["name"].toString())
                }

                comboList = cursosList + profesList //para mostrar ambos profes y cursos en la misma lista de autocomplete

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

        return view
    }
}