package com.example.cabinetjuridique

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class rendez_vous : AppCompatActivity() {

    private lateinit var date: EditText
    private lateinit var heure: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rendez_vous)

        /*

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiInterface::class.java)

        date = findViewById(R.id.editTextDate)
        heure = findViewById(R.id.editTextHeure)

        val rendezVousData = RendezVous(date, id_avocat)

        val call = apiService.postRendezVous(rendezVousData)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle successful response
                } else {
                    // Handle error response
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network failure
            }
        })

         */
    }

}