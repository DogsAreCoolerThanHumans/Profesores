package com.example.profesores.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R

class AdapterComentario (private val names: ArrayList<HashMap<String, String>>): RecyclerView.Adapter<ComentarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comentario_card, parent, false)
        return ComentarioViewHolder(view)
    }

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        holder.bind(names[position])
    }
}

class ComentarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val nameTitle: TextView = view.findViewById(R.id.comentario)

    fun bind(user: HashMap<String, String>) {
        nameTitle.text = user.get("name") + " " + user.get("lastName")
    }
}