package com.example.profesores.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.parse.ParseObject
import com.parse.ParseQuery

class AdapterHistorial(private val names: List<ParseObject>): RecyclerView.Adapter<HistorialViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.historial_card, parent, false)
            return HistorialViewHolder(view)
        }

        override fun getItemCount(): Int = names.size

        override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
            holder.bind(names[position])
        }
    }

    class HistorialViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val comment: TextView = view.findViewById(R.id.historial_card_comentario)
        private val profeTitle: TextView = view.findViewById(R.id.historial_card_profesor)
        private val cursoTitle: TextView = view.findViewById(R.id.historial_card_curso)
        private val queryProf = ParseQuery<ParseObject>("Profesores")
        private val queryCurso = ParseQuery<ParseObject>("Cursos")

        fun bind(user: ParseObject) {
            queryProf.whereEqualTo("objectId", (user["profesorComm"] as ParseObject).objectId)
            queryCurso.whereEqualTo("objectId", (user["cursoComm"] as ParseObject).objectId)
            profeTitle.text = queryProf.first["name"].toString()
            cursoTitle.text = queryCurso.first["name"].toString()
            comment.text = user["Comment"].toString()
        }
    }