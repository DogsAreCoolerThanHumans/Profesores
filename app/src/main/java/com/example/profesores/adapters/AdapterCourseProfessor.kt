package com.example.profesores.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.parse.ParseObject
import com.parse.ParseRelation
import com.parse.ParseUser

class AdapterCourseProfessor (val names: List<ParseObject>)
    : RecyclerView.Adapter<AdapterCourseProfessor.CourseProfessorViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun setListener(l: OnItemClickListener?) {
        listener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseProfessorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CourseProfessorViewHolder(view, listener)
    }

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(holder: CourseProfessorViewHolder, position: Int) {
        holder.bind(names[position])
    }

    class CourseProfessorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTitle: TextView = view.findViewById(R.id.item_card_name)
        private val profIcon: ImageView = view.findViewById(R.id.item_card_icon)
        val favoriteButton: ImageView = view.findViewById(R.id.item_card_fav)


        fun bind(user: ParseObject) {
            nameTitle.text = user.get("name").toString()
            profIcon.setImageResource(R.drawable.ic_profesores)

            val currentUser = ParseUser.getCurrentUser()
            var listProf = (currentUser["profesoresFav"] as ParseRelation<*>).query
            listProf.findInBackground { profList, e->
                if(e == null){
                    if(profList.size != 0) {
                        for (i in 0..profList.size - 1) {
                            if (profList[i]["name"] == user["name"])
                                favoriteButton.setImageResource(R.drawable.cards_heart)
                        }
                    }
                } else {
                    Log.e("error", "error con funcionalidad de favoritos")
                }
            }
        }

        constructor(itemView: View, listener: OnItemClickListener?): this(itemView) {
            nameTitle.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}

