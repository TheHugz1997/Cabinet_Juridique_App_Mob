package com.example.cabinetjuridique

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.cabinetjuridique.databinding.ActivityDomainesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class Domaines : AppCompatActivity() {

    private lateinit var binding: ActivityDomainesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDomainesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getDomaines()
    }

    private fun getDomaines() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getDomaines()

        retrofitData.enqueue(object : Callback<List<DomainesItem>?> {
            override fun onResponse(call: Call<List<DomainesItem>?>, response: Response<List<DomainesItem>?>) {

                val responseDomaines = response.body()!!

                for (domaineItem in responseDomaines) {
                    val button = Button(binding.buttonContainer.context)
                    button.text = domaineItem.nom_domaine
                    button.setOnClickListener {
                        val alertDialog = AlertDialog.Builder(binding.buttonContainer.context)
                            .setTitle(domaineItem.nom_domaine)
                            .setMessage(domaineItem.description)
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()

                        alertDialog.show()
                    }
                    binding.buttonContainer.addView(button) // Use binding to access the layout element
                }

            }

            override fun onFailure(call: Call<List<DomainesItem>?>, t: Throwable) {
                Log.d("Domaines", "onFailure: "+t.message)
            }

        })

    }

}