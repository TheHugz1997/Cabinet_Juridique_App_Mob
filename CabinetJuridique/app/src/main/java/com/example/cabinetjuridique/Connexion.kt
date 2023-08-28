package com.example.cabinetjuridique

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Connexion : AppCompatActivity() {

    private lateinit var editTextMail: EditText
    private lateinit var editTextMotDePasse: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        editTextMail = findViewById(R.id.editTextMail)
        editTextMotDePasse = findViewById(R.id.editTextMotDePasse)
        button = findViewById(R.id.button)

        button.setOnClickListener {
            onConnexionButtonClick()
        }
    }

    private fun onConnexionButtonClick() {
        val mail = editTextMail.text.toString()
        val motDePasse = editTextMotDePasse.text.toString()

        val userJson = Utilisateur(mail, motDePasse)

        apiClient.postConnexion(userJson)

        if (apiClient.isUserConnected()) {
            // User is connected, print relevant information
            Log.d("Connexion", "User is connected.")
            val prefs = getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
            val currentUser = prefs.getString("currentUser", "Unknown")
            val expiresAt = prefs.getLong("expires_at", 0)
            val isAdmin = prefs.getBoolean("is_admin", false)

            Log.d("Connexion", "Current User: $currentUser")
            Log.d("Connexion", "Expires At: $expiresAt")
            Log.d("Connexion", "Is Admin: $isAdmin")

            val intent = Intent(this@Connexion, MainActivity::class.java)
            startActivity(intent)
        } else {
            // User is not connected, handle accordingly
            Log.d("Connexion", "User is not connected.")
        }


    }

    private val apiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiInterface::class.java)

    private val apiClient = ApiUtilisateur(apiService, this)

}