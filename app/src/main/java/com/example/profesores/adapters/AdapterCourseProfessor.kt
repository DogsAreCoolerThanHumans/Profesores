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

    private var favListener: makeFavListener? = null

    fun setListener(l: OnItemClickListener?) {
        listener = l
    }

    fun setFavListener(fL: makeFavListener?){
        favListener = fL;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseProfessorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CourseProfessorViewHolder(view, listener, favListener)
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
            listProf.whereEqualTo("name", user["name"])
            listProf.getFirstInBackground { prof, e ->
                if(e == null)
                    favoriteButton.setImageResource(R.drawable.cards_heart)
                else if(e != null)
                    favoriteButton.setImageResource(R.drawable.heart_outline)
            }
        }

        constructor(itemView: View, listener: OnItemClickListener?, favListener: makeFavListener?): this(itemView) {
            nameTitle.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
            favoriteButton.setOnClickListener{
                favListener?.favItemClick(adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface makeFavListener {
        fun favItemClick(position: Int)
    }
}

