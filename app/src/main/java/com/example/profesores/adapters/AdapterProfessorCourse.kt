package com.example.profesores.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R

class AdapterProfessorCourse (val names: ArrayList<String>)
    : RecyclerView.Adapter<AdapterProfessorCourse.ProfessorCourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessorCourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ProfessorCourseViewHolder(view)
    }

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(holder: ProfessorCourseViewHolder, position: Int) {
        holder.bind(names[position])
    }

    class ProfessorCourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTitle: TextView = view.findViewById(R.id.item_card_name)

        fun bind(user: String) {
            nameTitle.text = user.toString()
        }

    }

}


