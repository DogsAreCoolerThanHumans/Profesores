package com.example.profesores.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.parse.ParseObject

class AdapterProfesor (val names: List<ParseObject>)
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

        @SuppressLint("SetTextI18n")
        fun bind(user: ParseObject) {
            nameTitle.text = user.get("name").toString()
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

