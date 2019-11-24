package com.example.profesores.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.Fragments.profesores.ProfesoresContract
import com.example.profesores.R
import com.example.profesores.activities.ActivityMain
import com.example.profesores.adapters.AdapterComentario
import com.example.profesores.adapters.AdapterCurso
import com.example.profesores.adapters.AdapterProfesor
import kotlinx.android.synthetic.main.fragment_com_cursos_profesores.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity
import android.widget.AdapterView

//import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast



class FragmentReview : Fragment(), ProfesoresContract.View, AdapterProfesor.OnItemClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_review, container, false)
        val title = view.findViewById<TextView>(R.id.fragment_review_tv_title) //profesores

        return view

        val colors = arrayOf(
            "Red","Green","Blue","Maroon","Magenta",
            "Gold","GreenYellow"
        )

        //para autocomplete:
        val textView = view.findViewById<AutoCompleteTextView>(R.id.re_input_profesor) as AutoCompleteTextView//id del textview en layout
        // Get the string array
        val countries = resources.getStringArray(R.array.countries_array)//id del array en strings.xml
        // Create the adapter and set it to the AutoCompleteTextView
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_dropdown_item_1line, colors) //simple_list_item_1
        textView.setAdapter(adapter)


        textView.threshold = 1 //número de caracteres escritos para mostrar sugerencias

        // Set a focus change listener for auto complete text view
        textView.onFocusChangeListener = View.OnFocusChangeListener{
                view, b ->
            if(b){
                // Display the suggestion dropdown on focus
                textView.showDropDown()
            }
        }
        //

    }

    //funct para tomar valor del radio btn del rating y pasar a variable numérica
    //va en el adapter o aquí???
    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.r1 ->
                    if (checked) {
                        //val rating = 1
                        //etc...
                    }
                R.id.r2 ->
                    if (checked) {
                        //val rating = 2
                    }
                R.id.r3 ->
                    if (checked) {
                        //val rating = 3
                    }
                R.id.r4 ->
                    if (checked) {
                        //val rating = 4
                    }
                R.id.r5 ->
                    if (checked) {
                        //val rating = 5
                    }
            }
        }
    }

    override fun onItemClick(position: Int) {

        //poner funcionalidad/switchCase de radio btns aquí?
        val rating = 1;



    }


}