package com.example.profesores.Fragments


import android.os.Bundle
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
import com.example.profesores.adapters.AdapterComCursosProfesores
import com.parse.ParseObject
import com.parse.ParseQuery


class FragmentComCursosProfesores : Fragment() {
    private lateinit var adapter: AdapterComCursosProfesores
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_com_cursos_profesores, container, false)

        val recyclerView =
            view.findViewById<RecyclerView>(R.id.fragment_com_cursos_profesores_rv)
        var totalLikes = 0
        var totalDislikes = 0
        var totalComments = 0
        var cursosList = mutableListOf<String>()
        var profesList = mutableListOf<String>()
        val cursoName = view.findViewById<TextView>(R.id.com_cr_pr_tv_curso)
        val profName = view.findViewById<TextView>(R.id.com_cr_pr_profesor)
        val commentCount = view.findViewById<TextView>(R.id.com_pr_cr_comments_count)

        val queryProf = ParseQuery<ParseObject>("Profesores")
        val queryCurso = ParseQuery<ParseObject>("Cursos")
        val profComm = arguments?.getString("profesor")
        val cursoComm = arguments?.getString("curso")

        val query = ParseQuery<ParseObject>("Comments")
        queryProf.whereEqualTo("objectId", profComm)
            .getFirstInBackground { prof, e ->
                if (e == null) {
                    query.whereEqualTo("profesorComm", prof)
                        .findInBackground { comWithProfs, err ->
                            if (err == null) {
                                var listComments = mutableListOf<ParseObject>()
                                for (i in 0..comWithProfs.size - 1) {
                                    val stringCursoId =
                                        (comWithProfs[i]["cursoComm"] as ParseObject).objectId
                                    if (stringCursoId == cursoComm) {
                                        totalLikes += Integer.parseInt(comWithProfs[i]["Likes"].toString())
                                        totalDislikes += Integer.parseInt(comWithProfs[i]["Dislikes"].toString())
                                        totalComments++
                                        listComments.add(comWithProfs[i])
                                    }
                                }
                                commentCount.text = totalComments.toString()
                                adapter = AdapterComCursosProfesores(listComments)
                                recyclerView.adapter = adapter
                                recyclerView.layoutManager =
                                    LinearLayoutManager(this.context)
                            } else {

                            }
                        }
                    profName.text = prof["name"].toString()
                }
            }


        queryCurso.whereEqualTo("objectId", cursoComm).getFirstInBackground { curso, e ->
            if (e == null) {
                query.whereEqualTo("cursoComm", curso)
                cursoName.text = curso["name"].toString()
            }
        }

        return view
    }
}