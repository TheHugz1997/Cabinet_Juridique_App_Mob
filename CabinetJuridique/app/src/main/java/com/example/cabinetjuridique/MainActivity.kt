package com.example.cabinetjuridique

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://pat.infolab.ecam.be:60838/"

class MainActivity : AppCompatActivity() {

    private lateinit var buttonConnexion: Button
    private lateinit var buttonEnregistrer: Button
    private lateinit var buttonDeconnexion: Button

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonConnexion = findViewById(R.id.acceuil_connexion)
        buttonEnregistrer = findViewById(R.id.acceuil_enregistrer)
        buttonDeconnexion = findViewById(R.id.acceuil_deconnexion)

        val prefs = getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
        val isUserConnected = prefs.getString("currentUser", null)
        Log.d("MainActivity", "$isUserConnected")

        if (isUserConnected.isNullOrEmpty()) {
            // User is not connected, handle accordingly
            Log.d("MainActivity", "User is not connected.")
        } else {
            // User is connected, update button visibility
            buttonConnexion.visibility = View.GONE
            buttonEnregistrer.visibility = View.GONE
            buttonDeconnexion.visibility = View.VISIBLE
        }

        buttonDeconnexion.setOnClickListener {
            // Clear user data from shared preferences
            val prefs = getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.clear().apply()

            // Update button visibility
            buttonConnexion.visibility = View.VISIBLE
            buttonEnregistrer.visibility = View.VISIBLE
            buttonDeconnexion.visibility = View.GONE
        }

        val buttonClickDomaines = findViewById<Button>(R.id.acceuil_domaines)
        buttonClickDomaines.setOnClickListener {
            val intent = Intent(this, Domaines::class.java)
            startActivity(intent)
        }

        val buttonClickAvocats = findViewById<Button>(R.id.acceuil_avocats)
        buttonClickAvocats.setOnClickListener {
            val intent = Intent(this, Avocats::class.java)
            startActivity(intent)
        }

        val buttonClickConnexion = findViewById<Button>(R.id.acceuil_connexion)
        buttonClickConnexion.setOnClickListener {
            val intent = Intent(this, Connexion::class.java)
            startActivity(intent)
        }

        val buttonClickEnregistrement = findViewById<Button>(R.id.acceuil_enregistrer)
        buttonClickEnregistrement.setOnClickListener {
            val intent = Intent(this, Enregistrement::class.java)
            startActivity(intent)
        }
    }
}