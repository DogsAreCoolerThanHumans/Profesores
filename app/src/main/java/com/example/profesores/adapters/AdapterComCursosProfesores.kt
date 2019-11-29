package com.example.profesores.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.parse.ParseObject


class AdapterComCursosProfesores (val names: List<ParseObject>)
    : RecyclerView.Adapter<AdapterComCursosProfesores.ComCursoProfesorViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComCursoProfesorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comentario_card, parent, false)
        return ComCursoProfesorViewHolder(view)
    }

    override fun getItemCount(): Int = names.size


    override fun onBindViewHolder(holder: ComCursoProfesorViewHolder, position: Int) {
        holder.bind(names[position])
    }

    class ComCursoProfesorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val comment: AppCompatTextView = view.findViewById(R.id.comentario)
        fun bind(user: ParseObject) {
            comment.text = user["Comment"].toString()
        }

    }

}

