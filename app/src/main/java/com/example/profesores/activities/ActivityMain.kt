package com.example.profesores.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.profesores.Fragments.FragmentCursos
import com.example.profesores.Fragments.FragmentFavoritos
import com.example.profesores.Fragments.FragmentProfesorCurso
import com.example.profesores.Fragments.FragmentUsuario
import com.example.profesores.Fragments.profesores.FragmentProfesores
import com.example.profesores.R
import com.google.android.material.bottomnavigation.BottomNavigationView




class ActivityMain : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigation = findViewById<BottomNavigationView>(R.id.activity_main_bnv_navigation)
        navigation.setOnNavigationItemSelectedListener(this)
        navigation.getMenu().findItem(R.id.action_profesores).setChecked(true)//default

        val profesores_cursos_layout = findViewById<TextView>(R.id.item_card_name)

        supportFragmentManager //clickeado por default
            .beginTransaction()
            .replace(R.id.activity_main_fl_main_content,
                FragmentProfesores() //profesores
            )
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_profesores -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_fl_main_content,
                    FragmentProfesores()
                )
                .commit()

            R.id.action_cursos -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_fl_main_content, FragmentCursos())
                .commit()

            R.id.action_favoritos -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_fl_main_content, FragmentFavoritos())
                .commit()

            R.id.action_usuario -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_fl_main_content, FragmentUsuario())
                .commit()
        }
        return true
    }

/*    override fun onBackPressed() {

        // super.onBackPressed();
    }*/

    fun openProfesorCurso(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_main_fl_main_content,
                            FragmentProfesorCurso()
            )
            .commit()
    }

    fun openFragment(fragment: Fragment, args: Bundle?) {
        fragment.arguments = args
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_main_fl_main_content,
                     fragment)
            .commit()
    }
}
//