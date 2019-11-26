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
        recyclerView.adapter = AdapterComentario(names)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }
}