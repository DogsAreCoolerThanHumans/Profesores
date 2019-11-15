package com.example.profesores.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R

class AdapterCourseProfessor (val names: ArrayList<String>)
    : RecyclerView.Adapter<CourseProfessorViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseProfessorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CourseProfessorViewHolder(view)
    }

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(holder: CourseProfessorViewHolder, position: Int) {
        holder.bind(names[position])
    }
}

class CourseProfessorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val nameTitle: TextView = view.findViewById(R.id.item_card_name)
    private val profIcon: ImageView = view.findViewById(R.id.item_card_icon)

    fun bind(user: String) {
        nameTitle.text = user.toString()
        profIcon.setImageResource(R.drawable.ic_profesores)
    }
}