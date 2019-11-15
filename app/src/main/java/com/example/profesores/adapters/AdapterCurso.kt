package com.example.profesores.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.R
import com.parse.ParseObject

class AdapterCurso (val names: List<ParseObject>):
    RecyclerView.Adapter<AdapterCurso.CursoViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun setListener(l: OnItemClickListener?) {
        listener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CursoViewHolder(view, listener)
    }

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        holder.bind(names[position])
    }

    class CursoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTitle: TextView = view.findViewById(R.id.item_card_name)

        fun bind(user: ParseObject) {
            nameTitle.text = user.get("name").toString()
        }

        constructor(itemView: View, listener: AdapterCurso.OnItemClickListener?): this(itemView) {
            nameTitle.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
