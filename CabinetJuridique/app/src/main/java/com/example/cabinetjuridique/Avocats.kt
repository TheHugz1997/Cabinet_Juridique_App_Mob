package com.example.cabinetjuridique

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cabinetjuridique.databinding.ActivityAvocatsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Avocats : AppCompatActivity() {

    private lateinit var binding: ActivityAvocatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvocatsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getAvocats()
    }

    private fun getAvocats() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getAvocats()

        retrofitData.enqueue(object : Callback<List<AvocatsItem>?> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<List<AvocatsItem>?>, response: Response<List<AvocatsItem>?>) {
                val responseAvocats = response.body()!!

                for (avocatsItem in responseAvocats) {
                    val button = Button(binding.buttonContainerAvocats.context)
                    button.text = avocatsItem.nom_avocat
                    button.setOnClickListener {
                        val avocat = avocatsItem

                        val dialogView = layoutInflater.inflate(R.layout.dialog_avocat_details, null)
                        val alertDialog = AlertDialog.Builder(binding.buttonContainerAvocats.context)
                            .setView(dialogView)
                            .create()
                        val textViewNom = dialogView.findViewById<TextView>(R.id.textViewNom)
                        val textViewCoodonnees = dialogView.findViewById<TextView>(R.id.textViewCoodonnees)
                        val textViewHonoraires = dialogView.findViewById<TextView>(R.id.textViewHonoraires)
                        val imageViewAvocat = dialogView.findViewById<ImageView>(R.id.imageViewAvocat)
                        val domainesViewAvocat = dialogView.findViewById<TextView>(R.id.textViewDomaines)
                        val buttonClose = dialogView.findViewById<Button>(R.id.buttonClose)
                        var listeDomaines = ""

                        if(avocat.Domaines.isNotEmpty()) {
                            for(domaine in avocat.Domaines){
                                listeDomaines += "\n${domaine.nom_domaine}"
                            }
                        } else {
                            listeDomaines = ""
                        }

                        textViewNom.text = avocat.nom_avocat
                        textViewCoodonnees.text = """Tel : ${avocat.coordonnees}"""
                        textViewHonoraires.text = """Honoraires : ${avocat.honoraires.toString()} euros par consultation"""
                        domainesViewAvocat.text = """Domaines juridiques : ${listeDomaines}"""

                        // Load and display the Avocat's image using a URL
                        Glide.with(binding.buttonContainerAvocats.context)
                            .load(avocat.photo)
                            .into(imageViewAvocat)

                        buttonClose.setOnClickListener {
                            alertDialog.dismiss() // Dismiss the dialog
                        }

                        val prefs = binding.buttonContainerAvocats.context.getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
                        val isUserConnected = prefs.getString("currentUser", null)

                        // Conditionally add the "Rendez-vous" button if the user is authenticated
                        if (isUserConnected.isNullOrEmpty())  {
                            Log.d("AvocatsActivity", "User is not connected.")
                        } else {
                            val buttonRendezVous = dialogView.findViewById<Button>(R.id.buttonRendezVous)
                            buttonRendezVous.visibility = View.VISIBLE

                            buttonRendezVous.setOnClickListener {
                                val intent = Intent(binding.buttonContainerAvocats.context, rendez_vous::class.java)
                                startActivity(intent)
                            }
                        }

                        alertDialog.show()
                    }
                    binding.buttonContainerAvocats.addView(button) // Use binding to access the layout element
                }
            }

            override fun onFailure(call: Call<List<AvocatsItem>?>, t: Throwable) {
                Log.d("Avocats", "onFailure: "+t.message)
            }
        })

    }
}