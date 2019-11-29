package com.example.profesores.Fragments

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profesores.Fragments.profesores.ProfesoresContract
import com.example.profesores.R
import com.example.profesores.activities.ActivityLogin
import com.example.profesores.activities.ActivityMain
import com.example.profesores.adapters.AdapterComentario
import com.example.profesores.adapters.AdapterHistorial
import com.parse.ParseObject
import com.parse.ParseQuery
import com.example.profesores.utils.SESSION_ID_KEY
import com.parse.Parse
import com.parse.ParseUser


class FragmentUsuario : Fragment(), ProfesoresContract.View {
    private lateinit var adapter: AdapterHistorial

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_usuario, container, false)
        val logOutButton = view.findViewById<Button>(R.id.log_out_button)
        val userName = view.findViewById<TextView>(R.id.us_tv_curso)
        val commentCount = view.findViewById<TextView>(R.id.us_comments_count)

        val recyclerView = view.findViewById<RecyclerView>(R.id.us_rv)


        var comments = 0
        val usuarioHistorial = ParseQuery<ParseObject>("Comments")
        usuarioHistorial.findInBackground { comentariosUser, e ->
            if(e == null){
                var listComments = mutableListOf<ParseObject>()
                for(i in 0..comentariosUser.size - 1){
                    if((comentariosUser[i]["userComm"] as ParseObject).objectId == ParseUser.getCurrentUser().objectId){
                        listComments.add(comentariosUser[i])
                        comments++
                    }
                }

                commentCount.setText(comments.toString())
                adapter = AdapterHistorial(listComments)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(view.context)
            }
        }
        logOutButton.setOnClickListener {

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context)
            val editor = sharedPreferences.edit()
            editor.putString(SESSION_ID_KEY, "")
            editor.apply()

            val intent = Intent(this.context, ActivityLogin::class.java)
            startActivity(intent)
        }

        userName.setText(ParseUser.getCurrentUser()["username"].toString())

        return view
    }
}