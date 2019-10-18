package com.example.profesores.parse

import android.app.Application
import com.parse.Parse

class ProfesoresApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this).applicationId("L3ZpvlZGCpANCVknBR5puLzIieXBAm2II9BirwPQ")
                .clientKey("CcMog2YKtOcD7PPxJXbigz9u4NMbSE35dpDaCtK9")
                .server("https://parseapi.back4app.com/")
                .build()
        )
    }
} 