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

class AdapterFavoritos(
    var recipes: List<ParseObject>,
    var isProf: Int
) :
    RecyclerView.Adapter<AdapterFavoritos.FavoritesViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun setListener(l: OnItemClickListener?) {
        listener = l
    }

    private var favListener: makeFavListener? = null


    fun setFavListener(fL: makeFavListener?) {
        favListener = fL;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return FavoritesViewHolder(view, listener, isProf)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTitle: TextView = view.findViewById(R.id.item_card_name)
        private val favoriteButton: ImageView = view.findViewById(R.id.item_card_fav)
        private var isProfe: Int = 0

        private val favIcon: ImageView = view.findViewById(R.id.item_card_icon)//para ícono global (ambos RVs)


        fun bind(fav: ParseObject) {
            nameTitle.text = fav.get("name").toString()
            val currentUser = ParseUser.getCurrentUser()
            favIcon.setImageResource(R.drawable.ic_favoritos)// no supe separarlo por ícono de prof/curs, puse uno global a ambos RV


            var list = (currentUser["profesoresFav"] as ParseRelation<*>).query
            list.findInBackground { profList, e ->
                if (e == null) {
                    if (profList.size != 0) {
                        for (i in 0..profList.size - 1) {
                            if (profList[i]["name"] == fav["name"])
                                favoriteButton.setImageResource(R.drawable.cards_heart)
                        }
                    }
                } else {
                    Log.e("error", "error con funcionalidad de favoritos")
                }
            }
            list = (currentUser["cursosFav"] as ParseRelation<*>).query
            list.findInBackground { cursoList, e ->
                if (e == null) {
                    if (cursoList.size != 0) {
                        for (i in 0..cursoList.size - 1) {
                            if (cursoList[i]["name"] == fav["name"])
                                favoriteButton.setImageResource(R.drawable.cards_heart)
                        }
                    }
                } else {
                    Log.e("error", "error con funcionalidad de favoritos")
                }
            }
        }

        constructor(itemView: View, listener: OnItemClickListener?, isProf: Int)
                : this(itemView) {
            nameTitle.setOnClickListener {
                listener?.onItemClick(adapterPosition, isProf)
            }
            this.isProfe = isProfe
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, isProf: Int)
    }

    interface makeFavListener {
        fun favItemClick(position: Int, isProf: Int)
    }
}


