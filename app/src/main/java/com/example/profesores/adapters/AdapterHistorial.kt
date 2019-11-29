package com.example.profesores.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R

class AdapterHistorial(private val names: ArrayList<HashMap<String, String>>): RecyclerView.Adapter<HistorialViewHolder>() {

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
        private val nameTitle: TextView = view.findViewById(R.id.historial_card_comentario)
        private val profeTitle: TextView = view.findViewById(R.id.historial_card_profesor)
        private val cursoTitle: TextView = view.findViewById(R.id.historial_card_profesor)

        fun bind(user: HashMap<String, String>) {
            nameTitle.text = user.get("name") + " " + user.get("lastName")
            //profeTitle.text = user.get("")
            //cursoTitle.text = user.get("")
        }
    }