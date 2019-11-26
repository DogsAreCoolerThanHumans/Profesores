package com.example.profesores.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.parse.ParseObject
import com.parse.ParseRelation
import com.parse.ParseUser

class AdapterProfesor (var names: List<ParseObject>)
    : RecyclerView.Adapter<AdapterProfesor.ProfesorViewHolder>(),
    Filterable { //filterable agregada

    //Para Filters!
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(filter: CharSequence?): FilterResults {
                Log.v("Filter", filter.toString())
                val f = FilterResults()
                f.values = ArrayList<ParseObject>() /////////////// hacer un filter que devuelva sólo los names que cumplan con tal parámetro
                return f
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                names = p1?.values as List<ParseObject>
                notifyDataSetChanged()
            }

        }
    }
    //

    private var listener: OnItemClickListener? = null
    private var favListener: makeFavListener? = null

    fun setListener(l: OnItemClickListener?) {
        listener = l
    }

    fun setFavListener(fL: makeFavListener?){
        favListener = fL;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ProfesorViewHolder(view, listener, favListener)
    }

    override fun getItemCount(): Int = names.size


    override fun onBindViewHolder(holder: ProfesorViewHolder, position: Int) {
        holder.bind(names[position])
    }

    class ProfesorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTitle: TextView = view.findViewById(R.id.item_card_name)
        val favoriteButton: ImageView = view.findViewById(R.id.item_card_fav)

        @SuppressLint("SetTextI18n")
        fun bind(user: ParseObject) {
            nameTitle.text = user.get("name").toString()
            val currentUser = ParseUser.getCurrentUser()
            var listProf = (currentUser["profesoresFav"] as ParseRelation<*>).query
            listProf.whereEqualTo("name", user["name"])
            listProf.getFirstInBackground { prof, e ->
                if(e == null)
                    favoriteButton.setImageResource(R.drawable.cards_heart)
                else
                    favoriteButton.setImageResource(R.drawable.heart_outline)
            }
        }

        constructor(itemView: View, listener: OnItemClickListener?, favListener: makeFavListener?)
                : this(itemView) {
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

