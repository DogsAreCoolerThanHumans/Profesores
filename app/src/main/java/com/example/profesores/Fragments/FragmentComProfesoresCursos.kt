package com.example.profesores.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.example.profesores.adapters.AdapterComProfesoresCursos
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser


class FragmentComProfesoresCursos : Fragment() {
    private lateinit var adapter: AdapterComProfesoresCursos

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_com_profesores_cursos, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.fragment_com_cursos_profesores_rv)
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

        cursoName.text = queryProf.whereEqualTo("objectId", arguments?.getString
            ("cursoId"))["name"].toString()
        profName.text = queryCurso.whereEqualTo("objectId", arguments?.getString
            ("profesorId"))["name"].toString()

        val query = ParseQuery<ParseObject>("Comments")
        query.include("cursoComm")
        query.include("profesorComm")
        query.include("userComm")

        query.whereEqualTo("profesorComm", arguments?.getString("profesorId"))
        query.whereEqualTo("cursoComm", arguments?.getString("cursoId"))
        query.findInBackground { commList, e->

            if(e == null){
                adapter = AdapterComProfesoresCursos(commList)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this.context)
                for(i in 0..commList.size - 1){
                    totalLikes += Integer.parseInt(commList[i]["Likes"].toString())
                    totalDislikes += Integer.parseInt(commList[i]["Dislikes"].toString())
                }
                totalComments = commList.size
                likesCount.text = totalLikes.toString()
                dislikesCount.text = totalDislikes.toString()
                commentCount.text = totalComments.toString()
            }
            else {
                Log.e("ERROR_COMMENT", "COMMENTS NOT FOUND")
            }

        }
        return view
    }

}