package com.example.cabinetjuridique

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Enregistrement : AppCompatActivity() {

    private lateinit var editTextNom: EditText
    private lateinit var editTextMail: EditText
    private lateinit var editTextMotDePasse: EditText
    private lateinit var editTextTelephone: EditText
    private lateinit var enregistrementButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enregistrement)

        editTextNom = findViewById(R.id.editTextNom)
        editTextMail = findViewById(R.id.editTextMail)
        editTextMotDePasse = findViewById(R.id.editTextMotDePasse)
        editTextTelephone = findViewById(R.id.editTextTelephone)
        enregistrementButton = findViewById(R.id.enregistrementButton)

        enregistrementButton.setOnClickListener {
            val nom_utilisateur = editTextNom.text.toString()
            val mail_utilisateur = editTextMail.text.toString()
            val mot_de_passe = editTextMotDePasse.text.toString()
            val telephone = editTextTelephone.text.toString()

            val utilisateurEnregistrement = UtilisateurEnregistrement(mail_utilisateur, mot_de_passe, nom_utilisateur, telephone)

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiInterface = retrofit.create(ApiInterface::class.java)
            val call = apiInterface.postInscription(utilisateurEnregistrement)

            call.enqueue(object : Callback<UtilisateurEnregistrement> {

                override fun onResponse(call: Call<UtilisateurEnregistrement>, response: Response<UtilisateurEnregistrement>) {
                    Log.d("Connexion", "User is register.")
                    val intent = Intent(this@Enregistrement, Connexion::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onFailure(call: Call<UtilisateurEnregistrement>, t: Throwable) {
                    Log.d("Connexion", "User is not register.")
                }
            })
        }
    }

}