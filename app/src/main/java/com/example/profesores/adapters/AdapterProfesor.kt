package com.example.profesores.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.example.profesores.activities.ActivityProfessorCourse

class AdapterProfesor (private val names: ArrayList<HashMap<String, String>>)
    : RecyclerView.Adapter<ProfesorViewHolder>() {

    lateinit var view: View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        this.view = view
        return ProfesorViewHolder(view)
    }

    override fun getItemCount(): Int = names.size


    override fun onBindViewHolder(holder: ProfesorViewHolder, position: Int) {
        holder.bind(names[position], view)
    }
}

class ProfesorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val nameTitle: TextView = view.findViewById(R.id.item_card_name)

    fun bind(user: HashMap<String, String>, view: View) {
        nameTitle.text = user.get("name") + " " + user.get("lastName")

        nameTitle.setOnClickListener {
            val intent = Intent(view.context, ActivityProfessorCourse::class.java)
            view.context.startActivity(intent)
        }
    }
}