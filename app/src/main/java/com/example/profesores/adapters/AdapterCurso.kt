package com.example.profesores.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R

class AdapterCurso (private val names: ArrayList<HashMap<String, String>>): RecyclerView.Adapter<CursoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CursoViewHolder(view)
    }

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        holder.bind(names[position])
    }
}

class CursoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val nameTitle: TextView = view.findViewById(R.id.item_card_name)

    fun bind(user: HashMap<String, String>) {
        nameTitle.text = user.get("name") + " " + user.get("lastName")
    }
}