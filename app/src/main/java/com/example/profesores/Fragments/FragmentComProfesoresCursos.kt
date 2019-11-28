package com.example.profesores.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.example.profesores.adapters.AdapterComProfesoresCursos
import com.parse.ParseObject
import com.parse.ParseQuery




class FragmentComProfesoresCursos : Fragment() {
    private lateinit var adapter: AdapterComProfesoresCursos
    private lateinit var cursosList: Array<String>
    private lateinit var profesList: Array<String>
    private lateinit var comboList: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_com_profesores_cursos, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.fragment_com_pr_cr_rv)
        var totalLikes = 0
        var totalDislikes = 0
        var totalComments: Int
        val cursoName = view.findViewById<TextView>(R.id.com_cr_pr_tv_curso)
        val profName = view.findViewById<TextView>(R.id.com_pr_cr_tv_profesor)
        val likesCount = view.findViewById<TextView>(R.id.com_pr_cr_likes_count)
        val dislikesCount = view.findViewById<TextView>(R.id.com_pr_cr_dislikes_count)
        val commentCount = view.findViewById<TextView>(R.id.com_pr_cr_comments_count)

        val queryProf = ParseQuery<ParseObject>("Profesores")
        val queryCurso = ParseQuery<ParseObject>("Cursos")
        val profComm = arguments?.getString("profesor")
        val cursoComm = arguments?.getString("curso")

        queryProf.whereEqualTo("objectId", profComm)
            .getFirstInBackground { prof, e ->
                if(e == null)
                    profName.text = prof["name"].toString()
            }

        queryCurso.whereEqualTo("objectId", cursoComm).getFirstInBackground{
            curso, e->
            if(e == null)
                cursoName.text = curso["name"].toString()
        }

        val queryProfCom = ParseQuery<ParseObject>("Comments")
        val queryCursoCom = ParseQuery<ParseObject>("Comments")

        queryProfCom.whereMatches("profesorComm", profComm)
        queryCursoCom.whereMatches("cursoComm", cursoComm)

        val queries = ArrayList<ParseQuery<ParseObject>>()
        queries.add(queryProfCom)
        queries.add(queryCursoCom)
        val query = ParseQuery.or(queries)
        query.findInBackground { commList, e->
            if(e == null){
                adapter = AdapterComProfesoresCursos(commList)
                Log.e("LOL", commList[0]["Comment"].toString())
                recyclerView.adapter = adapter
                for(i in 0..commList.size - 1){
                    totalLikes += Integer.parseInt(commList[i]["Likes"].toString())
                    totalDislikes += Integer.parseInt(commList[i]["Dislikes"].toString())
                }
                totalComments = commList.size
                likesCount.text = totalLikes.toString()
                dislikesCount.text = totalDislikes.toString()
                commentCount.text = totalComments.toString()
                recyclerView.layoutManager = LinearLayoutManager(this.context)
            }
            else {
                Log.e("ERROR_COMMENT", "COMMENTS NOT FOUND")
            }
        }

        val queryP = ParseQuery<ParseObject>("Profesores")
        val queryC = ParseQuery<ParseObject>("Cursos")

        val textView = view.findViewById<AutoCompleteTextView>(R.id.com_pr_cr_searchEdit)
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