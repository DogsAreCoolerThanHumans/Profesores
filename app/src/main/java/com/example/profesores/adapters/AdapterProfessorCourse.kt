package com.example.profesores.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.parse.ParseObject
import com.parse.ParseRelation
import com.parse.ParseUser

class AdapterProfessorCourse (val names: List<ParseObject>)
    : RecyclerView.Adapter<AdapterProfessorCourse.ProfessorCourseViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun setListener(l: OnItemClickListener?) {
        listener = l
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessorCourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ProfessorCourseViewHolder(view, listener)
    }

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(holder: ProfessorCourseViewHolder, position: Int) {
        holder.bind(names[position])
    }

    class ProfessorCourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTitle: TextView = view.findViewById(R.id.item_card_name)
        private val cursoIcon: ImageView = view.findViewById(R.id.item_card_icon)
        val favoriteButton: ImageView = view.findViewById(R.id.item_card_fav)


        fun bind(user: ParseObject) {
            nameTitle.text = user.get("name").toString()
            cursoIcon.setImageResource(R.drawable.ic_cursos)
            val currentUser = ParseUser.getCurrentUser()
            var listCursos = (currentUser["cursosFav"] as ParseRelation<*>).query
            listCursos.findInBackground { cursoList, e->
                if(e == null){
                    if(cursoList.size != 0) {
                        for (i in 0..cursoList.size - 1) {
                            if (cursoList[i]["name"] == user["name"])
                                favoriteButton.setImageResource(R.drawable.cards_heart)
                        }
                    }
                } else {
                    Log.e("error", "error con funcionalidad de favoritos")
                }
            }
        }

        constructor(itemView: View, listener: AdapterProfessorCourse.OnItemClickListener?): this(itemView) {
            nameTitle.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}


