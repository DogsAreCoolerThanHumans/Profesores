package com.example.profesores.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R

class AdapterProfesor (val names: ArrayList<HashMap<String, String>>)
    : RecyclerView.Adapter<AdapterProfesor.ProfesorViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun setListener(l: OnItemClickListener?) {
        listener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ProfesorViewHolder(view, listener)
    }

    override fun getItemCount(): Int = names.size


    override fun onBindViewHolder(holder: ProfesorViewHolder, position: Int) {
        holder.bind(names[position])
    }

    class ProfesorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTitle: TextView = view.findViewById(R.id.item_card_name)

        fun bind(user: HashMap<String, String>) {
            nameTitle.text = user.get("name") + " " + user.get("lastName")
        }

        constructor(itemView: View, listener: AdapterProfesor.OnItemClickListener?): this(itemView) {
            nameTitle.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}

