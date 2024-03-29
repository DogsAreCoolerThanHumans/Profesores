package com.example.profesores.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.parse.ParseObject
import com.parse.ParseRelation
import com.parse.ParseUser
import org.w3c.dom.Text

class AdapterComProfesoresCursos (val names: List<ParseObject>)
    : RecyclerView.Adapter<AdapterComProfesoresCursos.ComProfesorCursoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComProfesorCursoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comentario_card, parent, false)
        return ComProfesorCursoViewHolder(view)
    }

    override fun getItemCount(): Int = names.size


    override fun onBindViewHolder(holder: ComProfesorCursoViewHolder, position: Int) {
        holder.bind(names[position])
    }

    class ComProfesorCursoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val comment: AppCompatTextView = view.findViewById(R.id.comentario)

        fun bind(user: ParseObject) {
            comment.text = user["Comment"].toString()
        }

    }

}

