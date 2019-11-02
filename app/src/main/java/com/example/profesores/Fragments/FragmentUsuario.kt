package com.example.profesores.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.Fragments.profesores.ProfesoresContract
import com.example.profesores.R
import com.example.profesores.adapters.AdapterComentario
import com.example.profesores.adapters.AdapterCurso
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity

class FragmentUsuario : Fragment(), ProfesoresContract.View {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_usuario, container, false)
        val title = view.findViewById<TextView>(R.id.fragment_usuario_tv_title) //profesores

        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_name_usuario_rv)
        val names = arrayListOf<HashMap<String, String>>()
        names.add(HashMap())
        names[0].put("name", "Me gustó mucho su clase porque el coral blanco o el ambiente que estamos manejando, lo estamos contaminando de una manera inigmi, i, im, inimaginablemente, inig, inigmante. Esto quiere decir que el coral blanco se está maltratando, eh, pues esas algas rojas. Eh, las algas verde puede servir como cobustible ")
        names[0].put("lastName", "")
        names.add(HashMap())
        names[1].put("name", "Buen profe")
        names[1].put("lastName", "")
        names.add(HashMap())
        names[2].put("name", "Está bien papi hmmm")
        names[2].put("lastName", "")
        names.add(HashMap())
        names[3].put("name", "MELAPELAN TODOS")
        names[3].put("lastName", "")
        names.add(HashMap())
        names[4].put("name", "Súper barco alv")
        names[4].put("lastName", "")
        recyclerView.adapter = AdapterComentario(names)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }
}