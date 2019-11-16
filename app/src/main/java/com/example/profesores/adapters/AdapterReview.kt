package com.example.profesores.adapters

class AdapterReview {
    private var listener: AdapterProfesor.OnItemClickListener? = null

    fun setListener(l: AdapterProfesor.OnItemClickListener?) {
        listener = l
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}